package com.neuralgorithmic.rentathon.Signin;

import androidx.annotation.NonNull;
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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;
import com.neuralgorithmic.rentathon.Profile.VerifyEmail;
import com.neuralgorithmic.rentathon.R;

import org.jetbrains.annotations.NotNull;

public class SignUpPage1 extends AppCompatActivity{

    ImageView profilePicture;
    TextView clickToAddPic;
    static int PReqcode = 1;
    static int REQUESCODE = 1;
    static Uri pickedImgUri;
    private TextView signUpBtn;
    private EditText userEmail, userPassword, confirmPassword, firstNameEntry, lastNameEntry;
    private Button nextBtn;
    private FirebaseAuth mAuth;
    boolean profilePicSet;
    public static String email, fullName, password, firstName, lastName, userName;

    FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_signup_pg1);

        confirmPassword = findViewById(R.id.password_confirm_input);
        userEmail = findViewById(R.id.email_input);
        firstNameEntry = findViewById(R.id.first_name_input);
        lastNameEntry = findViewById(R.id.last_name_input);
        userPassword = findViewById(R.id.password_input);
        signUpBtn = findViewById(R.id.signup_btn);
        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        profilePicSet = false;

        userEmail.setInputType(InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS);



        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                firstName = firstNameEntry.getText().toString().trim();
                lastName = lastNameEntry.getText().toString().trim();
                email = userEmail.getText().toString().trim();
                fullName = firstName + " " + lastName;
                password = userPassword.getText().toString().trim();

                if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()|| confirmPassword.getText().toString().isEmpty()) {
                    showMessage("Please enter all information :)");
                }
                else if(!(password.equals(confirmPassword.getText().toString()))){
                    showMessage("Passwords do not match :)");
                }
                else{
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignUpPage1.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText(SignUpPage1.this, "Creating Account...",
                                        Toast.LENGTH_LONG).show();
                                FirebaseUser currentUser = mAuth.getCurrentUser();


                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(fullName).build();
                                currentUser.updateProfile(profileUpdates);
                                startActivity(new Intent(SignUpPage1.this, VerifyEmail.class));

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.e("Tag", "createUserWithEmail:failure", task.getException());
                                try {
                                    throw task.getException();
                                } catch (FirebaseAuthWeakPasswordException e) {
                                    userPassword.setError("Weak Password");
                                    userPassword.requestFocus();
                                } catch (FirebaseAuthInvalidCredentialsException e) {
                                    userEmail.setError("Invalid Email");
                                    userEmail.requestFocus();
                                } catch (FirebaseAuthUserCollisionException e) {
                                    userEmail.setError("Account Exists");
                                    userEmail.requestFocus();
                                } catch (FirebaseException e) {
                                    showMessage(e.toString());

                                } catch (Exception e) {
                                    Log.e("TAG", e.getMessage());
                                }
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
