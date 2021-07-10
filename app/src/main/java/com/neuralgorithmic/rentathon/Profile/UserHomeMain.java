package com.neuralgorithmic.rentathon.Profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.r0adkll.slidr.Slidr;
import com.neuralgorithmic.rentathon.Home.Home;
import com.neuralgorithmic.rentathon.R;
import com.neuralgorithmic.rentathon.Rent.RentProductMain;

public class UserHomeMain extends AppCompatActivity {
    TextView renterName, ratingAmntTxt;
    ImageView renterImage;
    private DocumentReference docRef, docRef2;
    FirebaseStorage storage;
    Button cartNavBtn, chatNavBtn, profileNavBtn;
    ImageView homebar;

    StorageReference storageRef;
    FirebaseFirestore mFirestore;
    CollectionReference collectionReference;
    public ImageView refreshbtn, productImage1, productImage2, productImage3, productImage4, productImage5, productImage6, productImage7, productImage8, productImage9, productImage10, productImage11, productImage12, productImage13, productImage14, productImage15, productImage16, productImage17, productImage18, productImage19, productImage20, productImage21, productImage22, productImage23, productImage24, backBtn, ratingPic;;
    TextView productValue1, productName1, productValue2, productName2, productValue3, productName3, productValue4, productName4, productValue5, productName5, productValue6, productName6, productValue7, productName7, productValue8, productName8, productValue9, productName9, productValue10, productName10, productValue11, productName11, productValue12, productName12, productValue13, productName13, productValue14, productName14, productValue15, productName15, productValue16, productName16, productValue17, productName17, productValue18, productName18, productValue19, productName19, productValue20, productName20, productValue21, productName21, productValue22, productName22, productValue23, productName23, productValue24, productName24;

    ConstraintLayout a, b, c, d, e, f, g, h, i1, j, k, l, m, n, o, p, q, r, s, t, u, v1, w, x, y, z;
    Query query;
    public int totalProductsByUser;
    public int loopMaster = 0;
    public double userRating;



    static public boolean fromUserHomeMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.market_user_products);
        renterImage = findViewById(R.id.profile_pic_output);
        renterName = findViewById(R.id.renter_name);
        Slidr.attach(this);
        RentProductMain.fromHome = false;
        fromUserHomeMain = true;

        ratingAmntTxt = findViewById(R.id.rating_amount_txt);
        ratingPic = findViewById(R.id.rating_pic);





        backBtn = findViewById(R.id.back_btn);

        homebar = findViewById(R.id.home_bar);
        productImage1 = findViewById(R.id.product_1);
        productValue1 = findViewById(R.id.productValue1);
        productName1 = findViewById(R.id.productName1);
        productImage2 = findViewById(R.id.product_2);
        productValue2 = findViewById(R.id.productValue2);
        productName2 = findViewById(R.id.productName2);
        productImage3 = findViewById(R.id.product_3);
        productValue3 = findViewById(R.id.productValue3);
        productName3 = findViewById(R.id.productName3);
        productImage4 = findViewById(R.id.product_4);
        productValue4 = findViewById(R.id.productValue4);
        productName4 = findViewById(R.id.productName4);
        productImage5 = findViewById(R.id.product_5);
        productValue5 = findViewById(R.id.productValue5);
        productName5 = findViewById(R.id.productName5);
        productImage6 = findViewById(R.id.product_6);
        productValue6 = findViewById(R.id.productValue6);
        productName6 = findViewById(R.id.productName6);
        productImage7 = findViewById(R.id.product_7);
        productValue7 = findViewById(R.id.productValue7);
        productName7 = findViewById(R.id.productName7);
        productImage8 = findViewById(R.id.product_8);
        productValue8 = findViewById(R.id.productValue8);
        productName8 = findViewById(R.id.productName8);
        productImage9 = findViewById(R.id.product_9);
        productValue9 = findViewById(R.id.productValue9);
        productName9 = findViewById(R.id.productName9);
        productImage10 = findViewById(R.id.product_10);
        productValue10 = findViewById(R.id.productValue10);
        productName10 = findViewById(R.id.productName10);
        productImage11 = findViewById(R.id.product_11);
        productValue11 = findViewById(R.id.productValue11);
        productName11 = findViewById(R.id.productName11);
        productImage12 = findViewById(R.id.product_12);
        productValue12 = findViewById(R.id.productValue12);
        productName12 = findViewById(R.id.productName12);
        productImage13 = findViewById(R.id.product_13);
        productValue13 = findViewById(R.id.productValue13);
        productName13 = findViewById(R.id.productName13);
        productImage14 = findViewById(R.id.product_14);
        productValue14 = findViewById(R.id.productValue14);
        productName14 = findViewById(R.id.productName14);
        productImage15 = findViewById(R.id.product_15);
        productValue15 = findViewById(R.id.productValue15);
        productName15 = findViewById(R.id.productName15);
        productImage16 = findViewById(R.id.product_16);
        productValue16 = findViewById(R.id.productValue16);
        productName16 = findViewById(R.id.productName16);
        productImage17 = findViewById(R.id.product_17);
        productValue17 = findViewById(R.id.productValue17);
        productName17 = findViewById(R.id.productName17);
        productImage18 = findViewById(R.id.product_18);
        productValue18 = findViewById(R.id.productValue18);
        productName18 = findViewById(R.id.productName18);
        productImage19 = findViewById(R.id.product_19);
        productValue19 = findViewById(R.id.productValue19);
        productName19 = findViewById(R.id.productName19);
        productImage20 = findViewById(R.id.product_20);
        productValue20 = findViewById(R.id.productValue20);
        productName20 = findViewById(R.id.productName20);
        productImage21 = findViewById(R.id.product_21);
        productValue21 = findViewById(R.id.productValue21);
        productName21 = findViewById(R.id.productName21);
        productImage22 = findViewById(R.id.product_22);
        productValue22 = findViewById(R.id.productValue22);
        productName22 = findViewById(R.id.productName22);
        productImage23 = findViewById(R.id.product_23);
        productValue23 = findViewById(R.id.productValue23);
        productName23 = findViewById(R.id.productName23);
        productImage24 = findViewById(R.id.product_24);
        productValue24 = findViewById(R.id.productValue24);
        productName24 = findViewById(R.id.productName24);

        a = findViewById(R.id.a);
        b = findViewById(R.id.b);
        c = findViewById(R.id.c);
        d = findViewById(R.id.d);
        e = findViewById(R.id.e);
        f = findViewById(R.id.f);
        g = findViewById(R.id.g);
        h = findViewById(R.id.h);
        i1 = findViewById(R.id.i);
        j = findViewById(R.id.j);
        k = findViewById(R.id.k);
        l = findViewById(R.id.l);
        m = findViewById(R.id.m);
        n = findViewById(R.id.n);
        o = findViewById(R.id.o);
        p = findViewById(R.id.p);
        q = findViewById(R.id.q);
        r = findViewById(R.id.r);
        s = findViewById(R.id.s);
        t = findViewById(R.id.t);
        u = findViewById(R.id.u);
        v1 = findViewById(R.id.v);
        w = findViewById(R.id.w);
        x = findViewById(R.id.x);






        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        mFirestore = FirebaseFirestore.getInstance();
        docRef = mFirestore.collection("products").document(String.valueOf(Home.userProductSelection));

        if(ProfileMain.fromProfile) {

            renterName.setText(ProfileMain.localName);
            storageRef.child("users/" + ProfileMain.currentUserID).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    // Got the download URL for 'users/me/profile.png'
                    Glide.with(UserHomeMain.this).load(uri).into(renterImage);


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                    //showMessage("Please connect to the internet to view the products");
                }
            });
        }
        else{
            renterName.setText(RentProductMain.selectedName);
            storageRef.child("users/" + RentProductMain.UID).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    // Got the download URL for 'users/me/profile.png'
                    Glide.with(UserHomeMain.this).load(uri).into(renterImage);


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                    //showMessage("Please connect to the internet to view the products");
                }
            });

        }


        if(ProfileMain.fromProfile) {
            docRef2 = mFirestore.collection("users").document(ProfileMain.currentUserID);
        }
        else {
            docRef2 = mFirestore.collection("users").document(RentProductMain.UID);
        }

        docRef2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                DocumentSnapshot document = task.getResult();
                if (document.exists()) {


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



                }
            }
        });


        collectionReference = docRef.collection("products");

        if (ProfileMain.fromProfile) {
            query = collectionReference.whereEqualTo("User ID", ProfileMain.currentUserID);
        }
        else{
            query = collectionReference.whereEqualTo("User ID", RentProductMain.UID);        }

        if (ProfileMain.fromProfile) {

            mFirestore.collection("products").whereEqualTo("User ID", ProfileMain.currentUserID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {


                        for (QueryDocumentSnapshot document : task.getResult()) {

                            if (loopMaster == 0) {
                                productImage1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        Home.userProductSelection = Integer.parseInt(document.get("Product Num").toString());
                                        startActivity(new Intent(UserHomeMain.this, RentProductMain.class));
                                        overridePendingTransition(0, 0);


                                    }
                                });


                                productValue1.setVisibility(View.VISIBLE);
                                productImage1.setVisibility(View.VISIBLE);
                                productName1.setVisibility(View.VISIBLE);
                                a.setVisibility(View.VISIBLE);
                                productValue1.setText("$" + document.get("Rental Fee").toString() + "/day");
                                productName1.setText(document.get("Product Name").toString());

                                storageRef.child("images/" + document.get("Product Num").toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        // Got the download URL for 'users/me/profile.png'
                                        Glide.with(UserHomeMain.this).load(uri).into(productImage1);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                    }
                                });


                            }

                            if (loopMaster == 1) {

                                productImage2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        Home.userProductSelection = Integer.parseInt(document.get("Product Num").toString());
                                        startActivity(new Intent(UserHomeMain.this, RentProductMain.class));
                                        overridePendingTransition(0, 0);
                                        overridePendingTransition(0, 0);


                                    }
                                });

                                productValue2.setVisibility(View.VISIBLE);
                                productImage2.setVisibility(View.VISIBLE);
                                productName2.setVisibility(View.VISIBLE);
                                b.setVisibility(View.VISIBLE);
                                productValue2.setText("$" + document.get("Rental Fee").toString() + "/day");
                                productName2.setText(document.get("Product Name").toString());

                                storageRef.child("images/" + document.get("Product Num").toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        // Got the download URL for 'users/me/profile.png'
                                        Glide.with(UserHomeMain.this).load(uri).into(productImage2);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                    }
                                });


                            }

                            if (loopMaster == 2) {

                                productImage3.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        Home.userProductSelection = Integer.parseInt(document.get("Product Num").toString());
                                        startActivity(new Intent(UserHomeMain.this, RentProductMain.class));
                                        overridePendingTransition(0, 0);
                                        overridePendingTransition(0, 0);


                                    }
                                });

                                productValue3.setVisibility(View.VISIBLE);
                                productImage3.setVisibility(View.VISIBLE);
                                productName3.setVisibility(View.VISIBLE);
                                c.setVisibility(View.VISIBLE);
                                productValue3.setText("$" + document.get("Rental Fee").toString() + "/day");
                                productName3.setText(document.get("Product Name").toString());

                                storageRef.child("images/" + document.get("Product Num").toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        // Got the download URL for 'users/me/profile.png'
                                        Glide.with(UserHomeMain.this).load(uri).into(productImage3);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                    }
                                });


                            }

                            if (loopMaster == 3) {

                                productImage4.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        Home.userProductSelection = Integer.parseInt(document.get("Product Num").toString());
                                        startActivity(new Intent(UserHomeMain.this, RentProductMain.class));
                                        overridePendingTransition(0, 0);
                                        overridePendingTransition(0, 0);


                                    }
                                });

                                productValue4.setVisibility(View.VISIBLE);
                                productImage4.setVisibility(View.VISIBLE);
                                productName4.setVisibility(View.VISIBLE);
                                d.setVisibility(View.VISIBLE);
                                productValue4.setText("$" + document.get("Rental Fee").toString() + "/day");
                                productName4.setText(document.get("Product Name").toString());

                                storageRef.child("images/" + document.get("Product Num").toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        // Got the download URL for 'users/me/profile.png'
                                        Glide.with(UserHomeMain.this).load(uri).into(productImage4);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                    }
                                });


                            }
                            if (loopMaster == 4) {

                                productImage5.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        Home.userProductSelection = Integer.parseInt(document.get("Product Num").toString());
                                        startActivity(new Intent(UserHomeMain.this, RentProductMain.class));
                                        overridePendingTransition(0, 0);
                                        overridePendingTransition(0, 0);


                                    }
                                });

                                productValue5.setVisibility(View.VISIBLE);
                                productImage5.setVisibility(View.VISIBLE);
                                productName5.setVisibility(View.VISIBLE);
                                e.setVisibility(View.VISIBLE);
                                productValue5.setText("$" + document.get("Rental Fee").toString() + "/day");
                                productName5.setText(document.get("Product Name").toString());

                                storageRef.child("images/" + document.get("Product Num").toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        // Got the download URL for 'users/me/profile.png'
                                        Glide.with(UserHomeMain.this).load(uri).into(productImage5);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                    }
                                });


                            }
                            if (loopMaster == 5) {

                                productImage6.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        Home.userProductSelection = Integer.parseInt(document.get("Product Num").toString());
                                        startActivity(new Intent(UserHomeMain.this, RentProductMain.class));
                                        overridePendingTransition(0, 0);
                                        overridePendingTransition(0, 0);


                                    }
                                });

                                productValue6.setVisibility(View.VISIBLE);
                                productImage6.setVisibility(View.VISIBLE);
                                productName6.setVisibility(View.VISIBLE);
                                f.setVisibility(View.VISIBLE);
                                productValue6.setText("$" + document.get("Rental Fee").toString() + "/day");
                                productName6.setText(document.get("Product Name").toString());

                                storageRef.child("images/" + document.get("Product Num").toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        // Got the download URL for 'users/me/profile.png'
                                        Glide.with(UserHomeMain.this).load(uri).into(productImage6);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                    }
                                });


                            }
                            if (loopMaster == 6) {

                                productImage7.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        Home.userProductSelection = Integer.parseInt(document.get("Product Num").toString());
                                        startActivity(new Intent(UserHomeMain.this, RentProductMain.class));
                                        overridePendingTransition(0, 0);
                                        overridePendingTransition(0, 0);


                                    }
                                });

                                productValue7.setVisibility(View.VISIBLE);
                                productImage7.setVisibility(View.VISIBLE);
                                productName7.setVisibility(View.VISIBLE);
                                g.setVisibility(View.VISIBLE);
                                productValue7.setText("$" + document.get("Rental Fee").toString() + "/day");
                                productName7.setText(document.get("Product Name").toString());

                                storageRef.child("images/" + document.get("Product Num").toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        // Got the download URL for 'users/me/profile.png'
                                        Glide.with(UserHomeMain.this).load(uri).into(productImage7);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                    }
                                });


                            }
                            if (loopMaster == 7) {

                                productImage8.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        Home.userProductSelection = Integer.parseInt(document.get("Product Num").toString());
                                        startActivity(new Intent(UserHomeMain.this, RentProductMain.class));
                                        overridePendingTransition(0, 0);
                                        overridePendingTransition(0, 0);


                                    }
                                });

                                productValue8.setVisibility(View.VISIBLE);
                                productImage8.setVisibility(View.VISIBLE);
                                productName8.setVisibility(View.VISIBLE);
                                h.setVisibility(View.VISIBLE);
                                productValue8.setText("$" + document.get("Rental Fee").toString() + "/day");
                                productName8.setText(document.get("Product Name").toString());

                                storageRef.child("images/" + document.get("Product Num").toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        // Got the download URL for 'users/me/profile.png'
                                        Glide.with(UserHomeMain.this).load(uri).into(productImage8);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                    }
                                });


                            }

                            if (loopMaster == 8) {

                                productImage9.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        Home.userProductSelection = Integer.parseInt(document.get("Product Num").toString());
                                        startActivity(new Intent(UserHomeMain.this, RentProductMain.class));
                                        overridePendingTransition(0, 0);
                                        overridePendingTransition(0, 0);


                                    }
                                });
                                productValue9.setVisibility(View.VISIBLE);
                                productImage9.setVisibility(View.VISIBLE);
                                productName9.setVisibility(View.VISIBLE);
                                i1.setVisibility(View.VISIBLE);
                                productValue9.setText("$" + document.get("Rental Fee").toString() + "/day");
                                productName9.setText(document.get("Product Name").toString());

                                storageRef.child("images/" + document.get("Product Num").toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        // Got the download URL for 'users/me/profile.png'
                                        Glide.with(UserHomeMain.this).load(uri).into(productImage9);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                    }
                                });


                            }

                            if (loopMaster == 9) {

                                productImage10.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        Home.userProductSelection = Integer.parseInt(document.get("Product Num").toString());
                                        startActivity(new Intent(UserHomeMain.this, RentProductMain.class));
                                        overridePendingTransition(0, 0);
                                        overridePendingTransition(0, 0);


                                    }
                                });

                                productValue10.setVisibility(View.VISIBLE);
                                productImage10.setVisibility(View.VISIBLE);
                                productName10.setVisibility(View.VISIBLE);
                                g.setVisibility(View.VISIBLE);
                                productValue10.setText("$" + document.get("Rental Fee").toString() + "/day");
                                productName10.setText(document.get("Product Name").toString());

                                storageRef.child("images/" + document.get("Product Num").toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        // Got the download URL for 'users/me/profile.png'
                                        Glide.with(UserHomeMain.this).load(uri).into(productImage10);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                    }
                                });


                            }


                            loopMaster++;


                        }
                    } else {
                        showMessage("Failed");
                    }


                }
            });

        }else{

            mFirestore.collection("products").whereEqualTo("User ID", RentProductMain.UID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {


                        for (QueryDocumentSnapshot document : task.getResult()) {

                            if (loopMaster == 0) {
                                productImage1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        Home.userProductSelection = Integer.parseInt(document.get("Product Num").toString());
                                        startActivity(new Intent(UserHomeMain.this, RentProductMain.class));
                                        overridePendingTransition(0, 0);


                                    }
                                });


                                productValue1.setVisibility(View.VISIBLE);
                                productImage1.setVisibility(View.VISIBLE);
                                productName1.setVisibility(View.VISIBLE);
                                a.setVisibility(View.VISIBLE);
                                productValue1.setText("$" + document.get("Rental Fee").toString() + "/day");
                                productName1.setText(document.get("Product Name").toString());

                                storageRef.child("images/" + document.get("Product Num").toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        // Got the download URL for 'users/me/profile.png'
                                        Glide.with(UserHomeMain.this).load(uri).into(productImage1);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                    }
                                });


                            }

                            if (loopMaster == 1) {

                                productImage2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        Home.userProductSelection = Integer.parseInt(document.get("Product Num").toString());
                                        startActivity(new Intent(UserHomeMain.this, RentProductMain.class));
                                        overridePendingTransition(0, 0);
                                        overridePendingTransition(0, 0);


                                    }
                                });

                                productValue2.setVisibility(View.VISIBLE);
                                productImage2.setVisibility(View.VISIBLE);
                                productName2.setVisibility(View.VISIBLE);
                                b.setVisibility(View.VISIBLE);
                                productValue2.setText("$" + document.get("Rental Fee").toString() + "/day");
                                productName2.setText(document.get("Product Name").toString());

                                storageRef.child("images/" + document.get("Product Num").toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        // Got the download URL for 'users/me/profile.png'
                                        Glide.with(UserHomeMain.this).load(uri).into(productImage2);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                    }
                                });


                            }

                            if (loopMaster == 2) {

                                productImage3.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        Home.userProductSelection = Integer.parseInt(document.get("Product Num").toString());
                                        startActivity(new Intent(UserHomeMain.this, RentProductMain.class));
                                        overridePendingTransition(0, 0);
                                        overridePendingTransition(0, 0);


                                    }
                                });

                                productValue3.setVisibility(View.VISIBLE);
                                productImage3.setVisibility(View.VISIBLE);
                                productName3.setVisibility(View.VISIBLE);
                                c.setVisibility(View.VISIBLE);
                                productValue3.setText("$" + document.get("Rental Fee").toString() + "/day");
                                productName3.setText(document.get("Product Name").toString());

                                storageRef.child("images/" + document.get("Product Num").toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        // Got the download URL for 'users/me/profile.png'
                                        Glide.with(UserHomeMain.this).load(uri).into(productImage3);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                    }
                                });


                            }

                            if (loopMaster == 3) {

                                productImage4.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        Home.userProductSelection = Integer.parseInt(document.get("Product Num").toString());
                                        startActivity(new Intent(UserHomeMain.this, RentProductMain.class));
                                        overridePendingTransition(0, 0);
                                        overridePendingTransition(0, 0);


                                    }
                                });

                                productValue4.setVisibility(View.VISIBLE);
                                productImage4.setVisibility(View.VISIBLE);
                                productName4.setVisibility(View.VISIBLE);
                                d.setVisibility(View.VISIBLE);
                                productValue4.setText("$" + document.get("Rental Fee").toString() + "/day");
                                productName4.setText(document.get("Product Name").toString());

                                storageRef.child("images/" + document.get("Product Num").toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        // Got the download URL for 'users/me/profile.png'
                                        Glide.with(UserHomeMain.this).load(uri).into(productImage4);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                    }
                                });


                            }
                            if (loopMaster == 4) {

                                productImage5.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        Home.userProductSelection = Integer.parseInt(document.get("Product Num").toString());
                                        startActivity(new Intent(UserHomeMain.this, RentProductMain.class));
                                        overridePendingTransition(0, 0);
                                        overridePendingTransition(0, 0);


                                    }
                                });

                                productValue5.setVisibility(View.VISIBLE);
                                productImage5.setVisibility(View.VISIBLE);
                                productName5.setVisibility(View.VISIBLE);
                                e.setVisibility(View.VISIBLE);
                                productValue5.setText("$" + document.get("Rental Fee").toString() + "/day");
                                productName5.setText(document.get("Product Name").toString());

                                storageRef.child("images/" + document.get("Product Num").toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        // Got the download URL for 'users/me/profile.png'
                                        Glide.with(UserHomeMain.this).load(uri).into(productImage5);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                    }
                                });


                            }
                            if (loopMaster == 5) {

                                productImage6.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        Home.userProductSelection = Integer.parseInt(document.get("Product Num").toString());
                                        startActivity(new Intent(UserHomeMain.this, RentProductMain.class));
                                        overridePendingTransition(0, 0);
                                        overridePendingTransition(0, 0);


                                    }
                                });

                                productValue6.setVisibility(View.VISIBLE);
                                productImage6.setVisibility(View.VISIBLE);
                                productName6.setVisibility(View.VISIBLE);
                                f.setVisibility(View.VISIBLE);
                                productValue6.setText("$" + document.get("Rental Fee").toString() + "/day");
                                productName6.setText(document.get("Product Name").toString());

                                storageRef.child("images/" + document.get("Product Num").toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        // Got the download URL for 'users/me/profile.png'
                                        Glide.with(UserHomeMain.this).load(uri).into(productImage6);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                    }
                                });


                            }
                            if (loopMaster == 6) {

                                productImage7.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        Home.userProductSelection = Integer.parseInt(document.get("Product Num").toString());
                                        startActivity(new Intent(UserHomeMain.this, RentProductMain.class));
                                        overridePendingTransition(0, 0);
                                        overridePendingTransition(0, 0);


                                    }
                                });

                                productValue7.setVisibility(View.VISIBLE);
                                productImage7.setVisibility(View.VISIBLE);
                                productName7.setVisibility(View.VISIBLE);
                                g.setVisibility(View.VISIBLE);
                                productValue7.setText("$" + document.get("Rental Fee").toString() + "/day");
                                productName7.setText(document.get("Product Name").toString());

                                storageRef.child("images/" + document.get("Product Num").toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        // Got the download URL for 'users/me/profile.png'
                                        Glide.with(UserHomeMain.this).load(uri).into(productImage7);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                    }
                                });


                            }
                            if (loopMaster == 7) {

                                productImage8.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        Home.userProductSelection = Integer.parseInt(document.get("Product Num").toString());
                                        startActivity(new Intent(UserHomeMain.this, RentProductMain.class));
                                        overridePendingTransition(0, 0);
                                        overridePendingTransition(0, 0);


                                    }
                                });

                                productValue8.setVisibility(View.VISIBLE);
                                productImage8.setVisibility(View.VISIBLE);
                                productName8.setVisibility(View.VISIBLE);
                                h.setVisibility(View.VISIBLE);
                                productValue8.setText("$" + document.get("Rental Fee").toString() + "/day");
                                productName8.setText(document.get("Product Name").toString());

                                storageRef.child("images/" + document.get("Product Num").toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        // Got the download URL for 'users/me/profile.png'
                                        Glide.with(UserHomeMain.this).load(uri).into(productImage8);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                    }
                                });


                            }

                            if (loopMaster == 8) {

                                productImage9.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        Home.userProductSelection = Integer.parseInt(document.get("Product Num").toString());
                                        startActivity(new Intent(UserHomeMain.this, RentProductMain.class));
                                        overridePendingTransition(0, 0);
                                        overridePendingTransition(0, 0);


                                    }
                                });
                                productValue9.setVisibility(View.VISIBLE);
                                productImage9.setVisibility(View.VISIBLE);
                                productName9.setVisibility(View.VISIBLE);
                                i1.setVisibility(View.VISIBLE);
                                productValue9.setText("$" + document.get("Rental Fee").toString() + "/day");
                                productName9.setText(document.get("Product Name").toString());

                                storageRef.child("images/" + document.get("Product Num").toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        // Got the download URL for 'users/me/profile.png'
                                        Glide.with(UserHomeMain.this).load(uri).into(productImage9);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                    }
                                });


                            }

                            if (loopMaster == 9) {

                                productImage10.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        Home.userProductSelection = Integer.parseInt(document.get("Product Num").toString());
                                        startActivity(new Intent(UserHomeMain.this, RentProductMain.class));
                                        overridePendingTransition(0, 0);
                                        overridePendingTransition(0, 0);


                                    }
                                });

                                productValue10.setVisibility(View.VISIBLE);
                                productImage10.setVisibility(View.VISIBLE);
                                productName10.setVisibility(View.VISIBLE);
                                g.setVisibility(View.VISIBLE);
                                productValue10.setText("$" + document.get("Rental Fee").toString() + "/day");
                                productName10.setText(document.get("Product Name").toString());

                                storageRef.child("images/" + document.get("Product Num").toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        // Got the download URL for 'users/me/profile.png'
                                        Glide.with(UserHomeMain.this).load(uri).into(productImage10);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                    }
                                });


                            }


                            loopMaster++;


                        }
                    } else {
                        showMessage("Failed");
                    }


                }
            });





        }

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(ProfileMain.fromProfile){
                    startActivity(new Intent(UserHomeMain.this, ProfileMain.class));
                }
                else {

                    Home.userProductSelection = Home.originalNum;
                    fromUserHomeMain = false;
                    startActivity(new Intent(UserHomeMain.this, RentProductMain.class));

                    overridePendingTransition(0, 0);
                }
            }
        });










    }







    private void showMessage(String message){

        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }
}
