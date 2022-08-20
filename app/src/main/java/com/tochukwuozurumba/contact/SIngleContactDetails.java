package com.tochukwuozurumba.contact;

import static com.tochukwuozurumba.contact.AddUpdateContact.CONTACT_ID_STRING;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
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
    private int mContactId;
    private ActionBar actionBar;
    private ContactViewModel mContactViewModel;
    public static final String EXTRA_TASK_ID = "contactId";
    private static final int DEFAULT_TASK_ID = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_contact_details);

        actionBar = getSupportActionBar();
        actionBar.setTitle("Contact Details");
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        mContactId = intent.getIntExtra(EXTRA_TASK_ID, DEFAULT_TASK_ID);
        Log.d("TAG", String.valueOf(mContactId));

        contactName = findViewById(R.id.single_contact_name);
        contactPhone = findViewById(R.id.single_contact_phone);
        contactEmail = findViewById(R.id.single_contact_email);
        contactNote = findViewById(R.id.single_contact_note);
        contactImage = findViewById(R.id.single_contact_imageUrl);
        editContactBtn = findViewById(R.id.single_contact_edit);
        deleteContactBtn = findViewById(R.id.single_contact_delete);

        mContactViewModel = ViewModelProviders.of(this).get(ContactViewModel.class);
        Contact contactDetails = mContactViewModel.getContact(mContactId);
        loadData(contactDetails);
    }

//    protected void onResume(){
//        super.onResume();
//        loadData();
//    }

    protected void loadData(Contact contactDetails) {

        contactName.setText(contactDetails.getName());
        contactPhone.setText(contactDetails.getPhone());
        contactEmail.setText(contactDetails.getEmail());
        contactNote.setText(contactDetails.getNote());

//        if (!(imageUrl == "")) {
//            contactImage.setImageURI(Uri.parse(imageUrl));
//        } else {
//            contactImage.setImageResource(R.drawable.ic_baseline_person_24dp);
//        }
    }
}