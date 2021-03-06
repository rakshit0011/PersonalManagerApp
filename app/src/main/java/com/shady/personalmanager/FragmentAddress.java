package com.shady.personalmanager;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentAddress#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAddress extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentAddress() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentAddress.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentAddress newInstance(String param1, String param2) {
        FragmentAddress fragment = new FragmentAddress();
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
        view= inflater.inflate(R.layout.fragment_address, container, false);
        ListView namesListView1= view.findViewById(R.id.namesList1);
        ListView addressesListView= view.findViewById(R.id.addressesList1);
        ArrayList<String>names=new ArrayList<>();
        ArrayList<String>addresses= new ArrayList<>();
        ArrayAdapter<String>namesAdapter= new ArrayAdapter<>(getActivity().getApplication(), android.R.layout.simple_list_item_1,names);
        ArrayAdapter<String>addressAdapter= new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1,addresses);
        namesListView1.setAdapter(namesAdapter);
        addressesListView.setAdapter(addressAdapter);
        FirebaseAuth mAuth;
        mAuth= FirebaseAuth.getInstance();
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getUid()).child("Addresses");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    names.add(dataSnapshot.getKey());
                    addresses.add(dataSnapshot.getValue().toString());
                }
                namesAdapter.notifyDataSetChanged();
                addressAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }
}