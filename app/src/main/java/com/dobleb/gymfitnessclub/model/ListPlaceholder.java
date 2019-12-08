package com.dobleb.gymfitnessclub.model;

import android.content.Context;

import com.dobleb.gymfitnessclub.dao.EvaluationDAO;

import java.util.ArrayList;

public class ListPlaceholder {

    public static ArrayList<Evaluation> getList(Context context) {
//        ArrayList<Evaluation> imcList = new ArrayList<>();
//        imcList.add(new Evaluation(1, 1, "2019/12/02", 70.5, 1.70));
//        imcList.add(new Evaluation(1,1, "2019/12/01", 68.5, 1.70));
//        imcList.add(new Evaluation(1,1, "2019/11/15", 75.1, 1.70));
//        imcList.add(new Evaluation(1,1, "2019/11/10", 82.8, 1.70));
//        imcList.add(new Evaluation(1, 1, "2019/11/03", 81.1, 1.70));
//        return imcList;
        EvaluationDAO dao = new EvaluationDAO(context);
        return dao.findAllByUser(1);
    }

}
