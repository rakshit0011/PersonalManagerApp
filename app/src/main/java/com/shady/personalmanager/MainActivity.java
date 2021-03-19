package com.shady.personalmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private ConnectivityManager cm;
    private DatabaseReference mDatabase,newUser;
    private FirebaseUser mCurrentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button signUpButton;
        Button signInButton;
        mAuth=FirebaseAuth.getInstance();
        editTextEmail=findViewById(R.id.editTextTextEmailAddress);
        editTextPassword=findViewById(R.id.editTextTextPassword);
        cm= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);


// To automatically sign in a user
        if(mAuth.getCurrentUser()!=null){
            Intent homeIntent= new Intent(MainActivity.this,HomeActivity.class);
            startActivity(homeIntent);
        }
// Now let us continue our work
        signInButton=findViewById(R.id.signIn);
        signUpButton=findViewById(R.id.signUp);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInUser();
            }
        });
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpUser();
            }
        });
    }
    public void signUpUser(){
        mDatabase=FirebaseDatabase.getInstance().getReference().child("users");
        String email,password;
        email=editTextEmail.getText().toString();
        password=editTextPassword.getText().toString();
        if(TextUtils.isEmpty(email)){
            editTextEmail.setError("Please enter email");
            return;
        }
        if(TextUtils.isEmpty(password)){
            editTextPassword.setError("Please enter password");
            return ;
        }
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    mCurrentUser=task.getResult().getUser();
                    newUser=mDatabase.child(mCurrentUser.getUid());
                    newUser.child("Email").setValue(email);
                    Toast.makeText(MainActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(MainActivity.this,HomeActivity.class);
                    startActivity(intent);
                }
                else if(cm.getActiveNetworkInfo()==null){
                    Toast.makeText(MainActivity.this, "Check your internet connection and try again", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this, "Sign up failed. Please try again", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void signInUser(){
        String email1,password1;
        email1=editTextEmail.getText().toString();
        password1=editTextPassword.getText().toString();
        if(TextUtils.isEmpty(email1)){
            editTextEmail.setError("Please enter email");
            return;
        }
        if(TextUtils.isEmpty(password1)){
            editTextPassword.setError("Please enter password");
            return;
        }
        mAuth.signInWithEmailAndPassword(email1,password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Sign in Successful", Toast.LENGTH_SHORT).show();
                    Intent intent1= new Intent(MainActivity.this,HomeActivity.class);
                    startActivity(intent1);

                }
                else if(cm.getActiveNetworkInfo()==null){
                    Toast.makeText(MainActivity.this, "Check your internet connection and try again", Toast.LENGTH_SHORT).show();

                }
                else{
                    Toast.makeText(MainActivity.this, "Sign in failed. Invalid email or password", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public void resetPassScreen(View view){
    Intent intent= new Intent(MainActivity.this,ChangePassActivity.class);
//    if(cm.getActiveNetworkInfo()==null){
//        Toast.makeText(this, "No internet connection. Please try again", Toast.LENGTH_SHORT).show();
//    }
//    else{
//    startActivity(intent);
//    }
    startActivity(intent);
    }

}