package com.dobleb.gymfitnessclub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dobleb.gymfitnessclub.dao.EvaluationDAO;
import com.dobleb.gymfitnessclub.model.Evaluation;

public class EvaluationItem extends AppCompatActivity {
    Button backBtn;
    Button deleteBtn;
    Button updateBtn;
    TextView tvDate;
    TextView tvWeight;
    TextView tvHeight;
    TextView tvImc;

    EvaluationDAO dao;
    Evaluation evaluation;

    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation_item);

        backBtn = (Button) findViewById(R.id.backBtn);
        deleteBtn = (Button) findViewById(R.id.deleteBtn);
        updateBtn = (Button) findViewById(R.id.updateBtn);

        tvDate = (TextView) findViewById(R.id.tv_date);
        tvWeight = (TextView) findViewById(R.id.tv_weight);
        tvHeight = (TextView) findViewById(R.id.tv_height);
        tvImc = (TextView) findViewById(R.id.tv_imc);

        dao = new EvaluationDAO(this);

        Bundle receivedObjects = getIntent().getExtras();
        evaluation = null;

        if(receivedObjects != null) {
            evaluation = (Evaluation) receivedObjects.getSerializable("evaluation");
        } else {
            Intent intent = new Intent(EvaluationItem.this, EvaluationList.class);
            startActivity(intent);
            Toast.makeText(EvaluationItem.this, "Hubo un problema al recuperar la información de la evaluación", Toast.LENGTH_SHORT).show();
        }

        id = evaluation.getId();

        tvDate.setText(evaluation.getDate());
        tvHeight.setText(String.format("%.1f",evaluation.getHeight()));
        tvWeight.setText(String.format("%.1f",evaluation.getWeight()));
        tvImc.setText(String.format("%.1f",evaluation.calculateImc()));

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),EvaluationList.class);
                startActivity(intent);
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),EvaluationUpdate.class);

                Bundle bundle = new Bundle();

                bundle.putSerializable("evaluation", evaluation);
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(dao.deleteById(id)) {
                Intent intent = new Intent(v.getContext(),EvaluationList.class);
                startActivity(intent);
                Toast.makeText(v.getContext(), ("Se ha eliminado el registro " + id), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(v.getContext(), ("Error al eliminar el registro " + id), Toast.LENGTH_SHORT).show();
            }
            }
        });
    }
}
