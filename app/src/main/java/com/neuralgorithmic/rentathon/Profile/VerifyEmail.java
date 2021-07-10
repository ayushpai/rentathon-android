package com.neuralgorithmic.rentathon.Profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.neuralgorithmic.rentathon.R;
import com.neuralgorithmic.rentathon.Signin.SignUpPage2;

import java.util.Timer;
import java.util.TimerTask;

public class VerifyEmail extends AppCompatActivity {
    Button ok;
    FirebaseUser user;
    Runnable refresh;
    Handler handler;
    boolean run = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_verify_email);
        //ok = findViewById(R.id.verifybtn);


        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        user.sendEmailVerification();
        handler = new Handler();


        refresh = new Runnable() {
            public void run() {
                if(run) {
                    user.reload();
                    if (user.isEmailVerified()) {
                        startActivity(new Intent(VerifyEmail.this, SignUpPage2.class));
                        run = false;

                    }
                    handler.postDelayed(refresh, 500);
                }
            }
        };
        handler.post(refresh);


        /*ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user.isEmailVerified()) {
                    startActivity(new Intent(VerifyEmail.this, SignUpPage2.class));
                }
                else{
                    showMessage("Email address was not verified or is being processed. Try again :)");
                    user.reload();
                }
            }
        });

         */






    }
    private void showMessage(String message){

        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }


}
