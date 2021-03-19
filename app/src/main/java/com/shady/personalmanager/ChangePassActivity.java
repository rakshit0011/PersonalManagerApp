package com.shady.personalmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class  ChangePassActivity extends AppCompatActivity {
    private EditText editTextEmail;
    private Button confirmButton;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        editTextEmail=findViewById(R.id.resetPassEmail);

        confirmButton=findViewById(R.id.confirmReset);
        mAuth=FirebaseAuth.getInstance();

    }
    public void sendEmail(View view){


        String str1=editTextEmail.getText().toString();

        if(str1.isEmpty()){
            editTextEmail.setError("Please enter email");
        }
       confirmButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               mAuth.sendPasswordResetEmail(str1).addOnCompleteListener(new OnCompleteListener<Void>() {
                   @Override
                   public void onComplete(@NonNull Task<Void> task) {
                       ConnectivityManager cm=(ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
                       if(task.isSuccessful()){
                           Toast.makeText(ChangePassActivity.this, "Email Sent", Toast.LENGTH_SHORT).show();
                       }
                       else if(cm.getActiveNetworkInfo()==null){
                           Toast.makeText(ChangePassActivity.this, "Check your internet connection and try again", Toast.LENGTH_SHORT).show();
                       }
                       else{
                           Toast.makeText(ChangePassActivity.this, "Email not sent. Please try again", Toast.LENGTH_SHORT).show();
                       }
                   }
               });
           }
       });

    }

}