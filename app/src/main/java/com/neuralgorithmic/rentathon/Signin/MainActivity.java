package com.neuralgorithmic.rentathon.Signin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.neuralgorithmic.rentathon.Home.Home;
import com.neuralgorithmic.rentathon.R;

public class MainActivity extends AppCompatActivity {

    private EditText mEmailField;
    private EditText mPasswordField;
    Button loginBtn, rentOutBtn;
    TextView signUpBtn, resetPasswordBtn;
    ImageView btn_google;
    public GoogleSignInClient mGoogleSignInClient;

    private FirebaseFirestore mFirestore;
    ProgressBar progressBar;

    static final int GOOGLE_SIGN = 1;

    private FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener mAuthListener;

    private DocumentReference docRef, docRef2;
    static String googleEmail, googleName;
    static Uri googlePhoto;
    boolean userExists;


    StorageReference storageRef;
    FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_login);

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        progressBar = findViewById(R.id.progress_bar);

        mAuth = FirebaseAuth.getInstance();
        mEmailField = (EditText) findViewById(R.id.signin_email_input);
        mPasswordField= (EditText) findViewById(R.id.signin_password_input);
        progressBar = findViewById(R.id.progressBarGoogle);
        btn_google = findViewById(R.id.google_btn);
        signUpBtn = findViewById(R.id.signup_button);
        mFirestore = FirebaseFirestore.getInstance();
        resetPasswordBtn = findViewById(R.id.forgot_password);


        loginBtn = (Button) findViewById(R.id.login_button);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if(firebaseAuth.getCurrentUser() != null){
                    //start home page
                    //startActivity(new Intent(LoginActivity.this, Home.class));
                }
            }
        };

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, SignUpPage1.class));

            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginBtn.setVisibility(View.INVISIBLE);
                final String email = mEmailField.getText().toString();
                final String password = mPasswordField.getText().toString();


                if (email.isEmpty() || password.isEmpty()){

                    showMessage("Please Verify All Fields!");
                    loginBtn.setVisibility(View.VISIBLE);

                }
                else {
                    signIn(email, password);
                }


            }
        });

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(MainActivity.this, googleSignInOptions);

        btn_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signIntent, GOOGLE_SIGN);
            }
        });




        resetPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                final EditText emailInput = new EditText(getApplicationContext());
                emailInput.getBackground().mutate().setColorFilter(getResources().getColor(R.color.accent_background), PorterDuff.Mode.SRC_ATOP);
                alert.setTitle("Reset Password");
                alert.setMessage("Enter your email and click on the link that is sent your email to reset your password.");

                emailInput.setHint("Enter Email");
                FrameLayout container = new FrameLayout(getApplicationContext());
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                int left_margin = dpToPx(20, getApplicationContext().getResources());
                int top_margin = dpToPx(10, getApplicationContext().getResources());
                int right_margin = dpToPx(20, getApplicationContext().getResources());
                int bottom_margin = dpToPx(4, getApplicationContext().getResources());
                params.setMargins(left_margin, top_margin, right_margin, bottom_margin);



                emailInput.setLayoutParams(params);
                container.addView(emailInput);

                alert.setView(container);

                alert.setPositiveButton("Send Email", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String email = emailInput.getText().toString();
                        mAuth.sendPasswordResetEmail(email);

                        AlertDialog.Builder builder2 = new AlertDialog.Builder(MainActivity.this);
                        builder2.setTitle("Email Link Sent");
                        //builder2.setIcon(R.drawable.thanks);
                        builder2.setMessage("Reset password link sent to your email.");

                        builder2.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                        builder2.show();

                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.cancel();
                    }
                });

                alert.show();

            }
        });















    }

    private void signIn(String email, String password) {

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    startActivity(new Intent(MainActivity.this, Home.class));
                    finish();


                }

                else{

                    showMessage(task.getException().getMessage());
                    loginBtn.setVisibility(View.VISIBLE);

                }

            }

        });

    }





    private void SignInGoogle () {


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == GOOGLE_SIGN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                showMessage("failed " + e.toString());
                // ...
            }
        }
    }



    private void firebaseAuthWithGoogle (GoogleSignInAccount account){


        AuthCredential credential = GoogleAuthProvider
                .getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {

                        showMessage("Success");
                        FirebaseUser user = mAuth.getCurrentUser();

                        docRef = mFirestore.collection("users").document(user.getUid());
                        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    startActivity(new Intent(MainActivity.this, Home.class));


                                }
                                else{
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    googleEmail = user.getEmail();
                                    googleName = user.getDisplayName();
                                    googlePhoto = user.getPhotoUrl();
                                    startActivity(new Intent(MainActivity.this, SignUpPage2.class));

                                }
                            }
                        });



                        Log.d("TAG", "Sign In Success!");
                        Toast.makeText(this, "Sign In Success!", Toast.LENGTH_SHORT).show();












                    } else {

                        showMessage("Failed!");

                        Toast.makeText(this, "Sign In Failed!", Toast.LENGTH_SHORT).show();

                    }
                });





    }

    public static int dpToPx(float dp, Resources resources) {
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
        return (int) px;
    }





    private void showMessage(String message){

        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = mAuth.getCurrentUser();

        if(user != null){

            startActivity(new Intent(MainActivity.this, Home.class));
            finish();

        }

    }
}
