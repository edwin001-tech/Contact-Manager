package com.example.contactmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.contactmanager.data.DatabaseHandler;
import com.example.contactmanager.model.Contact;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHandler db = new DatabaseHandler(MainActivity.this);

        //create contact object
        Contact edwin = new Contact();
        edwin.setName("Edwin");
        edwin.setPhoneNumber("0745481307");

        //db.addContact(edwin);

        Contact c = db.getContact(1);
        c.setName("newEdwin");
        c.setPhoneNumber("0707278158");

        int updatedRow = db.updateContact(c);
        Log.d("RowId", "onCreate: " +updatedRow);

        List<Contact> contactList = db.getAllContacts();

        for (Contact contact: contactList) {
            Log.d("MainActivity", "Oncreate: " +contact.getName());
        }
    }
}