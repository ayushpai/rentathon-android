package com.neuralgorithmic.rentathon.Product;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;
import com.google.zxing.WriterException;
import com.neuralgorithmic.rentathon.Home.Home;
import com.neuralgorithmic.rentathon.Profile.VerifyEmail;
import com.neuralgorithmic.rentathon.R;
import com.neuralgorithmic.rentathon.Rent.QRScan;
import com.neuralgorithmic.rentathon.Signin.SignUpProfilePic;

import org.jetbrains.annotations.NotNull;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class GenerateQRCode extends AppCompatActivity {

    private ImageView qrCodeIV;
    Bitmap bitmap;
    QRGEncoder qrgEncoder;

    FirebaseFirestore mFirestore;
    DocumentReference docRef;

    static String uniqueTransactionID;

    Runnable refresh;
    Handler handler;
    boolean run = true;
    String RAWtransactionID;

    boolean dropOffComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.owner_generate_qr);

        qrCodeIV = findViewById(R.id.idIVQrcode);

        mFirestore = FirebaseFirestore.getInstance();
        showMessage(String.valueOf(ProductOverLord.productIDForQRCode));
        //docRef = mFirestore.collection("transactions").document(transactionID);
        mFirestore.collection("transactions").whereEqualTo("ProductID", Integer.valueOf(ProductOverLord.productIDForQRCode)).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {

                        uniqueTransactionID = document.getString("OwnerUID") + "/" + document.getId() + "_" + document.getString("RenterUID");

                        RAWtransactionID = document.getId();

                        // below line is for getting
                        showMessage(uniqueTransactionID);

                        try {
                            WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);

                            // initializing a variable for default display.
                            Display display = manager.getDefaultDisplay();

                            // creating a variable for point which
                            // is to be displayed in QR Code.
                            Point point = new Point();
                            display.getSize(point);

                            // getting width and
                            // height of a point
                            int width = point.x;
                            int height = point.y;
                            // generating dimension from width and height.
                            int dimen = Math.min(width, height);
                            dimen = dimen * 3 / 4;
                            qrgEncoder = new QRGEncoder(uniqueTransactionID, null, QRGContents.Type.TEXT, dimen);
                            // getting our qrcode in the form of bitmap.
                            bitmap = qrgEncoder.encodeAsBitmap();
                            // the bitmap is set inside our image
                            // view using .setimagebitmap method.
                            qrCodeIV.setImageBitmap(bitmap);
                            showMessage("set image!");
                        } catch (WriterException e) {
                            // this method is called for
                            // exception handling.
                        }

                        handler = new Handler();


                        refresh = new Runnable() {
                            public void run() {
                                if(run) {
                                    docRef = mFirestore.collection("transactions").document(RAWtransactionID);

                                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                                            DocumentSnapshot document = task.getResult();
                                            if (document.exists()) {
                                                dropOffComplete = document.getBoolean("QRCodeVerifiedDropoff");
                                            }
                                        }
                                    });

                                    if(dropOffComplete){
                                        run = false;
                                        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                                                DocumentSnapshot document = task.getResult();
                                                if (document.exists()) {
                                                    AlertDialog.Builder dialogBox = new AlertDialog.Builder(GenerateQRCode.this);
                                                    dialogBox.setCancelable(false);
                                                    dialogBox.setIcon(R.drawable.check_icon_orange);
                                                    dialogBox.setTitle("Your Transaction Has Completed");
                                                    dialogBox.setMessage("You will be payed $" + document.getDouble("TransactionAmnt") + " and the rental will be due back to you in " + document.getDouble("RentDuration") + " days (" + document.getString("ReturnDate") + ")");

                                                    dialogBox.setNegativeButton("Continue", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            dialog.dismiss();
                                                            startActivity(new Intent(GenerateQRCode.this, ProductOverLord.class));
                                                        }
                                                    });

                                                    dialogBox.show();
                                                }
                                            }
                                        });

                                    }


                                    handler.postDelayed(refresh, 500);

                                }
                            }
                        };
                        handler.post(refresh);

                    }
                }
                else{
                    showMessage("here");
                }
            }
        });

                // initializing all variables.
        // the windowmanager service.


        // setting this dimensions inside our qr code
        // encoder to generate our qr code.







    }

    private void showMessage(String message){

        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }
}



