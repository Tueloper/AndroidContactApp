package com.tochukwuozurumba.contact;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

public class AddUpdateContact extends AppCompatActivity {

//    define contact variables
    private ImageView profileImageView;
    private Button submitButton;
    private EditText personName, personPhone, personEmail, personNote;
    private String name, email, phone, note, imageUrl;
    private ActionBar actionBar;
    public static final String CONTACT_ID_STRING = "contactId";
    public static final String EXTRA_TASK_ID = "extraTaskId";
    public static final String INSTANCE_TASK_ID = "instanceTaskId";
    private static final int DEFAULT_TASK_ID = -1;
    private static final String TAG = AddUpdateContact.class.getSimpleName();
    private int mContactId = DEFAULT_TASK_ID;
    private ContactRoomDatabase mDb;
    private ContactViewModel mContactViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_update_contact);

        SharedPreferenceManager.init(AddUpdateContact.this);

//        init actionBar
        actionBar = getSupportActionBar();
        actionBar.setTitle("Add Contact");
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

//        back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

//        link variables
        profileImageView = findViewById(R.id.profileImageView);
        personName = findViewById(R.id.personName);
        personPhone = findViewById(R.id.personPhone);
        personEmail = findViewById(R.id.personEmail);
        personNote = findViewById(R.id.personNote);
        submitButton = findViewById(R.id.submit_contact);

        mDb = ContactRoomDatabase.getDatabase(getApplicationContext());

        if (savedInstanceState != null && savedInstanceState.containsKey(INSTANCE_TASK_ID)) {
            mContactId = savedInstanceState.getInt(INSTANCE_TASK_ID, DEFAULT_TASK_ID);
        }

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_TASK_ID)) {
            submitButton.setText(R.string.update_button);
            if (mContactId == DEFAULT_TASK_ID) {
                mContactId = intent.getIntExtra(EXTRA_TASK_ID, DEFAULT_TASK_ID);
                mContactViewModel = ViewModelProviders.of(this).get(ContactViewModel.class);
                Contact contactDetails = mContactViewModel.getContact(mContactId);
                populateUI(contactDetails);
            }
        }

//        add submit listener
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveContact();
            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(INSTANCE_TASK_ID, mContactId);
        super.onSaveInstanceState(outState);
    }

    public void populateUI(Contact contact) {
        profileImageView.setImageURI(Uri.parse(contact.getImageUrl()));
        personNote.setText(contact.getNote());
        personName.setText(contact.getName());
        personEmail.setText(contact.getEmail());
        personPhone.setText(contact.getPhone());
    }

    private void saveContact() {
        name = personName.getText().toString();
        email = personEmail.getText().toString();
        phone = personPhone.getText().toString();
        note = personNote.getText().toString();
        imageUrl = personNote.getText().toString();

        Intent replyIntent = new Intent();

//        validate inputs
        if (name.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please input your Name..", Toast.LENGTH_SHORT).show();
            setResult(RESULT_CANCELED, replyIntent);
        } else if (phone.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please input your Phone..", Toast.LENGTH_SHORT).show();
            setResult(RESULT_CANCELED, replyIntent);
        } else if (email.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please input your Email..", Toast.LENGTH_SHORT).show();
            setResult(RESULT_CANCELED, replyIntent);
        } else if (note.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please input your Note..", Toast.LENGTH_SHORT).show();
            setResult(RESULT_CANCELED, replyIntent);
        } else {
//            submit your action
            SharedPreferenceManager.setString("contact_name", name);
            SharedPreferenceManager.setString("contact_phone", phone);
            SharedPreferenceManager.setString("contact_email", email);
            SharedPreferenceManager.setString("contact_note", note);
            SharedPreferenceManager.setString("contact_imageUrl", imageUrl);

            setResult(RESULT_OK, replyIntent);
            finish();
        }
    }

//    back button function
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}