package com.neuralgorithmic.rentathon.Misc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.neuralgorithmic.rentathon.Home.Home;
import com.neuralgorithmic.rentathon.Profile.VerifyEmail;
import com.neuralgorithmic.rentathon.R;

public class WelcomeProdcutMain extends AppCompatActivity {
    Button ok;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_prodcut_main);
        ok = findViewById(R.id.okay_btn);
        back = findViewById(R.id.back_btn);


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeProdcutMain.this, VerifyEmail.class));
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeProdcutMain.this, Home.class));
            }
        });
    }
}
