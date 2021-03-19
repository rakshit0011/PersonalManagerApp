package com.shady.personalmanager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.NameList;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private Intent intent;
    private FirebaseAuth mAuth;
    private TextView phoneNumberTextView, passwordTextView,addressTextView;
    private String username, UID;
    private long numberOfPasswords,numberOfPhoneNumbers,numberOfAddresses;
    SharedPreferences sharedPreferences;

  // Using a menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("NonConstantResourceId")

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // When someone selects an item in the menu
        mAuth = FirebaseAuth.getInstance();
        switch (item.getItemId()) {
            case R.id.logOut:
                // Let us use an Alert Dialog here. When the user clicks on Log Out, an alert dialog will get displayed as "Do you really want to log out?"

                new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Are you sure!?")
                        .setMessage("Do you want to log out?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // To sign Out the user and go back to the MainActivity

                        mAuth.signOut();
                        intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        Toast.makeText(HomeActivity.this, "You have been logged out!", Toast.LENGTH_SHORT).show();

                    }
                }).setNegativeButton("No", null).show();
                return true;
            case R.id.addData:
                intent = new Intent(HomeActivity.this, AddDataActivity.class);
                startActivity(intent);
                return true;

            case R.id.viewData:
                intent = new Intent(HomeActivity.this, ViewDataActivity.class);
                startActivity(intent);
                return true;

                case R.id.changeData:
                intent=new Intent(HomeActivity.this,ChangeDataActivity.class);
                startActivity(intent);
                return true;
                default:
                return false;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setTitle("Home");

        FirebaseAuth mAuth1;
        DatabaseReference ref,myRef;

        ref = FirebaseDatabase.getInstance().getReference();
        mAuth=FirebaseAuth.getInstance();
        mAuth1 = FirebaseAuth.getInstance();
        Button saveButton = findViewById(R.id.saveUsernameButton);
        EditText editTextName = findViewById(R.id.userNameEditText);
        TextView textView = findViewById(R.id.textView2);
        UID = mAuth1.getUid();
        phoneNumberTextView = findViewById(R.id.phoneNumberTextView);
        passwordTextView=findViewById(R.id.passwordTextView);
        addressTextView=findViewById(R.id.addressTextView);

        numberOfPasswords=-1;
        sharedPreferences = getSharedPreferences("com.shady.personalmanager", MODE_PRIVATE);
         myRef= FirebaseDatabase.getInstance().getReference();

//         Finding the number of passwords, phone numbers and addresses stored by the user.
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                numberOfPasswords=snapshot.child("users").child(UID).child("Passwords").getChildrenCount();
                numberOfPhoneNumbers=snapshot.child("users").child(UID).child("Numbers").getChildrenCount();
                numberOfAddresses=snapshot.child("users").child(UID).child("Addresses").getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
// Setting and then displaying a unique username to the current user which will be stored in the shared preferences with a unique user ID
        if (sharedPreferences.getString(UID, "").isEmpty()) {
            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    username = editTextName.getText().toString();
                    sharedPreferences.edit().putString(UID, username).apply();
                    saveButton.setVisibility(View.INVISIBLE);
                    editTextName.setVisibility(View.INVISIBLE);
                    String str = "Welcome, " + sharedPreferences.getString(UID, "") + "!";
                    textView.setText(str);
                }
            });
        } else {
            String str = "Welcome , \n" + sharedPreferences.getString(UID, "")+"!";
            textView.setText(str);
            saveButton.setVisibility(View.GONE);
            editTextName.setVisibility(View.GONE);
        }



}


    public void showData(View view){

        String s="Number of Passwords saved = "+numberOfPasswords;
        String s1="Number of Phone Numbers saved = "+numberOfPhoneNumbers;
        String s2="Number of Addresses saved ="+numberOfAddresses;
        if(numberOfPasswords<0){
            Toast.makeText(this, "There was a problem connecting with the server. Please try again", Toast.LENGTH_SHORT).show();
        }
        else{
            passwordTextView.setText(s);
            phoneNumberTextView.setText(s1);
            addressTextView.setText(s2);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        sharedPreferences=getSharedPreferences("com.shady.personalmanager",MODE_PRIVATE);
        String str= sharedPreferences.getString(mAuth.getUid(),"");
        TextView textView2=findViewById(R.id.textView2);
        String newStr= "Welcome , "+str;
        textView2.setText(newStr );
    }


}


