package com.bitlords.disasterapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bitlords.disasterapp.databinding.ActivitySignUpBinding;
import com.bitlords.disasterapp.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class Sign_up extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);

        ActivitySignUpBinding binding;
        FirebaseAuth firebaseAuth;
        FirebaseFirestore firebaseFirestore;
        ProgressDialog progressDialog;



        binding=ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.progresSignUp.setVisibility(View.INVISIBLE);


        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();


        binding.registerButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name=binding.signupName.getText().toString();
                String email=binding.signupMail.getText().toString();
                String password=binding.signupPass.getText().toString();
                if(name.isEmpty()){
                    binding.signupName.setError("Enter Your Name");

                }else if(email.isEmpty()){
                    binding.signupMail.setError("Enter Your Email");
                } else if (password.isEmpty()) {
                    binding.signupPass.setError("Enter Your PassWord");

                }
                else {
                    binding.progresSignUp.setVisibility(View.VISIBLE);
                    firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            User user=new User(name,email,password);

                            String id =task.getResult().getUser().getUid();
                            firebaseFirestore.collection("users").document(id).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        binding.progresSignUp.setVisibility(View.INVISIBLE);
                                        Toast.makeText(Sign_up.this,"User created", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(Sign_up.this,Login.class));
                                    }

                                }
                            });

                        }
                    });

                }

            }
        });
        Intent signIn=new Intent(Sign_up.this, Login.class);
        binding.alredyId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(signIn);
            }
        });




    }
}