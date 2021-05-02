package com.neuralgorithmic.rentathon.Profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;
import com.neuralgorithmic.rentathon.R;
import com.neuralgorithmic.rentathon.Rent.RentProductMain;

public class EditProfile extends AppCompatActivity {
    Button homeNav, rentNav, chatNav, save;
    EditText nameText, emailText, addressText, cityText, stateText;
    private DocumentReference docRef;
    private FirebaseAuth mAuth;
    FirebaseFirestore mFirestore;
    ImageView picture, backBtn;
    SeekBar seekBar;
    TextView seekBarText;
    public int miles;
    Spinner monday1, tuesday1, wednesday1, thursday1, friday1, saturday1,monday2, tuesday2, wednesday2, thursday2, friday2, saturday2, sunday1, sunday2;

    Boolean filledOut = false;
    TextView sunday, monday, tuesday, wednesday, thursday, friday, saturday;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        RentProductMain.fromHome = false;



        backBtn = findViewById(R.id.back_btn);

        nameText = findViewById(R.id.full_name_input);
        emailText = findViewById(R.id.email_address_input);
        addressText = findViewById(R.id.street_address_input);
        cityText = findViewById(R.id.city_input);
        stateText = findViewById(R.id.state_input);
        save = findViewById(R.id.save_btn);
        seekBarText = findViewById(R.id.proximity_seekbar_txt_output);
        seekBar = findViewById(R.id.proximity_seekbar);
        seekBar.setMax(20);

        sunday1 = findViewById(R.id.meet_spinner1);
        sunday2 = findViewById(R.id.meet_spinner2);
        monday1 = findViewById(R.id.meet_spinner3);
        monday2 = findViewById(R.id.meet_spinner4);
        tuesday1 = findViewById(R.id.meet_spinner5);
        tuesday2 = findViewById(R.id.meet_spinner6);
        wednesday1 = findViewById(R.id.meet_spinner7);
        wednesday2 = findViewById(R.id.meet_spinner8);
        thursday1 = findViewById(R.id.meet_spinner9);
        thursday2 = findViewById(R.id.meet_spinner10);
        friday1 = findViewById(R.id.meet_spinner11);
        friday2 = findViewById(R.id.meet_spinner12);
        saturday1 = findViewById(R.id.meet_spinner13);
        saturday2 = findViewById(R.id.meet_spinner14);





        seekBarText.setText(seekBar.getProgress() + " Miles");

        docRef = mFirestore.collection("users").document(mAuth.getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    //picID = Uri.parse(document.get("Profile Picture").toString());
                    //profliePic.setImageURI(picID);
                    String seekprog;
                    nameText.setText(document.get("Name").toString());
                    emailText.setText(document.get("Email").toString());
                    addressText.setText(document.get("Address").toString());
                    cityText.setText(document.get("City").toString());
                    stateText.setText(document.get("State").toString());
                    seekBarText.setText(document.get("Preffered Travel Distance(m)").toString());
                    seekprog = document.get("Preffered Travel Distance(m)").toString();

                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(EditProfile.this, R.array.Available_Times_2, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sunday1.setAdapter(adapter);
                    String sundayText1 = (document.get("Sunday").toString().substring(0, document.get("Sunday").toString().indexOf("")));
                    if (sundayText1 != null) {
                        int spinnerPosition = adapter.getPosition(sundayText1);
                        sunday1.setSelection(spinnerPosition);
                    }
                    else{
                        showMessage("fail");
                    }

                    //sunday1.setSelection(((ArrayAdapter<String>)sunday1.getAdapter()).getPosition(document.get("Sunday").toString().substring(0, document.get("Sunday").toString().indexOf("M"))));

                    seekBar.setProgress(Integer.parseInt(seekprog));
                    seekBar.post(new Runnable() {
                        @Override
                        public void run() {
                            seekBar.setProgress(Integer.parseInt(seekprog));
                        }
                    });



                }
            }
        });


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressValue;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                progressValue = progress;
                seekBarText.setText(progress + " Miles");

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                seekBarText.setText(progressValue + " Miles");

            }
        });





                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        miles = seekBar.getProgress();

                        WriteBatch batch = mFirestore.batch();
                        WriteBatch batch1 = mFirestore.batch();
                        WriteBatch batch2 = mFirestore.batch();
                        WriteBatch batch3 = mFirestore.batch();
                        WriteBatch batch4 = mFirestore.batch();
                        WriteBatch batch5 = mFirestore.batch();
                        WriteBatch batch6 = mFirestore.batch();
                        WriteBatch batch7 = mFirestore.batch();
                        WriteBatch batch8 = mFirestore.batch();
                        WriteBatch batch9 = mFirestore.batch();
                        WriteBatch batch10 = mFirestore.batch();
                        WriteBatch batch11 = mFirestore.batch();
                        WriteBatch batch12 = mFirestore.batch();
                        WriteBatch batch13 = mFirestore.batch();



                        docRef = mFirestore.collection("users").document(mAuth.getUid());
                        batch.update(docRef, "Name", nameText.getText().toString());
                        batch1.update(docRef, "Email", emailText.getText().toString());
                        batch2.update(docRef, "City", cityText.getText().toString());
                        batch3.update(docRef, "State", stateText.getText().toString());
                        batch4.update(docRef,"Address", addressText.getText().toString());
                        batch5.update(docRef, "Preffered Travel Distance(m)", String.valueOf(miles));



                        if(sunday1.getSelectedItem().toString().equals("Unavailable") || sunday2.getSelectedItem().toString().equals("Unavailable")) {
                            batch6.update(docRef, "Sunday", "Unavailable");
                        }
                        else{
                            batch6.update(docRef, "Sunday", sunday1.getSelectedItem().toString() + " - " + sunday2.getSelectedItem().toString());
                            filledOut = true;
                        }

                        if(monday1.getSelectedItem().toString().equals("Unavailable") || monday2.getSelectedItem().toString().equals("Unavailable")) {
                            batch7.update(docRef, "Monday", "Unavailable");
                        }
                        else{
                            batch7.update(docRef, "Monday", monday1.getSelectedItem().toString() + " - " + monday2.getSelectedItem().toString());
                            filledOut = true;
                        }

                        if(tuesday1.getSelectedItem().toString().equals("Unavailable") || tuesday2.getSelectedItem().toString().equals("Unavailable")) {
                            batch8.update(docRef, "Tuesday", "Unavailable");
                        }
                        else{
                            batch8.update(docRef, "Tuesday", tuesday1.getSelectedItem().toString() + " - " + tuesday2.getSelectedItem().toString());
                            filledOut = true;
                        }

                        if(wednesday1.getSelectedItem().toString().equals("Unavailable") || wednesday2.getSelectedItem().toString().equals("Unavailable")) {
                            batch9.update(docRef, "Wednesday", "Unavailable");
                        }
                        else{
                            batch9.update(docRef, "Wednesday", wednesday1.getSelectedItem().toString() + " - " + wednesday2.getSelectedItem().toString());
                            filledOut = true;
                        }

                        if(thursday1.getSelectedItem().toString().equals("Unavailable") || thursday2.getSelectedItem().toString().equals("Unavailable")) {
                            batch10.update(docRef, "Thursday", "Unavailable");
                        }
                        else{
                            batch10.update(docRef, "Thursday", thursday1.getSelectedItem().toString() + " - " + thursday2.getSelectedItem().toString());
                            filledOut = true;
                        }

                        if(friday1.getSelectedItem().toString().equals("Unavailable") || friday2.getSelectedItem().toString().equals("Unavailable")) {
                            batch11.update(docRef, "Friday", "Unavailable");
                        }
                        else{
                            batch11.update(docRef, "Friday", friday1.getSelectedItem().toString() + " - " + friday2.getSelectedItem().toString());
                            filledOut = true;
                        }

                        if(saturday1.getSelectedItem().toString().equals("Unavailable") || saturday2.getSelectedItem().toString().equals("Unavailable")) {
                            batch12.update(docRef, "Saturday", "Unavailable");
                        }
                        else{
                            batch12.update(docRef, "Saturday", saturday1.getSelectedItem().toString() + " - " + saturday2.getSelectedItem().toString());
                            filledOut = true;
                        }
                        batch13.update(docRef, "Filled Out", String.valueOf(filledOut));




                        batch.commit();
                        batch1.commit();
                        batch2.commit();
                        batch3.commit();
                        batch4.commit();
                        batch5.commit();
                        batch6.commit();
                        batch7.commit();
                        batch8.commit();
                        batch9.commit();
                        batch10.commit();
                        batch11.commit();
                        batch12.commit();
                        batch13.commit();


                        FirebaseUser user = mAuth.getCurrentUser();

                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(nameText.getText().toString()).build();

                        user.updateProfile(profileUpdates);

                        user.updateEmail(emailText.getText().toString())
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (!task.isSuccessful()) {
                                            showMessage(task.getException().getMessage());
                                        }
                                    }
                                });


                        startActivity(new Intent(EditProfile.this, ProfileMain.class));
                        showMessage("Profile Changed");




                    }
                });

















        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(EditProfile.this,ProfileMain.class));
                overridePendingTransition(0,0);

            }
        });

    }
    private void showMessage(String message){

        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }
}
