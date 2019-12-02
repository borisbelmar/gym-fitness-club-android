package com.dobleb.gymfitnessclub.model;

import java.util.ArrayList;

public class ListPlaceholder {

    public static ArrayList<Evaluation> getList() {
        ArrayList<Evaluation> imcList = new ArrayList<>();
        imcList.add(new Evaluation(1, "2019/12/02", 70.5, 1.70));
        imcList.add(new Evaluation(2, "2019/12/01", 68.5, 1.70));
        imcList.add(new Evaluation(3, "2019/11/15", 75.1, 1.70));
        imcList.add(new Evaluation(4, "2019/11/10", 82.8, 1.70));
        imcList.add(new Evaluation(5, "2019/11/03", 81.1, 1.70));
        return imcList;
    }

}
