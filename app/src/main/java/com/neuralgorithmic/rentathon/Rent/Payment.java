package com.neuralgorithmic.rentathon.Rent;

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

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wallet.PaymentsClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.r0adkll.slidr.Slidr;
import com.neuralgorithmic.rentathon.Home.Home;
import com.neuralgorithmic.rentathon.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Payment extends AppCompatActivity  {

    private String paymentIntentClientSecret;
    TextView productName, ownerName,  productCondition, rentPrice, locationTxt, returnTxt, pickUpTxt;
    ImageView productPic, back;
    private DocumentReference docRef, docRef2;
    private FirebaseAuth mAuth;
    FirebaseFirestore mFirestore;
    StorageReference storageRef;
    FirebaseStorage storage;
    Button cartNavBtn, chatNavBtn, profileNavBtn;
    String dt;
    ImageView confirm;

    private PaymentsClient paymentsClient;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.renter_payment);

        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        Slidr.attach(this);
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        confirm = findViewById(R.id.purchase_button);
        productName = findViewById(R.id.product_name);
        ownerName = findViewById(R.id.product_owner_name);
        productPic = findViewById(R.id.product_pic_input);
        productCondition = findViewById(R.id.product_condition_txt);
        rentPrice = findViewById(R.id.product_rent_price_txt);
        back = findViewById(R.id.back_btn);

        locationTxt = findViewById(R.id.location_txt);
        returnTxt = findViewById(R.id.return_txt);
        pickUpTxt = findViewById(R.id.pick_up_txt);
        RentProductMain.fromHome = false;

        dt = RentProductTime.selectedDate;
        SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyy");

        Calendar c = Calendar.getInstance();

        try {
            c.setTime(sdf.parse(dt));
        }
        catch (Exception e) {
            //The handling for the code
        }
        c.add(Calendar.DATE, Integer.parseInt(RentProductTime.numDays));  // number of days to add
        dt = sdf.format(c.getTime());  // dt is now the new date



        pickUpTxt.setText(RentProductTime.selectedDate + " at " + RentProductTime.pickupTime);
        returnTxt.setText(dt + " at " + RentProductTime.dropOffTime);
        locationTxt.setText(RentProductMain.selectedAddress);




        docRef = mFirestore.collection("products").document(String.valueOf(Home.userProductSelection));

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {





                DocumentSnapshot document = task.getResult();
                if(document.exists()){
//                    productPrimaryImage.setImageURI(Uri.parse(document.get("Primary Image").toString()));

                    productName.setText(document.get("Product Name").toString());
                    rentPrice.setText("$" +String.valueOf(Integer.parseInt(document.get("Rental Fee").toString()) * Integer.parseInt(RentProductTime.numDays)));
                    productCondition.setText(document.get("Product Condition").toString());


                        productName.setTextSize(15);

                    docRef2 = mFirestore.collection("users").document(RentProductMain.UID);

                    docRef2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {

                                ownerName.setText(document.get("Name").toString());

                            }
                        }
                    });




                }

                storageRef.child("images/" + String.valueOf(Home.userProductSelection)).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        // Got the download URL for 'users/me/profile.png'
                        Glide.with(Payment.this).load(uri).into(productPic);



                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle any errors
                        //showMessage("Please connect to the internet to view the products");

                    }
                });

            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Payment.this, RentProductTime.class));

            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMessage("Payments are not available in Beta Testing V1");
            }
        });




























    }

    private static JSONObject getBaseRequest() throws JSONException {
        return new JSONObject().put("apiVersion", 2).put("apiVersionMinor", 0);
    }



    private void showMessage(String message){

        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }






}
