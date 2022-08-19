package com.tochukwuozurumba.contact;

import android.content.Context;
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

    ContactListAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @Override
    public ContactListAdapter.ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ContactListAdapter.ContactViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ContactListAdapter.ContactViewHolder holder, int position) {
        if (mContacts != null) {
            Contact current = mContacts.get(position);
            int id = current.getId();
            String imageUrl = current.getImageUrl();

            if (imageUrl.equals("")) {
                holder.contactImageItemView.setImageResource(R.drawable.ic_baseline_person_24dp);
            }
            holder.contactNameItemView.setText(current.getName());
            holder.contactNumberItemView.setText(current.getPhone());
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
        private final TextView contactDialItemView;

        private ContactViewHolder(View itemView) {
            super(itemView);
            contactNameItemView = itemView.findViewById(R.id.textView);
            contactNumberItemView = itemView.findViewById(R.id.contact_number);
            contactImageItemView = itemView.findViewById(R.id.contact_image);
            contactDialItemView = itemView.findViewById(R.id.dial_contact);
        }
    }
}
