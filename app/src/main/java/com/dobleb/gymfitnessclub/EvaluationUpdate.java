package com.dobleb.gymfitnessclub;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.dobleb.gymfitnessclub.controller.Validate;
import com.dobleb.gymfitnessclub.dao.EvaluationDAO;
import com.dobleb.gymfitnessclub.model.Evaluation;
import com.dobleb.gymfitnessclub.ui.DatePickerFragment;
import com.google.android.material.textfield.TextInputLayout;

public class EvaluationUpdate extends AppCompatActivity {

    Button saveBtn;
    TextView tvImc;
    TextView tvHeight;
    TextInputLayout tilDate;
    TextInputLayout tilWeight;
    double height, weight;
    Evaluation evaluation;
    EvaluationDAO dao;
    SharedPreferences preferences;
    String strWeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation_register);

        dao = new EvaluationDAO(this);
        preferences = getSharedPreferences("userData", Context.MODE_PRIVATE);

        Bundle receivedObjects = getIntent().getExtras();
        evaluation = null;

        if(receivedObjects != null) {
            evaluation = (Evaluation) receivedObjects.getSerializable("evaluation");
        } else {
            Intent intent = new Intent(EvaluationUpdate.this, EvaluationList.class);
            startActivity(intent);
            Toast.makeText(EvaluationUpdate.this, "Hubo un problema al recuperar la información de la evaluación", Toast.LENGTH_SHORT).show();
        }

        saveBtn = (Button) findViewById(R.id.saveBtn);
        tvImc = (TextView) findViewById(R.id.tv_imc);
        tvHeight = (TextView) findViewById(R.id.tv_height);
        tilDate = (TextInputLayout) findViewById(R.id.til_date);
        tilWeight = (TextInputLayout) findViewById(R.id.til_weight);
        height = Double.parseDouble(preferences.getString("height","1.0"));

        tvHeight.setText(preferences.getString("height","1.0") + " metros");

        tilDate.getEditText().setText(evaluation.getDate());
        tilWeight.getEditText().setText(Double.toString(evaluation.getWeight()));
        tvImc.setText(String.format("%.1f", evaluation.getImc()));

        try {
            strWeight = tilWeight.getEditText().getText().toString();
            weight = Double.parseDouble(strWeight);
        } catch(Exception e) {
            Log.e("Error", "ASdf");
        }
        tilWeight.getEditText().addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                strWeight = tilWeight.getEditText().getText().toString();
                if(!strWeight.isEmpty()) {
                    weight = Double.parseDouble(strWeight);
                    tvImc.setText(String.format("%.1f", weight / (height * height)));
                    validate();
                } else {
                    weight = 1;
                    tvImc.setText("Ingresa un peso");
                }

            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()) {
                    String date = tilDate.getEditText().getText().toString();
                    evaluation.setDate(date);
                    evaluation.setHeight(height);
                    evaluation.setWeight(weight);
                    Log.i("Debug", Double.toString(evaluation.getImc()));
                    if(dao.update(evaluation)) {
                        Intent intent = new Intent(v.getContext(),EvaluationList.class);
                        startActivity(intent);
                        Toast.makeText(v.getContext(), "Actualizado con éxito", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(v.getContext(), "Error al actualizar evaluación", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(v.getContext(), "Debes llenar todos los datos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tilDate.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(tilDate);
            }
        });
    }

    private void showDatePickerDialog(final TextInputLayout til) {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                final String selectedDate = year + "-" + (month+1) + "-" + (day < 10 ? "0"+day : day);
                til.getEditText().setText(selectedDate);
            }
        });

        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    private boolean validate() {
        String weight = tilWeight.getEditText().getText().toString();
        String date = tilDate.getEditText().getText().toString();

        boolean validWeight;
        boolean validDate;

        if(Validate.required(weight) && Validate.notZero(weight)) {
            tilWeight.setError(null);
            validWeight = true;
        } else {
            tilWeight.setError("Ingresa un peso válido");
            tilWeight.getEditText().setText("");
            validWeight = false;
        }

        if(Validate.date(date)) {
            tilDate.setError(null);
            validDate = true;
        } else {
            tilDate.setError("Fecha inválida");
            tilDate.getEditText().setText("");
            validDate = false;
        }

        return validWeight && validDate;
    }
}
