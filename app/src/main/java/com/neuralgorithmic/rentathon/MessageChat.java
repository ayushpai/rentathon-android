package com.neuralgorithmic.rentathon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.neuralgorithmic.rentathon.Home.Home;
import com.neuralgorithmic.rentathon.Product.GenerateQRCode;
import com.neuralgorithmic.rentathon.Product.ProductOverLord;
import com.neuralgorithmic.rentathon.Rent.RentProductMain;
import com.r0adkll.slidr.Slidr;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MessageChat extends AppCompatActivity {

    DatabaseReference profileRef;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    RecyclerView recyclerView;
    private ChatAdapter listAdapter;
    StorageReference storageRef;
    FirebaseStorage storage;

    private ArrayList<ChatCardInfo> messageList = new ArrayList<>();
    FirebaseFirestore mFirestore;
    FieldPath fieldPath;
    DocumentReference docRef;

    boolean UIDMatches = false;
    String currentUserUID;
    Map<String, Object> messageMapInfo, newMessage;

    EditText sendBox;
    CollectionReference newMessageInfo;
    ImageView sendIcon;

    Runnable refresh;
    Handler handler;
    boolean run = true;

    int totalMessages = 0;
    int previousTotalMessages = totalMessages;
    int updateIteration = 1;

    ImageView backBtn, profilePic;
    TextView senderName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_user_chat);

        recyclerView = findViewById(R.id.message_recycler);

        mFirestore = FirebaseFirestore.getInstance();
        currentUserUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        sendBox = findViewById(R.id.sendBox);
        sendIcon = findViewById(R.id.send_icon);
        backBtn = findViewById(R.id.imageView3);
        senderName = findViewById(R.id.user_name);
        profilePic = findViewById(R.id.user1_image);

        Slidr.attach(this);
        overridePendingTransition(0, 0);

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();


        recyclerView.setLayoutManager(new LinearLayoutManager(MessageChat.this));
        listAdapter = new ChatAdapter(messageList, this);
        recyclerView.setAdapter(listAdapter);
        docRef = mFirestore.collection("transactions").document(MessageHomeAdapter.chatTransactionID);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    Map<String, Object> messageMap  = (Map) document.get("Messages");

                    for (int i = 0; i < messageMap.size(); i++){

                        if(currentUserUID.equals(document.getString("OwnerUID"))){
                            senderName.setText(document.getString("RenterName"));
                            storageRef.child("users/" + document.getString("RenterUID")).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    // Got the download URL for 'users/me/profile.png'
                                    Glide.with(MessageChat.this).load(uri).into(profilePic);



                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    // Handle any errors
                                    //showMessage("Please connect to the internet to view the products");
                                }
                            });



                        }
                        else{
                            senderName.setText(document.getString("OwnerName"));
                            storageRef.child("users/" + document.getString("OwnerUID")).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    // Got the download URL for 'users/me/profile.png'
                                    Glide.with(MessageChat.this).load(uri).into(profilePic);



                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    // Handle any errors
                                    //showMessage("Please connect to the internet to view the products");
                                }
                            });

                        }

                        messageMapInfo =  (Map) messageMap.get(String.valueOf(i+1));
                        if(currentUserUID.equals(messageMapInfo.get("Sender").toString())){
                            UIDMatches = true;
                        }
                        else{
                            UIDMatches = false;
                        }

                        messageList.add(new ChatCardInfo(messageMapInfo.get("Sender").toString(), messageMapInfo.get("Time").toString(), messageMapInfo.get("Message").toString(), UIDMatches));
                        listAdapter.notifyDataSetChanged();
                    }
                    recyclerView.scrollToPosition(listAdapter.getItemCount()-1);
                }
            }
        });

        sendBox.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    recyclerView.scrollToPosition(listAdapter.getItemCount() - 1);
                }
            }
        });

        handler = new Handler();




        refresh = new Runnable() {
            public void run() {
                if(run) {


                    docRef = mFirestore.collection("transactions").document(MessageHomeAdapter.chatTransactionID);

                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Map<String, Object> messageMap  = (Map) document.get("Messages");
                                totalMessages = messageMap.size();
                                if(updateIteration == 1){
                                    previousTotalMessages = totalMessages;
                                    updateIteration++;
                                }

                            }
                        }
                    });


                    if(totalMessages != previousTotalMessages){
                        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                                DocumentSnapshot document = task.getResult();

                                if (document.exists()) {
                                    Map<String, Object> messageMap  = (Map) document.get("Messages");
                                    for (int i = previousTotalMessages - 1; i < totalMessages; i++){
                                        messageMapInfo =  (Map) messageMap.get(String.valueOf(i + 1));
                                        if(currentUserUID.equals(messageMapInfo.get("Sender").toString())){
                                            UIDMatches = true;
                                        }
                                        else{
                                            UIDMatches = false;
                                        }

                                        messageList.add(new ChatCardInfo(messageMapInfo.get("Sender").toString(), messageMapInfo.get("Time").toString(), messageMapInfo.get("Message").toString(), UIDMatches));
                                        listAdapter.notifyItemInserted(messageList.size() - 1);
                                        recyclerView.scrollToPosition(listAdapter.getItemCount()-1);
                                    }
                                }
                            }
                        });


                    }
                    else{

                    }

                    previousTotalMessages = totalMessages;


                    handler.postDelayed(refresh, 100);

                }
            }
        };
        handler.post(refresh);

        newMessage = new HashMap<>();
        newMessageInfo = mFirestore.collection("transactions");
        sendIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(sendBox.getText().toString().isEmpty()){

                }
                else {
                    docRef = mFirestore.collection("transactions").document(MessageHomeAdapter.chatTransactionID);
                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {

                                Map<String, Object> messageMap  = (Map) document.get("Messages");



                                newMessage.put("Sender", currentUserUID);
                                String currentTime = new SimpleDateFormat("EEE, MMM d @ h:mm a", Locale.getDefault()).format(new Date());
                                newMessage.put("Time", currentTime);
                                newMessage.put("Message",sendBox.getText().toString());
                                messageMap.put(String.valueOf(messageMap.size() + 1), newMessage);
                                WriteBatch batch = mFirestore.batch();
                                batch.update(docRef, "Messages", messageMap);
                                batch.commit();
                                sendBox.setText("");
                                recyclerView.scrollToPosition(messageList.size() - 1);
                            }
                        }
                    });


                }
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MessageChat.this, MessagingHome.class));
            }
        });


        listAdapter.notifyDataSetChanged();
        recyclerView.scrollToPosition(listAdapter.getItemCount()-1);

    }
    private void showMessage(String message){

        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

}