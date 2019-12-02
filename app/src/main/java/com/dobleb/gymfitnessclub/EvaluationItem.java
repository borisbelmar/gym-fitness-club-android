package com.dobleb.gymfitnessclub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dobleb.gymfitnessclub.model.Evaluation;

import org.w3c.dom.Text;

public class EvaluationItem extends AppCompatActivity {
    Button backBtn;
    Button deleteBtn;
    TextView tvDate;
    TextView tvWeight;
    TextView tvHeight;
    TextView tvImc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation_item);

        backBtn = (Button) findViewById(R.id.backBtn);
        deleteBtn = (Button) findViewById(R.id.deleteBtn);

        tvDate = (TextView) findViewById(R.id.tv_date);
        tvWeight = (TextView) findViewById(R.id.tv_weight);
        tvHeight = (TextView) findViewById(R.id.tv_height);
        tvImc = (TextView) findViewById(R.id.tv_imc);

        Bundle receivedObjects = getIntent().getExtras();
        Evaluation evaluation = null;

        if(receivedObjects != null) {
            evaluation = (Evaluation) receivedObjects.getSerializable("evaluation");
        } else {
            Intent intent = new Intent(EvaluationItem.this, EvaluationList.class);
            startActivity(intent);
            Toast.makeText(EvaluationItem.this, "Hubo un problema al recuperar la información de la evaluación", Toast.LENGTH_SHORT).show();
        }

        tvDate.setText(evaluation.getDate());
        tvHeight.setText(String.format("%.1f",evaluation.getHeight()));
        tvWeight.setText(String.format("%.1f",evaluation.getWeight()));
        tvImc.setText(String.format("%.1f",evaluation.getImc()));

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),EvaluationList.class);
                startActivity(intent);
            }
        });

        final int id = evaluation.getId();
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(EvaluationItem.this, ("Esto eliminaría el registro " + id), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
