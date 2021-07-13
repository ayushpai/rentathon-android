package com.neuralgorithmic.rentathon.Product;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.neuralgorithmic.rentathon.R;
import com.neuralgorithmic.rentathon.Signin.SignUpPage2;
import com.neuralgorithmic.rentathon.Signin.SignUpProfilePic;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.jetbrains.annotations.NotNull;

public class BackgroundCheck extends AppCompatActivity {

    ImageView license, selfie, uploadIcon1, uploadIcon2, checkIcon1, checkIcon2;
    Button submitBtn;
    StorageReference storageRef;
    Uri licenseUri, selfieUri;
    FirebaseAuth mAuth;
    Boolean licenseButton = false;

    DocumentReference docRef;
    FirebaseFirestore mFirestore;
    String userName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.owner_background_check);


        license = findViewById(R.id.driverslicenseinput);
        selfie = findViewById(R.id.pictureinput);
        uploadIcon1 = findViewById(R.id.icon1);
        uploadIcon2 = findViewById(R.id.icon2);
        checkIcon1 = findViewById(R.id.check1);
        checkIcon2 = findViewById(R.id.check2);
        submitBtn = findViewById(R.id.submit_button);
        storageRef = FirebaseStorage.getInstance().getReference("OVerification");
        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

        docRef = mFirestore.collection("users").document(mAuth.getUid());

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
           @Override
           public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
               DocumentSnapshot document = task.getResult();
               if(document.exists()) {
                   userName = document.get("Name").toString();
               }
           }
       });



                license.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        licenseButton = true;
                        AlertDialog.Builder dialogBox = new AlertDialog.Builder(BackgroundCheck.this);
                        dialogBox.setCancelable(true);
                        dialogBox.setTitle("Drivers License Requirements");
                        dialogBox.setIcon(R.drawable.upload_icon_dark);
                        dialogBox.setMessage("1) Your name, DOB, and picture should be completely visible.\n\n2) Use the cropper to crop out the background.\n\n3) Make sure that there is no glare on the image \n\nIf any of these requirements are not met, you may be rejected.");

                        dialogBox.setPositiveButton("Select Photo", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                licenseButton = true;
                                CropImage.startPickImageActivity(BackgroundCheck.this);
                            }
                        });

                        dialogBox.show();
                    }
                });

        selfie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                licenseButton = false;
                AlertDialog.Builder dialogBox = new AlertDialog.Builder(BackgroundCheck.this);
                dialogBox.setCancelable(true);
                dialogBox.setTitle("Selfie W/ Drivers License");
                dialogBox.setIcon(R.drawable.upload_icon_dark);
                dialogBox.setMessage("1) Your face and drivers license must be fully in the frame.\n\n2) Your drivers license must be visible and clear to read.\n\n3) Make sure that there is no glare on the image \n\nIf any of these requirements are not met, you may be rejected.");

                dialogBox.setPositiveButton("Select Photo", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        licenseButton = false;
                        CropImage.startPickImageActivity(BackgroundCheck.this);
                    }
                });

                dialogBox.show();
            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == RESULT_OK) {
            
            if(licenseButton) {
                licenseUri = CropImage.getPickImageResultUri(this, data);

                if (CropImage.isReadExternalStoragePermissionsRequired(this, licenseUri)) {

                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
                } else {
                    startCrop(licenseUri);
                }
            }
            else{
                selfieUri = CropImage.getPickImageResultUri(this, data);

                if (CropImage.isReadExternalStoragePermissionsRequired(this, selfieUri)) {

                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
                } else {
                    startCrop(selfieUri);
                }
            }

        }


        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            final CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(resultCode == RESULT_OK){
                if(licenseButton) {
                    uploadIcon1.setVisibility(View.INVISIBLE);
                    checkIcon1.setVisibility(View.VISIBLE);
                }
                else{
                    uploadIcon2.setVisibility(View.INVISIBLE);
                    checkIcon2.setVisibility(View.VISIBLE);
                }
                submitBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        fileUploader(userName  + " Drivers Licence", licenseUri);
                        fileUploader(userName  + " Selfie Verification", selfieUri);

                        WriteBatch batch = mFirestore.batch();
                        batch.update(docRef, "PendingOVerified", true);
                        batch.commit();
                        startActivity(new Intent(BackgroundCheck.this, ProductOverLord.class));
                    }
                });
            }
        }
    }
    private void fileUploader(String uid, Uri uri){
        StorageReference Ref = storageRef.child(uid);
        Ref.putFile(uri);
    }

    private void startCrop(Uri imageUri){
        CropImage.activity(imageUri).setGuidelines(CropImageView.Guidelines.ON).setMultiTouchEnabled(true).start(BackgroundCheck.this);
    }

}