package com.tochukwuozurumba.contact;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SIngleContactDetails extends AppCompatActivity {

    private TextView contactName, contactEmail, contactPhone, contactNote;
    private ImageView contactImage;
    private Button editContactBtn, deleteContactBtn;
    private String name, phone, email, imageUrl, note;
    private int id;
    private int mContactId;
    private Contact mContact;
    private ActionBar actionBar;
    private ContactViewModel mContactViewModel;
    public static final String EXTRA_TASK_ID = "contactId";
    private static final int DEFAULT_CONTACT_ID = -1;

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
        mContactId = intent.getIntExtra(EXTRA_TASK_ID, DEFAULT_CONTACT_ID);

        contactName = findViewById(R.id.single_contact_name);
        contactPhone = findViewById(R.id.single_contact_phone);
        contactEmail = findViewById(R.id.single_contact_email);
        contactNote = findViewById(R.id.single_contact_note);
        contactImage = findViewById(R.id.single_contact_imageUrl);
        editContactBtn = findViewById(R.id.single_contact_edit);
        deleteContactBtn = findViewById(R.id.single_contact_delete);

        mContactViewModel = ViewModelProviders.of(this).get(ContactViewModel.class);
        mContactViewModel.getContact(mContactId).observe(this, new Observer<Contact>() {
            @Override
            public void onChanged(@Nullable Contact contact) {
//                when a new word is added to live data, this observes the data and make changes when a new word is added
                loadData(contact);
                mContact = contact;
            }
        });

        deleteContactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Delete the word
                mContactViewModel.deleteContact(mContact);
            }
        });

        editContactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddUpdateContact.class);
                intent.putExtra(SIngleContactDetails.EXTRA_TASK_ID, mContactId);
                v.getContext().startActivity(intent);
            }
        });
    }

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