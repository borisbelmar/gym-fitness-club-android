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

import com.dobleb.gymfitnessclub.controller.Validate;
import com.dobleb.gymfitnessclub.dao.UserDAO;
import com.dobleb.gymfitnessclub.model.User;
import com.dobleb.gymfitnessclub.ui.DatePickerFragment;
import com.google.android.material.textfield.TextInputLayout;

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
                String username, firstname, lastname, birth, height, password, repassword;

                username = tilUsername.getEditText().getText().toString();
                firstname = tilFirstname.getEditText().getText().toString();
                lastname = tilLastname.getEditText().getText().toString();
                birth = tilBirth.getEditText().getText().toString();
                height = tilHeight.getEditText().getText().toString();

                password = tilPassword.getEditText().getText().toString();
                repassword = tilRepassword.getEditText().getText().toString();
                if (validate()) {
                    if(password.equals(repassword)) {
                        User newUser = new User(username, firstname, lastname, birth, Double.parseDouble(height), password);
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
                final String selectedDate = year + "-" + (month+1) + "-" + (day < 10 ? "0"+day : day);
                til.getEditText().setText(selectedDate);
            }
        });

        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    private boolean validate() {
        String username = tilUsername.getEditText().getText().toString();
        String height = tilHeight.getEditText().getText().toString();
        String birth = tilBirth.getEditText().getText().toString();
        String firstname = tilFirstname.getEditText().getText().toString();
        String lastname = tilLastname.getEditText().getText().toString();

        boolean validHeight;
        boolean validBirth;
        boolean validFirstname;
        boolean validLastname;
        boolean validUsername;

        if (Validate.required(username)) {
            tilUsername.setError(null);
            validUsername = true;
        } else {
            tilUsername.setError("No puede estar vacío");
            tilUsername.getEditText().setText("");
            validUsername = false;
        }

        if (Validate.required(firstname)) {
            tilFirstname.setError(null);
            validFirstname = true;
        } else {
            tilFirstname.setError("No puede estar vacío");
            tilFirstname.getEditText().setText("");
            validFirstname = false;
        }

        if(Validate.required(lastname)) {
            tilLastname.setError(null);
            validLastname = true;
        } else {
            tilLastname.setError("No puede estar vacío");
            tilLastname.getEditText().setText("");
            validLastname = false;
        }

        if(Validate.required(height) && Validate.notZero(height)) {
            tilHeight.setError(null);
            validHeight = true;
        } else {
            tilHeight.setError("Ingresa un peso válido");
            tilHeight.getEditText().setText("");
            validHeight = false;
        }

        if(Validate.date(birth)) {
            tilBirth.setError(null);
            validBirth = true;
        } else {
            tilBirth.setError("Fecha inválida");
            tilBirth.getEditText().setText("");
            validBirth = false;
        }

        return validUsername && validHeight && validBirth && validFirstname && validLastname;
    }

}
