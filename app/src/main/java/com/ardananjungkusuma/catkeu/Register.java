package com.ardananjungkusuma.catkeu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {
    DatabaseHelper db;
    Button login, register;
    EditText username, password, passwordConf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new DatabaseHelper(this);
        username = (EditText)findViewById(R.id.formUsernameRegist);
        password = (EditText)findViewById(R.id.formPasswordRegist);
        passwordConf = (EditText)findViewById(R.id.formPasswordConfirmRegist);
        login = (Button)findViewById(R.id.btnLoginOnRegist);
        register = (Button)findViewById(R.id.btnRegisterOnRegist);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(Register.this, Login.class);
                startActivity(loginIntent);
                finish();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strUsername = username.getText().toString();
                String strPass = password.getText().toString();
                String strPassConf = passwordConf.getText().toString();
                if(strPass.equals(strPassConf)){
                    Boolean regist = db.insertUser(strUsername, strPass);
                    if(regist == true){
                        Toast.makeText(getApplicationContext(), "Registration Success!",Toast.LENGTH_SHORT).show();
                        Intent loginIntent = new Intent(Register.this, Login.class);
                        startActivity(loginIntent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Registration Failed, all field required to fill!",Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Password tidak sama!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}