package com.neuralgorithmic.rentathon.Home;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
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
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.neuralgorithmic.rentathon.Chat.ChatMain;
import com.neuralgorithmic.rentathon.Misc.WelcomeProdcutMain;
import com.neuralgorithmic.rentathon.Product.ProductMain;
import com.neuralgorithmic.rentathon.Product.ProductOverLord;
import com.neuralgorithmic.rentathon.Profile.ProfileMain;

import com.neuralgorithmic.rentathon.Profile.UserHomeMain;
import com.neuralgorithmic.rentathon.R;
import com.neuralgorithmic.rentathon.Rent.RentProductMain;

import pl.droidsonroids.gif.GifImageView;
//fgddfgd
public class Home_2 extends AppCompatActivity {

    Button  cartNavBtn, chatNavBtn, profileNavBtn, productBtn;
    GoogleSignInClient mGoogleSignInClient;
    public ImageView refreshbtn, productImage1, productImage2, productImage3, productImage4, productImage5, productImage6, productImage7, productImage8, productImage9, productImage10, productImage11, productImage12, productImage13, productImage14, productImage15, productImage16, productImage17, productImage18, productImage19, productImage20, productImage21, productImage22, productImage23, productImage24;
    private DocumentReference docRef;
    SharedPreferences sp;
    FirebaseFirestore mFirestore;
    FirebaseStorage storage;
    StorageReference pathReference;
    UploadTask uploadTask;
    int i;
    Boolean show = false;
    Boolean createdDoc = false;
    Uri url;
    FirebaseUser mFirebaseUser;
    StorageReference storageRef;
    DocumentReference docRef2;
    CollectionReference collectionRef;
    public static int userProductSelection = 0;
    TextView productValue1, productName1, productValue2, productName2, productValue3, productName3, productValue4, productName4, productValue5, productName5, productValue6, productName6, productValue7, productName7, productValue8, productName8, productValue9, productName9, productValue10, productName10, productValue11, productName11, productValue12, productName12, productValue13, productName13, productValue14, productName14, productValue15, productName15, productValue16, productName16, productValue17, productName17, productValue18, productName18, productValue19, productName19, productValue20, productName20, productValue21, productName21, productValue22, productName22, productValue23, productName23, productValue24, productName24;
    int Size = 0;
    private FirebaseAuth mAuth;
    Boolean filledOut = false;
    String currentUserID;
    ConstraintLayout a, b, c, d, e, f, g, h, i1, j, k, l, m, n, o, p, q, r, s, t, u, v1, w, x, y, z;
    TextView pageNum;
    int numProducts;
    TextView nextPage, previousPage;
    ScrollView mainScrollView;
    GifImageView gif;
    public int loopMaster = 1;
    static public int scrollY = 0;
    public int numOfPages = 1;
    public String doc24ID = "";
    public Query firstPage;


    static public int originalNum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mainScrollView = findViewById(R.id.scroll_home);

        UserHomeMain.fromUserHomeMain = false;
        mainScrollView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mainScrollView.smoothScrollTo(0,scrollY);
                scrollY = 0;
            }
        }, 600);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        //gif = findViewById(R.id.gg);
        sp = getSharedPreferences("app_name", Context.MODE_PRIVATE);
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        mAuth = FirebaseAuth.getInstance();
        RentProductMain.fromHome= true;

        ProductMain.fromPreview = true;






        nextPage = findViewById(R.id.next_pg_btn);
        previousPage = findViewById(R.id.back_pg_btn);
        pageNum = findViewById(R.id.page_number_txt);
        mFirestore = FirebaseFirestore.getInstance();
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
        cartNavBtn = findViewById(R.id.rent_button_nav);
        chatNavBtn = findViewById(R.id.chat_button_nav);
        profileNavBtn = findViewById(R.id.user_button_nav);
        refreshbtn = findViewById(R.id.refresh_btn);
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




        previousPage.setVisibility(View.GONE);



        final SwipeRefreshLayout pullToRefresh = findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // your code

                startActivity(new Intent(Home_2.this, Home_2.class));
                overridePendingTransition(0, 0);
                scrollY = 0;
                pullToRefresh.setRefreshing(false);


            }
        });


        firstPage = mFirestore.collection("products")
                .orderBy("Views", Query.Direction.DESCENDING)
                .limit(24);

        firstPage
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Size = task.getResult().size();


                            for (QueryDocumentSnapshot document : task.getResult()) {


                                if (loopMaster == 1) {
                                    productImage1.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            scrollY = mainScrollView.getScrollY();
                                            Home.userProductSelection = Integer.parseInt(document.get("Product Num").toString());
                                            originalNum = 1;
                                            startActivity(new Intent(Home_2.this, RentProductMain.class));


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
                                            Glide.with(Home_2.this).load(uri).into(productImage1);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception exception) {
                                        }
                                    });


                                }

                                if (loopMaster == 2) {

                                    productImage2.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            scrollY = mainScrollView.getScrollY();
                                            userProductSelection = Integer.parseInt(document.get("Product Num").toString());
                                            originalNum = 2;
                                            startActivity(new Intent(Home_2.this, RentProductMain.class));


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
                                            Glide.with(Home_2.this).load(uri).into(productImage2);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception exception) {
                                        }
                                    });


                                }
                                if (loopMaster == 3) {

                                    productImage3.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            scrollY = mainScrollView.getScrollY();
                                            userProductSelection = Integer.parseInt(document.get("Product Num").toString());
                                            originalNum = 3;
                                            startActivity(new Intent(Home_2.this, RentProductMain.class));


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
                                            Glide.with(Home_2.this).load(uri).into(productImage3);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception exception) {
                                        }
                                    });


                                }
                                if (loopMaster == 4) {

                                    productImage4.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            scrollY = mainScrollView.getScrollY();
                                            userProductSelection = Integer.parseInt(document.get("Product Num").toString());
                                            originalNum = 4;
                                            startActivity(new Intent(Home_2.this, RentProductMain.class));


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
                                            Glide.with(Home_2.this).load(uri).into(productImage4);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception exception) {
                                        }
                                    });


                                }
                                if (loopMaster == 5) {

                                    productImage5.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            scrollY = mainScrollView.getScrollY();
                                            userProductSelection = Integer.parseInt(document.get("Product Num").toString());
                                            originalNum = 5;
                                            startActivity(new Intent(Home_2.this, RentProductMain.class));


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
                                            Glide.with(Home_2.this).load(uri).into(productImage5);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception exception) {
                                        }
                                    });


                                }

                                if (loopMaster == 6) {

                                    productImage6.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            scrollY = mainScrollView.getScrollY();
                                            userProductSelection = Integer.parseInt(document.get("Product Num").toString());
                                            originalNum = 6;
                                            startActivity(new Intent(Home_2.this, RentProductMain.class));


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
                                            Glide.with(Home_2.this).load(uri).into(productImage6);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception exception) {
                                        }
                                    });


                                }
                                if (loopMaster == 7) {

                                    productImage7.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            scrollY = mainScrollView.getScrollY();
                                            userProductSelection = Integer.parseInt(document.get("Product Num").toString());
                                            originalNum = 7;
                                            startActivity(new Intent(Home_2.this, RentProductMain.class));


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
                                            Glide.with(Home_2.this).load(uri).into(productImage7);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception exception) {
                                        }
                                    });


                                }

                                if (loopMaster == 8) {

                                    productImage8.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            scrollY = mainScrollView.getScrollY();
                                            userProductSelection = Integer.parseInt(document.get("Product Num").toString());
                                            originalNum = 8;
                                            startActivity(new Intent(Home_2.this, RentProductMain.class));


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
                                            Glide.with(Home_2.this).load(uri).into(productImage8);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception exception) {
                                        }
                                    });


                                }
                                if (loopMaster == 9) {

                                    productImage9.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            scrollY = mainScrollView.getScrollY();
                                            userProductSelection = Integer.parseInt(document.get("Product Num").toString());
                                            originalNum = 9;
                                            startActivity(new Intent(Home_2.this, RentProductMain.class));


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
                                            Glide.with(Home_2.this).load(uri).into(productImage9);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception exception) {
                                        }
                                    });


                                }
                                if (loopMaster == 10) {

                                    productImage10.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            scrollY = mainScrollView.getScrollY();
                                            userProductSelection = Integer.parseInt(document.get("Product Num").toString());
                                            originalNum = 10;
                                            startActivity(new Intent(Home_2.this, RentProductMain.class));


                                        }
                                    });


                                    productValue10.setVisibility(View.VISIBLE);
                                    productImage10.setVisibility(View.VISIBLE);
                                    productName10.setVisibility(View.VISIBLE);
                                    j.setVisibility(View.VISIBLE);
                                    productValue10.setText("$" + document.get("Rental Fee").toString() + "/day");
                                    productName10.setText(document.get("Product Name").toString());


                                    storageRef.child("images/" + document.get("Product Num").toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            // Got the download URL for 'users/me/profile.png'
                                            Glide.with(Home_2.this).load(uri).into(productImage10);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception exception) {
                                        }
                                    });


                                }
                                if (loopMaster == 11) {

                                    productImage11.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            scrollY = mainScrollView.getScrollY();
                                            userProductSelection = Integer.parseInt(document.get("Product Num").toString());
                                            originalNum = 11;
                                            startActivity(new Intent(Home_2.this, RentProductMain.class));


                                        }
                                    });


                                    productValue11.setVisibility(View.VISIBLE);
                                    productImage11.setVisibility(View.VISIBLE);
                                    productName11.setVisibility(View.VISIBLE);
                                    k.setVisibility(View.VISIBLE);
                                    productValue11.setText("$" + document.get("Rental Fee").toString() + "/day");
                                    productName11.setText(document.get("Product Name").toString());


                                    storageRef.child("images/" + document.get("Product Num").toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            // Got the download URL for 'users/me/profile.png'
                                            Glide.with(Home_2.this).load(uri).into(productImage11);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception exception) {
                                        }
                                    });


                                }
                                if (loopMaster == 12) {

                                    productImage12.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            scrollY = mainScrollView.getScrollY();
                                            userProductSelection = Integer.parseInt(document.get("Product Num").toString());
                                            originalNum = 12;
                                            startActivity(new Intent(Home_2.this, RentProductMain.class));


                                        }
                                    });


                                    productValue12.setVisibility(View.VISIBLE);
                                    productImage12.setVisibility(View.VISIBLE);
                                    productName12.setVisibility(View.VISIBLE);
                                    l.setVisibility(View.VISIBLE);
                                    productValue12.setText("$" + document.get("Rental Fee").toString() + "/day");
                                    productName12.setText(document.get("Product Name").toString());


                                    storageRef.child("images/" + document.get("Product Num").toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            // Got the download URL for 'users/me/profile.png'
                                            Glide.with(Home_2.this).load(uri).into(productImage12);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception exception) {
                                        }
                                    });


                                }
                                if (loopMaster == 13) {

                                    productImage13.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            scrollY = mainScrollView.getScrollY();
                                            userProductSelection = Integer.parseInt(document.get("Product Num").toString());
                                            originalNum = 13;
                                            startActivity(new Intent(Home_2.this, RentProductMain.class));


                                        }
                                    });


                                    productValue13.setVisibility(View.VISIBLE);
                                    productImage13.setVisibility(View.VISIBLE);
                                    productName13.setVisibility(View.VISIBLE);
                                    m.setVisibility(View.VISIBLE);
                                    productValue13.setText("$" + document.get("Rental Fee").toString() + "/day");
                                    productName13.setText(document.get("Product Name").toString());


                                    storageRef.child("images/" + document.get("Product Num").toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            // Got the download URL for 'users/me/profile.png'
                                            Glide.with(Home_2.this).load(uri).into(productImage13);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception exception) {
                                        }
                                    });


                                }
                                if (loopMaster == 14) {

                                    productImage14.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            scrollY = mainScrollView.getScrollY();
                                            userProductSelection = Integer.parseInt(document.get("Product Num").toString());
                                            originalNum = 14;
                                            startActivity(new Intent(Home_2.this, RentProductMain.class));


                                        }
                                    });


                                    productValue14.setVisibility(View.VISIBLE);
                                    productImage14.setVisibility(View.VISIBLE);
                                    productName14.setVisibility(View.VISIBLE);
                                    n.setVisibility(View.VISIBLE);
                                    productValue14.setText("$" + document.get("Rental Fee").toString() + "/day");
                                    productName14.setText(document.get("Product Name").toString());


                                    storageRef.child("images/" + document.get("Product Num").toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            // Got the download URL for 'users/me/profile.png'
                                            Glide.with(Home_2.this).load(uri).into(productImage14);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception exception) {
                                        }
                                    });


                                }

                                if (loopMaster == 15) {

                                    productImage15.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            scrollY = mainScrollView.getScrollY();
                                            userProductSelection = Integer.parseInt(document.get("Product Num").toString());
                                            originalNum = 15;
                                            startActivity(new Intent(Home_2.this, RentProductMain.class));


                                        }
                                    });


                                    productValue15.setVisibility(View.VISIBLE);
                                    productImage15.setVisibility(View.VISIBLE);
                                    productName15.setVisibility(View.VISIBLE);
                                    o.setVisibility(View.VISIBLE);
                                    productValue15.setText("$" + document.get("Rental Fee").toString() + "/day");
                                    productName15.setText(document.get("Product Name").toString());


                                    storageRef.child("images/" + document.get("Product Num").toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            // Got the download URL for 'users/me/profile.png'
                                            Glide.with(Home_2.this).load(uri).into(productImage15);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception exception) {
                                        }
                                    });


                                }
                                if (loopMaster == 16) {

                                    productImage16.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            scrollY = mainScrollView.getScrollY();
                                            userProductSelection = Integer.parseInt(document.get("Product Num").toString());
                                            originalNum = 16;
                                            startActivity(new Intent(Home_2.this, RentProductMain.class));


                                        }
                                    });


                                    productValue16.setVisibility(View.VISIBLE);
                                    productImage16.setVisibility(View.VISIBLE);
                                    productName16.setVisibility(View.VISIBLE);
                                    p.setVisibility(View.VISIBLE);
                                    productValue16.setText("$" + document.get("Rental Fee").toString() + "/day");
                                    productName16.setText(document.get("Product Name").toString());


                                    storageRef.child("images/" + document.get("Product Num").toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            // Got the download URL for 'users/me/profile.png'
                                            Glide.with(Home_2.this).load(uri).into(productImage16);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception exception) {
                                        }
                                    });


                                }
                                if (loopMaster == 17) {

                                    productImage17.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            scrollY = mainScrollView.getScrollY();
                                            userProductSelection = Integer.parseInt(document.get("Product Num").toString());
                                            originalNum = 17;
                                            startActivity(new Intent(Home_2.this, RentProductMain.class));


                                        }
                                    });


                                    productValue17.setVisibility(View.VISIBLE);
                                    productImage17.setVisibility(View.VISIBLE);
                                    productName17.setVisibility(View.VISIBLE);
                                    q.setVisibility(View.VISIBLE);
                                    productValue17.setText("$" + document.get("Rental Fee").toString() + "/day");
                                    productName17.setText(document.get("Product Name").toString());


                                    storageRef.child("images/" + document.get("Product Num").toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            // Got the download URL for 'users/me/profile.png'
                                            Glide.with(Home_2.this).load(uri).into(productImage17);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception exception) {
                                        }
                                    });


                                }
                                if (loopMaster == 18) {

                                    productImage18.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            scrollY = mainScrollView.getScrollY();
                                            userProductSelection = Integer.parseInt(document.get("Product Num").toString());
                                            originalNum = 18;
                                            startActivity(new Intent(Home_2.this, RentProductMain.class));


                                        }
                                    });


                                    productValue18.setVisibility(View.VISIBLE);
                                    productImage18.setVisibility(View.VISIBLE);
                                    productName18.setVisibility(View.VISIBLE);
                                    r.setVisibility(View.VISIBLE);
                                    productValue18.setText("$" + document.get("Rental Fee").toString() + "/day");
                                    productName18.setText(document.get("Product Name").toString());


                                    storageRef.child("images/" + document.get("Product Num").toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            // Got the download URL for 'users/me/profile.png'
                                            Glide.with(Home_2.this).load(uri).into(productImage18);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception exception) {
                                        }
                                    });


                                }
                                if (loopMaster == 19) {

                                    productImage19.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            scrollY = mainScrollView.getScrollY();
                                            userProductSelection = Integer.parseInt(document.get("Product Num").toString());
                                            originalNum = 19;
                                            startActivity(new Intent(Home_2.this, RentProductMain.class));


                                        }
                                    });


                                    productValue19.setVisibility(View.VISIBLE);
                                    productImage19.setVisibility(View.VISIBLE);
                                    productName19.setVisibility(View.VISIBLE);
                                    s.setVisibility(View.VISIBLE);
                                    productValue19.setText("$" + document.get("Rental Fee").toString() + "/day");
                                    productName19.setText(document.get("Product Name").toString());


                                    storageRef.child("images/" + document.get("Product Num").toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            // Got the download URL for 'users/me/profile.png'
                                            Glide.with(Home_2.this).load(uri).into(productImage19);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception exception) {
                                        }
                                    });


                                }
                                if (loopMaster == 20) {

                                    productImage20.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            scrollY = mainScrollView.getScrollY();
                                            userProductSelection = Integer.parseInt(document.get("Product Num").toString());
                                            originalNum = 20;
                                            startActivity(new Intent(Home_2.this, RentProductMain.class));


                                        }
                                    });


                                    productValue20.setVisibility(View.VISIBLE);
                                    productImage20.setVisibility(View.VISIBLE);
                                    productName20.setVisibility(View.VISIBLE);
                                    t.setVisibility(View.VISIBLE);
                                    productValue20.setText("$" + document.get("Rental Fee").toString() + "/day");
                                    productName20.setText(document.get("Product Name").toString());


                                    storageRef.child("images/" + document.get("Product Num").toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            // Got the download URL for 'users/me/profile.png'
                                            Glide.with(Home_2.this).load(uri).into(productImage20);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception exception) {
                                        }
                                    });


                                }
                                if (loopMaster == 21) {

                                    productImage21.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            scrollY = mainScrollView.getScrollY();
                                            userProductSelection = Integer.parseInt(document.get("Product Num").toString());
                                            originalNum = 21;
                                            startActivity(new Intent(Home_2.this, RentProductMain.class));


                                        }
                                    });


                                    productValue21.setVisibility(View.VISIBLE);
                                    productImage21.setVisibility(View.VISIBLE);
                                    productName21.setVisibility(View.VISIBLE);
                                    u.setVisibility(View.VISIBLE);
                                    productValue21.setText("$" + document.get("Rental Fee").toString() + "/day");
                                    productName21.setText(document.get("Product Name").toString());


                                    storageRef.child("images/" + document.get("Product Num").toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            // Got the download URL for 'users/me/profile.png'
                                            Glide.with(Home_2.this).load(uri).into(productImage21);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception exception) {
                                        }
                                    });


                                }
                                if (loopMaster == 22) {

                                    productImage22.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            scrollY = mainScrollView.getScrollY();
                                            userProductSelection = Integer.parseInt(document.get("Product Num").toString());
                                            originalNum = 22;
                                            startActivity(new Intent(Home_2.this, RentProductMain.class));


                                        }
                                    });


                                    productValue22.setVisibility(View.VISIBLE);
                                    productImage22.setVisibility(View.VISIBLE);
                                    productName22.setVisibility(View.VISIBLE);
                                    v1.setVisibility(View.VISIBLE);
                                    productValue22.setText("$" + document.get("Rental Fee").toString() + "/day");
                                    productName22.setText(document.get("Product Name").toString());


                                    storageRef.child("images/" + document.get("Product Num").toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            // Got the download URL for 'users/me/profile.png'
                                            Glide.with(Home_2.this).load(uri).into(productImage22);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception exception) {
                                        }
                                    });


                                }
                                if (loopMaster == 23) {

                                    productImage23.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            scrollY = mainScrollView.getScrollY();
                                            userProductSelection = Integer.parseInt(document.get("Product Num").toString());
                                            originalNum = 23;
                                            startActivity(new Intent(Home_2.this, RentProductMain.class));


                                        }
                                    });


                                    productValue23.setVisibility(View.VISIBLE);
                                    productImage23.setVisibility(View.VISIBLE);
                                    productName23.setVisibility(View.VISIBLE);
                                    w.setVisibility(View.VISIBLE);
                                    productValue23.setText("$" + document.get("Rental Fee").toString() + "/day");
                                    productName23.setText(document.get("Product Name").toString());


                                    storageRef.child("images/" + document.get("Product Num").toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            // Got the download URL for 'users/me/profile.png'
                                            Glide.with(Home_2.this).load(uri).into(productImage23);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception exception) {
                                        }
                                    });


                                }
                                if (loopMaster == 24) {

                                    productImage24.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            scrollY = mainScrollView.getScrollY();
                                            userProductSelection = Integer.parseInt(document.get("Product Num").toString());
                                            originalNum = 24;
                                            startActivity(new Intent(Home_2.this, RentProductMain.class));


                                        }
                                    });


                                    productValue24.setVisibility(View.VISIBLE);
                                    productImage24.setVisibility(View.VISIBLE);
                                    productName24.setVisibility(View.VISIBLE);
                                    x.setVisibility(View.VISIBLE);
                                    productValue24.setText("$" + document.get("Rental Fee").toString() + "/day");
                                    productName24.setText(document.get("Product Name").toString());

                                    doc24ID = document.get("Product Num").toString();
                                    storageRef.child("images/" + document.get("Product Num").toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            // Got the download URL for 'users/me/profile.png'
                                            Glide.with(Home_2.this).load(uri).into(productImage24);
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




        nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                productValue1.setVisibility(View.GONE);
                productImage1.setVisibility(View.GONE);
                productName1.setVisibility(View.GONE);

                productValue2.setVisibility(View.GONE);
                productImage2.setVisibility(View.GONE);
                productName2.setVisibility(View.GONE);

                productValue3.setVisibility(View.GONE);
                productImage3.setVisibility(View.GONE);
                productName3.setVisibility(View.GONE);

                productValue4.setVisibility(View.GONE);
                productImage4.setVisibility(View.GONE);
                productName4.setVisibility(View.GONE);

                productValue5.setVisibility(View.GONE);
                productImage5.setVisibility(View.GONE);
                productName5.setVisibility(View.GONE);

                productValue6.setVisibility(View.GONE);
                productImage6.setVisibility(View.GONE);
                productName6.setVisibility(View.GONE);

                productValue7.setVisibility(View.GONE);
                productImage7.setVisibility(View.GONE);
                productName7.setVisibility(View.GONE);
                g.setVisibility(View.GONE);

                productValue8.setVisibility(View.GONE);
                productImage8.setVisibility(View.GONE);
                productName8.setVisibility(View.GONE);
                h.setVisibility(View.GONE);

                productValue9.setVisibility(View.GONE);
                productImage9.setVisibility(View.GONE);
                productName9.setVisibility(View.GONE);
                i1.setVisibility(View.GONE);
                productValue10.setVisibility(View.GONE);
                productImage10.setVisibility(View.GONE);
                productName10.setVisibility(View.GONE);
                j.setVisibility(View.GONE);
                productValue11.setVisibility(View.GONE);
                productImage11.setVisibility(View.GONE);
                productName11.setVisibility(View.GONE);
                k.setVisibility(View.GONE);
                productValue12.setVisibility(View.GONE);
                productImage12.setVisibility(View.GONE);
                productName12.setVisibility(View.GONE);
                l.setVisibility(View.GONE);
                productValue13.setVisibility(View.GONE);
                productImage13.setVisibility(View.GONE);
                productName13.setVisibility(View.GONE);
                m.setVisibility(View.GONE);
                productValue14.setVisibility(View.GONE);
                productImage14.setVisibility(View.GONE);
                productName14.setVisibility(View.GONE);
                n.setVisibility(View.GONE);
                productValue15.setVisibility(View.GONE);
                productImage15.setVisibility(View.GONE);
                productName15.setVisibility(View.GONE);
                o.setVisibility(View.GONE);
                productValue16.setVisibility(View.GONE);
                productImage16.setVisibility(View.GONE);
                productName16.setVisibility(View.GONE);
                p.setVisibility(View.GONE);
                productValue17.setVisibility(View.GONE);
                productImage17.setVisibility(View.GONE);
                productName17.setVisibility(View.GONE);
                q.setVisibility(View.GONE);
                productValue18.setVisibility(View.GONE);
                productImage18.setVisibility(View.GONE);
                productName18.setVisibility(View.GONE);
                r.setVisibility(View.GONE);
                productValue19.setVisibility(View.GONE);
                productImage19.setVisibility(View.GONE);
                productName19.setVisibility(View.GONE);
                s.setVisibility(View.GONE);
                productValue20.setVisibility(View.GONE);
                productImage20.setVisibility(View.GONE);
                productName20.setVisibility(View.GONE);
                t.setVisibility(View.GONE);
                productValue21.setVisibility(View.GONE);
                productImage21.setVisibility(View.GONE);
                productName21.setVisibility(View.GONE);
                u.setVisibility(View.GONE);
                productValue22.setVisibility(View.GONE);
                productImage22.setVisibility(View.GONE);
                productName22.setVisibility(View.GONE);
                v1.setVisibility(View.GONE);
                productValue23.setVisibility(View.GONE);
                productImage23.setVisibility(View.GONE);
                productName23.setVisibility(View.GONE);
                w.setVisibility(View.GONE);
                productValue24.setVisibility(View.GONE);
                productImage24.setVisibility(View.GONE);
                productName24.setVisibility(View.GONE);
                x.setVisibility(View.GONE);

                firstPage.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot documentSnapshots) {

                        // Get the last visible document
                        DocumentSnapshot lastVisible = documentSnapshots.getDocuments()
                                .get(documentSnapshots.size() -1);

                        // Construct a new query starting at this document,
                        // get the next 25 cities.
                        docRef = mFirestore.collection("products").document(doc24ID);
                        Query next = mFirestore.collection("products")
                                .orderBy("Views", Query.Direction.DESCENDING)
                                .startAfter(lastVisible);
                        loopMaster = 1;

                        next
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot document : task.getResult()) {

                                                if (loopMaster == 1) {
                                                    productImage1.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            scrollY = mainScrollView.getScrollY();
                                                            Home.userProductSelection = Integer.parseInt(document.get("Product Num").toString());
                                                            originalNum = 1;
                                                            startActivity(new Intent(Home_2.this, RentProductMain.class));


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
                                                            Glide.with(Home_2.this).load(uri).into(productImage1);
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception exception) {
                                                        }
                                                    });


                                                }

                                                if (loopMaster == 2) {

                                                    productImage2.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            scrollY = mainScrollView.getScrollY();
                                                            userProductSelection = Integer.parseInt(document.get("Product Num").toString());
                                                            originalNum = 2;
                                                            startActivity(new Intent(Home_2.this, RentProductMain.class));


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
                                                            Glide.with(Home_2.this).load(uri).into(productImage2);
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception exception) {
                                                        }
                                                    });


                                                }
                                                if (loopMaster == 3) {

                                                    productImage3.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            scrollY = mainScrollView.getScrollY();
                                                            userProductSelection = Integer.parseInt(document.get("Product Num").toString());
                                                            originalNum = 3;
                                                            startActivity(new Intent(Home_2.this, RentProductMain.class));


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
                                                            Glide.with(Home_2.this).load(uri).into(productImage3);
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception exception) {
                                                        }
                                                    });


                                                }
                                                if (loopMaster == 4) {

                                                    productImage4.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            scrollY = mainScrollView.getScrollY();
                                                            userProductSelection = Integer.parseInt(document.get("Product Num").toString());
                                                            originalNum = 4;
                                                            startActivity(new Intent(Home_2.this, RentProductMain.class));


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
                                                            Glide.with(Home_2.this).load(uri).into(productImage4);
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception exception) {
                                                        }
                                                    });


                                                }
                                                if (loopMaster == 5) {

                                                    productImage5.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            scrollY = mainScrollView.getScrollY();
                                                            userProductSelection = Integer.parseInt(document.get("Product Num").toString());
                                                            originalNum = 5;
                                                            startActivity(new Intent(Home_2.this, RentProductMain.class));


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
                                                            Glide.with(Home_2.this).load(uri).into(productImage5);
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception exception) {
                                                        }
                                                    });


                                                }

                                                if (loopMaster == 6) {

                                                    productImage6.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            scrollY = mainScrollView.getScrollY();
                                                            userProductSelection = Integer.parseInt(document.get("Product Num").toString());
                                                            originalNum = 6;
                                                            startActivity(new Intent(Home_2.this, RentProductMain.class));


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
                                                            Glide.with(Home_2.this).load(uri).into(productImage6);
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception exception) {
                                                        }
                                                    });


                                                }
                                                if (loopMaster == 7) {

                                                    productImage7.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            scrollY = mainScrollView.getScrollY();
                                                            userProductSelection = Integer.parseInt(document.get("Product Num").toString());
                                                            originalNum = 7;
                                                            startActivity(new Intent(Home_2.this, RentProductMain.class));


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
                                                            Glide.with(Home_2.this).load(uri).into(productImage7);
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception exception) {
                                                        }
                                                    });


                                                }

                                                if (loopMaster == 8) {

                                                    productImage8.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            scrollY = mainScrollView.getScrollY();
                                                            userProductSelection = Integer.parseInt(document.get("Product Num").toString());
                                                            originalNum = 8;
                                                            startActivity(new Intent(Home_2.this, RentProductMain.class));


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
                                                            Glide.with(Home_2.this).load(uri).into(productImage8);
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception exception) {
                                                        }
                                                    });


                                                }
                                                if (loopMaster == 9) {

                                                    productImage9.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            scrollY = mainScrollView.getScrollY();
                                                            userProductSelection = Integer.parseInt(document.get("Product Num").toString());
                                                            originalNum = 9;
                                                            startActivity(new Intent(Home_2.this, RentProductMain.class));


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
                                                            Glide.with(Home_2.this).load(uri).into(productImage9);
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception exception) {
                                                        }
                                                    });


                                                }
                                                if (loopMaster == 10) {

                                                    productImage10.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            scrollY = mainScrollView.getScrollY();
                                                            userProductSelection = Integer.parseInt(document.get("Product Num").toString());
                                                            originalNum = 10;
                                                            startActivity(new Intent(Home_2.this, RentProductMain.class));


                                                        }
                                                    });


                                                    productValue10.setVisibility(View.VISIBLE);
                                                    productImage10.setVisibility(View.VISIBLE);
                                                    productName10.setVisibility(View.VISIBLE);
                                                    j.setVisibility(View.VISIBLE);
                                                    productValue10.setText("$" + document.get("Rental Fee").toString() + "/day");
                                                    productName10.setText(document.get("Product Name").toString());


                                                    storageRef.child("images/" + document.get("Product Num").toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                        @Override
                                                        public void onSuccess(Uri uri) {
                                                            // Got the download URL for 'users/me/profile.png'
                                                            Glide.with(Home_2.this).load(uri).into(productImage10);
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception exception) {
                                                        }
                                                    });


                                                }
                                                if (loopMaster == 11) {

                                                    productImage11.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            scrollY = mainScrollView.getScrollY();
                                                            userProductSelection = Integer.parseInt(document.get("Product Num").toString());
                                                            originalNum = 11;
                                                            startActivity(new Intent(Home_2.this, RentProductMain.class));


                                                        }
                                                    });


                                                    productValue11.setVisibility(View.VISIBLE);
                                                    productImage11.setVisibility(View.VISIBLE);
                                                    productName11.setVisibility(View.VISIBLE);
                                                    k.setVisibility(View.VISIBLE);
                                                    productValue11.setText("$" + document.get("Rental Fee").toString() + "/day");
                                                    productName11.setText(document.get("Product Name").toString());


                                                    storageRef.child("images/" + document.get("Product Num").toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                        @Override
                                                        public void onSuccess(Uri uri) {
                                                            // Got the download URL for 'users/me/profile.png'
                                                            Glide.with(Home_2.this).load(uri).into(productImage11);
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception exception) {
                                                        }
                                                    });


                                                }
                                                if (loopMaster == 12) {

                                                    productImage12.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            scrollY = mainScrollView.getScrollY();
                                                            userProductSelection = Integer.parseInt(document.get("Product Num").toString());
                                                            originalNum = 12;
                                                            startActivity(new Intent(Home_2.this, RentProductMain.class));


                                                        }
                                                    });


                                                    productValue12.setVisibility(View.VISIBLE);
                                                    productImage12.setVisibility(View.VISIBLE);
                                                    productName12.setVisibility(View.VISIBLE);
                                                    l.setVisibility(View.VISIBLE);
                                                    productValue12.setText("$" + document.get("Rental Fee").toString() + "/day");
                                                    productName12.setText(document.get("Product Name").toString());


                                                    storageRef.child("images/" + document.get("Product Num").toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                        @Override
                                                        public void onSuccess(Uri uri) {
                                                            // Got the download URL for 'users/me/profile.png'
                                                            Glide.with(Home_2.this).load(uri).into(productImage12);
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception exception) {
                                                        }
                                                    });


                                                }
                                                if (loopMaster == 13) {

                                                    productImage13.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            scrollY = mainScrollView.getScrollY();
                                                            userProductSelection = Integer.parseInt(document.get("Product Num").toString());
                                                            originalNum = 13;
                                                            startActivity(new Intent(Home_2.this, RentProductMain.class));


                                                        }
                                                    });


                                                    productValue13.setVisibility(View.VISIBLE);
                                                    productImage13.setVisibility(View.VISIBLE);
                                                    productName13.setVisibility(View.VISIBLE);
                                                    m.setVisibility(View.VISIBLE);
                                                    productValue13.setText("$" + document.get("Rental Fee").toString() + "/day");
                                                    productName13.setText(document.get("Product Name").toString());


                                                    storageRef.child("images/" + document.get("Product Num").toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                        @Override
                                                        public void onSuccess(Uri uri) {
                                                            // Got the download URL for 'users/me/profile.png'
                                                            Glide.with(Home_2.this).load(uri).into(productImage13);
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception exception) {
                                                        }
                                                    });


                                                }
                                                if (loopMaster == 14) {

                                                    productImage14.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            scrollY = mainScrollView.getScrollY();
                                                            userProductSelection = Integer.parseInt(document.get("Product Num").toString());
                                                            originalNum = 14;
                                                            startActivity(new Intent(Home_2.this, RentProductMain.class));


                                                        }
                                                    });


                                                    productValue14.setVisibility(View.VISIBLE);
                                                    productImage14.setVisibility(View.VISIBLE);
                                                    productName14.setVisibility(View.VISIBLE);
                                                    n.setVisibility(View.VISIBLE);
                                                    productValue14.setText("$" + document.get("Rental Fee").toString() + "/day");
                                                    productName14.setText(document.get("Product Name").toString());


                                                    storageRef.child("images/" + document.get("Product Num").toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                        @Override
                                                        public void onSuccess(Uri uri) {
                                                            // Got the download URL for 'users/me/profile.png'
                                                            Glide.with(Home_2.this).load(uri).into(productImage14);
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception exception) {
                                                        }
                                                    });


                                                }

                                                if (loopMaster == 15) {

                                                    productImage15.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            scrollY = mainScrollView.getScrollY();
                                                            userProductSelection = Integer.parseInt(document.get("Product Num").toString());
                                                            originalNum = 15;
                                                            startActivity(new Intent(Home_2.this, RentProductMain.class));


                                                        }
                                                    });


                                                    productValue15.setVisibility(View.VISIBLE);
                                                    productImage15.setVisibility(View.VISIBLE);
                                                    productName15.setVisibility(View.VISIBLE);
                                                    o.setVisibility(View.VISIBLE);
                                                    productValue15.setText("$" + document.get("Rental Fee").toString() + "/day");
                                                    productName15.setText(document.get("Product Name").toString());


                                                    storageRef.child("images/" + document.get("Product Num").toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                        @Override
                                                        public void onSuccess(Uri uri) {
                                                            // Got the download URL for 'users/me/profile.png'
                                                            Glide.with(Home_2.this).load(uri).into(productImage15);
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception exception) {
                                                        }
                                                    });


                                                }
                                                if (loopMaster == 16) {

                                                    productImage16.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            scrollY = mainScrollView.getScrollY();
                                                            userProductSelection = Integer.parseInt(document.get("Product Num").toString());
                                                            originalNum = 16;
                                                            startActivity(new Intent(Home_2.this, RentProductMain.class));


                                                        }
                                                    });


                                                    productValue16.setVisibility(View.VISIBLE);
                                                    productImage16.setVisibility(View.VISIBLE);
                                                    productName16.setVisibility(View.VISIBLE);
                                                    p.setVisibility(View.VISIBLE);
                                                    productValue16.setText("$" + document.get("Rental Fee").toString() + "/day");
                                                    productName16.setText(document.get("Product Name").toString());


                                                    storageRef.child("images/" + document.get("Product Num").toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                        @Override
                                                        public void onSuccess(Uri uri) {
                                                            // Got the download URL for 'users/me/profile.png'
                                                            Glide.with(Home_2.this).load(uri).into(productImage16);
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception exception) {
                                                        }
                                                    });


                                                }
                                                if (loopMaster == 17) {

                                                    productImage17.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            scrollY = mainScrollView.getScrollY();
                                                            userProductSelection = Integer.parseInt(document.get("Product Num").toString());
                                                            originalNum = 17;
                                                            startActivity(new Intent(Home_2.this, RentProductMain.class));


                                                        }
                                                    });


                                                    productValue17.setVisibility(View.VISIBLE);
                                                    productImage17.setVisibility(View.VISIBLE);
                                                    productName17.setVisibility(View.VISIBLE);
                                                    q.setVisibility(View.VISIBLE);
                                                    productValue17.setText("$" + document.get("Rental Fee").toString() + "/day");
                                                    productName17.setText(document.get("Product Name").toString());


                                                    storageRef.child("images/" + document.get("Product Num").toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                        @Override
                                                        public void onSuccess(Uri uri) {
                                                            // Got the download URL for 'users/me/profile.png'
                                                            Glide.with(Home_2.this).load(uri).into(productImage17);
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception exception) {
                                                        }
                                                    });


                                                }
                                                if (loopMaster == 18) {

                                                    productImage18.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            scrollY = mainScrollView.getScrollY();
                                                            userProductSelection = Integer.parseInt(document.get("Product Num").toString());
                                                            originalNum = 18;
                                                            startActivity(new Intent(Home_2.this, RentProductMain.class));


                                                        }
                                                    });


                                                    productValue18.setVisibility(View.VISIBLE);
                                                    productImage18.setVisibility(View.VISIBLE);
                                                    productName18.setVisibility(View.VISIBLE);
                                                    r.setVisibility(View.VISIBLE);
                                                    productValue18.setText("$" + document.get("Rental Fee").toString() + "/day");
                                                    productName18.setText(document.get("Product Name").toString());


                                                    storageRef.child("images/" + document.get("Product Num").toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                        @Override
                                                        public void onSuccess(Uri uri) {
                                                            // Got the download URL for 'users/me/profile.png'
                                                            Glide.with(Home_2.this).load(uri).into(productImage18);
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception exception) {
                                                        }
                                                    });


                                                }
                                                if (loopMaster == 19) {

                                                    productImage19.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            scrollY = mainScrollView.getScrollY();
                                                            userProductSelection = Integer.parseInt(document.get("Product Num").toString());
                                                            originalNum = 19;
                                                            startActivity(new Intent(Home_2.this, RentProductMain.class));


                                                        }
                                                    });


                                                    productValue19.setVisibility(View.VISIBLE);
                                                    productImage19.setVisibility(View.VISIBLE);
                                                    productName19.setVisibility(View.VISIBLE);
                                                    s.setVisibility(View.VISIBLE);
                                                    productValue19.setText("$" + document.get("Rental Fee").toString() + "/day");
                                                    productName19.setText(document.get("Product Name").toString());


                                                    storageRef.child("images/" + document.get("Product Num").toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                        @Override
                                                        public void onSuccess(Uri uri) {
                                                            // Got the download URL for 'users/me/profile.png'
                                                            Glide.with(Home_2.this).load(uri).into(productImage19);
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception exception) {
                                                        }
                                                    });


                                                }
                                                if (loopMaster == 20) {

                                                    productImage20.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            scrollY = mainScrollView.getScrollY();
                                                            userProductSelection = Integer.parseInt(document.get("Product Num").toString());
                                                            originalNum = 20;
                                                            startActivity(new Intent(Home_2.this, RentProductMain.class));


                                                        }
                                                    });


                                                    productValue20.setVisibility(View.VISIBLE);
                                                    productImage20.setVisibility(View.VISIBLE);
                                                    productName20.setVisibility(View.VISIBLE);
                                                    t.setVisibility(View.VISIBLE);
                                                    productValue20.setText("$" + document.get("Rental Fee").toString() + "/day");
                                                    productName20.setText(document.get("Product Name").toString());


                                                    storageRef.child("images/" + document.get("Product Num").toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                        @Override
                                                        public void onSuccess(Uri uri) {
                                                            // Got the download URL for 'users/me/profile.png'
                                                            Glide.with(Home_2.this).load(uri).into(productImage20);
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception exception) {
                                                        }
                                                    });


                                                }
                                                if (loopMaster == 21) {

                                                    productImage21.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            scrollY = mainScrollView.getScrollY();
                                                            userProductSelection = Integer.parseInt(document.get("Product Num").toString());
                                                            originalNum = 21;
                                                            startActivity(new Intent(Home_2.this, RentProductMain.class));


                                                        }
                                                    });


                                                    productValue21.setVisibility(View.VISIBLE);
                                                    productImage21.setVisibility(View.VISIBLE);
                                                    productName21.setVisibility(View.VISIBLE);
                                                    u.setVisibility(View.VISIBLE);
                                                    productValue21.setText("$" + document.get("Rental Fee").toString() + "/day");
                                                    productName21.setText(document.get("Product Name").toString());


                                                    storageRef.child("images/" + document.get("Product Num").toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                        @Override
                                                        public void onSuccess(Uri uri) {
                                                            // Got the download URL for 'users/me/profile.png'
                                                            Glide.with(Home_2.this).load(uri).into(productImage21);
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception exception) {
                                                        }
                                                    });


                                                }
                                                if (loopMaster == 22) {

                                                    productImage22.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            scrollY = mainScrollView.getScrollY();
                                                            userProductSelection = Integer.parseInt(document.get("Product Num").toString());
                                                            originalNum = 22;
                                                            startActivity(new Intent(Home_2.this, RentProductMain.class));


                                                        }
                                                    });


                                                    productValue22.setVisibility(View.VISIBLE);
                                                    productImage22.setVisibility(View.VISIBLE);
                                                    productName22.setVisibility(View.VISIBLE);
                                                    v1.setVisibility(View.VISIBLE);
                                                    productValue22.setText("$" + document.get("Rental Fee").toString() + "/day");
                                                    productName22.setText(document.get("Product Name").toString());


                                                    storageRef.child("images/" + document.get("Product Num").toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                        @Override
                                                        public void onSuccess(Uri uri) {
                                                            // Got the download URL for 'users/me/profile.png'
                                                            Glide.with(Home_2.this).load(uri).into(productImage22);
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception exception) {
                                                        }
                                                    });


                                                }
                                                if (loopMaster == 23) {

                                                    productImage23.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            scrollY = mainScrollView.getScrollY();
                                                            userProductSelection = Integer.parseInt(document.get("Product Num").toString());
                                                            originalNum = 23;
                                                            startActivity(new Intent(Home_2.this, RentProductMain.class));


                                                        }
                                                    });


                                                    productValue23.setVisibility(View.VISIBLE);
                                                    productImage23.setVisibility(View.VISIBLE);
                                                    productName23.setVisibility(View.VISIBLE);
                                                    w.setVisibility(View.VISIBLE);
                                                    productValue23.setText("$" + document.get("Rental Fee").toString() + "/day");
                                                    productName23.setText(document.get("Product Name").toString());


                                                    storageRef.child("images/" + document.get("Product Num").toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                        @Override
                                                        public void onSuccess(Uri uri) {
                                                            // Got the download URL for 'users/me/profile.png'
                                                            Glide.with(Home_2.this).load(uri).into(productImage23);
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception exception) {
                                                        }
                                                    });


                                                }
                                                if (loopMaster == 24) {

                                                    productImage24.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            scrollY = mainScrollView.getScrollY();
                                                            userProductSelection = Integer.parseInt(document.get("Product Num").toString());
                                                            originalNum = 24;
                                                            startActivity(new Intent(Home_2.this, RentProductMain.class));


                                                        }
                                                    });


                                                    productValue24.setVisibility(View.VISIBLE);
                                                    productImage24.setVisibility(View.VISIBLE);
                                                    productName24.setVisibility(View.VISIBLE);
                                                    x.setVisibility(View.VISIBLE);
                                                    productValue24.setText("$" + document.get("Rental Fee").toString() + "/day");
                                                    productName24.setText(document.get("Product Name").toString());

                                                    doc24ID = document.get("Product Num").toString();
                                                    storageRef.child("images/" + document.get("Product Num").toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                        @Override
                                                        public void onSuccess(Uri uri) {
                                                            // Got the download URL for 'users/me/profile.png'
                                                            Glide.with(Home_2.this).load(uri).into(productImage24);
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
                });





            }

        });








        cartNavBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                mFirebaseUser = mAuth.getCurrentUser();
                if(mFirebaseUser != null) {
                    currentUserID = mFirebaseUser.getUid(); //Do what you need to do with the id
                }





                mAuth.getCurrentUser().reload();
                if(mFirebaseUser.isEmailVerified()){
                    startActivity(new Intent(Home_2.this, ProductOverLord.class));
                    overridePendingTransition(0, 0);
                }
                else{
                    startActivity(new Intent(Home_2.this, WelcomeProdcutMain.class));
                    overridePendingTransition(0, 0);

                }

            }
        });

        chatNavBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home_2.this, ChatMain.class));
                overridePendingTransition(0, 0);
                scrollY = 0;
            }
        });

        profileNavBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home_2.this, ProfileMain.class));
                overridePendingTransition(0, 0);
                scrollY = 0;
            }
        });
        refreshbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Home_2.this, Home_2.class));
                scrollY = 0;
            }
        });


        //Product Page Buttons





    }
    private void showMessage(String message){

        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }


    private boolean showProduct(int productNum, TextView productValueName, TextView productNameName, ImageView productImageName){

        return createdDoc;
    }

    public void notification(String notificationText){

        NotificationCompat.Builder notification = new NotificationCompat.Builder(this)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setSmallIcon(R.drawable.rentathon_the_renting_app_logo)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.rentathon_the_renting_app_logo))
                .setContentTitle("Notification from Rentathon")
                .setContentText(notificationText);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification.build());


    }

    public int numberOfPages(int num ){

        int re = 0;

        if(num <= 24){
            re = 1;
        }
        else if(num > 24 && num <= 48){
            re = 2;
        }
        else if(num > 48 && num <= 72){
            re = 3;
        }
        else if(num > 72 && num <= 96){
            re = 4;
        }
        else if(num > 96 && num <= 120){
            re = 5;
        }

        return re;
    }


}
