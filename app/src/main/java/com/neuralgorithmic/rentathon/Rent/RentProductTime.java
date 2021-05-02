package com.neuralgorithmic.rentathon.Rent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.model.Place;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.neuralgorithmic.rentathon.R;
import com.rtchagas.pingplacepicker.PingPlacePicker;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class RentProductTime extends AppCompatActivity {

    TextView sunday, monday, tuesday, wednesday, thursday, friday, saturday, available;
    private DocumentReference docRef, docRef2;
    FirebaseStorage storage;
    StorageReference storageRef;
    FirebaseAuth mAuth;
    FirebaseFirestore mFirestore;
    ImageView rentersImage, backBtn;
    Button cartNavBtn, chatNavBtn, profileNavBtn, rentBtn;
    CalendarView calendarView;
    public static String selectedDate;
    long eventOccursOn;
    Spinner pickUp;
    Spinner dropOff;
    static String pickupTime;
    static String dropOffTime;
    SeekBar seekBar;
    TextView seekBarText;
    static String numDays;
    ConstraintLayout durationConstraint, selectedDateConstraint;
    TextView startDate, endDate;
    static public final int PPR = 1;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rent_product_time_main);


        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();


        RentProductMain.fromHome = false;






        rentersImage = findViewById(R.id.profile_pic_input);
        pickUp = findViewById(R.id.meet_spinner);
        dropOff = findViewById(R.id.return_spinner);
        seekBar = findViewById(R.id.duration_seekbar);
        seekBarText = findViewById(R.id.duration_seekbar_txt_output);

        sunday = findViewById(R.id.sunday);
        monday = findViewById(R.id.monday);
        tuesday = findViewById(R.id.tuesday);
        wednesday = findViewById(R.id.wednesday);
        thursday = findViewById(R.id.thursday);
        friday = findViewById(R.id.friday);
        saturday = findViewById(R.id.saturday);
        available = findViewById(R.id.availability_title);

        rentBtn = findViewById(R.id.rent_button);
        backBtn = findViewById(R.id.back_btn);
        calendarView = findViewById(R.id.calendarView);
        seekBar.setMax(14);
        //calendarView.setMinDate((new Date().getTime()));
        durationConstraint = findViewById(R.id.duration_constraint);
        selectedDateConstraint = findViewById(R.id.selected_date_constraint);
        startDate = findViewById(R.id.meet_date);
        endDate = findViewById(R.id.return_date);












        AlertDialog.Builder builder2 = new AlertDialog.Builder(RentProductTime.this);

        builder2.setTitle("Selected Location:");
        builder2.setMessage(RentProductMain.selectedAddress);



        builder2.setNegativeButton("Change Location", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                PingPlacePicker.IntentBuilder builder = new PingPlacePicker.IntentBuilder();
                builder.setAndroidApiKey("AIzaSyCHBd7NQPWdOUor_B18mGpbrvBSkHo8gZc").setMapsApiKey("AIzaSyCHBd7NQPWdOUor_B18mGpbrvBSkHo8gZc");


                try {
                    startActivityForResult(builder.build(RentProductTime.this), PPR);
                    overridePendingTransition(0,0);

                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }


            }
        });

        builder2.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

            }
        });

        builder2.show();

        docRef = mFirestore.collection("users").document(RentProductMain.UID);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                DocumentSnapshot document = task.getResult();
                if (document.exists()) {


                    available.setText(document.get("Name").toString() + "'s" + " Availability");
                    sunday.setText(document.get("Sunday").toString());
                    monday.setText(document.get("Monday").toString());
                    tuesday.setText(document.get("Tuesday").toString());
                    wednesday.setText(document.get("Wednesday").toString());
                    thursday.setText(document.get("Thursday").toString());
                    friday.setText(document.get("Friday").toString());
                    saturday.setText(document.get("Saturday").toString());





                }
            }
        });

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            //show the selected date as a toast
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int day) {
                SimpleDateFormat df = new SimpleDateFormat("MMMM dd, yyy");
                selectedDate = String.valueOf(month + 1) + "/" + String.valueOf(day) + "/" + year;
                showMessage(String.valueOf(selectedDate));

                durationConstraint.setVisibility(View.VISIBLE);
                startDate.setText(selectedDate);
                numDays = String.valueOf(seekBar.getProgress());


                SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyy");

                Calendar c = Calendar.getInstance();

                try {
                    c.setTime(sdf.parse(selectedDate));
                }
                catch (Exception e) {
                    //The handling for the code
                }
                c.add(Calendar.DATE, Integer.parseInt(numDays));  // number of days to add
                endDate.setText(sdf.format(c.getTime()));  // dt is now the new date





            }
        });

        rentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                if(seekBar.getProgress() == 0 ){
                    showMessage("Please enter the input the number of days you want to rent for.");

                }
                else if(selectedDate == null ){

                    showMessage("Please click a date on the calendar.");

                }
                else{

                    openPay();
                }


            }
        });


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressValue;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                numDays = String.valueOf(seekBar.getProgress());
                progressValue = progress;
                seekBarText.setText(progress + " Days");
                selectedDateConstraint.setVisibility(View.VISIBLE);

                SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyy");

                Calendar c = Calendar.getInstance();

                try {
                    c.setTime(sdf.parse(selectedDate));
                }
                catch (Exception e) {
                    //The handling for the code
                }
                c.add(Calendar.DATE, Integer.parseInt(numDays));  // number of days to add
                endDate.setText(sdf.format(c.getTime()));  // dt is now the new date



            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                seekBarText.setText(progressValue + " Days");

            }
        });





        storageRef.child("users/" + RentProductMain.UID).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
                Glide.with(RentProductTime.this).load(uri).into(rentersImage);


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
                PingPlacePicker.IntentBuilder builder = new PingPlacePicker.IntentBuilder();
                builder.setAndroidApiKey("AIzaSyCHBd7NQPWdOUor_B18mGpbrvBSkHo8gZc").setMapsApiKey("AIzaSyCHBd7NQPWdOUor_B18mGpbrvBSkHo8gZc");


                try {
                    startActivityForResult(builder.build(RentProductTime.this), PPR);
                    overridePendingTransition(0,0);

                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });

    }
    private void showMessage(String message){

        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    private void openPay(){


        pickupTime = pickUp.getSelectedItem().toString();
        dropOffTime = dropOff.getSelectedItem().toString();
        numDays = String.valueOf(seekBar.getProgress());
        startActivity(new Intent(RentProductTime.this, Payment.class));
    }

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
                RentProductMain.selectedAddress = addresses.get(0).getAddressLine(0);


                Toast.makeText(this,"Chose: " + RentProductMain.selectedAddress, Toast.LENGTH_LONG).show();

                startActivity(new Intent(RentProductTime.this, RentProductTime.class));
            }
        }
    }


}
