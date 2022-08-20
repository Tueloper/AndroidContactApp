package com.tochukwuozurumba.contact;

import static com.tochukwuozurumba.contact.AddUpdateContact.CONTACT_ID_STRING;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
    final private ItemClickListener mItemClickListener;

    ContactListAdapter(Context context, ItemClickListener listener) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
        mItemClickListener = listener;
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

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Contact current = mContacts.get(holder.getAdapterPosition());
//                Intent intent = new Intent(mContext, SIngleContactDetails.class);
//                intent.putExtra("contact_id", current.getId());
//                Log.d("TAG", String.valueOf(current.getId()));
//                mContext.startActivity(intent);
//            }
//        });
    }

    void setContacts(List<Contact> contacts){
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

    public interface ItemClickListener {
        void onItemClickListener(int contactId);
    }

    class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            int elementId = mContacts.get(getAdapterPosition()).getId();
            mItemClickListener.onItemClickListener(elementId);
        }
    }
}
