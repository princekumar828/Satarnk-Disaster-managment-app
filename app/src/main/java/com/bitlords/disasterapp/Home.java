package com.bitlords.disasterapp;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bitlords.disasterapp.model.Addresss;
import com.bitlords.disasterapp.model.ReportModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private boolean locGran=false;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Home() {
        // Required empty public constructor
    }


    public static Home newInstance(String param1, String param2) {
        Home fragment = new Home();
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

        View view= inflater.inflate(R.layout.fragment_home, container, false);

        TextView location=view.findViewById(R.id.location);

        Button report=view.findViewById(R.id.home_report);
        Button sos=view.findViewById(R.id.home_sos);





        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(view.getContext(),Report.class);
                startActivity(intent);
            }
        });


        sos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(view.getContext(),Sos.class);
                startActivity(intent);
            }
        });

       DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("UserLoaction");
       FirebaseAuth auth=FirebaseAuth.getInstance();

        final String[] postalCodeCh = new String[1];


        databaseReference.child(auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Addresss addresss=snapshot.getValue(Addresss.class);
               location.setText(addresss.getCity()+" "+ addresss.getPostelCode());
               postalCodeCh[0] =addresss.getPostelCode();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        DatabaseReference refForCheck= FirebaseDatabase.getInstance().getReference("Reports");

        refForCheck.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ReportModel re=dataSnapshot.getValue(ReportModel.class);
                    if(Objects.equals(re.getPostCode(), postalCodeCh[0])){
                        view.findViewById(R.id.safe_notifi).setVisibility(View.GONE);
                        view.findViewById(R.id.not_safe_notifi).setVisibility(View.VISIBLE);
                       TextView textView1= view.findViewById(R.id.notificationTitle);
                        TextView textView= view.findViewById(R.id.notificationSubtext);
                       textView.setText(re.getAbout());
                       textView1.setText(re.getType());

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });









        return view;
    }


}