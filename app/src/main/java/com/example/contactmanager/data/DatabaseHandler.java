package com.example.contactmanager.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.contactmanager.R;
import com.example.contactmanager.model.Contact;
import com.example.contactmanager.util.Util;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {


    public DatabaseHandler( Context context ) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }
    //create our table

    @Override
    public void onCreate(SQLiteDatabase db) {
        /*
           create table _name(id, name, phone_number);
         */
        String CREATE_CONTACT_TABLE = "CREATE TABLE " + Util.TABLE_NAME + "("
                + Util.KEY_ID + " INTEGER PRIMARY KEY," + Util.KEY_NAME + " TEXT,"
                + Util.KEY_PHONE_NUMBER + " TEXT" + ")";
        db.execSQL(CREATE_CONTACT_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE = String.valueOf(R.string.db_drop);
        db.execSQL(DROP_TABLE, new String[]{Util.TABLE_NAME});

        //create a table again
        onCreate(db);

    }
    //CRUD operations

    //Add contact
    public void addContact (Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Util.KEY_NAME, contact.getName());
        values.put(Util.KEY_PHONE_NUMBER, contact.getPhoneNumber());

        //insert to row
        db.insert(Util.TABLE_NAME, null, values);
        Log.d("DbHandler", "addContact: " + "item added");
        db.close(); //close dn connection
    }

    //get a contact
    public Contact getContact (int id) {
        SQLiteDatabase db = this.getReadableDatabase();
         Cursor cursor = db.query(Util.TABLE_NAME,
                 new String[] {Util.KEY_ID, Util.KEY_NAME, Util.KEY_PHONE_NUMBER},
                 Util.KEY_ID +"=?", new String[] {String.valueOf(id)},
                 null, null, null);

         if (cursor != null)
             cursor.moveToFirst();

             Contact contact = new Contact();
             contact.setId(Integer.parseInt(cursor.getString(0)));
             contact.setName(cursor.getString(1));
             contact.setPhoneNumber(cursor.getString(2));

        return contact;
    }

    //get all contacts
    public List<Contact> getAllContacts () {
        List<Contact> contactList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        //select all contacts from our database
        String selectAll = "SELECT * FROM " + Util.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectAll, null);

        //Loop through our data
        if (cursor.moveToFirst()){
            Contact contact = new Contact();
            contact.setId(Integer.parseInt(cursor.getString(0)));
            contact.setName(cursor.getString(1));
            contact.setPhoneNumber(cursor.getString(2));

            //add contact objects to our list
            contactList.add(contact);
        }while (cursor.moveToNext());

        return contactList;

    }

    //Update contact
    public int updateContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Util.KEY_NAME, contact.getName());
        values.put(Util.KEY_PHONE_NUMBER, contact.getPhoneNumber());

        //update row
        return db.update(Util.TABLE_NAME, values, Util.KEY_ID + "=?",
                new String[] {String.valueOf(contact.getId())});


    }

    //Delete single contact
    public void deleteContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(Util.TABLE_NAME, Util.KEY_ID + "=?",
                new String[]{String.valueOf(contact.getId())});

        db.close();
    }

    //Get contacts count
    public int getCount() {
        String countQuery = "SELECT * FROM " + Util.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        return cursor.getCount();

    }




}
