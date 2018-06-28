package com.example.owner.internationalaset;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddEvent extends Activity {

    DatabaseReference mDatabase;
    Event temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        final EditText eventName= (EditText) findViewById(R.id.eventName);
        final EditText eventDate = (EditText) findViewById(R.id.eventDate);
        final EditText eventDes = (EditText) findViewById(R.id.eventDes);
        final Button submit = (Button) findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                temp = new Event(eventName.getText().toString(), eventDate.getText().toString(), eventDes.getText().toString());

                mDatabase = FirebaseDatabase.getInstance().getReference();
                String keyID  = mDatabase.child("Events").push().getKey();
                mDatabase.child("Events").child(keyID).setValue(temp.addEvent());
            }
        });

    }
}
