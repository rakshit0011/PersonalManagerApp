package com.shady.personalmanager;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment4#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment4 extends Fragment {
    FirebaseAuth mAuth;
    EditText currentPassword,newPassword;
    Button done;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment4() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment4.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment4 newInstance(String param1, String param2) {
        Fragment4 fragment = new Fragment4();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        mAuth=FirebaseAuth.getInstance();
        view= inflater.inflate(R.layout.fragment_4, container, false);
       currentPassword=view.findViewById(R.id.currentPasswordEditText);
       newPassword=view.findViewById(R.id.newPasswordEditText);

       done=view.findViewById(R.id.done);
       done.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               changePass();
           }
       });
        return view;
    }
    private void changePass(){
        String curPass= currentPassword.getText().toString();
        String newPass= newPassword.getText().toString();
        if(newPass.isEmpty()&&curPass.isEmpty()){
            Toast.makeText(getActivity().getApplicationContext(), "Passwords cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(),curPass);
        if(newPass.equals(curPass)&&!newPass.isEmpty()&&!curPass.isEmpty()){
            newPassword.setError("Please enter a new password");
        }
        else if(newPass.isEmpty()&&!curPass.isEmpty()){
            newPassword.setError("Please enter a password");
        }
        else if(!newPass.isEmpty()&& curPass.isEmpty()){
            currentPassword.setError("Please enter a password");
        }
        else{
        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        user.updatePassword(newPass).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task1) {
                                 if(task1.isSuccessful()){
                                     Toast.makeText(getActivity().getApplicationContext(), "Password Changed!", Toast.LENGTH_SHORT).show();
                                 }
                                 else{
                                     Toast.makeText(getActivity().getApplicationContext(), "Error in changing password! Please try again", Toast.LENGTH_SHORT).show();
                                 }
                            }
                        });
                    }
                    else{
                        Toast.makeText(getActivity().getApplicationContext(), "Error! Please try again", Toast.LENGTH_SHORT).show();
                    }
                    }
                });
        }

    currentPassword.setText(null);
        newPassword.setText(null);
    }


}