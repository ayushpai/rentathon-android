package com.neuralgorithmic.rentathon.Rent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.maps.MapView;
import com.r0adkll.slidr.Slidr;
import com.neuralgorithmic.rentathon.Chat.ChatMain;
import com.neuralgorithmic.rentathon.Home.Home;
import com.neuralgorithmic.rentathon.Product.ProductMain;
import com.neuralgorithmic.rentathon.Profile.ProfileMain;
import com.neuralgorithmic.rentathon.R;

public class RentProductLocation extends AppCompatActivity {
    Button next;
    private MapView mMapView;
    private static final String MAPVIEW_BUNDLE_KEY = "AIzaSyDHDkoaa3xmsP1V1Pm3_8MWKscg_-eSn-w";
    ImageView backBtn;
    Button cartNavBtn, chatNavBtn, profileNavBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rent_product_location_main);
        next = findViewById(R.id.rent_button);
        Slidr.attach(this);

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        mMapView = (MapView) findViewById(R.id.mapView);
        mMapView.onCreate(mapViewBundle);

        backBtn = findViewById(R.id.back_btn);

        cartNavBtn = findViewById(R.id.rent_button_nav);
        chatNavBtn = findViewById(R.id.chat_button_nav);
        profileNavBtn = findViewById(R.id.user_button_nav);

        RentProductMain.fromHome = false;

















        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RentProductLocation.this, RentProductTime.class));
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RentProductLocation.this, RentProductMain.class));

            }
        });

        cartNavBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Home.scrollY = 0;
                startActivity(new Intent(RentProductLocation.this, ProductMain.class));
                overridePendingTransition(0,0);
            }
        });

        chatNavBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Home.scrollY = 0;
                startActivity(new Intent(RentProductLocation.this, ChatMain.class));
                overridePendingTransition(0,0);
            }
        });

        profileNavBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Home.scrollY = 0;
                startActivity(new Intent(RentProductLocation.this, ProfileMain.class));
                overridePendingTransition(0,0);

            }
        });

    }





}
