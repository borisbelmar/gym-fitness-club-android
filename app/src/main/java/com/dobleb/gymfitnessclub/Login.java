package com.dobleb.gymfitnessclub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dobleb.gymfitnessclub.dao.UserDAO;
import com.dobleb.gymfitnessclub.model.User;
import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

public class Login extends AppCompatActivity {

    Button loginBtn;
    TextView registerLink;
    TextInputLayout tilUser;
    TextInputLayout tilPass;
    UserDAO dao;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn = (Button) findViewById(R.id.loginBtn);
        registerLink = (TextView) findViewById(R.id.registerLink);
        tilUser = (TextInputLayout) findViewById(R.id.til_user);
        tilPass = (TextInputLayout) findViewById(R.id.til_pass);

        preferences = getSharedPreferences("userData", Context.MODE_PRIVATE);

        if(preferences.contains("username")) {
            Intent intent = new Intent(Login.this, EvaluationList.class);
            startActivity(intent);
            Toast.makeText(this, "Bienvenido denuevo " + preferences.getString("firstname", "User"), Toast.LENGTH_SHORT).show();
        }

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = tilUser.getEditText().getText().toString();
                String pass = tilPass.getEditText().getText().toString();

                dao = new UserDAO(v.getContext());
                User user = dao.login(username, pass);

                if(user != null) {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putInt("id", user.getId());
                    editor.putString("username", user.getUsername());
                    editor.putString("firstname", user.getFirstname());
                    editor.putString("lastname", user.getLastname());
                    editor.putString("height", Double.toString(user.getHeight()));
                    editor.commit();
                    Intent intent = new Intent(v.getContext(),EvaluationList.class);
                    startActivity(intent);
                    Toast.makeText(v.getContext(), "Bienvenido " + user.getFirstname(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(v.getContext(), "Usuario o contraseña incorrecto", Toast.LENGTH_SHORT).show();
                }


            }
        });

        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),Register.class);
                startActivity(intent);
            }
        });
    }
}
