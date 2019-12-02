package com.dobleb.gymfitnessclub.controller;

import android.util.Patterns;

import java.util.regex.Pattern;

public class Validate {
    public static boolean email(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean number(String number) {
        Pattern pattern = Pattern.compile("^[0-9]*$");
        return pattern.matcher(number).matches();
    }

    public static boolean date(String date) {
        Pattern pattern = Pattern.compile("([12]\\d{3}\\/([1-9]|1[0-2])\\/([12]\\d|3[01]|[1-9]))");
        return pattern.matcher(date).matches();
    }

    public static boolean required(String text) {
        return text.trim().length() > 0;
    }
}