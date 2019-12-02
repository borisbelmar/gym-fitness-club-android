package com.dobleb.gymfitnessclub;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.dobleb.gymfitnessclub.controller.Validate;
import com.dobleb.gymfitnessclub.ui.DatePickerFragment;
import com.google.android.material.textfield.TextInputLayout;

public class EvaluationRegister extends AppCompatActivity {

    Button saveBtn;
    TextView tvImc;
    TextInputLayout tilDate;
    TextInputLayout tilWeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation_register);

        saveBtn = (Button) findViewById(R.id.saveBtn);
        tvImc = (TextView) findViewById(R.id.tv_imc);
        tilDate = (TextInputLayout) findViewById(R.id.til_date);
        tilWeight = (TextInputLayout) findViewById(R.id.til_weight);

        tilWeight.getEditText().addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                double height = 1.70;
                double weight;
                String strWeight = tilWeight.getEditText().getText().toString();

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
                    Intent intent = new Intent(v.getContext(),EvaluationList.class);
                    startActivity(intent);
                    Toast.makeText(v.getContext(), "Esto debería registrar el IMC en la BBDD", Toast.LENGTH_SHORT).show();
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
                final String selectedDate = year + "/" + (month+1) + "/" + day;
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

        if(Validate.required(weight)) {
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
