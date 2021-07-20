package com.neuralgorithmic.rentathon.Rent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
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
import com.neuralgorithmic.rentathon.Home.Home;
import com.neuralgorithmic.rentathon.Profile.UserHomeMain;
import com.neuralgorithmic.rentathon.R;
import com.r0adkll.slidr.Slidr;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import timber.log.Timber;

public class RentalDetails extends AppCompatActivity {

    TextView productName, productValue, rentalCost, condition, descripton, renterName, renterCity, price2, ratingAmntTxt, price3, daily, weekly, monthly, distanceTxt;
    Button cartNavBtn, chatNavBtn, profileNavBtn, deleteBtn, morePhotos, editBtn, insights;
    FirebaseFirestore mFirestore;
    ImageView productPrimaryImage, rentersImage, backBtn, ratingPic;
    private DocumentReference docRef, docRef2, reportRef;
    FirebaseStorage storage;
    StorageReference storageRef;
    ViewFlipper imageSlideShow;
    TextView moreProducts;
    TextView nxtBtn;
    int random;
    ImageView image1, image2, image3;
    Uri uri1, uri2, uri;
    public static String selectedName;
    public static String selectedProductName;
    public static String selectedPrice;
    FirebaseAuth mAuth;
    public String renterUID = "";
    public static String UID;
    FirebaseUser mFirebaseUser;
    public static String selectedAddress;
    String renterNameString;
    String currentName;
    public Boolean menuClicked = true;
    CollectionReference collectionRef;
    int Size;
    public double userRating;
    public int totalViews;
    public int uniqueVisitors;
    public static boolean fromHome = false;
    ImageView menu;

    public String reportText = "";

    public String renteeUID = "";
    int PPR = 1;
    static public int scrollY = 0;
    TextView searchBackground;
    ScrollView mainScrollView;
    public String productPublicName;
    public String reportSeletion;
    public boolean punishmentActive;
    public boolean adminConfirmed;
    public int punishmentAmount = 0;

    TextView report;
    ImageView share;
    public String currentUserID;
    public double intelligentRating;

    public static double productPrice = 0;
    public int numOfReviews;

    public static Uri imageUri;
    String[] listItems;
    ConstraintLayout verfiedLayout;
    public boolean verifiedUser;

    public double longitudeRenter;
    public double latitudeRenter;

    public double longitudeRentee;
    public double latitudeRentee;
    public double distance;
    public boolean verified;

    ConstraintLayout continueConstraint, barConstraint;
    ImageView darkenBackground, downArrow;
    Button newButton;
    SeekBar proximitySeekbar;
    TextView pSBarText, calendarTitle, returnDate, newPrice;
    CalendarView calendarView;
    ProgressBar progressBar;

    static int numDays = 0;
    static String selectedDate;
    static double totalPrice = 0;
    static String returnDateString;

    Map<String, Object> transactions;
    CollectionReference tInfo;

    DecimalFormat df;
    String maltRequiredString;

    DocumentReference ownerFile, renterFile, productFile;
    Button completeTransactionBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.renter_product_pg1_with_chat);

        Slidr.attach(this);
        overridePendingTransition(0, 0);
        mainScrollView = findViewById(R.id.scrollView2);

        UserHomeMain.fromUserHomeMain = false;

        barConstraint = findViewById(R.id.barConstraint);
        completeTransactionBtn = findViewById(R.id.completeTransactionBtn);

        searchBackground = findViewById(R.id.search_background);

        newButton = findViewById(R.id.rent2);
        proximitySeekbar = findViewById(R.id.proximity_seekbar);
        pSBarText = findViewById(R.id.proximity_seekbar_txt_output);
        calendarTitle = findViewById(R.id.calendartitle);
        calendarView = findViewById(R.id.calendarView);
        returnDate = findViewById(R.id.distance2);
        newPrice = findViewById(R.id.price32);
        progressBar = findViewById(R.id.progress_bar2);
        downArrow = findViewById(R.id.imageView);




        daily = findViewById(R.id.daily);
        weekly = findViewById(R.id.weekly);
        monthly = findViewById(R.id.monthly);
        price3 = findViewById(R.id.price3);
        share = findViewById(R.id.share_btn);
        report = findViewById(R.id.report_btn);
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        mAuth = FirebaseAuth.getInstance();
        verfiedLayout = findViewById(R.id.verified_layout);

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
        descripton = findViewById(R.id.product_description_input);


        nxtBtn = findViewById(R.id.rent);
        renterName = findViewById(R.id.renter_name);
        //mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        docRef = mFirestore.collection("products").document(String.valueOf(Home.userProductSelection));
        imageSlideShow.getLayoutParams().height = 0;
        //imageSlideShow.getBackground().setVisible(false, false);
        imageSlideShow.requestLayout();


        share.setTranslationY(-share.getHeight());
        report.setTranslationY(-share.getHeight());
        distanceTxt = findViewById(R.id.distance);

        continueConstraint = findViewById(R.id.continueConstraint);
        darkenBackground = findViewById(R.id.darkenBackground);
        transactions = new HashMap<>();
        tInfo = mFirestore.collection("transactions");

        mFirebaseUser = mAuth.getCurrentUser();
        if(mFirebaseUser != null) {
            renteeUID = mFirebaseUser.getUid(); //Do what you need to do with the id
        }

        completeTransactionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RentalDetails.this, QRScan.class));
            }
        });


        docRef2 = mFirestore.collection("users").document(renteeUID);


        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Chat Stuff Goes Here
            }
        });

        storageRef.child("images/" + String.valueOf(Home.userProductSelection)).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
                Glide.with(RentalDetails.this).load(uri).into(productPrimaryImage);
                imageUri = uri;
                mainScrollView.getViewTreeObserver()
                        .addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                            @Override
                            public void onScrollChanged() {
                                if (mainScrollView.getScrollY() > productPrimaryImage.getHeight()- searchBackground.getHeight()) {
                                    report.setVisibility(View.INVISIBLE);
                                    share.setVisibility(View.INVISIBLE);
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

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {

                    //Add all rental details

                }
            }
        });

        docRef2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    Timber.e("Inside Docref");
                    renterName.setText(document.get("Name").toString());
                    renterNameString = (document.get("Name").toString());
                    renterCity.setText(document.get("City").toString());
                    selectedName = document.get("Name").toString();
                    moreProducts.setHint("More Products From " + selectedName);
                    ratingAmntTxt.setText(document.get("RCount").toString() + " Reviews");
                    UID = document.getString("UID");

                   /* verifiedUser = Boolean.parseBoolean(document.get("Verified User").toString());
                    if(verifiedUser){
                        verfiedLayout.setVisibility(View.VISIBLE);
                        verfiedLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String url = "http://www.rentathonapp.com/verified-renters";
                                Intent i = new Intent(Intent.ACTION_VIEW);
                                i.setData(Uri.parse(url));
                                startActivity(i);
                            }
                        });
                    }

                    */

                    userRating = Double.parseDouble(document.get("RAverage").toString());
                    setUserRating(Double.parseDouble(document.get("RAverage").toString()));
                    if (userRating == 0) {
                        ratingPic.setImageResource(R.drawable.star5);
                    } else if (userRating <= 0.5) {
                        ratingPic.setImageResource(R.drawable.star05);
                    } else if (userRating <= 1) {
                        ratingPic.setImageResource(R.drawable.star1);
                    } else if (userRating <= 1.5) {
                        ratingPic.setImageResource(R.drawable.star15);
                    } else if (userRating <= 2) {
                        ratingPic.setImageResource(R.drawable.star2);
                    } else if (userRating <= 2.5) {
                        ratingPic.setImageResource(R.drawable.star25);
                    } else if (userRating <= 3) {
                        ratingPic.setImageResource(R.drawable.star3);
                    } else if (userRating <= 3.5) {
                        ratingPic.setImageResource(R.drawable.star35);
                    } else if (userRating <= 4) {
                        ratingPic.setImageResource(R.drawable.star4);
                    } else if (userRating <= 4.5) {
                        ratingPic.setImageResource(R.drawable.star45);
                    } else if (userRating <= 5) {
                        ratingPic.setImageResource(R.drawable.star5);
                    }

                    mFirebaseUser = mAuth.getCurrentUser();
                    if (mFirebaseUser != null) {
                        currentName = mFirebaseUser.getDisplayName();
                    }
                }
            }
        });

        rentersImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RentalDetails.this, UserHomeMain.class));
                //overridePendingTransition(0,0);

            }
        });

        storageRef.child("users/" + UID).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
                Glide.with(RentalDetails.this).load(uri).into(rentersImage);



            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                //showMessage("Please connect to the internet to view the products");
            }
        });






    }

    public void setUserRating(double uR){
        userRating = uR;
    }
}