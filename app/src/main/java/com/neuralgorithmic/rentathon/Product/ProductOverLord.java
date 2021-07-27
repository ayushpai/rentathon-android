package com.neuralgorithmic.rentathon.Product;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
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

import java.util.ArrayList;

public class ProductOverLord extends AppCompatActivity {
    ConstraintLayout a, b, c, d, e, f, g, h, i1, j, addProduct;
    ConstraintLayout ra, rb, rc, rd, re, rf, rg, rh, ri, rj;
    private FirebaseAuth mAuth;
    FirebaseFirestore mFirestore;
    StorageReference storageRef;
    FirebaseStorage storage;
    DocumentReference docRef, docRef2;
    public ImageView RproductImage1, RproductImage2, RproductImage3, RproductImage4, RproductImage5, RproductImage6, RproductImage7, RproductImage8, RproductImage9, RproductImage10;

    public ImageView productImage1, productImage2, productImage3, productImage4, productImage5, productImage6, productImage7, productImage8, productImage9, productImage10;
    public ImageView productIcon1, productIcon2, productIcon3, productIcon4, productIcon5, productIcon6, productIcon7, productIcon8, productIcon9, productIcon10;
    public ImageView RproductIcon1, RproductIcon2, RproductIcon3, RproductIcon4, RproductIcon5, RproductIcon6, RproductIcon7, RproductIcon8, RproductIcon9, RproductIcon10;

    FirebaseUser mFirebaseUser;
    String currentUserID;
    public int loopMaster = 1;
    ImageView back1, back2, back3, back4, back5, back6, back7, back8, back9, back10;
    ImageView Rback1, Rback2, Rback3, Rback4, Rback5, Rback6, Rback7, Rback8, Rback9, Rback10;
    public static boolean fromOverLord;
    public boolean verified;
    Button homeNav, chatNav, profileNav;
    TextView noProductsTxt;
    boolean PendingOVerification, OVerified;
    ScrollView scrollViewListings, scrollViewRentals;
    Button myRentalsBtn, myListingsBtn;

    public static ArrayList<String> rentalProductNames;
    public static ArrayList<String> rentalProductPrices;
    public static ArrayList<String> rentalTransactionIDs;
    public static ArrayList<String> rentalStatuses;
    public static ArrayList<String> rentalProductIDs;

    public static ArrayList<String> listingProductNames;
    public static ArrayList<String> listingProductPrices;
    public static ArrayList<String> listingTransactionIDs;
    public static ArrayList<String> listingStatuses;
    public static ArrayList<String> listingProductIDs;


    public static String productIDForQRCode;

    public TextView ProductName1, ProductStatus1, ProductPrice1, ProductName2, ProductStatus2, ProductPrice2, ProductName3, ProductStatus3, ProductPrice3, ProductName4, ProductStatus4, ProductPrice4, ProductName5, ProductStatus5, ProductPrice5, ProductName6, ProductStatus6, ProductPrice6, ProductName7, ProductStatus7, ProductPrice7, ProductName8, ProductStatus8, ProductPrice8, ProductName9, ProductStatus9, ProductPrice9, ProductName10, ProductStatus10, ProductPrice10;
    public TextView RProductName1, RProductStatus1, RProductPrice1, RProductName2, RProductStatus2, RProductPrice2, RProductName3, RProductStatus3, RProductPrice3, RProductName4, RProductStatus4, RProductPrice4, RProductName5, RProductStatus5, RProductPrice5, RProductName6, RProductStatus6, RProductPrice6, RProductName7, RProductStatus7, RProductPrice7, RProductName8, RProductStatus8, RProductPrice8, RProductName9, RProductStatus9, RProductPrice9, RProductName10, RProductStatus10, RProductPrice10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_my_rentals_and_my_products);
        RentProductMain.fromHome = false;
        fromOverLord = true;
        UserHomeMain.fromUserHomeMain = false;
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);

        rentalProductNames = new ArrayList<String>();
        rentalProductPrices = new ArrayList<String>();
        rentalTransactionIDs = new ArrayList<String>();
        rentalStatuses = new ArrayList<String>();
        rentalProductIDs = new ArrayList<String>();

        listingProductNames = new ArrayList<String>();
        listingProductPrices = new ArrayList<String>();
        listingTransactionIDs = new ArrayList<String>();
        listingStatuses = new ArrayList<String>();
        listingProductIDs = new ArrayList<String>();

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


        scrollViewRentals = findViewById(R.id.scrollView_MY_RENTALS);
        scrollViewListings = findViewById(R.id.scrollView_MY_PRODUCTS);

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

        ra = findViewById(R.id.ra);
        rb = findViewById(R.id.rb);
        rc = findViewById(R.id.rc);
        rd = findViewById(R.id.rd);
        re = findViewById(R.id.re);
        rf = findViewById(R.id.rf);
        rg = findViewById(R.id.rg);
        rh = findViewById(R.id.rh);
        ri = findViewById(R.id.ri);
        rj = findViewById(R.id.rj);

        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        addProduct = findViewById(R.id.addProduct);

        noProductsTxt = findViewById(R.id.something_special);

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

        myListingsBtn = findViewById(R.id.condition_txt_view);
        myRentalsBtn = findViewById(R.id.condition_title);

        productImage1 = findViewById(R.id.product_1_image);
        productImage2 = findViewById(R.id.product_2_image);
        productImage3 = findViewById(R.id.product_3_image);
        productImage4 = findViewById(R.id.product_4_image);
        productImage5 = findViewById(R.id.product_5_image);
        productImage6 = findViewById(R.id.product_6_image);
        productImage7 = findViewById(R.id.product_7_image);
        productImage8 = findViewById(R.id.product_8_image);
        productImage9 = findViewById(R.id.product_9_image);
        productImage10 = findViewById(R.id.product_10_image);

        RproductImage1 = findViewById(R.id.rproduct_1_image);
        RproductImage2 = findViewById(R.id.rproduct_2_image);
        RproductImage3 = findViewById(R.id.rproduct_3_image);
        RproductImage4 = findViewById(R.id.rproduct_4_image);
        RproductImage5 = findViewById(R.id.rproduct_5_image);
        RproductImage6 = findViewById(R.id.rproduct_6_image);
        RproductImage7 = findViewById(R.id.rproduct_7_image);
        RproductImage8 = findViewById(R.id.rproduct_8_image);
        RproductImage9 = findViewById(R.id.rproduct_9_image);
        RproductImage10 = findViewById(R.id.rproduct_10_image);

        //Just changed the names so it was runnable
        ProductName1 = findViewById(R.id.product_1_name);
        ProductPrice1 = findViewById(R.id.product_1_revenue);
        ProductStatus1 = findViewById(R.id.product_1_status);
        ProductName2 = findViewById(R.id.product_2_name);
        ProductPrice2 = findViewById(R.id.product_2_revenue);
        ProductStatus2 = findViewById(R.id.product_2_status);
        ProductName3 = findViewById(R.id.product_3_name);
        ProductPrice3 = findViewById(R.id.product_3_revenue);
        ProductStatus3 = findViewById(R.id.product_3_status);
        ProductName4 = findViewById(R.id.product_4_name);
        ProductPrice4 = findViewById(R.id.product_4_revenue);
        ProductStatus4 = findViewById(R.id.product_4_status);
        ProductName5 = findViewById(R.id.product_5_name);
        ProductPrice5 = findViewById(R.id.product_5_revenue);
        ProductStatus5 = findViewById(R.id.product_5_status);
        ProductName6 = findViewById(R.id.product_6_name);
        ProductPrice6 = findViewById(R.id.product_6_revenue);
        ProductStatus6 = findViewById(R.id.product_6_status);
        ProductName7 = findViewById(R.id.product_7_name);
        ProductPrice7 = findViewById(R.id.product_7_revenue);
        ProductStatus7 = findViewById(R.id.product_7_status);
        ProductName8 = findViewById(R.id.product_8_name);
        ProductPrice8 = findViewById(R.id.product_8_revenue);
        ProductStatus8 = findViewById(R.id.product_8_status);
        ProductName9 = findViewById(R.id.product_9_name);
        ProductPrice9 = findViewById(R.id.product_9_revenue);
        ProductStatus9 = findViewById(R.id.product_9_status);
        ProductName10 = findViewById(R.id.product_10_name);
        ProductPrice10 = findViewById(R.id.product_10_revenue);
        ProductStatus10 = findViewById(R.id.product_10_status);

        RProductName1 = findViewById(R.id.rproduct_1_name);
        RProductPrice1 = findViewById(R.id.rproduct_1_revenue);
        RProductStatus1 = findViewById(R.id.rproduct_1_status);
        RProductName2 = findViewById(R.id.rproduct_2_name);
        RProductPrice2 = findViewById(R.id.rproduct_2_revenue);
        RProductStatus2 = findViewById(R.id.rproduct_2_status);
        RProductName3 = findViewById(R.id.rproduct_3_name);
        RProductPrice3 = findViewById(R.id.rproduct_3_revenue);
        RProductStatus3 = findViewById(R.id.rproduct_3_status);
        RProductName4 = findViewById(R.id.rproduct_4_name);
        RProductPrice4 = findViewById(R.id.rproduct_4_revenue);
        RProductStatus4 = findViewById(R.id.rproduct_4_status);
        RProductName5 = findViewById(R.id.rproduct_5_name);
        RProductPrice5 = findViewById(R.id.rproduct_5_revenue);
        RProductStatus5 = findViewById(R.id.rproduct_5_status);
        RProductName6 = findViewById(R.id.rproduct_6_name);
        RProductPrice6 = findViewById(R.id.rproduct_6_revenue);
        RProductStatus6 = findViewById(R.id.rproduct_6_status);
        RProductName7 = findViewById(R.id.rproduct_7_name);
        RProductPrice7 = findViewById(R.id.rproduct_7_revenue);
        RProductStatus7 = findViewById(R.id.rproduct_7_status);
        RProductName8 = findViewById(R.id.rproduct_8_name);
        RProductPrice8 = findViewById(R.id.rproduct_8_revenue);
        RProductStatus8 = findViewById(R.id.rproduct_8_status);
        RProductName9 = findViewById(R.id.rproduct_9_name);
        RProductPrice9 = findViewById(R.id.rproduct_9_revenue);
        RProductStatus9 = findViewById(R.id.rproduct_9_status);
        RProductName10 = findViewById(R.id.rproduct_10_name);
        RProductPrice10 = findViewById(R.id.rproduct_10_revenue);
        RProductStatus10 = findViewById(R.id.rproduct_10_status);

        productIcon1 = findViewById(R.id.product_1_status_icon);
        productIcon2 = findViewById(R.id.product_2_status_icon);
        productIcon3 = findViewById(R.id.product_3_status_icon);
        productIcon4 = findViewById(R.id.product_4_status_icon);
        productIcon5 = findViewById(R.id.product_5_status_icon);
        productIcon6 = findViewById(R.id.product_6_status_icon);
        productIcon7 = findViewById(R.id.product_7_status_icon);
        productIcon8 = findViewById(R.id.product_8_status_icon);
        productIcon9 = findViewById(R.id.product_9_status_icon);
        productIcon10 = findViewById(R.id.product_10_status_icon);

        RproductIcon1 = findViewById(R.id.rproduct_1_status_icon);
        RproductIcon2 = findViewById(R.id.rproduct_2_status_icon);
        RproductIcon3 = findViewById(R.id.rproduct_3_status_icon);
        RproductIcon4 = findViewById(R.id.rproduct_4_status_icon);
        RproductIcon5 = findViewById(R.id.rproduct_5_status_icon);
        RproductIcon6 = findViewById(R.id.rproduct_6_status_icon);
        RproductIcon7 = findViewById(R.id.rproduct_7_status_icon);
        RproductIcon8 = findViewById(R.id.rproduct_8_status_icon);
        RproductIcon9 = findViewById(R.id.rproduct_9_status_icon);
        RproductIcon10 = findViewById(R.id.rproduct_10_status_icon);

        mFirebaseUser = mAuth.getCurrentUser();


        if(mFirebaseUser != null) {
            currentUserID = mFirebaseUser.getUid(); //Do what you need to do with the id
        }

        scrollViewListings.setVisibility(View.INVISIBLE);

        noProductsTxt.setVisibility(View.VISIBLE);

        mFirestore.collection("transactions").whereEqualTo("RenterUID", currentUserID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    loopMaster = 1;

                    for (QueryDocumentSnapshot transactionDocument : task.getResult()) {
                        rentalTransactionIDs.add(transactionDocument.getId());

                        rentalProductIDs.add(String.valueOf(transactionDocument.get("ProductID")));

                        docRef = mFirestore.collection("products").document(String.valueOf(transactionDocument.get("ProductID")));
                        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                                DocumentSnapshot productDocument = task.getResult();
                                rentalProductNames.add(productDocument.getString("Product Name"));
                                rentalProductPrices.add(String.valueOf(productDocument.getDouble("Rental Fee")));
                                rentalStatuses.add(productDocument.getString("Product Status"));
                                //showMessage(String.valueOf(rentalStatuses.size()));
                                if(rentalProductNames.size() == 1) {
                                    noProductsTxt.setVisibility(View.INVISIBLE);
                                    setRentalView(ra, RproductIcon1, RproductImage1, rentalProductIDs.get(0), RProductName1, RProductPrice1, RProductStatus1, rentalProductNames.get(0), rentalProductPrices.get(0), rentalStatuses.get(0));
                                }
                                else if(rentalProductNames.size() == 2) {
                                    setRentalView(rb, RproductIcon2, RproductImage2, rentalProductIDs.get(1), RProductName2, RProductPrice2, RProductStatus2, rentalProductNames.get(1), rentalProductPrices.get(1), rentalStatuses.get(1));
                                }
                                else if(rentalProductNames.size() == 3) {
                                    setRentalView(rc, RproductIcon3, RproductImage3, rentalProductIDs.get(2), RProductName3, RProductPrice3, RProductStatus3, rentalProductNames.get(2), rentalProductPrices.get(2), rentalStatuses.get(2));
                                }
                                else if(rentalProductNames.size() == 4) {
                                    setRentalView(rd, RproductIcon4, RproductImage4, rentalProductIDs.get(3), RProductName4, RProductPrice4, RProductStatus4, rentalProductNames.get(3), rentalProductPrices.get(3), rentalStatuses.get(3));
                                }
                                else if(rentalProductNames.size() == 5) {
                                    setRentalView(re, RproductIcon5, RproductImage5, rentalProductIDs.get(4), RProductName5, RProductPrice5, RProductStatus5, rentalProductNames.get(4), rentalProductPrices.get(4), rentalStatuses.get(4));
                                }
                                else if(rentalProductNames.size() == 6) {
                                    setRentalView(rf, RproductIcon6, RproductImage6, rentalProductIDs.get(5), RProductName6, RProductPrice6, RProductStatus6, rentalProductNames.get(5), rentalProductPrices.get(5), rentalStatuses.get(5));
                                }
                                else if(rentalProductNames.size() == 7) {
                                    setRentalView(rg, RproductIcon7, RproductImage7, rentalProductIDs.get(6), RProductName7, RProductPrice7, RProductStatus7, rentalProductNames.get(6), rentalProductPrices.get(6), rentalStatuses.get(6));
                                }
                                else if(rentalProductNames.size() == 8) {
                                    setRentalView(rh, RproductIcon8, RproductImage8, rentalProductIDs.get(7), RProductName8, RProductPrice8, RProductStatus8, rentalProductNames.get(7), rentalProductPrices.get(7), rentalStatuses.get(7));
                                }
                                else if(rentalProductNames.size() == 9) {
                                    setRentalView(ri, RproductIcon9, RproductImage9, rentalProductIDs.get(8), RProductName9, RProductPrice9, RProductStatus9, rentalProductNames.get(8), rentalProductPrices.get(8), rentalStatuses.get(8));
                                }
                                else if(rentalProductNames.size() == 10) {
                                    setRentalView(rj, RproductIcon10, RproductImage10, rentalProductIDs.get(9), RProductName10, RProductPrice10, RProductStatus10, rentalProductNames.get(9), rentalProductPrices.get(9), rentalStatuses.get(9));
                                }

                            }
                        });
                        loopMaster++;
                    }

                }
                else{

                }
            }
        });

        mFirestore.collection("products").whereEqualTo("User ID", currentUserID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot productDocument : task.getResult()) {

                        listingProductNames.add(productDocument.getString("Product Name"));
                        listingProductPrices.add(String.valueOf(productDocument.getDouble("Rental Fee")));
                        listingStatuses.add(productDocument.getString("Product Status"));
                        listingProductIDs.add(String.valueOf(productDocument.get("Product Num")));

                        if(listingProductNames.size() == 1) {
                            setListingView(a, productIcon1, productImage1, listingProductIDs.get(0), ProductName1, ProductPrice1, ProductStatus1, listingProductNames.get(0), listingProductPrices.get(0), listingStatuses.get(0));
                        }
                        else if(listingProductNames.size() == 2) {
                            setListingView(b, productIcon2, productImage2, listingProductIDs.get(1), ProductName2, ProductPrice2, ProductStatus2, listingProductNames.get(1), listingProductPrices.get(1), listingStatuses.get(1));
                        }
                        else if(listingProductNames.size() == 3) {
                            setListingView(c, productIcon3, productImage3, listingProductIDs.get(2), ProductName3, ProductPrice3, ProductStatus3, listingProductNames.get(2), listingProductPrices.get(2), listingStatuses.get(2));
                        }
                        else if(listingProductNames.size() == 4) {
                            setListingView(d, productIcon4, productImage4, listingProductIDs.get(3), ProductName4, ProductPrice4, ProductStatus4, listingProductNames.get(3), listingProductPrices.get(3), listingStatuses.get(3));
                        }
                        else if(listingProductNames.size() == 5) {
                            setListingView(e, productIcon5, productImage5, listingProductIDs.get(4), ProductName5, ProductPrice5, ProductStatus5, listingProductNames.get(4), listingProductPrices.get(4), listingStatuses.get(4));
                        }
                        else if(listingProductNames.size() == 6) {
                            setListingView(f, productIcon6, productImage6, listingProductIDs.get(5), ProductName6, ProductPrice6, ProductStatus6, listingProductNames.get(5), listingProductPrices.get(5), listingStatuses.get(5));
                        }
                        else if(listingProductNames.size() == 7) {
                            setListingView(g, productIcon7, productImage7, listingProductIDs.get(6), ProductName7, ProductPrice7, ProductStatus7, listingProductNames.get(6), listingProductPrices.get(6), listingStatuses.get(6));
                        }
                        else if(listingProductNames.size() == 8) {
                            setListingView(h, productIcon8, productImage8, listingProductIDs.get(7), ProductName8, ProductPrice8, ProductStatus8, listingProductNames.get(7), listingProductPrices.get(7), listingStatuses.get(7));
                        }
                        else if(listingProductNames.size() == 9) {
                            setListingView(i1, productIcon9, productImage9, listingProductIDs.get(8), ProductName9, ProductPrice9, ProductStatus9, listingProductNames.get(8), listingProductPrices.get(8), listingStatuses.get(8));
                        }
                        else if(listingProductNames.size() == 10) {
                            setListingView(j, productIcon10, productImage10, listingProductIDs.get(9), ProductName10, ProductPrice10, ProductStatus10, listingProductNames.get(9), listingProductPrices.get(9), listingStatuses.get(9));
                        }


                    }

                }

            }
        });





        addProduct.setVisibility(View.INVISIBLE);
        //for(int i = 0; i < rentalProductNames.size(); i++){

            //
        //}

        myRentalsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loopMaster = 1;
                myRentalsBtn.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.rental_product_selection_button_pressed, null));
                myRentalsBtn.setTypeface(ResourcesCompat.getFont(getApplicationContext(), R.font.raleway_bold));

                myListingsBtn.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.rental_product_selection_button_not_pressed, null));
                myListingsBtn.setTypeface(ResourcesCompat.getFont(getApplicationContext(), R.font.raleway_medium));
                scrollViewListings.setVisibility(View.INVISIBLE);
                scrollViewRentals.setVisibility(View.VISIBLE);
                addProduct.setVisibility(View.GONE);
                noProductsTxt.setVisibility(View.VISIBLE);

                if(rentalProductNames.size() == 0){
                    noProductsTxt.setVisibility(View.VISIBLE);
                }
                else{
                    noProductsTxt.setVisibility(View.INVISIBLE);
                }




                // Add the code for displaying my rental information

            }
        });




        myListingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loopMaster = 0;
                myListingsBtn.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.rental_product_selection_button_pressed, null));
                myListingsBtn.setTypeface(ResourcesCompat.getFont(getApplicationContext(), R.font.raleway_bold));

                myRentalsBtn.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.rental_product_selection_button_not_pressed, null));
                myRentalsBtn.setTypeface(ResourcesCompat.getFont(getApplicationContext(), R.font.raleway_medium));


                scrollViewListings.setVisibility(View.VISIBLE);
                scrollViewRentals.setVisibility(View.INVISIBLE);

                addProduct.setVisibility(View.VISIBLE);
                noProductsTxt.setVisibility(View.INVISIBLE);



            }
        });


        docRef = mFirestore.collection("users").document(currentUserID);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                DocumentSnapshot document = task.getResult();
                if (document.exists()) {

                    PendingOVerification = document.getBoolean("PendingOVerified");
                    OVerified = document.getBoolean("OVerified");

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











    }
    private void showMessage(String message){

        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    private void setRentalView(ConstraintLayout constraintLayout, ImageView productIconView, ImageView productImageView, String productID, TextView nameView, TextView priceView, TextView statusView, String name, String price, String status){
        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Home.userProductSelection = Integer.parseInt(productID);
                startActivity(new Intent(ProductOverLord.this, RentalDetails.class));
                overridePendingTransition(0, 0);
            }
        });
        constraintLayout.setVisibility(View.VISIBLE);

        nameView.setText(name);
        priceView.setText("$" + price + "/day");
        statusView.setText(status);

       if(status.equals("Pending Transaction")){
            productIconView.setImageResource(R.drawable.ic_baseline_access_time_24);
        }
        else if(status.equals("Click to Complete Transaction")){
            productIconView.setImageResource(R.drawable.ic_baseline_qr_code_scanner_24);
        }

        // ADD code for cancelled transaction

        storageRef.child("images/" + productID).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
                Glide.with(ProductOverLord.this).load(uri).into(productImageView);
                productImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
            }
        });
    }

    private void setListingView(ConstraintLayout constraintLayout, ImageView productIconView, ImageView productImageView, String productID, TextView nameView, TextView priceView, TextView statusView, String name, String price, String status){
        productIDForQRCode = productID;
        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Home.userProductSelection = Integer.parseInt(productID);
                startActivity(new Intent(ProductOverLord.this, GenerateQRCode.class));
                overridePendingTransition(0, 0);
            }
        });
        constraintLayout.setVisibility(View.VISIBLE);

        nameView.setText(name);
        priceView.setText("$" + price + "/day");
        statusView.setText(status);



        if(status.equals("Pending Transaction")){
            productIconView.setImageResource(R.drawable.ic_baseline_access_time_24);
        }
        else if(status.equals("Click to Complete Transaction")){
            productIconView.setImageResource(R.drawable.ic_baseline_qr_code_24);
        }
        else if(status.equals("Live On Market")){
            productIconView.setImageResource(R.drawable.icons_shopping_cart);
        }

        // ADD code for cancelled transaction

        storageRef.child("images/" + productID).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
                Glide.with(ProductOverLord.this).load(uri).into(productImageView);
                productImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
            }
        });
    }

}
