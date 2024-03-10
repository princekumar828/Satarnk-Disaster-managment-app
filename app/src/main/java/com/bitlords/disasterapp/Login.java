package com.bitlords.disasterapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bitlords.disasterapp.databinding.ActivityLoginBinding;
import com.bitlords.disasterapp.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class Login extends AppCompatActivity {

    ActivityLoginBinding binding;


    FirebaseAuth auth;
    FirebaseFirestore firebaseFirestore;

    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_login);

        binding=ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseFirestore=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        binding.createId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(Login.this,Sign_up.class));
            }
        });

        if(user!=null){
            Intent intent = new Intent(Login.this, MainActivity.class);
            finishAffinity();
            startActivity(intent);
        }


        binding.butSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=binding.signInMail.getText().toString();
                String pass=binding.signInPass.getText().toString();



                if(email.isEmpty()){
                    binding.signInMail.setError("Enter Email");
                }
                else if(pass.isEmpty()){
                    binding.signInPass.setError("Enter Password");
                }
                else{
                    binding.progressBar.setVisibility(View.VISIBLE);
                    auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                binding.progressBar.setVisibility(View.GONE);
                                Intent intent = new Intent(Login.this, MainActivity.class);
                                finishAffinity();
                                startActivity(intent);

                            }
                            else{
                                binding.progressBar.setVisibility(View.GONE);
                                Toast.makeText(Login.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }


            }
        });




    }
}