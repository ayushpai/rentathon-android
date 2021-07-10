package com.neuralgorithmic.rentathon.Profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.neuralgorithmic.rentathon.Home.Home;
import com.neuralgorithmic.rentathon.MessagingHome;
import com.neuralgorithmic.rentathon.Product.ProductOverLord;
import com.neuralgorithmic.rentathon.Signin.MainActivity;
import com.neuralgorithmic.rentathon.Product.ProductMain;
import com.neuralgorithmic.rentathon.R;

public class ProfileMain extends AppCompatActivity {
    FirebaseAuth mAuth;

    TextView userNameText, userEmailText, travelDistance, address, city, state;
    Button homeNav, rentNav, chatNav, logOut, userStats, editProfile;
    FirebaseFirestore mFirestore;
    CircularImageView profliePic;
    public Uri picID;
    private DocumentReference docRef;
    static  String localName= "";
    GoogleSignInClient mGoogleSignInClient;
    StorageReference storageRef;
    FirebaseStorage storage;
    public static String globalUID;
    TextView sunday, monday, tuesday, wednesday, thursday, friday, saturday;
    Boolean filledOut;
    FirebaseUser mFirebaseUser;
    static String currentUserID;

    Button manageProductsBtn;
   static public Boolean fromProfile;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_profile);
        fromProfile = true;

        ProductOverLord.fromOverLord = false;
        ProductMain.fromPreview = true;
        manageProductsBtn = findViewById(R.id.manage_products_btn);
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

        userNameText = findViewById(R.id.full_name);
        userEmailText = findViewById(R.id.email_address);
        profliePic = findViewById(R.id.product_pic_input);
        userStats = findViewById(R.id.personal_statistics_btn);
        travelDistance = findViewById(R.id.max_travel_distance);
        address = findViewById(R.id.street_address);
        city = findViewById(R.id.city);
        state = findViewById(R.id.state);
        logOut = findViewById(R.id.logout_btn);
        editProfile = findViewById(R.id.edit_profile_btn);
        sunday = findViewById(R.id.sunday);
        monday = findViewById(R.id.monday);
        tuesday = findViewById(R.id.tuesday);
        wednesday = findViewById(R.id.wednesday);
        thursday = findViewById(R.id.thursday);
        friday = findViewById(R.id.friday);
        saturday = findViewById(R.id.saturday);

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions
                .Builder()
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);
        mFirebaseUser = mAuth.getCurrentUser();
        if(mFirebaseUser != null) {
            currentUserID = mFirebaseUser.getUid(); //Do what you need to do with the id
        }


        docRef = mFirestore.collection("users").document(currentUserID);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                DocumentSnapshot document = task.getResult();
                if(document.exists()){
                    //picID = Uri.parse(document.get("Profile Picture").toString());
                    //profliePic.setImageURI(picID);
                    userNameText.setText(document.get("Name").toString());
                    localName = document.get("Name").toString();
                    userEmailText.setText(document.get("Email").toString());
                    travelDistance.setText(document.get("Preffered Travel Distance(m)").toString() + " Miles");
                    address.setText(document.get("Address").toString());
                    city.setText(document.get("City").toString());
                    state.setText(document.get("State").toString());
                    filledOut = Boolean.parseBoolean(document.get("Filled Out").toString());
                    if (filledOut) {
                        sunday.setText(document.get("Sunday").toString());
                        monday.setText(document.get("Monday").toString());
                        tuesday.setText(document.get("Tuesday").toString());
                        wednesday.setText(document.get("Wednesday").toString());
                        thursday.setText(document.get("Thursday").toString());
                        friday.setText(document.get("Friday").toString());
                        saturday.setText(document.get("Saturday").toString());
                    }




                    storageRef.child("users/" + mAuth.getUid()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            // Got the download URL for 'users/me/profile.png'
                            Glide.with(ProfileMain.this).load(uri).into(profliePic);


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle any errors
                            //showMessage("Please connect to the internet to view the products");
                        }
                    });







                    //userNameText.setText(document.getData().toString());

                }


                }

        });

        /*docRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                userNameText.setText(documentSnapshot.getString("name"));
            }
        });*/




/*
        docRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                userNameText.setText(documentSnapshot.getString("email"));

            }
        });*/
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ProfileMain.this, ConfiimEdit.class));

            }
        });


        userStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ProfileMain.this, UserStatsMain.class));
                overridePendingTransition(0,0);

            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);

        bottomNavigationView.setSelectedItemId(R.id.profile);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.chat:
                        startActivity(new Intent(getApplicationContext(), MessagingHome.class));
                        overridePendingTransition(0, 0);

                        return true;

                    case R.id.products:
                        startActivity(new Intent(getApplicationContext(), ProductOverLord.class));
                        overridePendingTransition(0, 0);

                        return true;

                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), ProfileMain.class));
                        overridePendingTransition(0, 0);

                        return true;

                    case R.id.home:
                    startActivity(new Intent(getApplicationContext(), Home.class));
                    overridePendingTransition(0, 0);

                    return true;


                }
                return false;
            }
        });






        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();

                mGoogleSignInClient.signOut();
                startActivity(new Intent(ProfileMain.this, MainActivity.class));

            }
        });













    }

    private void loadUserInfo(){








    }
}
