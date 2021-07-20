package com.neuralgorithmic.rentathon.CloudMessaging;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.neuralgorithmic.rentathon.R;
import com.neuralgorithmic.rentathon.Signin.MainActivity;

import static android.content.ContentValues.TAG;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onNewToken(@NonNull String token) {

        //TEST TOKEN: d3IPWqOcTR-afvVFcmFMdd:APA91bEdau0qq1vCcoByjtO0hSeeqC83GzUlOjzZbdY0Tp7yXKcwBPcXcSVhRIi7CEp7Ko8RV31j3Q8CLaNJ-XSjUyQwYy-GF68qKCbNIBcC6W4oVawDL01zChb9QBvqipxPdeIQMjDP
        Log.d("TAG", "The token refreshed: " + token);
        sendRegistrationToServer(token);

    }

    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.
    }


}
