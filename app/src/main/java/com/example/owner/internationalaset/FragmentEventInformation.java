package com.example.owner.internationalaset;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/*
    fragment event information
    search database with event name, add new event if not exist, modify/delete if exist
 */

public class FragmentEventInformation extends Fragment {
    DatabaseReference mDatabase;
    String getEventName;
    String key;
    ObjectEvent event;

    EditText eventName;
    EditText eventDate;
    EditText eventDes;
    TextView mode;
    Button editadd;

    public FragmentEventInformation(){}

    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_information, container, false);

        getEventName = getArguments().getString("eventName");

        eventName= (EditText) view.findViewById(R.id.eventName);
        eventDate = (EditText) view.findViewById(R.id.eventDate);
        eventDes = (EditText) view.findViewById(R.id.eventDes);
        mode = (TextView) view.findViewById(R.id.mode);
        editadd = (Button) view.findViewById(R.id.editadd);

        //FirebaseDatabase
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Events");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                getEvent(dataSnapshot);
            }
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        //create key and add event data/get key and modify event data
        editadd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                event = new ObjectEvent(eventName.getText().toString(), eventDate.getText().toString(), eventDes.getText().toString());
                mDatabase = FirebaseDatabase.getInstance().getReference();
                if(key == null) {
                    key  = mDatabase.child("Events").push().getKey();
                }
                mDatabase.child("Events").child(key).setValue(event.addEvent());
                getActivity().finish();
            }
        });

        return view;
    }

    //search event name in database
    public void getEvent(DataSnapshot dataSnapshot){
        for(DataSnapshot data : dataSnapshot.getChildren()){
            String tempName = (String)data.child("eventName").getValue();
            eventName.setText(getEventName);

            if(tempName.equals(getEventName)) {
                key = data.getKey();
                mode.setText("Modify Event");
                eventDate.setText((String) data.child("eventDate").getValue());
                eventDes.setText((String) data.child("eventDes").getValue());
                break;
            }else{
                key = null;
                mode.setText("Event not exist, Add Event?");
            }
        }
    }
}