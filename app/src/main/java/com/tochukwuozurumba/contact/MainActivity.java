package com.tochukwuozurumba.contact;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.tochukwuozurumba.contact.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

//    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private ContactViewModel mContactViewModel;
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferenceManager.init(MainActivity.this);

        setSupportActionBar(binding.toolbar);

//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final ContactListAdapter adapter = new ContactListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                move to add or update contact page
                Intent intent = new Intent(MainActivity.this, AddUpdateContact.class);
                startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
            }
        });

        //        view model access
//        the provider act as an outside state to store data and access the db
        mContactViewModel = ViewModelProviders.of(this).get(ContactViewModel.class);
        mContactViewModel.getmAllContacts().observe(this, new Observer<List<Contact>>() {
            @Override
            public void onChanged(@Nullable List<Contact> contacts) {
//                when a new word is added to live data, this observes the data and make changes when a new word is added
                adapter.setContacts(contacts);
            }
        });

        ItemTouchHelper helper = new ItemTouchHelper( new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = viewHolder.getAdapterPosition();
                        Contact myContact = adapter.getWordAtPosition(position);
                        Toast.makeText(MainActivity.this, "Deleting " + myContact.getName(), Toast.LENGTH_LONG).show();

                        // Delete the word
                        mContactViewModel.deleteContact(myContact);
                    }
                });

        helper.attachToRecyclerView(recyclerView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.clear_data) {
            // Add a toast just for confirmation
            Toast.makeText(this, "Clearing the contacts...",
                    Toast.LENGTH_SHORT).show();

            // Delete the existing data
            mContactViewModel.deleteAll();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {

            try {
                String name = SharedPreferenceManager.getString("contact_name", "");
                String phone = SharedPreferenceManager.getString("contact_phone", "");
                String email = SharedPreferenceManager.getString("contact_email", "");
                String note = SharedPreferenceManager.getString("contact_note", "");
                String imageUrl = SharedPreferenceManager.getString("contact_imageUrl", "");
                int contactId = SharedPreferenceManager.getInt("contact_id", 0);
                int defaultContactId = SharedPreferenceManager.getInt("default_contact_id", 0);

                Contact contact = new Contact(name, phone, email, note, imageUrl);

                if (contactId == defaultContactId) {
                    mContactViewModel.insert(contact);
                } else {
                    //update task
                    Log.d("onActivityResult", String.valueOf(contactId));
                    contact.setId(contactId);
                    mContactViewModel.updateContact(contact);
                }


            } catch (Exception ex) {
                Log.d("unique", ex.toString());
            }

        }

    }



}