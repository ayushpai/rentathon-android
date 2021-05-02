package com.neuralgorithmic.rentathon.Chat;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.neuralgorithmic.rentathon.Home.Home;
import com.neuralgorithmic.rentathon.Misc.ComingSoon;
import com.neuralgorithmic.rentathon.Product.ProductMain;
import com.neuralgorithmic.rentathon.Product.ProductOverLord;
import com.neuralgorithmic.rentathon.Profile.ProfileMain;
import com.neuralgorithmic.rentathon.R;

public class ChatMain extends AppCompatActivity {

    Button homeNav, cartNav, profileNav;
    ImageView picture1, picture2, picture3, picture4, picture5, picture6;
    TextView name1, name2, name3, name4, name5, name6, stats1, stats2, stats3;
    FirebaseUser mFirebaseUser;
    String currentUserID;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_main);

        ProductOverLord.fromOverLord = false;
        picture1 = findViewById(R.id.picture1);
        picture2 = findViewById(R.id.picture2);
        picture3 = findViewById(R.id.picture3);
        picture4 = findViewById(R.id.picture4);
        picture5 = findViewById(R.id.picture5);
        picture6 = findViewById(R.id.picture6);
        name1 = findViewById(R.id.name1);
        name2 = findViewById(R.id.name2);
        name3 = findViewById(R.id.name3);
        name4 = findViewById(R.id.name4);
        name5 = findViewById(R.id.name5);
        name6 = findViewById(R.id.name6);
        stats1 = findViewById(R.id.stats1);
        stats2 = findViewById(R.id.stats2);
        stats3 = findViewById(R.id.stats3);
        ProductMain.fromPreview = true;
        mAuth = FirebaseAuth.getInstance();


        picture1.setImageResource(R.drawable.tampa);
        name1.setText("Tampa");
        picture2.setImageResource(R.drawable.clearwater);
        name2.setText("Clearwater");
        picture3.setImageResource(R.drawable.palmharbor);
        name3.setText("Palm Harbor");
        picture4.setImageResource(R.drawable.spete);
        name4.setText("Saint Pete");
        picture5.setImageResource(R.drawable.sarasota);
        name5.setText("Sarasota");
        picture6.setImageResource(R.drawable.sh);
        name6.setText("Saftey Harbor");

        picture1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChatMain.this, ComingSoon.class));
            }
        });

        picture2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChatMain.this, ComingSoon.class));
            }
        });

        picture3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChatMain.this, ComingSoon.class));
            }
        });

        picture4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChatMain.this, ComingSoon.class));
            }
        });

        picture5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChatMain.this, ComingSoon.class));
            }
        });

        picture6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChatMain.this, ComingSoon.class));
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);

        bottomNavigationView.setSelectedItemId(R.id.chat);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.chat:
                        startActivity(new Intent(getApplicationContext(), ChatMain.class));
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











    }
}
