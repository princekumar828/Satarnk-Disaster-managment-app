package com.bitlords.disasterapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.bitlords.disasterapp.model.ReportModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Comunity#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Comunity extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Comunity() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Comunity.
     */
    // TODO: Rename and change types and number of parameters
    public static Comunity newInstance(String param1, String param2) {
        Comunity fragment = new Comunity();
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
        DatabaseReference databaseReference;
        // Inflate the layout for this fragme
        View view= inflater.inflate(R.layout.fragment_comunity, container, false);

        RecyclerView recyclerView=view.findViewById(R.id.recycleComunity);
        ArrayList<ReportModel> list;
        MyAddapterCom addapterCom;
        databaseReference= FirebaseDatabase.getInstance().getReference("Reports");
        list=new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        addapterCom=new MyAddapterCom(view.getContext(),list);
        recyclerView.setAdapter(addapterCom);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ReportModel re=dataSnapshot.getValue(ReportModel.class);
                    list.add(re);

                }
                addapterCom.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





    return view;
    }
}