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

    Contact[] getmContact(int id) { return mContactDao.getContact(id); };

    public void insert(Contact word) {
        new insertAsyncTask(mContactDao).execute(word);
    }

    public void deleteAll()  {
        new deleteAllWordsAsyncTask(mContactDao).execute();
    }

    public static class insertAsyncTask extends AsyncTask<Contact, Void, Void> {

        private ContactDao mAsyncTaskDao;

        insertAsyncTask(ContactDao dao) {
            mAsyncTaskDao = dao;
        }


        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p>
         * This will normally run on a background thread. But to better
         * support testing frameworks, it is recommended that this also tolerates
         * direct execution on the foreground thread, as part of the {@link #execute} call.
         * <p>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param contacts The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected Void doInBackground(final Contact... contacts) {
            mAsyncTaskDao.insert(contacts[0]);
            return null;
        }
    }

    private static class deleteAllWordsAsyncTask extends AsyncTask<Void, Void, Void> {
        private ContactDao mAsyncTaskDao;

        deleteAllWordsAsyncTask(ContactDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }
}
