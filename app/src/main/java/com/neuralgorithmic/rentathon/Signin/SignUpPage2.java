package com.neuralgorithmic.rentathon.Signin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.model.Place;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.neuralgorithmic.rentathon.Home.Home;
import com.neuralgorithmic.rentathon.R;
import com.rtchagas.pingplacepicker.PingPlacePicker;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;



public class SignUpPage2 extends AppCompatActivity {

    private static SeekBar seekBar;
    private static TextView seekBarText;
    Button startBtn;
     FirebaseAuth mAuth;
    static String userAttributes;
    FirebaseFirestore mFirestore;
    EditText userCity;
    TextView  userAddress;
    public String address, city, state;
    public int miles;
    StorageReference storageRef;
    Spinner userState;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_signup_pg2);

        startBtn = findViewById(R.id.done_button);
        mAuth = FirebaseAuth.getInstance();
        seekBarText = findViewById(R.id.proximity_seekbar_txt_output);
        seekBar = findViewById(R.id.proximity_seekbar);
        seekBar.setMax(20);
        seekBarText.setText(seekBar.getProgress() + " Miles");
        mFirestore = FirebaseFirestore.getInstance();
        userAddress = findViewById(R.id.signup_address_input);
        userCity = findViewById(R.id.signup_city_input);
        userState = findViewById(R.id.signup_country_input);
        storageRef = FirebaseStorage.getInstance().getReference("users");

       /* userAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PingPlacePicker.IntentBuilder builder = new PingPlacePicker.IntentBuilder();
                builder.setAndroidApiKey("AIzaSyCHBd7NQPWdOUor_B18mGpbrvBSkHo8gZc").setMapsApiKey("AIzaSyCHBd7NQPWdOUor_B18mGpbrvBSkHo8gZc");


                try {
                    startActivityForResult(builder.build(SignUpPage2.this), 1);
                    overridePendingTransition(0,0);

                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });

        */

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressValue;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                progressValue = progress;
                seekBarText.setText(progress + " Miles");

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                seekBarText.setText(progressValue + " Miles");

            }
        });

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startBtn.setVisibility(View.INVISIBLE);
                address = userAddress.getText().toString();
                city = userCity.getText().toString();

                miles = seekBar.getProgress();




                if(address.isEmpty()){
                    showMessage(("Please enter your address.."));
                    startBtn.setVisibility(View.VISIBLE);
                }
                else if(city.isEmpty()){
                    showMessage("Please enter your city.");
                    startBtn.setVisibility(View.VISIBLE);
                }


                else {



                    CreateUserAccount(SignUpPage1.email, SignUpPage1.fullName, SignUpPage1.password);


                }






            }
        });








    }

    private void CreateUserAccount(String email, final String name, String password){




        AlertDialog.Builder builder2 = new AlertDialog.Builder(SignUpPage2.this);
        builder2.setCancelable(false);
        builder2.setTitle("Terms & Conditions");


        builder2.setMessage("By clicking " + "\"Agree\"" +", you are agreeing to Rentathon's Terms & Conditions: \n \n" + "rentathonapp.com/tos");



        builder2.setPositiveButton("Agree", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(SignUpPage2.this, Home.class));

                FirebaseUser userSet = FirebaseAuth.getInstance().getCurrentUser();

                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(SignUpPage1.fullName).build();

                userSet.updateProfile(profileUpdates);
                String currentDate = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault()).format(new Date());



                Map<String, Object> userInformation = new HashMap<>();

                CollectionReference user = mFirestore.collection("users");
                Map<String, Object> blackoutDays = new HashMap<>();
                // Black Out Collections:
                Map<String, Object> sunday = new HashMap<>();
                Map<String, Object> monday = new HashMap<>();
                Map<String, Object> tuesday = new HashMap<>();
                Map<String, Object> wednesday = new HashMap<>();
                Map<String, Object> thursday = new HashMap<>();
                Map<String, Object> friday = new HashMap<>();
                Map<String, Object> saturday = new HashMap<>();


                userInformation.put("Name", SignUpPage1.fullName);
                userInformation.put("Email", SignUpPage1.email);
                userInformation.put("Address", address);
                userInformation.put("City", city);
                userInformation.put("State", userState.getSelectedItem().toString());
                userInformation.put("MPTD", miles);
                userInformation.put("UID", mAuth.getUid());
                userInformation.put("RAverage", 0);
                userInformation.put("RCount", 0);
                userInformation.put("JDate", currentDate);
                userInformation.put("Payment", false);
                userInformation.put("OVerified", false);
                userInformation.put("PendingOVerified", false);
                userInformation.put("VAnn", false);
                userInformation.put("Blackout Days", blackoutDays);
                userInformation.put("NProducts", 0);


                blackoutDays.put("Sunday", sunday);
                blackoutDays.put("Monday", monday);
                blackoutDays.put("Tuesday", tuesday);
                blackoutDays.put("Wednesday", wednesday);
                blackoutDays.put("Thursday", thursday);
                blackoutDays.put("Friday", friday);
                blackoutDays.put("Saturday", saturday);

                sunday.put("012:00 AM", true);
                sunday.put("012:30 AM", true);
                sunday.put("01:00 AM", true);
                sunday.put("01:30 AM", true);
                sunday.put("02:00 AM", true);
                sunday.put("02:30 AM", true);
                sunday.put("03:00 AM", true);
                sunday.put("03:30 AM", true);
                sunday.put("04:00 AM", true);
                sunday.put("04:30 AM", true);
                sunday.put("05:00 AM", true);
                sunday.put("05:30 AM", true);
                sunday.put("06:00 AM", true);
                sunday.put("06:30 AM", true);
                sunday.put("07:00 AM", true);
                sunday.put("07:30 AM", true);
                sunday.put("08:00 AM", true);
                sunday.put("08:30 AM", true);
                sunday.put("09:00 AM", true);
                sunday.put("09:30 AM", true);
                sunday.put("10:00 AM", true);
                sunday.put("10:30 AM", true);
                sunday.put("11:00 AM", true);
                sunday.put("11:30 AM", true);
                sunday.put("012:00 PM", true);
                sunday.put("012:30 PM", true);
                sunday.put("01:00 PM", true);
                sunday.put("01:30 PM", true);
                sunday.put("02:00 PM", true);
                sunday.put("02:30 PM", true);
                sunday.put("03:00 PM", true);
                sunday.put("03:30 PM", true);
                sunday.put("04:00 PM", true);
                sunday.put("04:30 PM", true);
                sunday.put("05:00 PM", true);
                sunday.put("05:30 PM", true);
                sunday.put("06:00 PM", true);
                sunday.put("06:30 PM", true);
                sunday.put("07:00 PM", true);
                sunday.put("07:30 PM", true);
                sunday.put("08:00 PM", true);
                sunday.put("08:30 PM", true);
                sunday.put("09:00 PM", true);
                sunday.put("09:30 PM", true);
                sunday.put("10:00 PM", true);
                sunday.put("10:30 PM", true);
                sunday.put("11:00 PM", true);
                sunday.put("11:30 PM", true);

                monday.put("012:00 AM", true);
                monday.put("012:30 AM", true);
                monday.put("01:00 AM", true);
                monday.put("01:30 AM", true);
                monday.put("02:00 AM", true);
                monday.put("02:30 AM", true);
                monday.put("03:00 AM", true);
                monday.put("03:30 AM", true);
                monday.put("04:00 AM", true);
                monday.put("04:30 AM", true);
                monday.put("05:00 AM", true);
                monday.put("05:30 AM", true);
                monday.put("06:00 AM", true);
                monday.put("06:30 AM", true);
                monday.put("07:00 AM", true);
                monday.put("07:30 AM", true);
                monday.put("08:00 AM", true);
                monday.put("08:30 AM", true);
                monday.put("09:00 AM", true);
                monday.put("09:30 AM", true);
                monday.put("10:00 AM", true);
                monday.put("10:30 AM", true);
                monday.put("11:00 AM", true);
                monday.put("11:30 AM", true);
                monday.put("012:00 PM", true);
                monday.put("012:30 PM", true);
                monday.put("01:00 PM", true);
                monday.put("01:30 PM", true);
                monday.put("02:00 PM", true);
                monday.put("02:30 PM", true);
                monday.put("03:00 PM", true);
                monday.put("03:30 PM", true);
                monday.put("04:00 PM", true);
                monday.put("04:30 PM", true);
                monday.put("05:00 PM", true);
                monday.put("05:30 PM", true);
                monday.put("06:00 PM", true);
                monday.put("06:30 PM", true);
                monday.put("07:00 PM", true);
                monday.put("07:30 PM", true);
                monday.put("08:00 PM", true);
                monday.put("08:30 PM", true);
                monday.put("09:00 PM", true);
                monday.put("09:30 PM", true);
                monday.put("10:00 PM", true);
                monday.put("10:30 PM", true);
                monday.put("11:00 PM", true);
                monday.put("11:30 PM", true);

                tuesday.put("012:00 AM", true);
                tuesday.put("012:30 AM", true);
                tuesday.put("01:00 AM", true);
                tuesday.put("01:30 AM", true);
                tuesday.put("02:00 AM", true);
                tuesday.put("02:30 AM", true);
                tuesday.put("03:00 AM", true);
                tuesday.put("03:30 AM", true);
                tuesday.put("04:00 AM", true);
                tuesday.put("04:30 AM", true);
                tuesday.put("05:00 AM", true);
                tuesday.put("05:30 AM", true);
                tuesday.put("06:00 AM", true);
                tuesday.put("06:30 AM", true);
                tuesday.put("07:00 AM", true);
                tuesday.put("07:30 AM", true);
                tuesday.put("08:00 AM", true);
                tuesday.put("08:30 AM", true);
                tuesday.put("09:00 AM", true);
                tuesday.put("09:30 AM", true);
                tuesday.put("10:00 AM", true);
                tuesday.put("10:30 AM", true);
                tuesday.put("11:00 AM", true);
                tuesday.put("11:30 AM", true);
                tuesday.put("012:00 PM", true);
                tuesday.put("012:30 PM", true);
                tuesday.put("01:00 PM", true);
                tuesday.put("01:30 PM", true);
                tuesday.put("02:00 PM", true);
                tuesday.put("02:30 PM", true);
                tuesday.put("03:00 PM", true);
                tuesday.put("03:30 PM", true);
                tuesday.put("04:00 PM", true);
                tuesday.put("04:30 PM", true);
                tuesday.put("05:00 PM", true);
                tuesday.put("05:30 PM", true);
                tuesday.put("06:00 PM", true);
                tuesday.put("06:30 PM", true);
                tuesday.put("07:00 PM", true);
                tuesday.put("07:30 PM", true);
                tuesday.put("08:00 PM", true);
                tuesday.put("08:30 PM", true);
                tuesday.put("09:00 PM", true);
                tuesday.put("09:30 PM", true);
                tuesday.put("10:00 PM", true);
                tuesday.put("10:30 PM", true);
                tuesday.put("11:00 PM", true);
                tuesday.put("11:30 PM", true);

                wednesday.put("012:00 AM", true);
                wednesday.put("012:30 AM", true);
                wednesday.put("01:00 AM", true);
                wednesday.put("01:30 AM", true);
                wednesday.put("02:00 AM", true);
                wednesday.put("02:30 AM", true);
                wednesday.put("03:00 AM", true);
                wednesday.put("03:30 AM", true);
                wednesday.put("04:00 AM", true);
                wednesday.put("04:30 AM", true);
                wednesday.put("05:00 AM", true);
                wednesday.put("05:30 AM", true);
                wednesday.put("06:00 AM", true);
                wednesday.put("06:30 AM", true);
                wednesday.put("07:00 AM", true);
                wednesday.put("07:30 AM", true);
                wednesday.put("08:00 AM", true);
                wednesday.put("08:30 AM", true);
                wednesday.put("09:00 AM", true);
                wednesday.put("09:30 AM", true);
                wednesday.put("10:00 AM", true);
                wednesday.put("10:30 AM", true);
                wednesday.put("11:00 AM", true);
                wednesday.put("11:30 AM", true);
                wednesday.put("012:00 PM", true);
                wednesday.put("012:30 PM", true);
                wednesday.put("01:00 PM", true);
                wednesday.put("01:30 PM", true);
                wednesday.put("02:00 PM", true);
                wednesday.put("02:30 PM", true);
                wednesday.put("03:00 PM", true);
                wednesday.put("03:30 PM", true);
                wednesday.put("04:00 PM", true);
                wednesday.put("04:30 PM", true);
                wednesday.put("05:00 PM", true);
                wednesday.put("05:30 PM", true);
                wednesday.put("06:00 PM", true);
                wednesday.put("06:30 PM", true);
                wednesday.put("07:00 PM", true);
                wednesday.put("07:30 PM", true);
                wednesday.put("08:00 PM", true);
                wednesday.put("08:30 PM", true);
                wednesday.put("09:00 PM", true);
                wednesday.put("09:30 PM", true);
                wednesday.put("10:00 PM", true);
                wednesday.put("10:30 PM", true);
                wednesday.put("11:00 PM", true);
                wednesday.put("11:30 PM", true);

                thursday.put("012:00 AM", true);
                thursday.put("012:30 AM", true);
                thursday.put("01:00 AM", true);
                thursday.put("01:30 AM", true);
                thursday.put("02:00 AM", true);
                thursday.put("02:30 AM", true);
                thursday.put("03:00 AM", true);
                thursday.put("03:30 AM", true);
                thursday.put("04:00 AM", true);
                thursday.put("04:30 AM", true);
                thursday.put("05:00 AM", true);
                thursday.put("05:30 AM", true);
                thursday.put("06:00 AM", true);
                thursday.put("06:30 AM", true);
                thursday.put("07:00 AM", true);
                thursday.put("07:30 AM", true);
                thursday.put("08:00 AM", true);
                thursday.put("08:30 AM", true);
                thursday.put("09:00 AM", true);
                thursday.put("09:30 AM", true);
                thursday.put("10:00 AM", true);
                thursday.put("10:30 AM", true);
                thursday.put("11:00 AM", true);
                thursday.put("11:30 AM", true);
                thursday.put("012:00 PM", true);
                thursday.put("012:30 PM", true);
                thursday.put("01:00 PM", true);
                thursday.put("01:30 PM", true);
                thursday.put("02:00 PM", true);
                thursday.put("02:30 PM", true);
                thursday.put("03:00 PM", true);
                thursday.put("03:30 PM", true);
                thursday.put("04:00 PM", true);
                thursday.put("04:30 PM", true);
                thursday.put("05:00 PM", true);
                thursday.put("05:30 PM", true);
                thursday.put("06:00 PM", true);
                thursday.put("06:30 PM", true);
                thursday.put("07:00 PM", true);
                thursday.put("07:30 PM", true);
                thursday.put("08:00 PM", true);
                thursday.put("08:30 PM", true);
                thursday.put("09:00 PM", true);
                thursday.put("09:30 PM", true);
                thursday.put("10:00 PM", true);
                thursday.put("10:30 PM", true);
                thursday.put("11:00 PM", true);
                thursday.put("11:30 PM", true);

                friday.put("012:00 AM", true);
                friday.put("012:30 AM", true);
                friday.put("01:00 AM", true);
                friday.put("01:30 AM", true);
                friday.put("02:00 AM", true);
                friday.put("02:30 AM", true);
                friday.put("03:00 AM", true);
                friday.put("03:30 AM", true);
                friday.put("04:00 AM", true);
                friday.put("04:30 AM", true);
                friday.put("05:00 AM", true);
                friday.put("05:30 AM", true);
                friday.put("06:00 AM", true);
                friday.put("06:30 AM", true);
                friday.put("07:00 AM", true);
                friday.put("07:30 AM", true);
                friday.put("08:00 AM", true);
                friday.put("08:30 AM", true);
                friday.put("09:00 AM", true);
                friday.put("09:30 AM", true);
                friday.put("10:00 AM", true);
                friday.put("10:30 AM", true);
                friday.put("11:00 AM", true);
                friday.put("11:30 AM", true);
                friday.put("012:00 PM", true);
                friday.put("012:30 PM", true);
                friday.put("01:00 PM", true);
                friday.put("01:30 PM", true);
                friday.put("02:00 PM", true);
                friday.put("02:30 PM", true);
                friday.put("03:00 PM", true);
                friday.put("03:30 PM", true);
                friday.put("04:00 PM", true);
                friday.put("04:30 PM", true);
                friday.put("05:00 PM", true);
                friday.put("05:30 PM", true);
                friday.put("06:00 PM", true);
                friday.put("06:30 PM", true);
                friday.put("07:00 PM", true);
                friday.put("07:30 PM", true);
                friday.put("08:00 PM", true);
                friday.put("08:30 PM", true);
                friday.put("09:00 PM", true);
                friday.put("09:30 PM", true);
                friday.put("10:00 PM", true);
                friday.put("10:30 PM", true);
                friday.put("11:00 PM", true);
                friday.put("11:30 PM", true);

                saturday.put("012:00 AM", true);
                saturday.put("012:30 AM", true);
                saturday.put("01:00 AM", true);
                saturday.put("01:30 AM", true);
                saturday.put("02:00 AM", true);
                saturday.put("02:30 AM", true);
                saturday.put("03:00 AM", true);
                saturday.put("03:30 AM", true);
                saturday.put("04:00 AM", true);
                saturday.put("04:30 AM", true);
                saturday.put("05:00 AM", true);
                saturday.put("05:30 AM", true);
                saturday.put("06:00 AM", true);
                saturday.put("06:30 AM", true);
                saturday.put("07:00 AM", true);
                saturday.put("07:30 AM", true);
                saturday.put("08:00 AM", true);
                saturday.put("08:30 AM", true);
                saturday.put("09:00 AM", true);
                saturday.put("09:30 AM", true);
                saturday.put("10:00 AM", true);
                saturday.put("10:30 AM", true);
                saturday.put("11:00 AM", true);
                saturday.put("11:30 AM", true);
                saturday.put("012:00 PM", true);
                saturday.put("012:30 PM", true);
                saturday.put("01:00 PM", true);
                saturday.put("01:30 PM", true);
                saturday.put("02:00 PM", true);
                saturday.put("02:30 PM", true);
                saturday.put("03:00 PM", true);
                saturday.put("03:30 PM", true);
                saturday.put("04:00 PM", true);
                saturday.put("04:30 PM", true);
                saturday.put("05:00 PM", true);
                saturday.put("05:30 PM", true);
                saturday.put("06:00 PM", true);
                saturday.put("06:30 PM", true);
                saturday.put("07:00 PM", true);
                saturday.put("07:30 PM", true);
                saturday.put("08:00 PM", true);
                saturday.put("08:30 PM", true);
                saturday.put("09:00 PM", true);
                saturday.put("09:30 PM", true);
                saturday.put("10:00 PM", true);
                saturday.put("10:30 PM", true);
                saturday.put("11:00 PM", true);
                saturday.put("11:30 PM", true);


                user.document(mAuth.getUid()).set(userInformation);
                fileUploader(mAuth.getUid());

            }
        });

        builder2.show();

    }



    private void updateUserInfo(final String name, Uri pickedImgUri, final FirebaseUser currentUser) {

        StorageReference mStorage = FirebaseStorage.getInstance().getReference().child("users_photos");
        final StorageReference imageFilePath = mStorage.child(pickedImgUri.getLastPathSegment());
        Uri file = Uri.fromFile(new File("path/to/images/rivers.jpg"));
        imageFilePath.putFile(pickedImgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                // image uploaded succesfully
                // now we can get our image url

                imageFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        // uri contain user image url


                        UserProfileChangeRequest profleUpdate = new UserProfileChangeRequest.Builder()
                                .setDisplayName(name)
                                .setPhotoUri(uri)
                                .build();


                        currentUser.updateProfile(profleUpdate)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (task.isSuccessful()) {
                                            // user info updated successfully
                                            showMessage("Register Complete");

                                        }

                                    }
                                });

                    }
                });


            }
        });


    }

    private void showMessage(String message){

        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    private String getExtension(Uri uri){

        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));


    }

    private void fileUploader(String uid){
        if(SignUpPage1.pickedImgUri != null) {

            StorageReference Ref = storageRef.child(uid);

            Ref.putFile(SignUpPage1.pickedImgUri);

        }



    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == 1) && (resultCode == RESULT_OK)) {
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
                String full = addresses.get(0).getAddressLine(0);

                userAddress.setText(full.substring(0, full.indexOf(",")));





            }
        }
    }


}
