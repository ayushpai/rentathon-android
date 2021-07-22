package com.neuralgorithmic.rentathon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.neuralgorithmic.rentathon.Home.Home;
import com.neuralgorithmic.rentathon.Product.ProductOverLord;
import com.neuralgorithmic.rentathon.Profile.ProfileMain;
import com.neuralgorithmic.rentathon.Rent.QRScan;

public class MessagingHome extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_home);

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);


        bottomNavigationView.setSelectedItemId(R.id.chat);
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
    }
}