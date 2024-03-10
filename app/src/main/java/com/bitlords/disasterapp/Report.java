package com.bitlords.disasterapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bitlords.disasterapp.databinding.ActivityLoginBinding;
import com.bitlords.disasterapp.databinding.ActivityReportBinding;
import com.bitlords.disasterapp.model.Addresss;
import com.bitlords.disasterapp.model.ReportModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Report extends AppCompatActivity {

    String type,postCode,area,about;
    ActivityReportBinding binding;

    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_report);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding= ActivityReportBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth=FirebaseAuth.getInstance();

        binding.SubmitReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               type=binding.typeReport.getText().toString().trim();
               postCode=binding.posttalCodeReport.getText().toString().trim();
               area=binding.areaReport.getText().toString().trim();
               about=binding.reportLine.getText().toString().trim();

                DatabaseReference databaseReference;

                if(type.isEmpty()){
                    binding.typeReport.setError("Enter Type");
                }
                else if(about.isEmpty()){
                    binding.reportLine.setError("Enter About");

                }else if(postCode.isEmpty()){
                    binding.posttalCodeReport.setError("Enter ");

                }
                else if(area.isEmpty()){
                    binding.areaReport.setError("Enter ");

                }
                else{
                    ReportModel reportModel=new ReportModel(type,postCode,area,about);
                    databaseReference=FirebaseDatabase.getInstance().getReference("Reports");
                    String postId=databaseReference.push().getKey();
                    databaseReference.child(postId).setValue(reportModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(Report.this, "Posted", Toast.LENGTH_SHORT).show();
                            binding.posttalCodeReport.setText("");
                            binding.areaReport.setText("");
                            binding.reportLine.setText("");
                            binding.typeReport.setText("");
                        }
                    });

                }

            }
        });

        binding.backReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });














    }
}