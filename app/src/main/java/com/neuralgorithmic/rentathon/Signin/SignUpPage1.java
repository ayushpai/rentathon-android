package com.neuralgorithmic.rentathon.Signin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.neuralgorithmic.rentathon.R;

public class SignUpPage1 extends AppCompatActivity{

    ImageView profilePicture;
    TextView clickToAddPic;
    static int PReqcode = 1;
    static int REQUESCODE = 1;
    static Uri pickedImgUri;
    private EditText userEmail, userPassword, userName, confirmPassword;
    private Button nextBtn;
    private FirebaseAuth mAuth;
    boolean profilePicSet;
    public static String email, name, password;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_signup_pg1);

        confirmPassword = findViewById(R.id.confirm_password_input);

        profilePicture = findViewById(R.id.profile_pic_input);
        clickToAddPic = findViewById(R.id.click_profile_pic_txt);
        userEmail = findViewById(R.id.signup_email_input);
        userName = findViewById(R.id.signup_fullname_input);
        userPassword = findViewById(R.id.signup_password_input);
        nextBtn = findViewById(R.id.next_button);

        mAuth = FirebaseAuth.getInstance();
        profilePicSet = false;

        userEmail.setInputType(InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS);

        profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(Build.VERSION.SDK_INT >= 22){

                    checkAndRequestForPermission();

                }
                else{
                    openGallery();

                }
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                  email = userEmail.getText().toString();
                  name = userName.getText().toString();
                  password = userPassword.getText().toString();





                    if(!profilePicSet){

                        showMessage("Please upload a profile picture.");
                    }
                    else if(name.isEmpty()){
                        showMessage(("Please enter your name."));
                    }
                    else if(email.isEmpty()){
                        showMessage("Please enter your email.");
                    }
                    else if(password.isEmpty()){
                        showMessage("Please enter your password.");
                    }
                    else if(!(confirmPassword.getText().toString().equals(password))){
                        showMessage("Passwords do not match");

                    }

                else{

                        startActivity(new Intent(SignUpPage1.this, SignUpPage2.class));


                }













            }
        });



    }





    private void showMessage(String message){

        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    private void  checkAndRequestForPermission(){

        if(ContextCompat.checkSelfPermission(SignUpPage1.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){

            if(ActivityCompat.shouldShowRequestPermissionRationale(SignUpPage1.this, Manifest.permission.READ_EXTERNAL_STORAGE)){
                Toast.makeText(SignUpPage1.this, "Please accept for required permission.", Toast.LENGTH_SHORT).show();
            }
            else{
                ActivityCompat.requestPermissions(SignUpPage1.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PReqcode);

            }


        }
        else{
            openGallery();
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == REQUESCODE && data != null){

            pickedImgUri = data.getData();
            profilePicture.setImageURI(pickedImgUri);
            profilePicSet = true;


        }

    }

    private void openGallery(){

        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");

        startActivityForResult(galleryIntent, REQUESCODE);


    }
}
