package com.neuralgorithmic.rentathon.Product;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.algolia.search.saas.Client;
import com.algolia.search.saas.Index;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.neuralgorithmic.rentathon.Home.Home;
import com.neuralgorithmic.rentathon.R;
import com.neuralgorithmic.rentathon.Rent.RentProductMain;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductMain extends AppCompatActivity {

    ImageView productNameInfo, productValueInfo, conditionInfo, descriptionInfo, productImage, productImage1;
    Button homeNav, profileNav, chatNav, rentBtn, resetBtn;
    EditText productName, productValue, rentalFee, productDescription;
    String savedName;
    FirebaseFirestore mFirestore;
    boolean exists;
    Spinner productCondition;
    TextView productValue1, uploadMorePhotos;
    public String product, value, description, condition, fee;
    static int PReqcode = 1;
    static int REQUESCODE = 1;
     int productNum;
    SharedPreferences sp;
    private StorageTask uploadTask;
    int productsBeingRented;
    private static final int REQUEST_LOAD_IMAGE = 1;
    FirebaseUser mFirebaseUser;
    static Uri pickedImgUri;
    private DocumentReference docRef, docRef2;
    public Boolean picPicked = false;
    StorageReference storageRef;
    String userName;
    private List<String> fileNameList;
    Boolean multipleSelected = false;

    private FirebaseAuth mAuth;
    ArrayList<Uri> ImageList = new ArrayList<Uri>();
    private Uri imageUri;
    private int uploadCount = 0;
    GoogleSignInClient mGoogleSignInClient;
    String city = "";
    Boolean filledOut = true;
    public String currentUserID;
    public Boolean clicked;
    ImageView backBtn;
    int i;
    ScrollView scrollView;
    String currentPhotoPath;
    public double intelligentRating = 0;
    public double rating = 0;
    TextView previewBtn;
    static public String pricestatic, descriptionstatic, namestatic, conditionstatic, valuestatic;

    private final static String saveprice = "price_key";
    public static boolean fromPreview;
    ImageView popup;
    TextView selectPhoto, takePhoto;
    ImageView xButton;

    public final static int REQUEST_CAMERA = 1001;
    Button layer;


    //Intellegence
    public int distance = 5;
    public int numberOfViews = 0;
    public int numberOfUniqueViews = 1;

    public boolean descriptionLayer = false;
    public boolean nameLayer = false;
    public boolean rentalFeeLayer = false;
    public boolean productValueLayer = false;
    public boolean productNameLayer = false;
    public double longitudeRenter;
    public double latitudeRenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.owner_upload_product);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        ProductOverLord.fromOverLord = false;
        //sp = getSharedPreferences("app_name", Context.MODE_PRIVATE);
        //productNum = sp.getInt("productNum", 0);
        clicked = false;
        scrollView = findViewById(R.id.scrollView2);
        backBtn = findViewById(R.id.back_btn);

        scrollView.postDelayed(new Runnable() {
            @Override
            public void run() {
               scrollView.fullScroll(ScrollView.FOCUS_UP);
            }
        }, 200);

        storageRef = FirebaseStorage.getInstance().getReference().child("images");

        layer = findViewById(R.id.layer);

        popup = findViewById(R.id.camera_menu);
        xButton = findViewById(R.id.white_x);
        takePhoto = findViewById(R.id.take_picture);
        selectPhoto = findViewById(R.id.choose_photo);
        //info buttons
        previewBtn = findViewById(R.id.preview_button);
        productNameInfo = findViewById(R.id.product_name_info_button);
        productValueInfo = findViewById(R.id.product_value_and_fee_info_button);
        conditionInfo = findViewById(R.id.condition_info_button);
        descriptionInfo = findViewById(R.id.product_description_info_button);

        rentBtn = findViewById(R.id.save);
        mFirestore = FirebaseFirestore.getInstance();



        uploadMorePhotos = findViewById(R.id.textView);

        productName = findViewById(R.id.product_name_input);
        productValue = findViewById(R.id.product_relative_price_input);
        rentalFee = findViewById(R.id.product_rent_price_input);
        productDescription = findViewById(R.id.product_description_input);
        productCondition = findViewById(R.id.condition_spinner);
        productImage = findViewById(R.id.product_pic_input);
        productDescription.setGravity(Gravity.LEFT);

        productValue.setInputType(InputType.TYPE_CLASS_NUMBER);
        rentalFee.setInputType(InputType.TYPE_CLASS_NUMBER);

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions
                .Builder()
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        mAuth = FirebaseAuth.getInstance();


        if(fromPreview){
            picPicked = true;
            rentalFee.setText(pricestatic);
            productValue.setText(valuestatic);
            productDescription.setText(descriptionstatic);
            productImage.setImageURI(pickedImgUri);
            productName.setText(namestatic);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(ProductMain.this, R.array.Conditions, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            productCondition.setAdapter(adapter);
            if (conditionstatic != null) {
                int spinnerPosition = adapter.getPosition(conditionstatic);
                productCondition.setSelection(spinnerPosition);
            }
            fromPreview = false;
        }


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Home.fromHome){

                    startActivity(new Intent(ProductMain.this, Home.class));

                }
                else {
                    startActivity(new Intent(ProductMain.this, ProductOverLord.class));
                    overridePendingTransition(0, 0);
                }
            }
        });

        /* GET USER LOCATION */
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);


        }
        else {
            ActivityCompat.requestPermissions(this, new String[] {
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION },
                    200);

        }





        productNameInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showMessage("Enter the name and model of the product that you are renting and be as specific as possible. ");

            }
        });


        productValueInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMessage("Product Value: Enter the estimated value of the product if you were to sell it. Rental Fee: Enter the fee per day for your product.");
            }
        });

        descriptionInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMessage("Product Description: 50 Word Min, 200 Word Max Description of the product's specifications and features.");
            }
        });

        conditionInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showMessage("Select an option that best fits the condition of the product.");

            }
        });







        previewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                product = productName.getText().toString();
                value = productValue.getText().toString();
                fee = rentalFee.getText().toString();
                description = productDescription.getText().toString();
                condition = productCondition.getSelectedItem().toString();

                if(!picPicked){
                    showMessage("You must upload a picture to preview the product.");
                }
                else if(fee.isEmpty()){
                    namestatic = product;
                    conditionstatic = condition;
                    descriptionstatic = description;
                    valuestatic = value;


                    startActivity(new Intent(ProductMain.this, PreviewProduct.class));
                    overridePendingTransition(0, 0);
                }
                else{
                    namestatic = product;
                    pricestatic = fee;
                    conditionstatic = condition;
                    descriptionstatic = description;
                    valuestatic = value;


                    startActivity(new Intent(ProductMain.this, PreviewProduct.class));
                    overridePendingTransition(0, 0);
                }



            }
        });





        productDescription.addTextChangedListener(new TextWatcher() {
            String description = productDescription.getText().toString();

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

                descriptionLayer = true;

                if(descriptionLayer && rentalFeeLayer && productValueLayer && productNameLayer){
                    layer.setVisibility(View.INVISIBLE);
                }
            }
        });

        productName.addTextChangedListener(new TextWatcher() {
            String product = productName.getText().toString();

            public void afterTextChanged(Editable s) {

            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

                productNameLayer = true;
                if(descriptionLayer && rentalFeeLayer && productValueLayer && productNameLayer){
                    layer.setVisibility(View.INVISIBLE);
                }

            }
        });

        rentalFee.addTextChangedListener(new TextWatcher() {
            String product = productName.getText().toString();

            public void afterTextChanged(Editable s) {

            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

                rentalFeeLayer = true;
                if(descriptionLayer && rentalFeeLayer && productValueLayer && productNameLayer){
                    layer.setVisibility(View.INVISIBLE);
                }

            }
        });

        productValue.addTextChangedListener(new TextWatcher() {
            String product = productName.getText().toString();

            public void afterTextChanged(Editable s) {

            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

                productValueLayer = true;
                if(descriptionLayer && rentalFeeLayer && productValueLayer && productNameLayer){
                    layer.setVisibility(View.GONE);
                }

            }
        });






        rentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFirebaseUser = mAuth.getCurrentUser();
                if(mFirebaseUser != null) {
                    currentUserID = mFirebaseUser.getUid(); //Do what you need to do with the id
                }

                product = productName.getText().toString();
                value = productValue.getText().toString();
                fee = rentalFee.getText().toString();
                description = productDescription.getText().toString();
                condition = productCondition.getSelectedItem().toString();
                docRef2 = mFirestore.collection("users").document(currentUserID);
                docRef2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            //picID = Uri.parse(document.get("Profile Picture").toString());
                            //profliePic.setImageURI(picID);
                            rating = Double.parseDouble(document.get("RAverage").toString());

                            mAuth.getCurrentUser().reload();

                            if(product.isEmpty()){

                                showMessage("Please enter a product name.");
                            }
                            else if(product.length() < 4){

                                showMessage("Please enter a more descriptive product name.");
                            }
                            else if(value.isEmpty()){
                                showMessage("Please enter the product's value.");
                            }
                            else if(Integer.parseInt(value) > 50000){
                                showMessage("This product is too expensive to be listed on Rentathon. Please review the terms and service to see what the product value limit is.");

                            }
                            else if(fee.isEmpty()){
                                showMessage("Please enter a rental fee per day.");
                            }
                            else if(description.isEmpty()){
                                showMessage("Please enter a product description");
                            }
                            else if(description.length() < 50 && description.length() > 0){
                                showMessage("Please enter a more descriptive description.");
                            }
                            else if(description.length() > 500){
                                showMessage("The description is too long.");
                            }
                            else if(!picPicked){
                                showMessage("Please input a photo of the product.");
                            }


                            else {

                                rentBtn.setVisibility(View.INVISIBLE);
                                createProduct();



                                showMessage("Product Uploaded!");

                            }




                        }
                    }
                });


            }
        });



        productImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        if(Build.VERSION.SDK_INT >= 22){

                            checkAndRequestForPermission();


                        }
                        else{



                        }





            }
        });


        uploadMorePhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(clicked) {

                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_LOAD_IMAGE);
                    multipleSelected = true;
                }
                else{
                    showMessage("Please upload the primary image first!");
                }

            }
        });




















    }

    private void showMessage(String message){

        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    private void createProduct(){


        docRef = mFirestore.collection("users").document(mAuth.getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    city = document.get("City").toString();
                    //showMessage("City Uploaded");
                    int current = Integer.parseInt(document.get("NProducts").toString());

                    current++;
                    WriteBatch batch = mFirestore.batch();
                    docRef = mFirestore.collection("users").document(String.valueOf(mAuth.getUid()));
                    batch.update(docRef, "NProducts", current);
                    batch.commit();


                }

            }
        });

        //sp.edit().putInt("productNum", productNum).commit();

        docRef = mFirestore.collection("rentNum").document("rentNum");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                productNum = Integer.parseInt(document.get("num").toString());

                productNum++;
                //condition = productCondition.get

                Map<String, Object> rentalMap = new HashMap<>();


                CollectionReference products = mFirestore.collection("products");

                String randomCode = String.valueOf(generateProductCode());

                rentalMap.put("Product Name", product);
                rentalMap.put("Product Value", Double.valueOf(value));
                rentalMap.put("User Name", mAuth.getCurrentUser().getDisplayName());
                rentalMap.put("Rental Fee", Integer.parseInt(fee));

                rentalMap.put("Product Description", description);
                rentalMap.put("Product Condition", condition);
                rentalMap.put("User Products Number", productsBeingRented);
                rentalMap.put("User City", city);
                rentalMap.put("User ID", mAuth.getUid());
                //rentalMap.put("Product Code", randomCode );
                rentalMap.put("Views", 0);
                rentalMap.put("Unique Visitors", 0);
                rentalMap.put("Product Num", productNum);
                rentalMap.put("ProductLong", longitudeRenter);
                rentalMap.put("ProductLang", latitudeRenter);
                rentalMap.put("Product Status", "Live On Market");


                intelligentRating = ((((double) numberOfViews / numberOfUniqueViews) * 250) + (numberOfUniqueViews * 10) + (rating * 110) + ((35 - Double.parseDouble(fee)) * 6)) - ((distance - 10) * 100);
                //NEW RATING EQUATION intelligentRating = Math.pow(rating, 1.5) * ((((double) numberOfViews / numberOfUniqueViews) * 250) + (numberOfUniqueViews * 10) + (rating * 110) - (Double.parseDouble(fee) * 6)) - (Math.pow(distance, 1.15) * 70);

                rentalMap.put("Intelligent Rating", intelligentRating);




                Client client = new Client("T4FYPXFRV9", "3e113a6bd81eb1930bc67296ce51835c");
                Index index = client.getIndex("products");

                List<JSONObject> array = new ArrayList<>();


                try {
                    array.add(
                            new JSONObject().put("objectID", productNum).put("Product Name", product).put("Product Num", productNum)
                    );
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }

                index.addObjectsAsync(new JSONArray(array), null);


                products.document(String.valueOf(productNum)).set(rentalMap);






                WriteBatch batch = mFirestore.batch();
                docRef = mFirestore.collection("rentNum").document("rentNum");
                batch.update(docRef, "num", String.valueOf(productNum));
                batch.commit();
                fileUploader(productNum);

                for(uploadCount = 0; uploadCount < ImageList.size(); uploadCount++){


                    Uri InduvidualImage = ImageList.get(uploadCount);
                    StorageReference ImageName = storageRef.child(String.valueOf(productNum) + "_" + uploadCount);

                    ImageName.putFile(InduvidualImage);

                }
            }
        });
            new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Home.userProductSelection = productNum;
                Home.originalNum = productNum;
                startActivity(new Intent(ProductMain.this, RentProductMain.class));
            }
        }, 1750);





















       // rentalMap.get("User Products Number");


        //mFirestore.collection("products").add(rentalMap);













    }

    public long generateProductCode()
    {
            return (long)(Math.random()*1000000000 );

    }

    private void  checkAndRequestForPermission(){

        if(ContextCompat.checkSelfPermission(ProductMain.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){

            if(ActivityCompat.shouldShowRequestPermissionRationale(ProductMain.this, Manifest.permission.READ_EXTERNAL_STORAGE)){
                Toast.makeText(ProductMain.this, "Please accept for required permission.", Toast.LENGTH_SHORT).show();
            }
            else{
                ActivityCompat.requestPermissions(ProductMain.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PReqcode);

            }


        }
        else{


            AlertDialog.Builder builder2 = new AlertDialog.Builder(ProductMain.this);
            builder2.setCancelable(true);
            builder2.setTitle("Upload Picture");
            builder2.setIcon(R.drawable.camera_icon);
            builder2.setMessage("Product Picture Guidelines: \n" + "1. High Definition. We support any size pictures. \n" + "2. Real representation of the product at hand. Stock images will produce lower results.\n" + "3. To qualify for our Lost Product Guarantee, the image must be time stamped. \n\n" + "Violations of these guidelines may result in a lower product score."  );



            builder2.setNegativeButton("Take a Picture", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUESCODE);


                }
            });
            builder2.setPositiveButton("Choose From Files", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    openGallery();


                }
            });

            builder2.show();
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        int totalItemsSelected;
        int currentImage;


        if(resultCode == RESULT_OK && requestCode == REQUESCODE && data.getData() != null && multipleSelected){
            showMessage("No images were selected. At least 2 images need to be selected. Hold down on any image to upload multiple.");
        }

        else if(resultCode == RESULT_OK && requestCode == REQUESCODE && data.getData() != null){

            pickedImgUri = data.getData();
            productImage.setImageURI(pickedImgUri);
            picPicked = true;


        }

        else if(resultCode == RESULT_OK && requestCode == REQUESCODE && data.getClipData() != null) {

            totalItemsSelected = data.getClipData().getItemCount();

            if(totalItemsSelected >= 4){
                showMessage("Maximum images is 3!");
            }
            else{

                totalItemsSelected = data.getClipData().getItemCount();

                currentImage = 0;
                while(currentImage < totalItemsSelected){

                    imageUri = data.getClipData().getItemAt(currentImage).getUri();
                    ImageList.add(imageUri);

                    currentImage = currentImage + 1;



                }
                showMessage("Selected " + ImageList.size() + " Images");

            }
        }






    }




    private void openGallery(){
        clicked = true;
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");

        startActivityForResult(galleryIntent, REQUESCODE);




    }

    private String getExtension(Uri uri){

        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));


    }

    private void fileUploader(int num){
        if(pickedImgUri != null) {

            StorageReference Ref = storageRef.child(String.valueOf(num));

            Ref.putFile(pickedImgUri);

        }



    }
    @TargetApi(Build.VERSION_CODES.M)

    public String getFileName(Uri uri){
        String result = null;
        if(uri.getScheme().equals("content")){
            Cursor cursor = getContentResolver().query(uri, null, null, null);
            try{
                if(cursor !=null && cursor.moveToFirst()){
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            }finally {
                cursor.close();
            }

            if(result == null){
                result = uri.getPath();
                int cut = result.lastIndexOf('/');
                if(cut != -1){
                    result = result.substring(cut+1);
                }
            }
        }
        return  result;

    }








}
