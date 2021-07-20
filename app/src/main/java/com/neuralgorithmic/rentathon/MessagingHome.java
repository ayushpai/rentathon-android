package com.neuralgorithmic.rentathon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.neuralgorithmic.rentathon.Rent.QRScan;

public class MessagingHome extends AppCompatActivity {

    Button qrScannerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_home);

        qrScannerBtn = findViewById(R.id.scanner);
        qrScannerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MessagingHome.this, QRScan.class));
            }
        });

    }
}