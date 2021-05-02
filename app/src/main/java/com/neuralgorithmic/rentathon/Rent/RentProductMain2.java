package com.neuralgorithmic.rentathon.Rent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;

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
import com.google.firebase.firestore.WriteBatch;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.r0adkll.slidr.Slidr;
import com.neuralgorithmic.rentathon.Chat.ChatMain;
import com.neuralgorithmic.rentathon.Home.Home;

import com.neuralgorithmic.rentathon.Product.ProductMain;
import com.neuralgorithmic.rentathon.Product.ProductOverLord;
import com.neuralgorithmic.rentathon.Profile.ProfileMain;
import com.neuralgorithmic.rentathon.R;
import com.neuralgorithmic.rentathon.Profile.UserHomeMain;
import com.rtchagas.pingplacepicker.PingPlacePicker;


public class RentProductMain2 extends AppCompatActivity{

    TextView productName, productValue ,rentalCost, condition, descripton, renterName, renterCity, price2, ratingAmntTxt;
    Button cartNavBtn, chatNavBtn, profileNavBtn, nxtBtn, deleteBtn, morePhotos, editBtn, insights, moreProducts;
    FirebaseFirestore mFirestore;
    ImageView productPrimaryImage, rentersImage, backBtn, ratingPic;
    private DocumentReference docRef, docRef2;
    FirebaseStorage storage;
    StorageReference storageRef;
    ViewFlipper imageSlideShow;
    int random;
    ImageView image1, image2, image3;
    Uri uri1, uri2, uri;
    public static String selectedName;
    FirebaseAuth mAuth;
    public String renterUID = "";
    public static String UID;
    FirebaseUser mFirebaseUser;
    String renterNameString;
    String currentName;
    public Boolean menuClicked = true;
    CollectionReference collectionRef;
    int Size;
    double userRating;
    int totalViews;
    int uniqueVisitors;
    public static boolean fromHome = false;
    ImageView menu;

    public String renteeUID = "";
    int PPR = 1;
    static public int scrollY = 0;
    TextView searchBackground;
    ScrollView mainScrollView;
    public String productPublicName;

    Button share, report;

    Button homeNav, profileNav, chatNav;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_product_main2);

        ProfileMain.fromProfile = false;
        ProductMain.fromPreview = false;

        Slidr.attach(this);
        overridePendingTransition(0, 0);
        mainScrollView = findViewById(R.id.scrollView2);

        UserHomeMain.fromUserHomeMain = false;

        menu = findViewById(R.id.menu_btn);
        searchBackground = findViewById(R.id.search_background);


        share = findViewById(R.id.share_btn);
        report = findViewById(R.id.report_btn);
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        mAuth = FirebaseAuth.getInstance();

        ratingAmntTxt = findViewById(R.id.rating_amount_txt);
        ratingPic = findViewById(R.id.rating_pic);


        moreProducts = findViewById(R.id.user_info_btn);
        editBtn = findViewById(R.id.edit_btn);
        price2 = findViewById(R.id.price2);
        imageSlideShow = findViewById(R.id.product_photos);
        rentersImage = findViewById(R.id.profile_pic_output);
        productName = findViewById(R.id.product_name_input);
        productPrimaryImage = findViewById(R.id.product_pic_input);
        renterCity = findViewById(R.id.city_user_txt);
        backBtn = findViewById(R.id.back_btn);
        deleteBtn = findViewById(R.id.delete_btn);
        insights = findViewById(R.id.insights_btn);


        condition = findViewById(R.id.condition_txt_view);
        morePhotos = findViewById(R.id.more_photos_btn);
        descripton = findViewById(R.id.product_description_input);

        homeNav = findViewById(R.id.home_button_nav);
        profileNav = findViewById(R.id.user_button_nav);
        chatNav = findViewById(R.id.chat_button_nav);

        nxtBtn = findViewById(R.id.rent_button);
        renterName = findViewById(R.id.renter_name);
        //mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        docRef = mFirestore.collection("products").document(String.valueOf(Home.userProductSelection));
        imageSlideShow.getLayoutParams().height = 0;
        //imageSlideShow.getBackground().setVisible(false, false);
        imageSlideShow.requestLayout();



        editBtn.bringToFront();

        morePhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(RentProductMain2.this, MorePhotos.class));

            }
        });

        storageRef.child("images/" + String.valueOf(Home.userProductSelection)).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
                Glide.with(RentProductMain2.this).load(uri).into(productPrimaryImage);



                mainScrollView.getViewTreeObserver()
                        .addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                            @Override
                            public void onScrollChanged() {
                                if (mainScrollView.getScrollY() > productPrimaryImage.getHeight()- searchBackground.getHeight()) {
                                    searchBackground.setBackgroundResource(R.color.accent_background);
                                    searchBackground.setText(productPublicName);
                                }
                                else{
                                    searchBackground.setBackgroundResource(R.color.transparent);
                                    searchBackground.setText("");
                                }
                            }
                        });




            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                //showMessage("Please connect to the internet to view the products");

            }
        });






        imageSlideShow.setFlipInterval(3500);
        imageSlideShow.setAutoStart(true);





        imageSlideShow.setInAnimation(RentProductMain2.this, android.R.anim.slide_in_left);
        imageSlideShow.setOutAnimation(RentProductMain2.this, android.R.anim.slide_out_right);

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(RentProductMain2.this, EditProduct.class));
                overridePendingTransition(0,0);


            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(RentProductMain2.this);
                builder.setCancelable(true);
                builder.setTitle("Delete Product");
                builder.setMessage("Are you sure you want to delete this product?");


                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();

                    }
                });

                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        docRef.delete();
                        storageRef.child("images/" + String.valueOf(Home.userProductSelection)).delete();
                        showMessage("Product Deleted");
                        startActivity(new Intent(RentProductMain2.this, Home.class));
                        overridePendingTransition(0,0);

                    }
                });

                builder.show();




            }
        });


        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {





                DocumentSnapshot document = task.getResult();
                if(document.exists()){

//                    productPrimaryImage.setImageURI(Uri.parse(document.get("Primary Image").toString()));

                    productName.setText(document.get("Product Name").toString());

                    productPublicName = document.get("Product Name").toString();
                    condition.setText(document.get("Product Condition").toString());
                    descripton.setText(document.get("Product Description").toString());
                    price2.setText("$" + document.get("Rental Fee").toString());

                    totalViews = Integer.parseInt(document.get("Views").toString());
                    uniqueVisitors = Integer.parseInt(document.get("Unique Visitors").toString());

                    renterUID = document.get("User ID").toString();

                    mFirebaseUser = mAuth.getCurrentUser();
                    if(mFirebaseUser != null) {
                        renteeUID = mFirebaseUser.getUid(); //Do what you need to do with the id
                    }

                    if(renterUID.equals(renteeUID) && uniqueVisitors == 0){

                        uniqueVisitors++;
                        WriteBatch batch = mFirestore.batch();
                        docRef = mFirestore.collection("products").document(String.valueOf(Home.userProductSelection));
                        batch.update(docRef, "Unique Visitors", String.valueOf(uniqueVisitors));
                        batch.commit();


                    }

                    else if(!renterUID.equals(renteeUID)){

                        uniqueVisitors++;
                        WriteBatch batch = mFirestore.batch();
                        docRef = mFirestore.collection("products").document(String.valueOf(Home.userProductSelection));
                        batch.update(docRef, "Unique Visitors", String.valueOf(uniqueVisitors));
                        batch.commit();


                    }





                    if(fromHome) {
                        totalViews++;
                        WriteBatch batch = mFirestore.batch();
                        docRef = mFirestore.collection("products").document(String.valueOf(Home.userProductSelection));
                        batch.update(docRef, "Views", String.valueOf(totalViews));
                        batch.commit();
                    }


                    UID = document.get("User ID").toString();
                    //renterName.setText(document.get("User Name").toString());
                    //renterCity.setText(document.get("User City").toString());
                    docRef2 = mFirestore.collection("users").document(UID);

                    docRef2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {


                                renterName.setText(document.get("Name").toString());
                                renterNameString = (document.get("Name").toString());
                                renterCity.setText(document.get("City").toString());
                                selectedName = document.get("Name").toString();
                                moreProducts.setHint("More Products From " + selectedName );
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
                                    nxtBtn.setVisibility(View.INVISIBLE);

                                    menu.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if(menuClicked) {


                                                deleteBtn.setVisibility(View.VISIBLE);
                                                insights.setVisibility(View.VISIBLE);
                                                editBtn.setVisibility(View.VISIBLE);
                                                menuClicked = false;

                                            }
                                            else if(!menuClicked){

                                                deleteBtn.setVisibility(View.INVISIBLE);
                                                insights.setVisibility(View.INVISIBLE);
                                                editBtn.setVisibility(View.INVISIBLE);
                                                menuClicked = true;
                                            }

                                        }
                                    });


                                }
                                else{

                                    menu.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if(menuClicked) {

                                                share.setVisibility(View.VISIBLE);
                                                report.setVisibility(View.VISIBLE);
                                                menuClicked = false;

                                            }
                                            else if(!menuClicked){
                                                share.setVisibility(View.INVISIBLE);
                                                report.setVisibility(View.INVISIBLE);
                                                menuClicked = true;

                                            }

                                        }
                                    });

                                }


                            }
                        }
                    });




                    rentersImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(RentProductMain2.this, UserHomeMain.class));
                            //overridePendingTransition(0,0);

                        }
                    });



                    storageRef.child("users/" + UID).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            // Got the download URL for 'users/me/profile.png'
                            Glide.with(RentProductMain2.this).load(uri).into(rentersImage);



                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle any errors
                            //showMessage("Please connect to the internet to view the products");
                        }
                    });






                }



            }
        });




















        nxtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PingPlacePicker.IntentBuilder builder = new PingPlacePicker.IntentBuilder();
                builder.setAndroidApiKey("AIzaSyCHBd7NQPWdOUor_B18mGpbrvBSkHo8gZc").setMapsApiKey("AIzaSyCHBd7NQPWdOUor_B18mGpbrvBSkHo8gZc");

                try {
                    startActivityForResult(builder.build(RentProductMain2.this), PPR);
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }


                overridePendingTransition(0,0);

            }
        });

        moreProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(RentProductMain2.this, UserHomeMain.class));
                overridePendingTransition(0,0);

            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                    startActivity(new Intent(RentProductMain2.this, ProductOverLord.class));
                    overridePendingTransition(0, 0);



            }
        });
        insights.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RentProductMain2.this, ProductInsights.class));
                overridePendingTransition(0,0);

            }
        });

        homeNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(RentProductMain2.this, Home.class));
                overridePendingTransition(0,0);
            }
        });

        chatNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(RentProductMain2.this, ChatMain.class));
                overridePendingTransition(0,0);

            }
        });

        profileNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(RentProductMain2.this, ProfileMain.class));
                overridePendingTransition(0,0);


            }
        });








        //flipperImage(storageRef.child("images/" + String.valueOf(Home.userProductSelection) + "_" + "0").getDownloadUrl());




        storageRef.child("images/" + String.valueOf(Home.userProductSelection) + "_" + "0").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                morePhotos.setVisibility(View.VISIBLE);




            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
/*
        storageRef.child("images/" + String.valueOf(Home.userProductSelection) + "_" + "1").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {


                ImageView imageView = new ImageView(RentProductMain2.this);
                Glide.with(RentProductMain2.this).load(uri).into(imageView);



                imageSlideShow.addView(imageView);



            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                imageSlideShow.getLayoutParams().height = 0;
                //imageSlideShow.getBackground().setVisible(false, false);
                imageSlideShow.requestLayout();
            }
        });
        storageRef.child("images/" + String.valueOf(Home.userProductSelection) + "_" + "2").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                ImageView imageView = new ImageView(RentProductMain2.this);
                Glide.with(RentProductMain2.this).load(uri).into(imageView);



                imageSlideShow.addView(imageView);



            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                imageSlideShow.getLayoutParams().height = 0;
                //imageSlideShow.getBackground().setVisible(false, false);
                imageSlideShow.requestLayout();
            }
        });*/









    }










    private void showMessage(String message){

        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PPR){
            if(resultCode == RESULT_OK){


            }
        }

    }








}
