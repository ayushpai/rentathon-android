package com.neuralgorithmic.rentathon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.neuralgorithmic.rentathon.Home.Home;
import com.neuralgorithmic.rentathon.Product.ProductOverLord;
import com.neuralgorithmic.rentathon.Profile.ProfileMain;
import com.neuralgorithmic.rentathon.Rent.QRScan;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MessagingHome extends AppCompatActivity {

    DatabaseReference profileRef;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    RecyclerView recyclerView;
    private MessageHomeAdapter listAdapter;

    private ArrayList<MessageHomeCardInfo> messageList = new ArrayList<>();
    FirebaseFirestore mFirestore;

    String currentUserUID;





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

        recyclerView = findViewById(R.id.recycler_view);

        mFirestore = FirebaseFirestore.getInstance();
        currentUserUID = FirebaseAuth.getInstance().getCurrentUser().getUid();


        recyclerView.setLayoutManager(new LinearLayoutManager(MessagingHome.this));
        listAdapter = new MessageHomeAdapter(messageList, this);
        recyclerView.setAdapter(listAdapter);

        mFirestore.collection("transactions").whereEqualTo("RenterUID", currentUserUID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot message : task.getResult()) {
                    Map<String, Object> messageMap  = (Map) message.get("Messages");
                    Map<String, Object> messageMapInfo  = (Map) messageMap.get("1");


                    messageList.add(new MessageHomeCardInfo(message.getString("OwnerName"), messageMapInfo.get("Message").toString(), messageMapInfo.get("Time").toString(), messageMapInfo.get("Sender").toString(), message.getId()));

                    listAdapter.notifyDataSetChanged();
                }
            }
        });


        mFirestore.collection("transactions").whereEqualTo("OwnerUID", currentUserUID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot message : task.getResult()) {
                    Map<String, Object> messageMap  = (Map) message.get("Messages");
                    Map<String, Object> messageMapInfo  = (Map) messageMap.get("1");


                    messageList.add(new MessageHomeCardInfo(message.getString("RenterName"), messageMapInfo.get("Message").toString(), messageMapInfo.get("Time").toString(), messageMapInfo.get("Sender").toString(), message.getId()));

                    listAdapter.notifyDataSetChanged();
                }
            }
        });










    }

    private void showMessage(String message){

        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

}