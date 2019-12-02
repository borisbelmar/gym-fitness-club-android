package com.dobleb.gymfitnessclub.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dobleb.gymfitnessclub.R;
import com.dobleb.gymfitnessclub.model.Evaluation;

import java.util.ArrayList;

public class EvaluationAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Evaluation> evaluations;

    public EvaluationAdapter(Context context, ArrayList<Evaluation> evaluations) {
        this.context = context;
        this.evaluations = evaluations;
    }

    @Override
    public int getCount() {
        return evaluations.size();
    }

    @Override
    public Object getItem(int position) {
        return evaluations.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null) {
            convertView = View.inflate(context, R.layout.list_item_evaluation, null);
        }

        TextView date = (TextView) convertView.findViewById(R.id.tv_date);
        TextView imc = (TextView) convertView.findViewById(R.id.tv_imc);

        Evaluation evaluation = evaluations.get(position);
        date.setText(evaluation.getDate());
        imc.setText(String.format("%.1f",evaluation.getImc()));

        return convertView;
    }

}
