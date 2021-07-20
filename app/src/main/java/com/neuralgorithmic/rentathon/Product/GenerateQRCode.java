package com.neuralgorithmic.rentathon.Product;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.zxing.WriterException;
import com.neuralgorithmic.rentathon.R;

import org.jetbrains.annotations.NotNull;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class GenerateQRCode extends AppCompatActivity {

    private ImageView qrCodeIV;
    Bitmap bitmap;
    QRGEncoder qrgEncoder;

    FirebaseFirestore mFirestore;
    DocumentReference docRef;

    static String transactionID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.owner_generate_qr);

        mFirestore = FirebaseFirestore.getInstance();

        docRef = mFirestore.collection("transactions").document(transactionID);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
               @Override
               public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                   DocumentSnapshot document = task.getResult();
                   if (document.exists()) {

                   }
               }
           });

                // initializing all variables.
                qrCodeIV = findViewById(R.id.idIVQrcode);

        // below line is for getting
        // the windowmanager service.
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
        int dimen = width < height ? width : height;
        dimen = dimen * 3 / 4;

        // setting this dimensions inside our qr code
        // encoder to generate our qr code.
        qrgEncoder = new QRGEncoder(transactionID, null, QRGContents.Type.TEXT, dimen);
        try {
            // getting our qrcode in the form of bitmap.
            bitmap = qrgEncoder.encodeAsBitmap();
            // the bitmap is set inside our image
            // view using .setimagebitmap method.
            qrCodeIV.setImageBitmap(bitmap);
        } catch (WriterException e) {
            // this method is called for
            // exception handling.
        }
    }
}



