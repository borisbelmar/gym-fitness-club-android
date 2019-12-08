package com.dobleb.gymfitnessclub;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.dobleb.gymfitnessclub.dao.UserDAO;
import com.dobleb.gymfitnessclub.model.User;
import com.dobleb.gymfitnessclub.ui.DatePickerFragment;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Register extends AppCompatActivity {

    TextInputLayout tilUsername, tilFirstname, tilLastname, tilBirth, tilHeight, tilPassword, tilRepassword;
    Button registerBtn;
    TextView loginLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        tilUsername = (TextInputLayout) findViewById(R.id.til_username);
        tilFirstname = (TextInputLayout) findViewById(R.id.til_firstname);
        tilLastname = (TextInputLayout) findViewById(R.id.til_lastname);
        tilBirth = (TextInputLayout) findViewById(R.id.til_birth);
        tilHeight = (TextInputLayout) findViewById(R.id.til_height);
        tilPassword = (TextInputLayout) findViewById(R.id.til_password);
        tilRepassword = (TextInputLayout) findViewById(R.id.til_repassword);
        registerBtn = (Button) findViewById(R.id.registerBtn);
        loginLink = (TextView) findViewById(R.id.loginLink);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username, firstname, lastname, birth, password, repassword;
                Double height = 0.0;

                username = tilUsername.getEditText().getText().toString();
                firstname = tilFirstname.getEditText().getText().toString();
                lastname = tilLastname.getEditText().getText().toString();
                birth = tilBirth.getEditText().getText().toString();
                height = Double.parseDouble(tilHeight.getEditText().getText().toString());

                password = tilPassword.getEditText().getText().toString();
                repassword = tilRepassword.getEditText().getText().toString();

                if(password.equals(repassword)) {
                    User newUser = new User(username, firstname, lastname, birth, height, password);
                    UserDAO dao = new UserDAO(v.getContext());
                    if(dao.insert(newUser)) {
                        Intent intent = new Intent(v.getContext(),Login.class);
                        startActivity(intent);
                        Toast.makeText(v.getContext(), "Usuario registrado exitosamente, inicia sesión para continuar",Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(v.getContext(), "Error al registrar usuario en la BBDD",Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(v.getContext(), "Las contraseñas no coinciden",Toast.LENGTH_SHORT).show();
                }
            }
        });

        tilBirth.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(tilBirth);
            }
        });

        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),Login.class);
                startActivity(intent);
            }
        });
    }

    private void showDatePickerDialog(final TextInputLayout til) {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                final String selectedDate = year + "/" + (month+1) + "/" + day;
                til.getEditText().setText(selectedDate);
            }
        });

        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
}
