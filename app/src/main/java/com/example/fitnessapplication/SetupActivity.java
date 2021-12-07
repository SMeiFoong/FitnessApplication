package com.example.fitnessapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnessapplication.Reminder.DashBoardActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class SetupActivity extends AppCompatActivity {

    private static  final  int REQUEST_CODE = 101;
    CircleImageView profileImageView;
    EditText inputFullName, inputPhoneNo, inputUsername, inputHeight, inputWeight, inputAge, inputUniversity;
    Button btnSave, btnDone;

    Uri imageUri;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference mRef;
    StorageReference StorageRef;

    ProgressDialog mloadingBar;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        profileImageView = findViewById(R.id.profile_image);
        inputFullName = findViewById(R.id.inputFullName);
        inputUsername = findViewById(R.id.inputUsername);
        inputPhoneNo = findViewById(R.id.inputPhoneNo);
        inputHeight = findViewById(R.id.inputHeight);
        inputWeight = findViewById(R.id.inputWeight);
        inputAge = findViewById(R.id.inputAge);
        inputUniversity = findViewById(R.id.inputUniversity);
        btnSave = findViewById(R.id.btnSave);
        btnDone = findViewById(R.id.btnDone);



        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mRef = FirebaseDatabase.getInstance().getReference().child("users");
        StorageRef = FirebaseStorage.getInstance().getReference().child("ProfileImages");

        mloadingBar = new ProgressDialog(this);

        //toolbar = findViewById(R.id.app_bar);
        //setSupportActionBar(toolbar);
        //Objects.requireNonNull(getSupportActionBar()).setTitle("Setup Profile");

        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,REQUEST_CODE);
            }
        });



        mRef.child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    String profileImageUrl = snapshot.child("profileImage").getValue().toString();
                    String name = snapshot.child("name").getValue().toString();
                    String username = snapshot.child("username").getValue().toString();
                    String phoneNo = snapshot.child("phoneNo").getValue().toString();
                    String height = snapshot.child("height").getValue().toString();
                    String weight = snapshot.child("weight").getValue().toString();
                    String age = snapshot.child("age").getValue().toString();
                    String university = snapshot.child("university").getValue().toString();




                    Picasso.get().load(profileImageUrl).into(profileImageView);
                    inputFullName.setText(name);
                    inputUsername.setText(username);
                    inputPhoneNo.setText(phoneNo);
                    inputHeight.setText(height + "    CM");
                    inputWeight.setText(weight + "       KG");
                    inputAge.setText(age);
                    inputUniversity.setText(university);

                }
                else
                {
                    Toast.makeText(SetupActivity.this, "Data not exists", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SetupActivity.this, ""+error.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }

        });



        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveData();
            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SetupActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

    }

    private void SaveData() {

        String fullName = Objects.requireNonNull(inputFullName.getText().toString());
        String phoneNo = Objects.requireNonNull(inputPhoneNo.getText().toString());
        String username = Objects.requireNonNull(inputUsername.getText().toString());
        String height = Objects.requireNonNull(inputHeight.getText().toString());
        String weight = Objects.requireNonNull(inputWeight.getText().toString());
        String age = Objects.requireNonNull(inputAge.getText().toString());
        String university = Objects.requireNonNull(inputUniversity.getText().toString());

        if (fullName.isEmpty())
        {
            showError(inputFullName, "Please fill in your Full Name");
        }
        else if(fullName.length()<2)
        {
            showError(inputFullName, "Full Name Minimum length is 2");
        }
        else if(fullName.length()>20)
        {
            showError(inputFullName, "Full Name Maximum length is 30");
        }
        else if (username.isEmpty())
        {
            showError(inputUsername, "Please fill in Username");
        }
        else if(username.length()<6)
        {
            showError(inputUsername, "Username Minimum length is 6");
        }
        else if(username.length()>20)
        {
            showError(inputUsername, "Username Maximum length is 20");
        }
        else if(phoneNo.isEmpty())
        {
            showError(inputPhoneNo, "Please fill in Phone number");
        }
        else if(phoneNo.length()<10 || phoneNo.length()>11)
        {
            showError(inputPhoneNo, "Please enter 10 or 11 digits");
        }
        else if(!phoneNo.matches("^(01)[0-90-9]*[0-9]{7,8}$"))
        {
            showError(inputPhoneNo, "Phone number format 01XXXXXXXX");
        }
        else if (height.isEmpty() || height.length()<3)
        {
            showError(inputHeight, "Height is not valid");
        }
        else if (weight.isEmpty() || weight.length()<2)
        {
            showError(inputWeight, "Weight is not valid");
        }
        else if (age.isEmpty() || age.length()<2)
        {
            showError(inputAge, "Age is not valid");
        }
        else if (university.isEmpty())
        {
            showError(inputUniversity, "Please fill in your University");
        }
        else if (imageUri==null)
        {
            Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show();
        }
        else
        {

            mloadingBar.setTitle("adding setup Profile");
            mloadingBar.setCanceledOnTouchOutside(false);
            mloadingBar.show();

            StorageRef.child(mUser.getUid()).putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                    if (task.isSuccessful())
                    {
                        StorageRef.child(mUser.getUid()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                HashMap<String, Object> hashMap = new HashMap<String, Object>();
                                hashMap.put("name", fullName);
                                hashMap.put("phoneNo", phoneNo);
                                hashMap.put("username", username);
                                hashMap.put("height", height);
                                hashMap.put("weight", weight);
                                hashMap.put("age", age);
                                hashMap.put("university", university);
                                hashMap.put("profileImage", uri.toString());

                                mRef.child(mUser.getUid()).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                                    @Override
                                    public void onSuccess(Object o) {
                                        Intent intent = new Intent(SetupActivity.this, HomeActivity.class);
                                        startActivity(intent);
                                        mloadingBar.dismiss();
                                        Toast.makeText(SetupActivity.this, "Setup Profile completed", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        mloadingBar.dismiss();
                                        Toast.makeText(SetupActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        });
                    }

                }
            });
        }
    }

    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data!=null)
        {
            imageUri = data.getData();
            profileImageView.setImageURI(imageUri);
        }
    }
}