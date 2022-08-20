package com.tochukwuozurumba.contact;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactViewHolder> {

    private final LayoutInflater mInflater;
    private List<Contact> mContacts; // Cached copy of words
    String name, phone, email, note, imageUrl;
    int id;
    private Context mContext;

    ContactListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public ContactListAdapter.ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ContactListAdapter.ContactViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ContactListAdapter.ContactViewHolder holder, int position) {
        if (mContacts != null) {
            Contact current = mContacts.get(position);
            id = current.getId();
            imageUrl = current.getImageUrl();
            name = current.getName();
            phone = current.getPhone();
            note = current.getNote();

            if (imageUrl.equals("")) {
                holder.contactImageItemView.setImageResource(R.drawable.ic_baseline_person_24dp);
            }
            holder.contactNameItemView.setText(name);
            holder.contactNumberItemView.setText(phone);
        } else {
            // Covers the case of data not being ready yet.
            holder.contactNameItemView.setText("No Word");
        }

        holder.contactDialItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SIngleContactDetails.class);

                SharedPreferenceManager.setString("contact_name", name);
                SharedPreferenceManager.setString("contact_phone", phone);
                SharedPreferenceManager.setString("contact_email", email);
                SharedPreferenceManager.setString("contact_note", note);
                SharedPreferenceManager.setString("contact_imageUrl", imageUrl);
                SharedPreferenceManager.setInt("contact_id", id);

                mContext.startActivity(intent);
            }
        });
    }

    void setWords(List<Contact> contacts){
        mContacts = contacts;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mContacts != null)
            return mContacts.size();
        else return 0;
    }

    class ContactViewHolder extends RecyclerView.ViewHolder {
        private final TextView contactNameItemView;
        private final TextView contactNumberItemView;
        private final ImageView contactImageItemView;
        private final ImageView contactDialItemView;

        private ContactViewHolder(View itemView) {
            super(itemView);
            contactNameItemView = itemView.findViewById(R.id.textView);
            contactNumberItemView = itemView.findViewById(R.id.contact_number);
            contactImageItemView = itemView.findViewById(R.id.contact_image);
            contactDialItemView = itemView.findViewById(R.id.dial_contact);
        }
    }
}
