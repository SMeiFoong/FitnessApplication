package com.example.fitnessapplication.Login_Student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fitnessapplication.R;

public class PasswordActivity extends AppCompatActivity {

    EditText username;
    Button reset, backLogin;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        username = (EditText) findViewById(R.id.username_reset);
        reset = (Button) findViewById(R.id.btnReset);
        DB = new DBHelper(this);
        backLogin = (Button) findViewById(R.id.backLogin);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user = username.getText() .toString();

                Boolean checkuser = DB.checkusername(user);
                if(checkuser == true)
                {
                    Intent intent = new Intent(getApplicationContext(), ResetActivity.class);
                    intent.putExtra("username", user);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(PasswordActivity.this, "User does not exists", Toast.LENGTH_LONG).show();
                }

            }
        });

        backLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);

            }
        });

    }
}