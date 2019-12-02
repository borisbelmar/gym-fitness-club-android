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

import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

public class Login extends AppCompatActivity {

    Button loginBtn;
    TextView registerLink;
    TextInputLayout tilUser;
    TextInputLayout tilPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn = (Button) findViewById(R.id.loginBtn);
        registerLink = (TextView) findViewById(R.id.registerLink);
        tilUser = (TextInputLayout) findViewById(R.id.til_user);
        tilPass = (TextInputLayout) findViewById(R.id.til_pass);

        final SharedPreferences preferences = getSharedPreferences("userData", Context.MODE_PRIVATE);

        if(preferences.contains("username")) {
            Intent intent = new Intent(Login.this, EvaluationList.class);
            startActivity(intent);
            Toast.makeText(this, "Bienvenido denuevo", Toast.LENGTH_SHORT).show();
        }

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = tilUser.getEditText().getText().toString();
                String pass = tilPass.getEditText().getText().toString();

                if(username.equals("test") && pass.equals("test")) {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("username", username);
                    editor.commit();
                    Intent intent = new Intent(v.getContext(),EvaluationList.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(v.getContext(), "Usuario o contraseña incorrecto (Intenta user: test | pass: test)", Toast.LENGTH_SHORT).show();
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
