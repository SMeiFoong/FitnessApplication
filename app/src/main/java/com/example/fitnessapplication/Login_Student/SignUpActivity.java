package com.example.fitnessapplication.Login_Student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitnessapplication.MainActivity;
import com.example.fitnessapplication.R;

public class SignUpActivity extends AppCompatActivity {

    EditText username, password, repassword;
    Button signUp;
    DBHelper DB;
    TextView forgot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        repassword = (EditText) findViewById(R.id.repassword);
        signUp = (Button) findViewById(R.id.btnSignUp);
        forgot = (TextView) findViewById(R.id.btnForgot);

        TextView signIn = findViewById(R.id.btnSigIn);

        DB = new DBHelper(this);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user = username.getText() .toString();
                String pass = password.getText() .toString();
                String repass = repassword.getText() .toString();

                if(user.equals("") || pass.equals("") || repass.equals(""))
                    Toast.makeText(SignUpActivity.this, "Please enter all the fields", Toast.LENGTH_LONG).show();
                else
                {
                    if(pass.equals(repass)){
                        Boolean checkuser = DB.checkusername(user);
                        if(!checkuser){
                            Boolean insert = DB.insertData(user, pass);
                            if(insert){
                                Toast.makeText(SignUpActivity.this, "Register successful", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(SignUpActivity.this, "Register failed", Toast.LENGTH_LONG).show();
                            }
                        }
                        else {
                            Toast.makeText(SignUpActivity.this, "User already exists, please sign in!", Toast.LENGTH_LONG).show();
                        }
                    }
                    else {
                        Toast.makeText(SignUpActivity.this, "Password not matching", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Sign In", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), PasswordActivity.class);
                startActivity(intent);

            }
        });

    }
}