package com.bitlords.disasterapp;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bitlords.disasterapp.model.Addresss;
import com.bitlords.disasterapp.model.User;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    Home homeFra = new Home();
    Comunity comunityFra = new Comunity();
    Donate donateFra = new Donate();
    DrawerLayout drawerLayout;
    FrameLayout frameLayout;
    NavigationView logOut;
    FirebaseFirestore firebaseFirestore;

    FirebaseUser firebaseUser;

    DatabaseReference databaseReference;

    FirebaseAuth auth;

    static double longitude;
    static double latitude;
    static String address;
    static String city;

     static String postelCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();


        //navigation drawer
        drawerLayout = findViewById(R.id.drawerLayout);

        ImageView drawerButton = findViewById(R.id.btnDrawer);

        drawerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.open();
            }
        });

        //Drawer menu

        TextView userName = findViewById(R.id.drawerUserName);
        TextView userMail = findViewById(R.id.drawerEmail);

        firebaseUser = auth.getCurrentUser();
        if (firebaseUser != null) {
            String nam = firebaseUser.getDisplayName();
            String email = firebaseUser.getEmail();

        }


        logOut = findViewById(R.id.drawerMenunav);
        logOut.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int drId = menuItem.getItemId();
                if (drId == R.id.logOut) {
                    FirebaseAuth.getInstance().signOut();
                    Toast.makeText(MainActivity.this, "Loged Out", Toast.LENGTH_SHORT).show();
                    finishAffinity();
                    startActivity(new Intent(MainActivity.this, Login.class));
                }
                return false;
            }
        });


        //Bottom nav

        bottomNavigationView = findViewById(R.id.b_nav);
        frameLayout = findViewById(R.id.cont);
        getSupportFragmentManager().beginTransaction().replace(R.id.cont, homeFra).commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int idd = menuItem.getItemId();
                if (idd == R.id.home) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.cont, homeFra).commit();
                    return true;
                } else if (idd == R.id.donate) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.cont, donateFra).commit();

                    return true;

                } else if (idd == R.id.comunity) {

                    getSupportFragmentManager().beginTransaction().replace(R.id.cont, comunityFra).commit();
                    return true;
                }
                return false;
            }
        });




        FusedLocationProviderClient fusedLocationClient;

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityResultLauncher<String[]> locationPermissionRequest =
                    registerForActivityResult(new ActivityResultContracts
                                    .RequestMultiplePermissions(), result -> {

                            }
                    );

            locationPermissionRequest.launch(new String[] {
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
            });
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {


                            Geocoder geocoder=new Geocoder(MainActivity.this, Locale.getDefault());
                            try {
                                List<Address> addresses=geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                                postelCode=addresses.get(0).getPostalCode();
                                city=addresses.get(0).getLocality();
                                address=addresses.get(0).getAddressLine(0);
                                latitude=addresses.get(0).getLatitude();
                                longitude=addresses.get(0).getLongitude();
                                Addresss add=new Addresss(auth.getUid(),longitude,latitude,address,city,postelCode);
                                String uid=auth.getUid();

                                databaseReference=FirebaseDatabase.getInstance().getReference("UserLoaction");
                                databaseReference.child(uid).setValue(add);

                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }

                        }
                    }
                });

        firebaseFirestore=FirebaseFirestore.getInstance();
        String uid=auth.getUid();


//        DocumentReference documentReference=firebaseFirestore.collection("users").document(uid);
//
//        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
//
//              String na=  value.getString("name");
//              String mai=  value.getString("email");
//              userMail.setText(mai);
//              userName.setText(na);
//
//
//            }
//        });
















    }
}