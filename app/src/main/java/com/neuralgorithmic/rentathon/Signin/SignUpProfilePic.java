package com.neuralgorithmic.rentathon.Signin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.neuralgorithmic.rentathon.R;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import de.hdodenhof.circleimageview.CircleImageView;

public class SignUpProfilePic extends AppCompatActivity {

    CircleImageView profilePicView;
    Uri mainUri;
    Button nextPageBtn;

    FirebaseAuth mAuth;
    StorageReference storageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_signup_profile_pic);

        nextPageBtn = findViewById(R.id.skip_button);
        profilePicView = findViewById(R.id.profile_pic_input);

        mAuth = FirebaseAuth.getInstance();
        storageRef = FirebaseStorage.getInstance().getReference("users");

        nextPageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogBox = new AlertDialog.Builder(SignUpProfilePic.this);
                dialogBox.setCancelable(false);
                dialogBox.setTitle("Are You Sure?");
                dialogBox.setIcon(R.drawable.alert_icon);
                dialogBox.setMessage("Having no profile picture will decrease the chance your products will be viewed. According to our research, users have a 45% overall experience boost with an updated profile picture.");

                dialogBox.setPositiveButton("Skip", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(SignUpProfilePic.this, SignUpPage2.class));
                    }
                });
                dialogBox.setNegativeButton("Add Picture", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                dialogBox.show();
            }
        });

        profilePicView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogBox = new AlertDialog.Builder(SignUpProfilePic.this);
                dialogBox.setCancelable(true);
                dialogBox.setTitle("Profile Picture");
                dialogBox.setIcon(R.drawable.add_photo_icon);
                dialogBox.setMessage("Uploading a profile picture will increase the chances of your listing being viewed and interacted with!");

                dialogBox.setPositiveButton("Select Photo", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CropImage.startPickImageActivity(SignUpProfilePic.this);
                    }
                });

                dialogBox.show();
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == RESULT_OK){

            mainUri = CropImage.getPickImageResultUri(this, data);

            if(CropImage.isReadExternalStoragePermissionsRequired(this, mainUri)){

                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            }
            else{
                startCrop(mainUri);

            }

        }

        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            final CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(resultCode == RESULT_OK){
                profilePicView.setImageURI(result.getUri());
                nextPageBtn.setText("Continue");
                nextPageBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fileUploader(mAuth.getUid());
                        startActivity(new Intent(SignUpProfilePic.this, SignUpPage2.class));
                    }
                });

            }
        }
    }

    private void fileUploader(String uid){
        StorageReference Ref = storageRef.child(uid);
        Ref.putFile(mainUri);
    }

    private void startCrop(Uri imageUri){
        CropImage.activity(imageUri).setGuidelines(CropImageView.Guidelines.ON).setMultiTouchEnabled(true).start(SignUpProfilePic.this);
    }
}