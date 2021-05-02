package com.neuralgorithmic.rentathon.Misc;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.neuralgorithmic.rentathon.R;
import com.neuralgorithmic.rentathon.Signin.MainActivity;

import gr.net.maroulis.library.EasySplashScreen;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_home_rent_main);

        EasySplashScreen config = new EasySplashScreen(SplashScreen.this)
                .withFullScreen()
                .withTargetActivity(MainActivity.class)
                .withSplashTimeOut(2500)
                .withBackgroundColor(Color.parseColor("#f2af58"))
                .withLogo(R.drawable.offical_logo_login);

        View easySplashScreen = config.create();
        setContentView(easySplashScreen);



    }
}
