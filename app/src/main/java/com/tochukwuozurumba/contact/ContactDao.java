package com.tochukwuozurumba.contact;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ContactDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Contact... contact);

    @Query("DELETE FROM contact_table")
    void deleteAll();

    @Delete
    void deleteContact(Contact... contact);

    @Update
    void updateContact(Contact... contact);

    @Query("SELECT * FROM contact_table WHERE id = :id")
    Contact[] getContact(int id);

    @Query("SELECT * FROM contact_table ORDER BY name ASC")
    LiveData<List<Contact>> getAllContact();
}
