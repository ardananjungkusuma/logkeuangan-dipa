package com.ardananjungkusuma.catkeu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    DatabaseHelper db;
    Button login, register;
    EditText username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new DatabaseHelper(this);
        username = (EditText)findViewById(R.id.formUsername);
        password = (EditText)findViewById(R.id.formPassword);
        login = (Button)findViewById(R.id.btnLoginOnLogin);
//        register = (Button)findViewById(R.id.btnRegisterOnLogin);

        Boolean checkSession = db.sessionCheck("ada");
        if(checkSession == true){
            Intent loginIntent = new Intent(Login.this,MainActivity.class);
            startActivity(loginIntent);
            finish();
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strUsername = username.getText().toString();
                String strPass = password.getText().toString();
                Boolean auth = db.checkLogin(strUsername, strPass);
                if(auth == true) {
                    int jumlahData = db.sessionCount();
                    Boolean updateSess = false;
                    Boolean createSess = false;
                    if (jumlahData < 1) {
                        createSess = db.createSession("ada", 1);
                    } else {
                        updateSess = db.updateSession("ada", 1);
                    }
                    if (updateSess == true || createSess == true) {
                        Toast.makeText(getApplicationContext(), "Login Sucess", Toast.LENGTH_SHORT).show();
                        Intent mainIntent = new Intent(Login.this, MainActivity.class);
                        startActivity(mainIntent);
                        finish();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Login Failed, Username/Password is wrong.", Toast.LENGTH_SHORT).show();
                }
            }
        });

//        register.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent registIntent = new Intent(Login.this, Register.class);
//                startActivity(registIntent);
//                finish();
//            }
//        });

    }
}