package com.example.recipeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Model.User;
import Prevelant.Prevelant;

public class LoginActivity extends AppCompatActivity {

    EditText username, password;
    TextView signUp;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username);
        password = findViewById(R.id.pass);
        submit = findViewById(R.id.login_btn);
        signUp = findViewById(R.id.signUp);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, Sign_upActivity.class));
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(username.getText().toString())){
                    Snackbar.make(view, "Fill in Username",Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else if (TextUtils.isEmpty(password.getText().toString())){
                    Snackbar.make(view, "Fill in Password",Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else{
                    validate(username, password);
                }
            }
        });

    }

    private void validate(EditText username, EditText password) {
        final ProgressDialog loading = new ProgressDialog(this);

        loading.setTitle("Logging In");
        loading.setMessage("Please wait...");
        loading.setCanceledOnTouchOutside(false);
        loading.show();


        final DatabaseReference RootRef = FirebaseDatabase.getInstance().getReference().child("Users");

        RootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(username.getText().toString()).exists()){

                    User userData = snapshot.child(username.getText().toString()).getValue(User.class);

                    if ( userData.getMobile_Number().equals(username.getText().toString()) &&
                         userData.getPassword().equals(password.getText().toString())){

                        Prevelant.CurrentUserOnline = userData;
                        loading.dismiss();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }
                    else{
                        Toast.makeText(LoginActivity.this, "Wrong Username/ Password", Toast.LENGTH_SHORT).show();
                        loading.dismiss();
                    }


                }
                else{
                    Toast.makeText(LoginActivity.this, "Wrong Username/ Password", Toast.LENGTH_SHORT).show();
                    loading.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(LoginActivity.this, ""+error, Toast.LENGTH_SHORT).show();
                loading.dismiss();
            }
        });
    }
}