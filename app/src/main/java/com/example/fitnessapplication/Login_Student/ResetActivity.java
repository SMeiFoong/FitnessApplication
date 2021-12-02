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

public class ResetActivity extends AppCompatActivity {

    TextView username;
    EditText pass, repass;
    Button confirm;

    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);

        username = (TextView) findViewById(R.id.username_reset_text);
        pass = (EditText) findViewById(R.id.Reset_Password);
        repass = (EditText) findViewById(R.id.Reset_RetypePassword);
        confirm = (Button) findViewById(R.id.btnConfirm);
        DB = new DBHelper(this);

        Intent intent = getIntent();
        username.setText(intent.getStringExtra("username"));

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user = username.getText().toString();
                String password = pass.getText().toString();
                String repassword = repass.getText().toString();

                if(user.equals("") || password.equals("") || repassword.equals(""))
                    Toast.makeText(ResetActivity.this, "Please enter all the fields", Toast.LENGTH_LONG).show();
                else
                {
                    if (password.equals(repassword)) {

                        Boolean checkpasswordupdate = DB.updatepassword(user, password);
                        if (checkpasswordupdate == true) {
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                            Toast.makeText(ResetActivity.this, "Password Updated Successfully", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(ResetActivity.this, "Password Not Updated", Toast.LENGTH_LONG).show();
                        }

                    } else {
                        Toast.makeText(ResetActivity.this, "Password Not Matching", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }
}