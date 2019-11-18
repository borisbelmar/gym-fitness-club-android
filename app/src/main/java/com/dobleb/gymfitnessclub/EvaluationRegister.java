package com.dobleb.gymfitnessclub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EvaluationRegister extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation_register);
        Button saveBtn = (Button) findViewById(R.id.saveBtn);
        TextView imcText = (TextView) findViewById(R.id.imcText);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),EvaluationList.class);
                startActivity(intent);
            }
        });
    }
}
