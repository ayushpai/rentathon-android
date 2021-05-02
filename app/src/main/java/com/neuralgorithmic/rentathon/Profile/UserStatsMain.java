package com.neuralgorithmic.rentathon.Profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.r0adkll.slidr.Slidr;
import com.neuralgorithmic.rentathon.R;

public class UserStatsMain extends AppCompatActivity {
    ViewPager userProductPhotos;
    TextView productsBeingRented, since, rating;
    TextView productsRentedNum;
    private DocumentReference docRef;
    FirebaseFirestore mFirestore;
    FirebaseStorage storage;
    StorageReference storageRef;
    FirebaseAuth mAuth;
    FirebaseUser mFirebaseUser;
    String currentUserID;
    String date;
    String dt;
    ImageView backBtn;
    Button homeNav, rentNav, chatNav;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_statistics_main);
        Slidr.attach(this);

        userProductPhotos = findViewById(R.id.user_product_photos);
        userProductPhotos.setVisibility(View.GONE);
        productsBeingRented = findViewById(R.id.productsRented);
        since = findViewById(R.id.since);
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        since = findViewById(R.id.since);

        backBtn = findViewById(R.id.back_btn);
        rating = findViewById(R.id.vro);




        mFirebaseUser = mAuth.getCurrentUser();
        if(mFirebaseUser != null) {
            currentUserID = mFirebaseUser.getUid(); //Do what you need to do with the id
        }



        docRef = mFirestore.collection("users").document(mAuth.getCurrentUser().getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {

                    since.setText(document.get("Creation Date").toString());
                    productsBeingRented.setText(document.get("Current Product Num").toString());
                    rating.setText(document.get("Rating").toString() + "/5.0");





                }
            }

        });



        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(UserStatsMain.this, ProfileMain.class));
                overridePendingTransition(0,0);

            }
        });







    }



}
