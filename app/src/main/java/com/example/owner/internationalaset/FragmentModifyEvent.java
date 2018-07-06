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
    search database with event name, add new event if not exist, Fragment Event Modify if exist
 */
public class FragmentModifyEvent extends Fragment {
    DatabaseReference mDatabase;
    String getEventKey = null;
    ObjectEvent event;

    EditText eventName;
    EditText eventDate;
    EditText eventDes;
    TextView mode;
    Button edit;
    Button cancel;

    public FragmentModifyEvent(){}

    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_modify_event, container, false);

        getEventKey = getArguments().getString("eventKey");

        eventName= (EditText) view.findViewById(R.id.eventName);
        eventDate = (EditText) view.findViewById(R.id.eventDate);
        eventDes = (EditText) view.findViewById(R.id.eventDes);
        mode = (TextView) view.findViewById(R.id.mode);
        edit = (Button) view.findViewById(R.id.edit);
        cancel = (Button) view.findViewById(R.id.cancel);

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
        edit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                event = new ObjectEvent(eventName.getText().toString(), eventDate.getText().toString(), eventDes.getText().toString());

                if(getEventKey==null){getEventKey = mDatabase.push().getKey();}
                mDatabase.child(getEventKey).setValue(event.addEvent());
                getActivity().finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        return view;
    }

    //search event name in database
    public void getEvent(DataSnapshot dataSnapshot){
        for(DataSnapshot data : dataSnapshot.getChildren()){
            String key = data.getKey();

            if(key.equals(getEventKey)) {
                mode.setText("Edit Event");
                eventName.setText((String) data.child("eventName").getValue());
                eventDate.setText((String) data.child("eventDate").getValue());
                eventDes.setText((String) data.child("eventDes").getValue());
                break;
            }else{
                mode.setText("Create Event");
            }
        }
    }
}