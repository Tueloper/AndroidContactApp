package com.tochukwuozurumba.contact;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class ContactRepository {

    private ContactDao mContactDao;
    private LiveData<List<Contact>> mAllContacts;

    ContactRepository(Application application) {
        ContactRoomDatabase db = ContactRoomDatabase.getDatabase(application);
        mContactDao = db.contactDao();
        mAllContacts = mContactDao.getAllContact();
    }

    LiveData<List<Contact>> getmAllContacts() {
        return mAllContacts;
    }

    LiveData<Contact> getmContact(int id) { return mContactDao.getContact(id); };

    public void insert(Contact word) {
        new insertAsyncTask(mContactDao).execute(word);
    }

    public void deleteAll()  {
        new deleteAllContactsAsyncTask(mContactDao).execute();
    }

    public void deleteContact(Contact contact)  {
        new deleteContactAsyncTask(mContactDao).execute(contact);
    }

    public void updateContact(Contact contact)  {
        new updateContactAsyncTask(mContactDao).execute(contact);
    }

    public static class insertAsyncTask extends AsyncTask<Contact, Void, Void> {

        private ContactDao mAsyncTaskDao;

        insertAsyncTask(ContactDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Contact... contacts) {
            mAsyncTaskDao.insert(contacts[0]);
            return null;
        }
    }

    private static class deleteAllContactsAsyncTask extends AsyncTask<Void, Void, Void> {
        private ContactDao mAsyncTaskDao;

        deleteAllContactsAsyncTask(ContactDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    private static class deleteContactAsyncTask extends AsyncTask<Contact, Void, Void> {
        private ContactDao mAsyncTaskDao;

        deleteContactAsyncTask(ContactDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Contact... params) {
            mAsyncTaskDao.deleteContact(params[0]);
            return null;
        }
    }

    private static class updateContactAsyncTask extends AsyncTask<Contact, Void, Void> {
        private ContactDao mAsyncTaskDao;

        updateContactAsyncTask(ContactDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Contact... params) {
            mAsyncTaskDao.deleteContact(params[0]);
            return null;
        }
    }
}
