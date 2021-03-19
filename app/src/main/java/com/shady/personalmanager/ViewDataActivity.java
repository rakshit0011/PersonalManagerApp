package com.shady.personalmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.content.SharedPreferences;

import android.renderscript.Sampler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;


public class ViewDataActivity extends AppCompatActivity {
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);
        fragmentManager=getSupportFragmentManager();
        if(findViewById(R.id.frameLayout)!=null){
            if(savedInstanceState!=null){
                // If the activity is resumed i.e. the fragment has been added already
                return;
            }
           fragmentTransaction=fragmentManager.beginTransaction();
            FragmentPassword fragmentPassword= new FragmentPassword();
            fragmentTransaction.add(R.id.frameLayout,fragmentPassword,null);
            fragmentTransaction.commit();
    }
    }
    public void viewPasswords(View view){
        fragmentTransaction=fragmentManager.beginTransaction();
        FragmentPassword fragmentPassword=new FragmentPassword();
        fragmentTransaction.add(R.id.frameLayout,fragmentPassword,null);
        fragmentTransaction.commit();
    }

    public void viewAddresses(View view){
        fragmentTransaction=fragmentManager.beginTransaction();
        FragmentAddress fragmentAddress=new FragmentAddress();
        fragmentTransaction.add(R.id.frameLayout,fragmentAddress,null);
        fragmentTransaction.commit();
    }
    public void viewNumbers(View view){
        fragmentTransaction= fragmentManager.beginTransaction();
        FragmentNumber fragmentNumber= new FragmentNumber();
        fragmentTransaction.add(R.id.frameLayout,fragmentNumber,null);
        fragmentTransaction.commit();
    }
}