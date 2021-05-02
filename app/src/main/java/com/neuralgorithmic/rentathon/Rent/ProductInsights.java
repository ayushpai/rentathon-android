package com.neuralgorithmic.rentathon.Rent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.r0adkll.slidr.Slidr;
import com.neuralgorithmic.rentathon.Home.Home;
import com.neuralgorithmic.rentathon.R;

import java.text.DecimalFormat;

public class ProductInsights extends AppCompatActivity {
    TextView productTitle, views, uniqueVisitors;
    ImageView productImage;

    private DocumentReference docRef, docRef2;
    FirebaseAuth mAuth;
    FirebaseFirestore mFirestore;
    StorageReference storageRef;
    FirebaseStorage storage;
    ImageView backBtn;
    Button cartNavBtn, chatNavBtn, profileNavBtn;
    TextView score, scoreBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_insights);
        Slidr.attach(this);


        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();


        backBtn = findViewById(R.id.back_btn);
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        score = findViewById(R.id.score);
        scoreBtn = findViewById(R.id.score_btn);



        productTitle = findViewById(R.id.textView3);
        views = findViewById(R.id.vro);
        productImage = findViewById(R.id.user_product_photos);
        uniqueVisitors = findViewById(R.id.since);

        docRef = mFirestore.collection("products").document(String.valueOf(Home.userProductSelection));

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {


                    String totalViews = document.get("Views").toString();
                    productTitle.setText(document.get("Product Name").toString() + " Insights");
                    views.setText(document.get("Views").toString());
                    uniqueVisitors.setText(document.get("Unique Visitors").toString());
                    DecimalFormat numberFormat = new DecimalFormat("#.00");
                    double IR = Double.parseDouble(document.get("Intelligent Rating").toString());

                    score.setText(numberFormat.format(IR));






                }

            }
        });

        scoreBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://www.rentathonapp.com/app/intelligence"));
                startActivity(intent);
            }
        });


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ProductInsights.this, RentProductMain.class));
                overridePendingTransition(0,0);
                RentProductMain.fromHome = false;

            }
        });








    }
}
