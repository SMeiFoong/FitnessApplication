package com.example.fitnessapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    private TextInputLayout inputEmail, inputPassword, inputConfirmPassword;
    Button btnRegister;
    TextView alreadyAnUser;
    FirebaseAuth mAuth;
    ProgressDialog mloadingBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //all elements to use from xml

        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        inputConfirmPassword = findViewById(R.id.inputConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);
        alreadyAnUser = findViewById(R.id.alreadyAnUser);


        mAuth = FirebaseAuth.getInstance();

        mloadingBar = new ProgressDialog(this);

        //Register button
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AtemptRegistration();
            }
        });


        //To login
        alreadyAnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, com.example.fitnessapplication.LoginActivity.class);
                startActivity(intent);
            }
        });
    }


    //Validation
    private void AtemptRegistration(){
        String email = Objects.requireNonNull(inputEmail.getEditText()).getText().toString();
        String password = Objects.requireNonNull(inputPassword.getEditText()).getText().toString();
        String confirmPassword = Objects.requireNonNull(inputConfirmPassword.getEditText()).getText().toString();

        if(email.isEmpty() || !email.contains("@gmail"))
        {
            showError(inputEmail, "Email is not valid");
        }
        else if(password.isEmpty() || password.length()<5)
        {
            showError(inputPassword, "Password must be greater than 5 letter");
        }
        else if(!confirmPassword.equals(password))
        {
            showError(inputConfirmPassword, "Password did not match");
        }
        else
        {
            mloadingBar.setTitle("Registration");
            mloadingBar.setMessage("Please Wait, While your Credentials");
            mloadingBar.setCanceledOnTouchOutside(false);
            mloadingBar.show();
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(RegisterActivity.this, "Registration is Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        mloadingBar.dismiss();
                        Toast.makeText(RegisterActivity.this, "Registration is Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void showError(TextInputLayout field, String text){
        field.setError(text);
        field.requestFocus();
    }
}