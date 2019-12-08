package com.dobleb.gymfitnessclub.model;

import java.io.Serializable;

public class Evaluation implements Serializable {

    private int id;
    private int userId;
    private String date;
    private double weight;
    private double height;
    private double imc;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getImc() {
        return imc;
    }

    public void setImc(double imc) {
        this.imc = imc;
    }

    public double calculateImc() {
        this.imc = this.weight / (this.height*this.height);
        return this.imc;
    }

    public Evaluation(int userId, String date, double weight, double height) {
        this.userId = userId;
        this.date = date;
        this.weight = weight;
        this.height = height;
        this.imc = this.calculateImc();
    }

    public Evaluation(int id, int userId, String date, double weight, double height) {
        this.id = id;
        this.userId = userId;
        this.date = date;
        this.weight = weight;
        this.height = height;
        this.imc = this.calculateImc();
    }
}
