package com.neuralgorithmic.rentathon.Rent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.r0adkll.slidr.Slidr;
import com.neuralgorithmic.rentathon.Home.Home;
import com.neuralgorithmic.rentathon.R;

public class MorePhotos extends AppCompatActivity {

    private DocumentReference docRef, docRef2;
    private FirebaseAuth mAuth;
    FirebaseFirestore mFirestore;
    StorageReference storageRef;
    FirebaseStorage storage;
    ImageView i1, i2, i3;
    Button cartNavBtn, chatNavBtn, profileNavBtn;
    ImageView nxtBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_photos);
        Slidr.attach(this);


        nxtBtn = findViewById(R.id.back_btn);

        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        i1 = findViewById(R.id.i1);
        i2 = findViewById(R.id.i2);
        RentProductMain.fromHome = false;
        i3 = findViewById(R.id.i3);



        storageRef.child("images/" + String.valueOf(Home.userProductSelection) + "_" + "0").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {


                Glide.with(MorePhotos.this).load(uri).into(i1);





            }
        });

        storageRef.child("images/" + String.valueOf(Home.userProductSelection) + "_" + "1").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {


                Glide.with(MorePhotos.this).load(uri).into(i2);





            }
        });

        storageRef.child("images/" + String.valueOf(Home.userProductSelection) + "_" + "2").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {


                Glide.with(MorePhotos.this).load(uri).into(i3);





            }
        });


        nxtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MorePhotos.this, RentProductMain.class));
                overridePendingTransition(0,0);

            }
        });





    }
}
