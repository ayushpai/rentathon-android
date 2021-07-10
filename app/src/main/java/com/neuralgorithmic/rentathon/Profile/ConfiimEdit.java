package com.neuralgorithmic.rentathon.Profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.neuralgorithmic.rentathon.Product.ProductMain;
import com.neuralgorithmic.rentathon.R;

public class ConfiimEdit extends AppCompatActivity {

    EditText email, password;
    Button confirim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_confirm_profile_edit);
        email = findViewById(R.id.signin_email_input);
        password = findViewById(R.id.signin_password_input);
        confirim = findViewById(R.id.confirm_button);
        ProductMain.fromPreview = true;



        confirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

// Get auth credentials from the user for re-authentication. The rentathon below shows
// email and password credentials but there are multiple possible providers,
// such as GoogleAuthProvider or FacebookAuthProvider.
                AuthCredential credential = EmailAuthProvider
                        .getCredential(email.getText().toString(), password.getText().toString());

// Prompt the user to re-provide their sign-in credentials
                user.reauthenticate(credential)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    startActivity(new Intent(ConfiimEdit.this, EditProfile.class));
                                }
                                else{
                                    showMessage(task.getException().getMessage());
                                }

                            }
                        });




            }
        });








    }

    private void showMessage(String message){

        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

}
