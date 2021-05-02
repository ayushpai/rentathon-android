package com.neuralgorithmic.rentathon.Misc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.neuralgorithmic.rentathon.Chat.ChatMain;
import com.neuralgorithmic.rentathon.R;

public class ComingSoon extends AppCompatActivity {
    Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coming_soon_main);

        backBtn = findViewById(R.id.back_btn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ComingSoon.this, ChatMain.class));
            }
        });







    }
}
