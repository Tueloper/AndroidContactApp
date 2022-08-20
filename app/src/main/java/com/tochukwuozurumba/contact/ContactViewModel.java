package com.tochukwuozurumba.contact;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class ContactViewModel extends AndroidViewModel {

    private ContactRepository mRepository;

    private LiveData<List<Contact>> mAllContacts;

    public ContactViewModel(@NonNull Application application) {
        super(application);
        mRepository = new ContactRepository(application);
        mAllContacts = mRepository.getmAllContacts();
    }

    LiveData<List<Contact>> getmAllContacts() {
        return mAllContacts;
    }

    public LiveData<Contact> getContact(int id) {
        return mRepository.getmContact(id);
    }

    public void insert(Contact contact) {
        mRepository.insert(contact);
    }

    public void deleteAll() {mRepository.deleteAll();}

    public void deleteContact(Contact contact) {mRepository.deleteContact(contact);}

    public void updateContact(Contact contact) {mRepository.updateContact(contact);}
}
