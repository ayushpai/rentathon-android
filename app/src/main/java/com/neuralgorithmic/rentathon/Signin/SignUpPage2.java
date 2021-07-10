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

        userAddress.setOnClickListener(new View.OnClickListener() {
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



                Map<String, Object> userMap = new HashMap<>();
                mFirestore.collection("users").add(userMap);
                CollectionReference user = mFirestore.collection("users");
                userMap.put("Name", SignUpPage1.fullName);
                userMap.put("Email", SignUpPage1.email);
                userMap.put("Address", address);
                userMap.put("City", city);
                userMap.put("State", userState.getSelectedItem().toString());
                userMap.put("Preffered Travel Distance(m)", String.valueOf(miles));
                userMap.put("Sunday", "");
                userMap.put("Monday", "");
                userMap.put("Tuesday", "");
                userMap.put("Wednesday", "");
                userMap.put("Thursday","");
                userMap.put("Friday", "");
                userMap.put("Saturday", "");
                userMap.put("Filled Out", "false");
                userMap.put("User ID", mAuth.getUid());
                userMap.put("Num Reviews", "0");
                userMap.put("Rating", "0");
                userMap.put("Creation Date", currentDate);
                userMap.put("Current Product Num", "0");
                userMap.put("Viewed Announcement", false);
                userMap.put("Verified User", false);




                user.document(mAuth.getUid()).set(userMap);
                mFirestore.collection("users").add(userMap);
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
