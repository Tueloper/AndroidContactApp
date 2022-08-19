package com.tochukwuozurumba.contact;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


@Entity(tableName = "contact_table")
public class Contact {
    @PrimaryKey( autoGenerate = true )
    @NonNull
//    @ColumnInfo(name = "id")
    public int id;

    @NonNull
    @ColumnInfo(name = "name")
    private String mName;

    @NonNull
    @ColumnInfo(name = "phone")
    private String mPhone;

    @ColumnInfo(name = "email")
    private String mEmail;

    @ColumnInfo(name = "imageUrl")
    private String mImageUrl;

    @ColumnInfo(name = "note")
    private String mNote;


    public Contact(@NonNull String name, @NonNull String phone, String email, String note, String imageUrl) {
        this.mName = name;
        this.mPhone = phone;
        this.mEmail = email;
        this.mNote = note;
        this.mImageUrl = imageUrl;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.mName;
    }

    public String getPhone() {
        return this.mPhone;
    }

    public String getEmail() {
        return this.mEmail;
    }

    public String getNote() {
        return this.mNote;
    }

    public String getImageUrl() {
        return this.mImageUrl;
    }
}
