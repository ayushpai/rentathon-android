package com.neuralgorithmic.rentathon.Rent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.model.Place;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.neuralgorithmic.rentathon.Product.GenerateQRCode;
import com.neuralgorithmic.rentathon.ResizeAnimation;
import com.neuralgorithmic.rentathon.Signin.SignUpPage1;
import com.r0adkll.slidr.Slidr;
import com.neuralgorithmic.rentathon.Home.Home;

import com.neuralgorithmic.rentathon.Misc.WelcomeProdcutMain;
import com.neuralgorithmic.rentathon.Product.ProductMain;
import com.neuralgorithmic.rentathon.Product.ProductOverLord;
import com.neuralgorithmic.rentathon.Profile.ProfileMain;
import com.neuralgorithmic.rentathon.R;
import com.neuralgorithmic.rentathon.Profile.UserHomeMain;
import com.rtchagas.pingplacepicker.PingPlacePicker;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import timber.log.Timber;


public class RentProductMain extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.renter_product_pg1);


        ProfileMain.fromProfile = false;
        ProductMain.fromPreview = true;

        Slidr.attach(this);
        overridePendingTransition(0, 0);
        mainScrollView = findViewById(R.id.scrollView2);

        UserHomeMain.fromUserHomeMain = false;

        barConstraint = findViewById(R.id.barConstraint);

        menu = findViewById(R.id.menu_btn);
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
        morePhotos = findViewById(R.id.more_photos_btn);
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


        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Renter = Owner, Rentee == Renter
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();


                ownerFile = mFirestore.collection("users").document(renteeUID);
                renterFile = mFirestore.collection("users").document(renteeUID);
                productFile = mFirestore.collection("products").document(String.valueOf(Home.userProductSelection));


                ownerFile.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot document = task.getResult();
                        if(document.exists()){
                            transactions.put("OwnerName", document.getString("Name"));
                            transactions.put("OwnerCity", document.getString("City"));
                            transactions.put("OwnerRating", document.getDouble("RAverage"));
                            transactions.put("OwnerName", document.getString("Name"));
                        }
                    }
                });

                renterFile.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            transactions.put("RenterName", document.getString("Name"));
                            transactions.put("RenterCity", document.getString("City"));

                        }
                    }
                });

                productFile.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            df = new DecimalFormat("0.00");
                            transactions.put("ProductName", document.getString("Product Name"));
                            transactions.put("ProductID", Home.userProductSelection);
                            transactions.put("ProductValue", document.getDouble("Product Value"));
                            SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyy");
                            transactions.put("TransactionAmnt", totalPrice);
                            transactions.put("RentathonEarnings", df.format(totalPrice * 0.12));
                            transactions.put("ReturnDate", returnDateString);
                            transactions.put("PickupDate", sdf.format(calendarView.getDate()));
                            transactions.put("RenterUID", renteeUID);
                            transactions.put("OwnerUID", renterUID);
                            transactions.put("PaymentComplete", false);
                            transactions.put("QRCodeVerifiedPickup", false);
                            transactions.put("QRCodeVerifiedDropoff", false);
                            transactions.put("OwnerAccepted", false);
                            transactions.put("RentDuration", numDays);
                            transactions.put("ProductStatus", "Pending Transaction");

                            WriteBatch batch = mFirestore.batch();
                            docRef = mFirestore.collection("products").document(String.valueOf(Home.userProductSelection));
                            batch.update(docRef, "Product Status", "Pending Transaction");
                            batch.commit();

                            tInfo.document(renterCity.getText().toString() + " - "+String.valueOf(Home.userProductSelection) + " - $" + String.valueOf(totalPrice)).set(transactions);
                        }
                    }
                });


            }
        });

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyy");

                selectedDate = String.valueOf(month + 1) + "/" + String.valueOf(dayOfMonth) + "/" + year;
                Calendar c = Calendar.getInstance();

                try {
                    c.setTime(sdf.parse(selectedDate));
                }
                catch (Exception e) {
                    //The handling for the code
                }
                c.add(Calendar.DATE, numDays);  // number of days to add
                returnDate.setText(sdf.format(c.getTime()));  // dt is now the new date
                returnDateString = sdf.format(c.getTime());

            }
        });


        df = new DecimalFormat("0.00");
        proximitySeekbar.setMax(30);
        proximitySeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressValue;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                numDays = seekBar.getProgress();
                progressValue = progress;
                pSBarText.setText(progress + " Days");
                totalPrice = (progress * productPrice);

                maltRequiredString = df.format(totalPrice);
                newPrice.setText("Total Price: $" + maltRequiredString);

                SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyy");

                Calendar c = Calendar.getInstance();

                try {
                    c.setTime(sdf.parse(selectedDate));
                }
                catch (Exception e) {
                    //The handling for the code
                }
                c.add(Calendar.DATE, numDays);  // number of days to add
                returnDate.setText(sdf.format(c.getTime()));  // dt is now the new date
                returnDateString = sdf.format(c.getTime());

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                newPrice.setText("Total Price: $" + maltRequiredString);
                pSBarText.setText(progressValue + " Days");

            }
        });


        morePhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(RentProductMain.this, MorePhotos.class));

            }
        });

        storageRef.child("images/" + String.valueOf(Home.userProductSelection)).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
                Glide.with(RentProductMain.this).load(uri).into(productPrimaryImage);
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

        downArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResizeAnimation animationObject = new ResizeAnimation(continueConstraint, Home.dpToPx(70), Home.dpToPx(435), false);
                    animationObject.setDuration(200);
                    continueConstraint.startAnimation(animationObject);

                    animationObject.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {


                            calendarTitle.animate().setDuration(200).alpha(1).setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    calendarTitle.setVisibility(View.INVISIBLE);
                                }
                            });
                            calendarView.animate().setDuration(200).alpha(1).setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    calendarView.setVisibility(View.INVISIBLE);
                                }
                            });
                            proximitySeekbar.animate().setDuration(200).alpha(1).setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    proximitySeekbar.setVisibility(View.INVISIBLE);
                                }
                            });
                            pSBarText.animate().setDuration(200).alpha(1).setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    pSBarText.setVisibility(View.INVISIBLE);
                                }
                            });
                            downArrow.animate().setDuration(200).alpha(1).setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    downArrow.setVisibility(View.INVISIBLE);
                                }
                            });



                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            barConstraint.setVisibility(View.VISIBLE);
                            continueConstraint.setVisibility(View.GONE);
                            darkenBackground.setVisibility(View.INVISIBLE);



                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });



            }
        });


        imageSlideShow.setFlipInterval(3500);
        imageSlideShow.setAutoStart(true);

        imageSlideShow.setInAnimation(RentProductMain.this, android.R.anim.slide_in_left);
        imageSlideShow.setOutAnimation(RentProductMain.this, android.R.anim.slide_out_right);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                if(document.exists()){

//                    productPrimaryImage.setImageURI(Uri.parse(document.get("Primary Image").toString()))
                    report.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            listItems = new String[]{"Inappropriate Product", "Spam Listing", "Promotes Bullying", "I just don't like it.", "Other"};
                            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(RentProductMain.this);
                            builder.setTitle("Report Product:");
                            builder.setCancelable(true);
                            builder.setIcon(R.drawable.report_icon);
                            builder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    reportSeletion = listItems[which];

                                }
                            });

                            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();

                                }
                            });

                            builder.setPositiveButton("Report", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    if(reportSeletion != null) {

                                        Map<String, Object> reportData = new HashMap<>();
                                        reportData.put("Product Name", document.get("Product Name").toString());
                                        reportData.put("Product Value", document.get("Product Value").toString());
                                        reportData.put("Rental Fee", document.get("Rental Fee").toString());
                                        reportData.put("Renter UID", document.get("User ID").toString());
                                        reportData.put("Report Reason", reportSeletion);
                                        reportData.put("Admin Confirmed", false);
                                        reportData.put("Punishment Active", false);
                                        reportData.put("Punishment Amount", 0);
                                        reportData.put("Intelligent Rating", document.get("Intelligent Rating").toString());

                                        mFirestore.collection("reports").document(String.valueOf(Home.userProductSelection)).set(reportData);

                                        AlertDialog.Builder builder2 = new AlertDialog.Builder(RentProductMain.this);
                                        builder2.setCancelable(true);
                                        builder2.setTitle("Product Reported");
                                        builder2.setIcon(R.drawable.thanks);
                                        builder2.setMessage("Thank you for helping us make the Rentathon community a safe, fair, and pleasing environment!");

                                        builder2.setNeutralButton("Continue RENTING", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                startActivity(new Intent(RentProductMain.this, Home.class));
                                                overridePendingTransition(0, 0);
                                                Home.scrollY = 0;

                                            }
                                        });

                                        builder2.show();

                                    }

                                }
                            });

                            android.app.AlertDialog dialog = builder.create();
                            dialog.show();

                        }
                    });


                    distanceTxt.setText("3.85 Miles Away");


                    productName.setText(document.get("Product Name").toString());
                    selectedProductName = document.get("Product Name").toString();
                    productPublicName = document.get("Product Name").toString();
                    //condition.setText(document.get("Product Condition").toString());
                    double price = Integer.valueOf(document.get("Rental Fee").toString());
                    descripton.setText("The owner states that this product is in " + document.get("Product Condition").toString() + " condition." + "\n\n" + document.get("Product Description").toString());
                    price2.setText("$" + document.get("Rental Fee").toString());
                    price3.setText("From " + "$" + document.get("Rental Fee").toString() + "/Day");
                    daily.setText("$" + document.get("Rental Fee").toString());
                    weekly.setText("$" + (price * 7));
                    productPrice = price;
                    monthly.setText("$" + (price * 14));
                    selectedPrice = document.get("Rental Fee").toString();
                    totalViews = Integer.parseInt(document.get("Views").toString());
                    uniqueVisitors = Integer.parseInt(document.get("Unique Visitors").toString());

                    renterUID = document.get("User ID").toString();


                    storageRef.child("images/" + String.valueOf(Home.userProductSelection)).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            // Got the download URL for 'users/me/profile.png'
                            Glide.with(RentProductMain.this).load(uri).into(productPrimaryImage);
                            imageUri = uri;

                            share.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent sendIntent = new Intent();
                                    sendIntent.setAction(Intent.ACTION_SEND);
                                    sendIntent.putExtra(Intent.EXTRA_TEXT, "Rent this " + selectedProductName + " for " + "$" + selectedPrice + "/day. Download Rentathon Today: https://play.google.com/store/apps/details?id=com.rentathon.rentathon3_6_20&hl=en_GB");
                                    sendIntent.setData(imageUri);
                                    sendIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                    sendIntent.setType("text/plain");
                                    sendIntent.setType("image/*");
                                    Intent shareIntent = Intent.createChooser(sendIntent, null);
                                    startActivity(shareIntent);
                                }
                            });
                        }
        });

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
            batch.update(docRef, "Views", totalViews);
            batch.commit();
        }

        UID = document.get("User ID").toString();
        //renterName.setText(document.get("User Name").toString());
        //renterCity.setText(document.get("User City").toString());
        docRef2 = mFirestore.collection("users").document(renteeUID);


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
                    moreProducts.setHint("More Products From " + selectedName );
                    ratingAmntTxt.setText(document.get("RCount").toString() + " Reviews");

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
                        nxtBtn.setText("Action");
                        price3.setText("Edit Product");
                        distanceTxt.setText("View Insights");


                        nxtBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(RentProductMain.this, GenerateQRCode.class));
                                overridePendingTransition(0,0);

                            }
                        });

                        price3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(RentProductMain.this, EditProduct.class));
                                overridePendingTransition(0,0);

                            }
                        });
                        distanceTxt.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(RentProductMain.this, ProductInsights.class));
                                overridePendingTransition(0,0);
                            }
                        });

                    }
                    else{
                        Timber.e("Got to Else");
                        nxtBtn.setText("Continue");
                        distanceTxt.setVisibility(View.VISIBLE);

                                docRef2 = mFirestore.collection("users").document(mAuth.getUid());
                                docRef2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                        DocumentSnapshot document = task.getResult();

                                            verified = Boolean.parseBoolean(document.get("Payment").toString());
                                            nxtBtn.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {

                                                    if (verified) {
                                                        continueConstraint.setVisibility(View.VISIBLE);
                                                        darkenBackground.setVisibility(View.VISIBLE);
                                                        barConstraint.setVisibility(View.GONE); //comment dis
                                                        newButton.setVisibility(View.VISIBLE);
                                                        newPrice.setVisibility(View.VISIBLE);
                                                        returnDate.setVisibility(View.VISIBLE);

                                                        calendarTitle.setVisibility(View.INVISIBLE);
                                                        proximitySeekbar.setVisibility(View.INVISIBLE);
                                                        pSBarText.setVisibility(View.INVISIBLE);
                                                        //downArrow.setVisibility(View.INVISIBLE);





                                                            ResizeAnimation animationObject = new ResizeAnimation(continueConstraint, dpToPx(435),dpToPx(70), true);
                                                            animationObject.setDuration(200);
                                                            continueConstraint.startAnimation(animationObject);




                                                            animationObject.setAnimationListener(new Animation.AnimationListener() {
                                                                @Override
                                                                public void onAnimationStart(Animation animation) {


                                                                    calendarTitle.animate().setDuration(200).alpha(1).setListener(new AnimatorListenerAdapter() {
                                                                        @Override
                                                                        public void onAnimationEnd(Animator animation) {
                                                                            calendarTitle.setVisibility(View.VISIBLE);
                                                                        }
                                                                    });
                                                                    calendarView.animate().setDuration(200).alpha(1).setListener(new AnimatorListenerAdapter() {
                                                                        @Override
                                                                        public void onAnimationEnd(Animator animation) {
                                                                            calendarView.setVisibility(View.VISIBLE);
                                                                        }
                                                                    });
                                                                    proximitySeekbar.animate().setDuration(200).alpha(1).setListener(new AnimatorListenerAdapter() {
                                                                        @Override
                                                                        public void onAnimationEnd(Animator animation) {
                                                                            proximitySeekbar.setVisibility(View.VISIBLE);
                                                                        }
                                                                    });
                                                                    pSBarText.animate().setDuration(200).alpha(1).setListener(new AnimatorListenerAdapter() {
                                                                        @Override
                                                                        public void onAnimationEnd(Animator animation) {
                                                                            pSBarText.setVisibility(View.VISIBLE);
                                                                        }
                                                                    });
                                                                    downArrow.animate().setDuration(200).alpha(1).setListener(new AnimatorListenerAdapter() {
                                                                        @Override
                                                                        public void onAnimationEnd(Animator animation) {
                                                                            downArrow.setVisibility(View.VISIBLE);
                                                                        }
                                                                    });



                                                                }

                                                                @Override
                                                                public void onAnimationEnd(Animation animation) {
                                                                    /*barConstraint.setVisibility(View.INVISIBLE);

                                                                    Animation animUpDown;

                                                                    // load the animation
                                                                    animUpDown = AnimationUtils.loadAnimation(getApplicationContext(),
                                                                            R.anim.up_down);
                                                                    // start the animation
                                                                    continueConstraint.startAnimation(animUpDown);


                                                                     */


                                                                }

                                                                @Override
                                                                public void onAnimationRepeat(Animation animation) {

                                                                }
                                                            });



                                                        } else {
                                                            showMessage("Please Verify Your Payment");
                                                        }


                                                    }
                                                });

                                }
                            });

                    }
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
        });


        reportRef = mFirestore.collection("reports").document(String.valueOf(Home.userProductSelection));

                reportRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {


                DocumentSnapshot document2 = task.getResult();
                if(document2.exists()) {
                    punishmentActive = Boolean.parseBoolean(document2.get("Punishment Active").toString());
                    adminConfirmed = Boolean.parseBoolean(document2.get("Admin Confirmed").toString());
                    punishmentAmount = Integer.parseInt(document2.get("Punishment Amount").toString());


                }



                docRef2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        DocumentSnapshot document = task.getResult();


                        //userRating = Double.parseDouble(document.get("Rating").toString());
                        //numOfReviews = Integer.parseInt(document.get("Num Reviews").toString());


                        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {


                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    //intelligentRatingMAIN = (((double) Integer.parseInt(document.get("Unique Visitors").toString()) / Integer.parseInt(document.get("Views").toString())) * 250) + (Integer.parseInt(document.get("Unique Visitors").toString()) * 10) + (userRating * 110) + ((35 - Integer.parseInt(document.get("Rental Fee").toString())) * 6) - ((5 - 10) * 100);
                                    //intelligentRating = (((double)Integer.parseInt(document.get("Views").toString()) / Integer.parseInt(document.get("Unique Visitors").toString())) * 192) + (Integer.parseInt(document.get("Unique Visitors").toString())) + (Math.pow(userRating, 1.2) * 110) + Math.pow(numOfReviews, 1.8) - (Math.pow( Integer.parseInt(document.get("Rental Fee").toString()), 1.5) * 6) - (Math.pow(5, 2.35) * 10);
                                    WriteBatch intelligenceBatch = mFirestore.batch();
                                    WriteBatch reportBatch = mFirestore.batch();
                                    if(punishmentActive && adminConfirmed){
                                        intelligentRating = ((((double)Integer.parseInt(document.get("Views").toString()) / Integer.parseInt(document.get("Unique Visitors").toString())) * 250) + (Integer.parseInt(document.get("Unique Visitors").toString()) * 10) + ((userRating - 3) * numOfReviews * 55) + ((35 - Integer.parseInt(document.get("Rental Fee").toString())) * 6) + ((10 - 5) * 100)) - punishmentAmount;
                                        punishmentActive = false;
                                        reportBatch.update(reportRef, "Punishment Active", punishmentActive);
                                        reportBatch.update(reportRef, "Intelligent Rating", intelligentRating);
                                        reportBatch.commit();


                                    }
                                    else{
                                        intelligentRating = (((double)Integer.parseInt(document.get("Views").toString()) / Integer.parseInt(document.get("Unique Visitors").toString())) * 250) + (Integer.parseInt(document.get("Unique Visitors").toString()) * 10) + ((userRating - 3) * numOfReviews * 55) + ((35 - Integer.parseInt(document.get("Rental Fee").toString())) * 6) + ((10 - 5) * 100)-punishmentAmount;

                                    }

                                    //showMessage("\t$" + Integer.parseInt(document.get("Rental Fee").toString()) + "/day\t" + String.valueOf(userRating) + "/5.0 Stars\t" + "5" + " mi away\t" + "Unique Views: " + Integer.parseInt(document.get("Unique Visitors").toString()) + "\tViews: " + Integer.parseInt(document.get("Views").toString()) + "\tIntelligence Rating: " + String.format("%6.2f", intelligentRating));

                                    //showMessageFast(String.format("%6.2f", intelligentRating));

                                    intelligenceBatch.update(docRef, "Intelligent Rating", intelligentRating);
                                    intelligenceBatch.commit();
                                }
                            }
                        });

                    }

                });





            }
        });








        rentersImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RentProductMain.this, UserHomeMain.class));
                //overridePendingTransition(0,0);

            }
        });



        storageRef.child("users/" + UID).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
                Glide.with(RentProductMain.this).load(uri).into(rentersImage);



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



















        moreProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(RentProductMain.this, UserHomeMain.class));
                overridePendingTransition(0,0);

            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ProductOverLord.fromOverLord ){
                    startActivity(new Intent(RentProductMain.this, ProductOverLord.class));
                    overridePendingTransition(0, 0);
                }
                else{
                    startActivity(new Intent(RentProductMain.this, Home.class));
                    overridePendingTransition(0, 0);
                }

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


                ImageView imageView = new ImageView(RentProductMain.this);
                Glide.with(RentProductMain.this).load(uri).into(imageView);



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

                ImageView imageView = new ImageView(RentProductMain.this);
                Glide.with(RentProductMain.this).load(uri).into(imageView);



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

    private void showMessageFast(String message){

        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == PPR) && (resultCode == RESULT_OK)) {
            Place place = PingPlacePicker.getPlace(data);
            if (place != null) {

                String latLang = place.getLatLng().toString();

                String lat = latLang.substring(latLang.indexOf("(") + 1, latLang.indexOf(","));
            //lat/lang: (32324, 2332)
                String lang = latLang.substring(latLang.indexOf(",") + 1, latLang.indexOf(")"));


                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

                List<Address> addresses  = null;
                try {
                    addresses = geocoder.getFromLocation(Double.parseDouble(lat), Double.parseDouble(lang), 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                selectedAddress = addresses.get(0).getAddressLine(0);




//                startActivity(new Intent(RentProductMain.this, RentProductTime.class));
            }
        }
    }

    public double getUserRating(){
        return userRating;
    }
    public void setUserRating(double uR){
        userRating = uR;
    }


    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    public static double distanceCalc(double lat1, double lng1, double lat2, double lng2) {
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        float dist = (float) (earthRadius * c);

        return dist;
    }
/*
    public float getDistance(double lat1, double lon1, double lat2, double lon2) {
        String result_in_kms = "";
        String url = "http://maps.google.com/maps/api/directions/xml?origin=" + lat1 + "," + lon1 + "&destination=" + lat2 + "," + lon2 + "&sensor=false&units=metric";
        String tag[] = {"text"};
        HttpResponse response = null;
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpContext localContext = new BasicHttpContext();
            HttpPost httpPost = new HttpPost(url);
            response = httpClient.execute   (httpPost, localContext);
            InputStream is = response.getEntity().getContent();
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(is);
            if (doc != null) {
                NodeList nl;
                ArrayList args = new ArrayList();
                for (String s : tag) {
                    nl = doc.getElementsByTagName(s);
                    if (nl.getLength() > 0) {
                        Node node = nl.item(nl.getLength() - 1);
                        args.add(node.getTextContent());
                    } else {
                        args.add(" - ");
                    }
                }
                result_in_kms =String.valueOf( args.get(0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Float f=Float.valueOf(result_in_kms);
        return f*1000;
    }*/

    public float calculateDistance(double userLat, double userLng, double venueLat, double venueLng) {

        double latDistance = Math.toRadians(userLat - venueLat);
        double lngDistance = Math.toRadians(userLng - venueLng);

        double a = (Math.sin(latDistance / 2) * Math.sin(latDistance / 2)) +
                (Math.cos(Math.toRadians(userLat))) *
                        (Math.cos(Math.toRadians(venueLat))) *
                        (Math.sin(lngDistance / 2)) *
                        (Math.sin(lngDistance / 2));

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return (float) (Math.round(6371 * c));

    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }


    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getApplicationContext().getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }






}
