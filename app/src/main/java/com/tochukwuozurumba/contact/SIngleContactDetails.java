package com.tochukwuozurumba.contact;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class SIngleContactDetails extends AppCompatActivity {

    private TextView contactName, contactEmail, contactPhone, contactNote;
    private ImageView contactImage;
    private Button editContactBtn, deleteContactBtn;
    private String name, phone, email, imageUrl, note;
    private int id;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_contact_details);

        actionBar = getSupportActionBar();
        actionBar.setTitle("Contact Details");
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        SharedPreferenceManager.init(SIngleContactDetails.this);

        contactName = findViewById(R.id.single_contact_name);
        contactPhone = findViewById(R.id.single_contact_phone);
        contactEmail = findViewById(R.id.single_contact_email);
        contactNote = findViewById(R.id.single_contact_note);
        contactImage = findViewById(R.id.single_contact_imageUrl);
        editContactBtn = findViewById(R.id.single_contact_edit);
        deleteContactBtn = findViewById(R.id.single_contact_delete);


    }

    protected void onResume(){
        super.onResume();
        loadData();
    }

    protected void loadData() {
        name = SharedPreferenceManager.getString("contact_name", "");
        phone = SharedPreferenceManager.getString("contact_phone", "");
        email = SharedPreferenceManager.getString("contact_email", "");
        note = SharedPreferenceManager.getString("contact_note", "");
        imageUrl = SharedPreferenceManager.getString("contact_imageUrl", "");
        id = SharedPreferenceManager.getInt("contact_id", 0);

        contactName.setText(name);
        contactPhone.setText(phone);
        contactEmail.setText(email);
        contactNote.setText(note);

//        if (!(imageUrl == "")) {
//            contactImage.setImageResource(Integer.parseInt(imageUrl));
//        }
    }
}