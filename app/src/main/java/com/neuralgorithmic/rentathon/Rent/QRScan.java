package com.neuralgorithmic.rentathon.Rent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.zxing.Result;
import com.google.zxing.qrcode.encoder.QRCode;
import com.neuralgorithmic.rentathon.Product.ProductOverLord;
import com.neuralgorithmic.rentathon.R;
import com.neuralgorithmic.rentathon.Signin.SignUpPage1;

import org.jetbrains.annotations.NotNull;

import java.security.Permission;

public class QRScan extends AppCompatActivity {
    private CodeScanner mCodeScanner;

    FirebaseFirestore mFirestore;
    DocumentReference docRef;
    String transactionID;

    String ownerID, renterID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.renter_scan_qr);

        mFirestore = FirebaseFirestore.getInstance();

        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(this, scannerView);

        if(ContextCompat.checkSelfPermission(QRScan.this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED){

            if(ActivityCompat.shouldShowRequestPermissionRationale(QRScan.this, Manifest.permission.CAMERA)){
                Toast.makeText(getApplicationContext(), "Please accept for required permission.", Toast.LENGTH_SHORT).show();
            }
            else{
                ActivityCompat.requestPermissions(QRScan.this, new String[]{Manifest.permission.CAMERA}, 0);

            }
        }
        else {
            mCodeScanner.setDecodeCallback(new DecodeCallback() {
                @Override
                public void onDecoded(@NonNull @NotNull Result result) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            transactionID = result.getText().trim();

                            Toast.makeText(QRScan.this, result.getText(), Toast.LENGTH_SHORT).show();
                            ownerID = transactionID.substring(0, transactionID.indexOf("/"));
                            renterID = transactionID.substring(transactionID.indexOf("_") + 1, transactionID.length());
                            transactionID = transactionID.substring(transactionID.indexOf("/") + 1, transactionID.indexOf("_"));



                            docRef = mFirestore.collection("transactions").document(transactionID);
                            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {

                                        if(ownerID.equals(document.getString("OwnerUID")) && renterID.equals(document.getString("RenterUID"))){
                                            AlertDialog.Builder dialogBox = new AlertDialog.Builder(QRScan.this);
                                            dialogBox.setCancelable(false);
                                            dialogBox.setIcon(R.drawable.check_icon_orange);
                                            dialogBox.setTitle("Your Rental Has Been Confirmed");
                                            dialogBox.setMessage("You will be charged for $" + document.getDouble("TransactionAmnt") + " and your rental will be due back to " + document.getString("OwnerName") +  " in " + document.getDouble("RentDuration") +  " days (" + document.getString("ReturnDate") + ")");

                                            dialogBox.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                    startActivity(new Intent(QRScan.this, ProductOverLord.class));
                                                }
                                            });

                                            dialogBox.show();
                                        }
                                        else {
                                            AlertDialog.Builder dialogBox = new AlertDialog.Builder(QRScan.this);
                                            dialogBox.setCancelable(true);
                                            dialogBox.setTitle("Invalid QR Code");
                                            dialogBox.setMessage("Rescan by tapping anywhere on the page.");

                                            dialogBox.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            });

                                            dialogBox.show();
                                        }

                                    }
                                    else{
                                        AlertDialog.Builder dialogBox = new AlertDialog.Builder(QRScan.this);
                                        dialogBox.setCancelable(true);
                                        dialogBox.setTitle("Invalid QR Code");
                                        dialogBox.setMessage("Rescan by tapping anywhere on the page.");

                                        dialogBox.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });

                                        dialogBox.show();
                                    }
                                }
                            });



                        }
                    });

                }
            });
        }
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCodeScanner.startPreview();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }

    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

}