package com.dobleb.gymfitnessclub.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.dobleb.gymfitnessclub.controller.AdminSQLiteOpenHelper;
import com.dobleb.gymfitnessclub.model.Evaluation;

import java.util.ArrayList;

public class EvaluationDAO implements iCRUD<Evaluation> {

    private Context context;
    private AdminSQLiteOpenHelper admin;
    private String dbName = "dbGym";
    private String tableName = "evaluations";

    public EvaluationDAO(Context context) {
        this.context = context;
    }

    @Override
    public boolean insert(Evaluation evaluation) {
        admin = new AdminSQLiteOpenHelper(context, dbName, null, 1);
        SQLiteDatabase database = admin.getWritableDatabase();

        ContentValues registry = new ContentValues();

        registry.put("user_id", evaluation.getUserId());
        registry.put("date", evaluation.getDate());
        registry.put("weight", evaluation.getWeight());
        registry.put("height", evaluation.getHeight());

        if(database.insert(tableName, null, registry) != -1) {
            database.close();
            return true;
        } else {
            database.close();
            return false;
        }
    }

    @Override
    public ArrayList<Evaluation> findAll() {
        return null;
    }

    public ArrayList<Evaluation> findAllByUser(int user_id) {
        admin = new AdminSQLiteOpenHelper(context, dbName, null, 1);
        SQLiteDatabase database = admin.getWritableDatabase();

        Cursor rows = database.rawQuery("SELECT id, date, weight, height FROM evaluations WHERE user_id = " + user_id, null);

        ArrayList<Evaluation> resultList = new ArrayList<>();

        while(rows.moveToNext()) {
            int id = Integer.parseInt(rows.getString(0));
            String date = rows.getString(1);
            double weight = Double.parseDouble(rows.getString(2));
            double height = Double.parseDouble(rows.getString(3));

            Evaluation evaluation = new Evaluation(id, user_id, date, weight, height);

            resultList.add(evaluation);
        }
        database.close();
        return resultList;
    }

    public ArrayList<Evaluation> findAllByDates(String[] dates, int user_id) {
        admin = new AdminSQLiteOpenHelper(context, dbName, null, 1);
        SQLiteDatabase database = admin.getWritableDatabase();

        Cursor rows = database.rawQuery("SELECT id, date, weight, height FROM evaluations WHERE date BETWEEN '" + dates[0] + "' AND '" + dates[1] + "'", null);

        Log.i("Log", "SELECT id, date, weight, height FROM evaluations WHERE date BETWEEN " + dates[0] + " AND " + dates[1]);

        ArrayList<Evaluation> resultList = new ArrayList<>();

        while(rows.moveToNext()) {
            int id = Integer.parseInt(rows.getString(0));
            String date = rows.getString(1);
            double weight = Double.parseDouble(rows.getString(2));
            double height = Double.parseDouble(rows.getString(3));

            Evaluation evaluation = new Evaluation(id, user_id, date, weight, height);

            resultList.add(evaluation);
        }
        database.close();
        return resultList;
    }

    @Override
    public Evaluation findById(int id) {
        admin = new AdminSQLiteOpenHelper(context, dbName, null, 1);
        SQLiteDatabase database = admin.getWritableDatabase();

        Cursor row = database.rawQuery("SELECT user_id, date, weight, height FROM evaluations WHERE id = " + id, null);

        if(row.moveToFirst()) {
            int user_id = Integer.parseInt(row.getString(0));
            String date = row.getString(1);
            double weight = Double.parseDouble(row.getString(2));
            double height = Double.parseDouble(row.getString(3));
            database.close();
            return new Evaluation(id, user_id, date, weight, height);
        } else {
            database.close();
            return null;
        }
    }

    @Override
    public boolean update(Evaluation evaluation) {
        admin = new AdminSQLiteOpenHelper(context, dbName, null, 1);
        SQLiteDatabase database = admin.getWritableDatabase();

        ContentValues registry = new ContentValues();

        registry.put("user_id", evaluation.getUserId());
        registry.put("date", evaluation.getDate());
        registry.put("weight", evaluation.getWeight());
        registry.put("height", evaluation.getHeight());

        boolean isUpdated = database.update(tableName, registry, "id = ?", new String[] {Integer.toString(evaluation.getId())}) > 0;

        if(isUpdated) {
            database.close();
            return true;
        } else {
            database.close();
            return false;
        }

    }

    @Override
    public boolean deleteById(int id) {
        admin = new AdminSQLiteOpenHelper(context, dbName, null, 1);
        SQLiteDatabase database = admin.getWritableDatabase();

        int qty = database.delete(tableName, "id = " + id, null);

        if(qty > 0) {
            return true;
        } else {
            return false;
        }
    }

}
