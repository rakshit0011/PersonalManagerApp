package com.shady.personalmanager;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.renderscript.Sampler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentNumber#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentNumber extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentNumber() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentNumber.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentNumber newInstance(String param1, String param2) {
        FragmentNumber fragment = new FragmentNumber();
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
        view=inflater.inflate(R.layout.fragment_number, container, false);
        ListView namesListView= view.findViewById(R.id.namelistViewX);
        ListView numberListView=view.findViewById(R.id.numberListV);
        ArrayList<String> namesArrayList= new ArrayList<>();
        ArrayList<String>numbersArrayList= new ArrayList<>();
        ArrayAdapter<String> namesAdapter= new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1,namesArrayList);
        ArrayAdapter<String>numbersAdapter= new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1,numbersArrayList);
        namesListView.setAdapter(namesAdapter);
        numberListView.setAdapter(numbersAdapter);
       FirebaseAuth mAuth=FirebaseAuth.getInstance();
       DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getUid()).child("Numbers");
       ref.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                   namesArrayList.add(dataSnapshot.getKey());
                   numbersArrayList.add(dataSnapshot.getValue().toString());
               }
               namesAdapter.notifyDataSetChanged();
               numbersAdapter.notifyDataSetChanged();
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });
        return view;
    }
}