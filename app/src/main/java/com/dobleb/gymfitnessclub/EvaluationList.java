package com.dobleb.gymfitnessclub;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dobleb.gymfitnessclub.adapter.EvaluationAdapter;
import com.dobleb.gymfitnessclub.dao.EvaluationDAO;
import com.dobleb.gymfitnessclub.model.Evaluation;
import com.dobleb.gymfitnessclub.ui.DatePickerFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class EvaluationList extends AppCompatActivity {

    private TextView logoutLink;
    private FloatingActionButton addBtn;
    private TextInputLayout tilFrom;
    private TextInputLayout tilTo;
    private Button filterBtn;
    private ListView listView;

    private ArrayList<Evaluation> evaluations;
    private EvaluationAdapter evaluationAdapter;
    private EvaluationDAO dao;
    private SharedPreferences preferences;

    private String[] dates;

    private int userId;
    private String firstname;
    private String lastname;
    private String height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation_list);

        logoutLink = (TextView) findViewById(R.id.logoutLink);
        addBtn = (FloatingActionButton) findViewById(R.id.addBtn);
        listView = (ListView) findViewById(R.id.list_view);
        tilFrom = (TextInputLayout) findViewById(R.id.til_from);
        tilTo = (TextInputLayout) findViewById(R.id.til_to);
        filterBtn = (Button) findViewById(R.id.filterBtn);

        preferences = getSharedPreferences("userData", Context.MODE_PRIVATE);
        dao = new EvaluationDAO(this);

        userId = preferences.getInt("id", 0);
        firstname = preferences.getString("firstname", "Firstname");
        lastname = preferences.getString("lastname", "Lastname");
        height = preferences.getString("height", "0");

        Bundle receivedObjects = getIntent().getExtras();
        dates = new String[2];

        if(receivedObjects != null) {
            dates = receivedObjects.getStringArray("dates");
            evaluations = dao.findAllByDates(dates, userId);
            tilFrom.getEditText().setText(dates[0]);
            tilTo.getEditText().setText(dates[1]);
        } else {
            evaluations = dao.findAllByUser(userId);
        }

        evaluationAdapter = new EvaluationAdapter(EvaluationList.this, evaluations);
        listView.setAdapter(evaluationAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Evaluation evaluation = evaluations.get(position);

                Intent intent = new Intent(EvaluationList.this, EvaluationItem.class);
                Bundle bundle = new Bundle();

                bundle.putSerializable("evaluation", evaluation);
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });

        logoutLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences("userData", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.remove("username");
                editor.commit();
                Intent intent = new Intent(v.getContext(),Login.class);
                startActivity(intent);
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),EvaluationRegister.class);
                startActivity(intent);
            }
        });

        tilFrom.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(tilFrom);
            }
        });

        tilTo.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(tilTo);
            }
        });

        filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!tilFrom.getEditText().getText().toString().isEmpty() && !tilTo.getEditText().getText().toString().isEmpty()) {
                    dates[0] = tilFrom.getEditText().getText().toString();
                    dates[1] = tilTo.getEditText().getText().toString();

                    Intent intent = new Intent(v.getContext(),EvaluationList.class);
                    Bundle bundle = new Bundle();

                    bundle.putStringArray("dates", dates);
                    intent.putExtras(bundle);

                    startActivity(intent);

                    Toast.makeText(v.getContext(), "Filtrando entre las fechas fechas " + tilFrom.getEditText().getText() + " y " + tilTo.getEditText().getText(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(v.getContext(), "Ingresa fechas válidas", Toast.LENGTH_SHORT).show();
                }
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
}
