package com.shady.personalmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.core.view.Change;

public class ChangeDataActivity extends AppCompatActivity {
   FragmentManager fragmentManager;
   Button changeUsername,changePassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_data);
        // Check whether the fragment container is available or not
      fragmentManager=getSupportFragmentManager();
      changeUsername=findViewById(R.id.changeUsername);
      changePassword=findViewById(R.id.changePassword);
      if(findViewById(R.id.fragment_container)!=null){
          if(savedInstanceState!=null){
              return;
          }
          Fragment3 fragment3= new Fragment3();
          FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
          fragmentTransaction.add(R.id.fragment_container,fragment3,null);
          fragmentTransaction.commit();
      }
            changeUsername.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeUsername();
                }
            });
      changePassword.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              changePassword();
          }
      });
        }
        private void changeUsername(){
            Fragment3 fragment3= new Fragment3();
            FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.fragment_container,fragment3,null);
            fragmentTransaction.commit();
        }
        private void changePassword(){
        Fragment4 fragment4= new Fragment4();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container,fragment4,null);
        fragmentTransaction.commit();

        }

        }

