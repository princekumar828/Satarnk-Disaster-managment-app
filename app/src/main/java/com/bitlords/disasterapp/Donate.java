package com.bitlords.disasterapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitlords.disasterapp.model.FundAddapter;
import com.bitlords.disasterapp.model.FundRaiserModel;
import com.bitlords.disasterapp.model.ReportModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Donate#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Donate extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Donate() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Donate.
     */
    // TODO: Rename and change types and number of parameters
    public static Donate newInstance(String param1, String param2) {
        Donate fragment = new Donate();
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
        View view = inflater.inflate(R.layout.fragment_donate, container, false);

        DatabaseReference databaseReference;

        RecyclerView recyclerView=view.findViewById(R.id.recycleFund);

        ArrayList<FundRaiserModel> list;

        FundAddapter fundAddapter;

       // databaseReference= FirebaseDatabase.getInstance().getReference("Funds");

        list=new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        fundAddapter=new FundAddapter(view.getContext(),list);
        recyclerView.setAdapter(fundAddapter);

        FundRaiserModel f1=new FundRaiserModel("Flood","At least 10 dead, 10 missing as landslide, flash floods hit Indonesia’s Sumatra island",
                "https://i.natgeofe.com/k/d25caf36-60a1-44c6-b078-348d78bc7772/Flash-Flood_Floods_KIDS_1022_16x9.jpg"
        ,"4000","35000");
        list.add(f1);
        FundRaiserModel f2=new FundRaiserModel("Climate change","The planet just shattered heat records for the ninth month in a row",
                "https://images.newscientist.com/wp-content/uploads/2019/07/03111113/what-is-global-warming-ct36ke_web.jpg"
                ,"5000","34445");
        list.add(f2);
        FundRaiserModel f3=new FundRaiserModel("Earthquake","Earthquake With Magnitude 6.0 Hits Southeast Of Mindanao Island In Philippines",
                "https://www.newsx.com/wp-content/uploads/2024/03/Untitled-design-2-6.png"
                ,"9000","450000");
        list.add(f3);
        FundRaiserModel f4=new FundRaiserModel("Drought","Unprecedented drought brings threat of starvation to millions in Ethiopia, Kenya, and Somalia",
                "https://joint-research-centre.ec.europa.eu/sites/default/files/styles/oe_theme_medium_2x_no_crop/public/2022-06/AdobeStock_82112003.jpeg"
                ,"5000","700000");
        list.add(f4);
//
//        databaseReference=FirebaseDatabase.getInstance().getReference("Fundes");
//        String postId=databaseReference.push().getKey();
//        databaseReference.child(postId).setValue(f1);

//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    FundRaiserModel re=dataSnapshot.getValue(FundRaiserModel.class);
//                    list.add(re);
//
//                }
//                fundAddapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });




        return view;
    }
}