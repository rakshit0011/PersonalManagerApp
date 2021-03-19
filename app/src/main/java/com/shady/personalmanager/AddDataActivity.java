package com.shady.personalmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddDataActivity extends AppCompatActivity {
    private EditText editTextName,editTextData;
    private DatabaseReference mDatabase;
    private boolean stateNumber,stateAddress,statePassword;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);
        Button saveButton,addressButton,numberButton,passwordButton;
        setTitle("Add Data");
        editTextName=findViewById(R.id.editTextTextPersonName);
        editTextData=findViewById(R.id.editTextTextPersonName2);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth= FirebaseAuth.getInstance();

        numberButton=findViewById(R.id.numberButton);
        addressButton=findViewById(R.id.addressButton);
        saveButton=findViewById(R.id.saveButton);
        passwordButton=findViewById(R.id.addPassword);

        stateNumber=false;
       stateAddress=false;

       passwordButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               editTextData.setHint("Add Password");
               stateAddress=false;
               statePassword=true;
               stateNumber=false;
           }
       });

       numberButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                editTextData.setHint("Add Number");
                statePassword=false;
                stateAddress=false;
                stateNumber=true;
            }
        });
        addressButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                editTextData.setHint("Add Address");
                 statePassword=false;
                stateAddress=true;
                stateNumber=false;
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=editTextName.getText().toString();
                String data= editTextData.getText().toString();

                if(stateNumber){
                    mDatabase.child("users").child(mAuth.getUid()).child("Numbers").child(name).setValue(data);
                    Toast.makeText(AddDataActivity.this, "Number Saved!", Toast.LENGTH_SHORT).show();
                }
                else if(stateAddress){
                    mDatabase.child("users").child(mAuth.getUid()).child("Addresses").child(name).setValue(data);
                    Toast.makeText(AddDataActivity.this, "Address Saved!", Toast.LENGTH_SHORT).show();
                }
                else{
                    mDatabase.child("users").child(mAuth.getUid()).child("Passwords").child(name).setValue(data);
                    Toast.makeText(AddDataActivity.this, "Password Saved!", Toast.LENGTH_SHORT).show();
                }
                editTextName.setText(null);
                editTextData.setText(null);

            }
        });

    }
}