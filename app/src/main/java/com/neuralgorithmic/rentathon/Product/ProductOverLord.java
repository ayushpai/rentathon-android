package com.neuralgorithmic.rentathon.Product;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.neuralgorithmic.rentathon.Home.Home;
import com.neuralgorithmic.rentathon.MessagingHome;
import com.neuralgorithmic.rentathon.Profile.ProfileMain;
import com.neuralgorithmic.rentathon.Profile.UserHomeMain;
import com.neuralgorithmic.rentathon.R;
import com.neuralgorithmic.rentathon.Rent.RentProductMain;

import org.jetbrains.annotations.NotNull;

public class ProductOverLord extends AppCompatActivity {
    ConstraintLayout a, b, c, d, e, f, g, h, i1, j, addProduct;
    private FirebaseAuth mAuth;
    FirebaseFirestore mFirestore;
    StorageReference storageRef;
    FirebaseStorage storage;
    DocumentReference docRef, docRef2;
    public ImageView refreshbtn, productImage1, productImage2, productImage3, productImage4, productImage5, productImage6, productImage7, productImage8, productImage9, productImage10;
    FirebaseUser mFirebaseUser;
    String currentUserID;
    public int loopMaster;
    ImageView back1, back2, back3, back4, back5, back6, back7, back8, back9, back10;
    public static boolean fromOverLord;
    public boolean verified;
    Button homeNav, chatNav, profileNav;
    boolean PendingOVerification, OVerified;
    public TextView ProductName1, ProductViews1, ProductPrice1, ProductName2, ProductViews2, ProductPrice2, ProductName3, ProductViews3, ProductPrice3, ProductName4, ProductViews4, ProductPrice4, ProductName5, ProductViews5, ProductPrice5, ProductName6, ProductViews6, ProductPrice6, ProductName7, ProductViews7, ProductPrice7, ProductName8, ProductViews8, ProductPrice8, ProductName9, ProductViews9, ProductPrice9, ProductName10, ProductViews10, ProductPrice10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_my_rentals_and_my_products);
        RentProductMain.fromHome = false;
        fromOverLord = true;
        UserHomeMain.fromUserHomeMain = false;
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);

        bottomNavigationView.setSelectedItemId(R.id.products);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.chat:
                        startActivity(new Intent(getApplicationContext(), MessagingHome.class));
                        overridePendingTransition(0, 0);

                        return true;

                    case R.id.products:
                        startActivity(new Intent(getApplicationContext(), ProductOverLord.class));
                        overridePendingTransition(0, 0);

                        return true;

                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), ProfileMain.class));
                        overridePendingTransition(0, 0);

                        return true;

                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), Home.class));
                        overridePendingTransition(0, 0);

                        return true;


                }
                return false;
            }
        });


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

        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        addProduct = findViewById(R.id.addProduct);


        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        back1 = findViewById(R.id.back1);
        back2 = findViewById(R.id.back2);
        back3 = findViewById(R.id.back3);
        back4 = findViewById(R.id.back4);
        back5 = findViewById(R.id.back5);
        back6 = findViewById(R.id.back6);
        back7 = findViewById(R.id.back7);
        back8 = findViewById(R.id.back8);
        back9 = findViewById(R.id.back9);
        back10 = findViewById(R.id.back10);

        productImage1 = findViewById(R.id.product_1);
        productImage2 = findViewById(R.id.product_2);
        productImage3 = findViewById(R.id.product_3);
        productImage4 = findViewById(R.id.product_4);
        productImage5 = findViewById(R.id.product_5);
        productImage6 = findViewById(R.id.product_6);
        productImage7 = findViewById(R.id.product_7);
        productImage8 = findViewById(R.id.product_8);
        productImage9 = findViewById(R.id.product_9);
        productImage10 = findViewById(R.id.product_10);

        ProductName1 = findViewById(R.id.product_1_name);
        ProductPrice1 = findViewById(R.id.product_1_price);
        ProductViews1 = findViewById(R.id.product_1_views);
        ProductName2 = findViewById(R.id.product_2_name);
        ProductPrice2 = findViewById(R.id.product_2_price);
        ProductViews2 = findViewById(R.id.product_2_views);
        ProductName3 = findViewById(R.id.product_3_name);
        ProductPrice3 = findViewById(R.id.product_3_price);
        ProductViews3 = findViewById(R.id.product_3_views);
        ProductName4 = findViewById(R.id.product_4_name);
        ProductPrice4 = findViewById(R.id.product_4_price);
        ProductViews4 = findViewById(R.id.product_4_views);
        ProductName5 = findViewById(R.id.product_5_name);
        ProductPrice5 = findViewById(R.id.product_5_price);
        ProductViews5 = findViewById(R.id.product_5_views);
        ProductName6 = findViewById(R.id.product_6_name);
        ProductPrice6 = findViewById(R.id.product_6_price);
        ProductViews6 = findViewById(R.id.product_6_views);
        ProductName7 = findViewById(R.id.product_7_name);
        ProductPrice7 = findViewById(R.id.product_7_price);
        ProductViews7 = findViewById(R.id.product_7_views);
        ProductName8 = findViewById(R.id.product_8_name);
        ProductPrice8 = findViewById(R.id.product_8_price);
        ProductViews8 = findViewById(R.id.product_8_views);
        ProductName9 = findViewById(R.id.product_9_name);
        ProductPrice9 = findViewById(R.id.product_9_price);
        ProductViews9 = findViewById(R.id.product_9_views);
        ProductName10 = findViewById(R.id.product_10_name);
        ProductPrice10 = findViewById(R.id.product_10_price);
        ProductViews10 = findViewById(R.id.product_10_views);
        mFirebaseUser = mAuth.getCurrentUser();
        if(mFirebaseUser != null) {
            currentUserID = mFirebaseUser.getUid(); //Do what you need to do with the id
        }


        docRef = mFirestore.collection("users").document(currentUserID);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                if(document.exists()) {
                    PendingOVerification = document.getBoolean("PendingOVerified");
                    OVerified = document.getBoolean("OVerified");
                }
            }
        });

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                DocumentSnapshot document = task.getResult();
                if (document.exists()) {

                    addProduct.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(OVerified) {
                                startActivity(new Intent(getApplicationContext(), ProductMain.class));
                                overridePendingTransition(0, 0);
                            }
                            else if(PendingOVerification) {
                                AlertDialog.Builder dialogBox = new AlertDialog.Builder(ProductOverLord.this);
                                dialogBox.setCancelable(true);
                                dialogBox.setTitle("Information Being Processed");
                                dialogBox.setMessage("Your renter information is being processed. You will be notified within 24-48 hours of your application.");

                                dialogBox.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });

                                dialogBox.show();
                            }
                            else{
                                startActivity(new Intent(ProductOverLord.this, BackgroundCheck.class));
                                overridePendingTransition(0, 0);
                            }


                        }
                    });

                }
            }
        });

        mFirestore.collection("products").whereEqualTo("User ID", currentUserID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {


                    for (QueryDocumentSnapshot document : task.getResult()) {


                        if (loopMaster == 0) {
                            a.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Home.userProductSelection = Integer.parseInt(document.get("Product Num").toString());
                                    startActivity(new Intent(ProductOverLord.this, RentProductMain.class));
                                    overridePendingTransition(0, 0);



                                }
                            });

                            ProductPrice1.setVisibility(View.VISIBLE);
                            ProductViews1.setVisibility(View.VISIBLE);
                            ProductName1.setVisibility(View.VISIBLE);
                            productImage1.setVisibility(View.VISIBLE);
                            back1.setVisibility(View.VISIBLE);
                            a.setVisibility(View.VISIBLE);

                            ProductPrice1.setText("$" + document.get("Rental Fee").toString() + "/day");
                            ProductName1.setText(document.get("Product Name").toString());
                            ProductViews1.setText(document.get("Views").toString() + " Views");

                            storageRef.child("images/" + document.get("Product Num").toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    // Got the download URL for 'users/me/profile.png'
                                    Glide.with(ProductOverLord.this).load(uri).into(productImage1);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                }
                            });


                        }
                        if (loopMaster == 1) {

                            b.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Home.userProductSelection = Integer.parseInt(document.get("Product Num").toString());
                                    startActivity(new Intent(ProductOverLord.this, RentProductMain.class));
                                    overridePendingTransition(0, 0);



                                }
                            });

                            ProductPrice2.setVisibility(View.VISIBLE);
                            ProductViews2.setVisibility(View.VISIBLE);
                            ProductName2.setVisibility(View.VISIBLE);
                            productImage2.setVisibility(View.VISIBLE);
                            back2.setVisibility(View.VISIBLE);
                            b.setVisibility(View.VISIBLE);

                            ProductPrice2.setText("$" + document.get("Rental Fee").toString() + "/day");
                            ProductName2.setText(document.get("Product Name").toString());
                            ProductViews2.setText(document.get("Views").toString() + " Views");

                            storageRef.child("images/" + document.get("Product Num").toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    // Got the download URL for 'users/me/profile.png'
                                    Glide.with(ProductOverLord.this).load(uri).into(productImage2);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                }
                            });


                        }
                        if (loopMaster == 2) {

                            c.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Home.userProductSelection = Integer.parseInt(document.get("Product Num").toString());
                                    startActivity(new Intent(ProductOverLord.this, RentProductMain.class));
                                    overridePendingTransition(0, 0);



                                }
                            });

                            ProductPrice3.setVisibility(View.VISIBLE);
                            ProductViews3.setVisibility(View.VISIBLE);
                            ProductName3.setVisibility(View.VISIBLE);
                            productImage3.setVisibility(View.VISIBLE);
                            back3.setVisibility(View.VISIBLE);
                            c.setVisibility(View.VISIBLE);

                            ProductPrice3.setText("$" + document.get("Rental Fee").toString() + "/day");
                            ProductName3.setText(document.get("Product Name").toString());
                            ProductViews3.setText(document.get("Views").toString() + " Views");

                            storageRef.child("images/" + document.get("Product Num").toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    // Got the download URL for 'users/me/profile.png'
                                    Glide.with(ProductOverLord.this).load(uri).into(productImage3);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                }
                            });


                        }
                        if (loopMaster == 3) {

                            d.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Home.userProductSelection = Integer.parseInt(document.get("Product Num").toString());
                                    startActivity(new Intent(ProductOverLord.this, RentProductMain.class));
                                    overridePendingTransition(0, 0);



                                }
                            });

                            ProductPrice4.setVisibility(View.VISIBLE);
                            ProductViews4.setVisibility(View.VISIBLE);
                            ProductName4.setVisibility(View.VISIBLE);
                            productImage4.setVisibility(View.VISIBLE);
                            back4.setVisibility(View.VISIBLE);
                            d.setVisibility(View.VISIBLE);

                            ProductPrice4.setText("$" + document.get("Rental Fee").toString() + "/day");
                            ProductName4.setText(document.get("Product Name").toString());
                            ProductViews4.setText(document.get("Views").toString() + " Views");

                            storageRef.child("images/" + document.get("Product Num").toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    // Got the download URL for 'users/me/profile.png'
                                    Glide.with(ProductOverLord.this).load(uri).into(productImage4);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                }
                            });


                        }
                        if (loopMaster == 4) {

                            e.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Home.userProductSelection = Integer.parseInt(document.get("Product Num").toString());
                                    startActivity(new Intent(ProductOverLord.this, RentProductMain.class));
                                    overridePendingTransition(0, 0);



                                }
                            });

                            ProductPrice5.setVisibility(View.VISIBLE);
                            ProductViews5.setVisibility(View.VISIBLE);
                            ProductName5.setVisibility(View.VISIBLE);
                            productImage5.setVisibility(View.VISIBLE);
                            back5.setVisibility(View.VISIBLE);
                            e.setVisibility(View.VISIBLE);

                            ProductPrice5.setText("$" + document.get("Rental Fee").toString() + "/day");
                            ProductName5.setText(document.get("Product Name").toString());
                            ProductViews5.setText(document.get("Views").toString() + " Views");

                            storageRef.child("images/" + document.get("Product Num").toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    // Got the download URL for 'users/me/profile.png'
                                    Glide.with(ProductOverLord.this).load(uri).into(productImage5);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                }
                            });


                        }
                        if (loopMaster == 5) {

                            f.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Home.userProductSelection = Integer.parseInt(document.get("Product Num").toString());
                                    startActivity(new Intent(ProductOverLord.this, RentProductMain.class));
                                    overridePendingTransition(0, 0);



                                }
                            });

                            ProductPrice6.setVisibility(View.VISIBLE);
                            ProductViews6.setVisibility(View.VISIBLE);
                            ProductName6.setVisibility(View.VISIBLE);
                            productImage6.setVisibility(View.VISIBLE);
                            back6.setVisibility(View.VISIBLE);
                            f.setVisibility(View.VISIBLE);

                            ProductPrice6.setText("$" + document.get("Rental Fee").toString() + "/day");
                            ProductName6.setText(document.get("Product Name").toString());
                            ProductViews6.setText(document.get("Views").toString() + " Views");

                            storageRef.child("images/" + document.get("Product Num").toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    // Got the download URL for 'users/me/profile.png'
                                    Glide.with(ProductOverLord.this).load(uri).into(productImage6);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                }
                            });


                        }
                        if (loopMaster == 6) {

                            g.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Home.userProductSelection = Integer.parseInt(document.get("Product Num").toString());
                                    startActivity(new Intent(ProductOverLord.this, RentProductMain.class));
                                    overridePendingTransition(0, 0);



                                }
                            });

                            ProductPrice7.setVisibility(View.VISIBLE);
                            ProductViews7.setVisibility(View.VISIBLE);
                            ProductName7.setVisibility(View.VISIBLE);
                            productImage7.setVisibility(View.VISIBLE);
                            back7.setVisibility(View.VISIBLE);
                            g.setVisibility(View.VISIBLE);

                            ProductPrice7.setText("$" + document.get("Rental Fee").toString() + "/day");
                            ProductName7.setText(document.get("Product Name").toString());
                            ProductViews7.setText(document.get("Views").toString() + " Views");

                            storageRef.child("images/" + document.get("Product Num").toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    // Got the download URL for 'users/me/profile.png'
                                    Glide.with(ProductOverLord.this).load(uri).into(productImage7);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                }
                            });


                        }
                        if (loopMaster == 7) {

                            h.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Home.userProductSelection = Integer.parseInt(document.get("Product Num").toString());
                                    startActivity(new Intent(ProductOverLord.this, RentProductMain.class));
                                    overridePendingTransition(0, 0);



                                }
                            });

                            ProductPrice8.setVisibility(View.VISIBLE);
                            ProductViews8.setVisibility(View.VISIBLE);
                            ProductName8.setVisibility(View.VISIBLE);
                            productImage8.setVisibility(View.VISIBLE);
                            back8.setVisibility(View.VISIBLE);
                            h.setVisibility(View.VISIBLE);

                            ProductPrice8.setText("$" + document.get("Rental Fee").toString() + "/day");
                            ProductName8.setText(document.get("Product Name").toString());
                            ProductViews8.setText(document.get("Views").toString() + " Views");

                            storageRef.child("images/" + document.get("Product Num").toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    // Got the download URL for 'users/me/profile.png'
                                    Glide.with(ProductOverLord.this).load(uri).into(productImage8);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                }
                            });


                        }
                        if (loopMaster == 8) {

                            i1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Home.userProductSelection = Integer.parseInt(document.get("Product Num").toString());
                                    startActivity(new Intent(ProductOverLord.this, RentProductMain.class));
                                    overridePendingTransition(0, 0);



                                }
                            });

                            ProductPrice9.setVisibility(View.VISIBLE);
                            ProductViews9.setVisibility(View.VISIBLE);
                            ProductName9.setVisibility(View.VISIBLE);
                            productImage9.setVisibility(View.VISIBLE);
                            back9.setVisibility(View.VISIBLE);
                            i1.setVisibility(View.VISIBLE);

                            ProductPrice9.setText("$" + document.get("Rental Fee").toString() + "/day");
                            ProductName9.setText(document.get("Product Name").toString());
                            ProductViews9.setText(document.get("Views").toString() + " Views");

                            storageRef.child("images/" + document.get("Product Num").toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    // Got the download URL for 'users/me/profile.png'
                                    Glide.with(ProductOverLord.this).load(uri).into(productImage9);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                }
                            });


                        }
                        if (loopMaster == 9) {

                            j.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Home.userProductSelection = Integer.parseInt(document.get("Product Num").toString());
                                    startActivity(new Intent(ProductOverLord.this, RentProductMain.class));
                                    overridePendingTransition(0, 0);



                                }
                            });

                            ProductPrice10.setVisibility(View.VISIBLE);
                            ProductViews10.setVisibility(View.VISIBLE);
                            ProductName10.setVisibility(View.VISIBLE);
                            productImage10.setVisibility(View.VISIBLE);
                            back10.setVisibility(View.VISIBLE);
                            j.setVisibility(View.VISIBLE);

                            ProductPrice10.setText("$" + document.get("Rental Fee").toString() + "/day");
                            ProductName10.setText(document.get("Product Name").toString());
                            ProductViews10.setText(document.get("Views").toString() + " Views");

                            storageRef.child("images/" + document.get("Product Num").toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    // Got the download URL for 'users/me/profile.png'
                                    Glide.with(ProductOverLord.this).load(uri).into(productImage10);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                }
                            });


                        }
                        loopMaster++;

                    }



                }

            }
        });











    }
    private void showMessage(String message){

        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }
}
