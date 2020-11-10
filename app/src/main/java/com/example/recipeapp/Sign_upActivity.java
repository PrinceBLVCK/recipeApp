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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Sign_upActivity extends AppCompatActivity {

    EditText names, mobile_num, pass, confirm_pass;
    Button submit;
    
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        
        names = findViewById(R.id.full_names);
        mobile_num = findViewById(R.id.mob_num);
        pass = findViewById(R.id.signup_pass);
        confirm_pass = findViewById(R.id.confirm_pass);
        submit = findViewById(R.id.signUp_btn);
        
        
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(names.getText().toString())){
                    Snackbar.make(view, "Fill in Your Full Names", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else if (TextUtils.isEmpty(mobile_num.getText().toString())){
                    Snackbar.make(view, "Fill in Your Mobile Number", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else if (TextUtils.isEmpty(pass.getText().toString())){
                    Snackbar.make(view, "Fill in Your Password", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else if (!pass.getText().toString().equals(confirm_pass.getText().toString()) ){
                    Snackbar.make(view, "Confirm Password do not match Password", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else{
                    setUpAcc(names, mobile_num, pass);
                }
            }
        });
        
    }

    private void setUpAcc(EditText names, EditText mobile_num, EditText pass) {

        final ProgressDialog loading = new ProgressDialog(this);

        loading.setTitle("Creating Account");
        loading.setMessage("Please wait...");
        loading.setCanceledOnTouchOutside(false);
        loading.show();

        final DatabaseReference RootRef = FirebaseDatabase.getInstance().getReference().child("Users");

        RootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.child(mobile_num.getText().toString()).exists()){

                    HashMap<String, Object>userData = new HashMap<>();

                    userData.put("Username", names.getText().toString());
                    userData.put("Password", pass.getText().toString());
                    userData.put("Mobile_Number", mobile_num.getText().toString());

                    RootRef.child(mobile_num.getText().toString()).updateChildren(userData)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        loading.dismiss();
                                        startActivity(new Intent(Sign_upActivity.this, LoginActivity.class));
                                        finish();
                                    }
                                }
                            });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Sign_upActivity.this, ""+error, Toast.LENGTH_SHORT).show();
                loading.dismiss();
            }
        });

    }
}