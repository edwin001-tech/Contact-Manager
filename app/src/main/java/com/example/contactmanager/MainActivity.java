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
        Contact c = new Contact("polly", "5546372278");
        db.addContact(c);

        db.addContact(new Contact("Purity", "5654764"));
        db.addContact(new Contact("Boney", "64327"));
        db.addContact(new Contact("James", "657269"));
        db.addContact(new Contact("Faith", "623786587"));
        db.addContact(new Contact("Pamela", "678625827"));
        db.addContact(new Contact("Wendy", "65557837"));
        db.addContact(new Contact("Patrick", "7854938"));
        db.addContact(new Contact("Jeff", "879863"));


        List<Contact> contactList = db.getAllContacts();
        for (Contact contact : contactList) {
            Log.d("MainActivity", "onCreate: " + contact.getName());

        }
    }
}