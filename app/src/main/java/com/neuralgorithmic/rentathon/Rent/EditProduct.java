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
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.algolia.search.saas.Client;
import com.algolia.search.saas.Index;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.r0adkll.slidr.Slidr;
import com.neuralgorithmic.rentathon.Home.Home;
import com.neuralgorithmic.rentathon.Product.ProductMain;
import com.neuralgorithmic.rentathon.Profile.UserHomeMain;
import com.neuralgorithmic.rentathon.R;

public class EditProduct extends AppCompatActivity {

    private DocumentReference docRef, docRef2;
    private FirebaseAuth mAuth;
    FirebaseFirestore mFirestore;
    StorageReference storageRef;
    FirebaseStorage storage;
    ImageView productPic, rentersImage, backBtn, ratingPic;
    ViewFlipper imageSlideShow;
    EditText descripton, productName, rentalFee;
    Spinner condition;
    TextView renterName, renterCity, ratingAmntTxt, price2, save;
    FirebaseUser mFirebaseUser;
    Button cartNavBtn, chatNavBtn, profileNavBtn, changePhoto;
    double userRating;
    static Uri pickedImgUri;
    Button layer;
    TextView delete;
    static int PReqcode = 1;
    static int REQUESCODE = 1;
    ScrollView scrollView;
    public String beforeLayerC;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.owner_edit_product);
        RentProductMain.fromHome = false;
        Slidr.attach(this);
        UserHomeMain.fromUserHomeMain = false;
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        scrollView = findViewById(R.id.scrollView2);
        Slidr.attach(this);
        ProductMain.fromPreview = true;

        scrollView.postDelayed(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_UP);
            }
        }, 200);

        changePhoto = findViewById(R.id.more_photos_btn);

        layer = findViewById(R.id.layer);
        save = findViewById(R.id.save);
        //delete = findViewById(R.id.delete);
        //wrentalFee = findViewById(R.id.RentalFee);
        productPic = findViewById(R.id.product_pic_input);
        imageSlideShow = findViewById(R.id.product_photos);
        price2 = findViewById(R.id.price2);
        condition = findViewById(R.id.condition_spinner);
        descripton = findViewById(R.id.product_description_input);
        productName = findViewById(R.id.product_name_input);
        progressBar = findViewById(R.id.progress_bar);

        backBtn = findViewById(R.id.back_btn);

        imageSlideShow.getLayoutParams().height = 0;
        imageSlideShow.setVisibility(View.GONE);
        imageSlideShow.requestLayout();

        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();


        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        rentalFee.setInputType(InputType.TYPE_CLASS_NUMBER);

                docRef = mFirestore.collection("products").document(String.valueOf(Home.userProductSelection));

        storageRef.child("images/" + String.valueOf(Home.userProductSelection)).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {


                Glide.with(EditProduct.this).load(uri).into(productPic);





            }
        });


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(EditProduct.this);
                builder.setCancelable(true);
                builder.setTitle("Delete Product");
                builder.setMessage("Are you sure you want to delete this product?");


                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();

                    }
                });

                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Client client = new Client("T4FYPXFRV9", "3e113a6bd81eb1930bc67296ce51835c");
                        Index index = client.getIndex("products");

                        index.deleteObjectAsync(String.valueOf(Home.userProductSelection), null);

                        docRef.delete();
                        storageRef.child("images/" + String.valueOf(Home.userProductSelection)).delete();
                        showMessage("Product Deleted");
                        startActivity(new Intent(EditProduct.this, Home.class));
                        overridePendingTransition(0,0);

                    }
                });

                builder.show();




            }
        });

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {





                DocumentSnapshot document = task.getResult();
                if(document.exists()){

//                    productPrimaryImage.setImageURI(Uri.parse(document.get("Primary Image").toString()));

                    productName.setText(document.get("Product Name").toString());

                    String compareValue = document.get("Product Condition").toString();
                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(EditProduct.this, R.array.Conditions, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    condition.setAdapter(adapter);
                    if (compareValue != null) {
                        int spinnerPosition = adapter.getPosition(compareValue);
                        condition.setSelection(spinnerPosition);
                    }

                    //condition.setText(document.get("Product Condition").toString());
                    
                    descripton.setText(document.get("Product Description").toString());
                    price2.setText("$" + document.get("Rental Fee").toString());
                    rentalFee.setText(document.get("Rental Fee").toString());



                    rentalFee.addTextChangedListener(new TextWatcher() {
                        String beforeLayer = rentalFee.getText().toString();

                        public void afterTextChanged(Editable s) {}

                        public void beforeTextChanged(CharSequence s, int start,
                                                      int count, int after) {
                        }

                        public void onTextChanged(CharSequence s, int start,
                                                  int before, int count) {

                            if(beforeLayer.equals(rentalFee.getText().toString())) {
                                layer.setVisibility(View.VISIBLE);

                            }

                            else if (rentalFee.getText().toString().isEmpty()) {
                                layer.setVisibility(View.VISIBLE);
                            }
                            else{
                                layer.setVisibility(View.GONE);
                            }
                        }
                    });

                    descripton.addTextChangedListener(new TextWatcher() {
                        String beforeLayer = descripton.getText().toString();

                        public void afterTextChanged(Editable s) {}

                        public void beforeTextChanged(CharSequence s, int start,
                                                      int count, int after) {
                        }

                        public void onTextChanged(CharSequence s, int start,
                                                  int before, int count) {

                            if(beforeLayer.equals(descripton.getText().toString())) {
                                layer.setVisibility(View.VISIBLE);

                            }

                            else if (descripton.getText().toString().isEmpty()) {
                                layer.setVisibility(View.VISIBLE);
                            }
                            else{
                                layer.setVisibility(View.GONE);
                            }
                        }
                    });

                    productName.addTextChangedListener(new TextWatcher() {
                        String beforeLayer = productName.getText().toString();

                        public void afterTextChanged(Editable s) {}

                        public void beforeTextChanged(CharSequence s, int start,
                                                      int count, int after) {
                        }

                        public void onTextChanged(CharSequence s, int start,
                                                  int before, int count) {

                            if(beforeLayer.equals(productName.getText().toString())) {
                                layer.setVisibility(View.VISIBLE);

                            }

                            else if (productName.getText().toString().isEmpty()) {
                                layer.setVisibility(View.VISIBLE);
                            }
                            else{
                                layer.setVisibility(View.GONE);
                            }
                        }
                    });

                    beforeLayerC = condition.getSelectedItem().toString();
                    condition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                            // your code here


                            if(beforeLayerC.equals(condition.getSelectedItem().toString())) {
                                layer.setVisibility(View.VISIBLE);

                            }
                            else{
                                layer.setVisibility(View.GONE);
                            }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parentView) {
                            // your code here
                        }

                    });










                }



            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                save.setVisibility(View.INVISIBLE);






                if(productName.getText().toString().isEmpty()){
                    showMessage("Please enter a product Name");
                    save.setVisibility(View.VISIBLE);
                }

                else if(descripton.getText().toString().isEmpty()){
                    showMessage("Please enter a description.");
                    save.setVisibility(View.VISIBLE);
                }
                else if(rentalFee.getText().toString().isEmpty()){
                    showMessage("Please enter a price.");
                    save.setVisibility(View.VISIBLE);
                }


                else {

                    save.setVisibility(View.INVISIBLE);
                    WriteBatch batch = mFirestore.batch();
                    WriteBatch batch1 = mFirestore.batch();
                    WriteBatch batch2 = mFirestore.batch();
                    WriteBatch batch3 = mFirestore.batch();

                    docRef = mFirestore.collection("products").document(String.valueOf(Home.userProductSelection));
                    batch.update(docRef, "Product Name", productName.getText().toString().trim());
                    batch1.update(docRef, "Rental Fee", rentalFee.getText().toString().trim());
                    batch2.update(docRef, "Product Description", descripton.getText().toString().trim());
                    batch3.update(docRef, "Product Condition", condition.getSelectedItem().toString());
                    batch.commit();
                    batch1.commit();
                    batch2.commit();
                    batch3.commit();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(EditProduct.this, RentProductMain.class));
                            overridePendingTransition(0,0);
                        }
                    }, 1000);


                    fileUploader();


                }






            }
        });






        rentalFee.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {   //Convert the Text to String
                String inputText = rentalFee.getText().toString();
                price2.setText("$" +inputText);

            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(EditProduct.this, RentProductMain.class));
                overridePendingTransition(0,0);
                RentProductMain.fromHome = false;

            }
        });




        changePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT >= 22){

                    checkAndRequestForPermission();
                    openGallery();
                    layer.setVisibility(View.GONE);


                }
                else{
                    openGallery();
                    layer.setVisibility(View.GONE);


                }
            }
        });



    }

    private void showMessage(String message){

        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    private void  checkAndRequestForPermission(){

        if(ContextCompat.checkSelfPermission(EditProduct.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){

            if(ActivityCompat.shouldShowRequestPermissionRationale(EditProduct.this, Manifest.permission.READ_EXTERNAL_STORAGE)){
                Toast.makeText(EditProduct.this, "Please accept for required permission.", Toast.LENGTH_SHORT).show();
            }
            else{
                ActivityCompat.requestPermissions(EditProduct.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PReqcode);

            }


        }
        else{
            openGallery();
        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        int totalItemsSelected;
        int currentImage;


       if(resultCode == RESULT_OK && requestCode == REQUESCODE && data.getData() != null){

            pickedImgUri = data.getData();
            productPic.setImageURI(pickedImgUri);



           final Handler handler = new Handler();
           handler.postDelayed(new Runnable() {
               @Override
               public void run() {
                   // Do something after 5s = 5000ms
                   productPic.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
                   productPic.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
               }
           }, 5000);






        }









    }

    private void fileUploader(){
        if(pickedImgUri != null) {
            storageRef = FirebaseStorage.getInstance().getReference().child("images");

            StorageReference Ref = storageRef.child(String.valueOf(Home.userProductSelection));

            Ref.putFile(pickedImgUri);

        }



    }



    private void openGallery(){
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");

        startActivityForResult(galleryIntent, REQUESCODE);




    }

}
