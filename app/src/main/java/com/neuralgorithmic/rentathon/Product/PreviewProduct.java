package com.neuralgorithmic.rentathon.Product;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.neuralgorithmic.rentathon.Profile.ProfileMain;
import com.neuralgorithmic.rentathon.Profile.UserHomeMain;
import com.neuralgorithmic.rentathon.R;

public class PreviewProduct extends AppCompatActivity {

    TextView productName, productValue ,rentalCost, condition, descripton, renterName, renterCity, price2, ratingAmntTxt;
    Button cartNavBtn, chatNavBtn, profileNavBtn, nxtBtn, deleteBtn, morePhotos, editBtn, insights, moreProducts;
    FirebaseFirestore mFirestore;
    ImageView productPrimaryImage, rentersImage, backBtn, ratingPic;
    private DocumentReference docRef, docRef2;
    FirebaseStorage storage;
    StorageReference storageRef;
    ViewFlipper imageSlideShow;
    ImageView image1, image2, image3;
    Uri uri1, uri2, uri;
    public static String selectedName;
    FirebaseAuth mAuth;
    public String renterUID = "";
    public static String UID;
    FirebaseUser mFirebaseUser;
    String renterNameString;
    String currentName;
    CollectionReference collectionRef;
    int Size;
    double userRating;
    int totalViews;
    int uniqueVisitors;
    public static boolean fromHome = false;

    public String renteeUID = "";

    TextView daily, weekly, monthly;
    public int price;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.owner_preview_product);
        ProfileMain.fromProfile = false;
        ProductMain.fromPreview = true;


        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        mAuth = FirebaseAuth.getInstance();

        ratingAmntTxt = findViewById(R.id.rating_amount_txt);
        ratingPic = findViewById(R.id.rating_pic);



        price2 = findViewById(R.id.price2);
        imageSlideShow = findViewById(R.id.product_photos);
        rentersImage = findViewById(R.id.profile_pic_output);
        productName = findViewById(R.id.product_name_input);
        productPrimaryImage = findViewById(R.id.product_pic_input);
        renterCity = findViewById(R.id.city_user_txt);
        backBtn = findViewById(R.id.back_btn);
        daily = findViewById(R.id.daily);
        weekly = findViewById(R.id.weekly);
        monthly = findViewById(R.id.monthly);



        condition = findViewById(R.id.condition_txt_view);
        morePhotos = findViewById(R.id.more_photos_btn);
        descripton = findViewById(R.id.product_description_input);


        nxtBtn = findViewById(R.id.rent_button);
        renterName = findViewById(R.id.renter_name);
        //mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

        imageSlideShow.getLayoutParams().height = 0;
        //imageSlideShow.getBackground().setVisible(false, false);
        imageSlideShow.requestLayout();
        descripton.setText(ProductMain.descriptionstatic);
        condition.setText(ProductMain.conditionstatic);

        productName.setText(ProductMain.namestatic);
        if(ProductMain.pricestatic != null){
            price2.setText("$"+ProductMain.pricestatic);
            price = Integer.valueOf(ProductMain.pricestatic);
            daily.setText("$" + price);
            weekly.setText("$" + (price * 7));
            monthly.setText("$" + (price * 14));
        }
        else{
            price = 0;
            price2.setText("$0");
            daily.setText("$" +0);
            weekly.setText("$" + (0));
            monthly.setText("$" + (0));
        }



        Glide.with(PreviewProduct.this).load(ProductMain.pickedImgUri).into(productPrimaryImage);







        imageSlideShow.setInAnimation(PreviewProduct.this, android.R.anim.slide_in_left);
        imageSlideShow.setOutAnimation(PreviewProduct.this, android.R.anim.slide_out_right);


/*
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                docRef.delete();
                storageRef.child("images/" + String.valueOf(Home.userProductSelection)).delete();
                startActivity(new Intent(PreviewProduct.this, Home.class));
                overridePendingTransition(0,0);



            }
        });*/



        //renterName.setText(document.get("User Name").toString());
        //renterCity.setText(document.get("User City").toString());
        docRef2 = mFirestore.collection("users").document(mAuth.getUid());

        docRef2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                DocumentSnapshot document = task.getResult();
                if (document.exists()) {


                    renterName.setText(document.get("Name").toString());
                    renterNameString = (document.get("Name").toString());
                    renterCity.setText(document.get("City").toString());



                    ratingAmntTxt.setText(document.get("Num Reviews").toString() + " Reviews");

                    userRating = Double.parseDouble(document.get("Rating").toString());

                    if(userRating == 0){
                        ratingPic.setImageResource(R.drawable.star5);
                    }

                    else if (userRating <= 0.5){
                        ratingPic.setImageResource(R.drawable.star05);
                    }
                    else if (userRating <= 1){
                        ratingPic.setImageResource(R.drawable.star1);
                    }
                    else if (userRating <= 1.5){
                        ratingPic.setImageResource(R.drawable.star15);
                    }
                    else if (userRating <= 2){
                        ratingPic.setImageResource(R.drawable.star2);
                    }
                    else if (userRating <= 2.5){
                        ratingPic.setImageResource(R.drawable.star25);
                    }
                    else if (userRating <= 3){
                        ratingPic.setImageResource(R.drawable.star3);
                    }
                    else if (userRating <= 3.5){
                        ratingPic.setImageResource(R.drawable.star35);
                    }
                    else if (userRating <= 4){
                        ratingPic.setImageResource(R.drawable.star4);
                    }
                    else if (userRating <= 4.5){
                        ratingPic.setImageResource(R.drawable.star45);
                    }
                    else if (userRating <= 5){
                        ratingPic.setImageResource(R.drawable.star5);
                    }




                    mFirebaseUser = mAuth.getCurrentUser();
                    if(mFirebaseUser != null) {
                        currentName = mFirebaseUser.getDisplayName();
                    }
                    if(renterUID.equals(renteeUID)){


                    }


                }
            }
        });




        rentersImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PreviewProduct.this, UserHomeMain.class));
                //overridePendingTransition(0,0);

            }
        });



        storageRef.child("users/" + mAuth.getUid()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
                Glide.with(PreviewProduct.this).load(uri).into(rentersImage);



            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                //showMessage("Please connect to the internet to view the products");
            }
        });






















        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(UserHomeMain.fromUserHomeMain) {


                    startActivity(new Intent(PreviewProduct.this, ProductMain.class));
                    overridePendingTransition(0, 0);
                }

            }
        });




        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PreviewProduct.this, ProductMain.class));
                overridePendingTransition(0,0);
            }
        });






        //flipperImage(storageRef.child("images/" + String.valueOf(Home.userProductSelection) + "_" + "0").getDownloadUrl());














    }




    private void showMessage(String message){

        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }
}
