package com.dobleb.gymfitnessclub.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.dobleb.gymfitnessclub.controller.AdminSQLiteOpenHelper;
import com.dobleb.gymfitnessclub.model.User;

import java.util.ArrayList;

public class UserDAO implements iCRUD<User> {

    private Context context;
    private AdminSQLiteOpenHelper admin;
    private String dbName = "dbGym";
    private String tableName = "users";

    public UserDAO(Context context) {
        this.context = context;
    }

    @Override
    public boolean insert(User user) {
        admin = new AdminSQLiteOpenHelper(context, dbName, null, 1);
        SQLiteDatabase database = admin.getWritableDatabase();

        ContentValues registry = new ContentValues();

        registry.put("username", user.getUsername());
        registry.put("firstname", user.getFirstname());
        registry.put("lastname", user.getLastname());
        registry.put("birth", user.getBirth());
        registry.put("height", user.getHeight());
        registry.put("password", user.getPassword());

        if(database.insert(tableName, null, registry) != -1) {
            database.close();
            return true;
        } else {
            database.close();
            return false;
        }
    }

    @Override
    public ArrayList<User> findAll() {
        return null;
    }

    @Override
    public User findById(int id) {
        return null;
    }

    @Override
    public boolean update(User user) {
        return false;
    }

    @Override
    public boolean deleteById(int id) {
        return false;
    }

    public User login(String username, String password) {
        admin = new AdminSQLiteOpenHelper(context, dbName, null, 1);
        SQLiteDatabase database = admin.getWritableDatabase();

        Cursor row = database.rawQuery("SELECT id, firstname, lastname, birth, height FROM users WHERE username = '" + username + "' AND password = '" + password + "'", null);

        if(row.moveToFirst()) {
            int id = Integer.parseInt(row.getString(0));
            String firstname = row.getString(1);
            String lastname = row.getString(2);
            String birth = row.getString(3);
            double height = Double.parseDouble(row.getString(4));
            database.close();
            return new User(id, username, firstname, lastname, birth, height);
        } else {
            database.close();
            return null;
        }
    }

}
