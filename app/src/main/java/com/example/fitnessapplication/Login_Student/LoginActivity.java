package com.example.fitnessapplication.Login_Student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitnessapplication.R;

public class LoginActivity extends AppCompatActivity {

    EditText username, password;
    TextView forgot, signup;
    Button btnLogin;

    DBHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.usernameLogin);
        password = (EditText) findViewById(R.id.passwordLogin);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        forgot = (TextView) findViewById(R.id.btnForgot);
        signup = (TextView) findViewById(R.id.btnSignUp);

        myDB = new DBHelper(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = password.getText().toString();

                if (user.equals("") || pass.equals("")){
                    Toast.makeText(LoginActivity.this, "Please enter the Username or Password.", Toast.LENGTH_SHORT).show();
                }
                else{
                    Boolean result = myDB.checkusernamePassword(user, pass);
                    if (result == true){
                        Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                        startActivity(intent);

                    }
                    else{
                        Toast.makeText(LoginActivity.this, "Invalid Username or Password.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), PasswordActivity.class);
                startActivity(intent);

            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);

            }
        });

    }
}