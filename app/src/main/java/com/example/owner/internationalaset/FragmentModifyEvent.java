package com.example.owner.internationalaset;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.Calendar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

/*
    FragmentModifyEvent: get eventKey from ActivityControlEvent
    check valid eventKey and get event from firebase or create new eventKey and new event
    Edit button to push update/new event, cancel button back to ActivityControlEvent
 */
public class FragmentModifyEvent extends HelperDateTime{
    private HelperFirebase helperFirebase = new HelperFirebase();
    private ObjectEvent event;
    private String getEventKey = null;

    private TextView mode;
    private EditText eventName;
    private EditText eventStartDate;
    private EditText eventEndDate;
    private EditText eventDes;
    private EditText eventLocation;
    private EditText eventVenue;
    private String eventImg = " ";
    private String emptyName;
    private static final String TAG = "FragmentModifyEvent";

    public FragmentModifyEvent(){}

    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_modify_event, container, false);

        eventName= (EditText) view.findViewById(R.id.eventName);
        eventStartDate = (EditText) view.findViewById(R.id.eventStartDate);
        eventEndDate = (EditText) view.findViewById(R.id.eventEndDate);
        eventDes = (EditText) view.findViewById(R.id.eventDes);
        eventLocation = (EditText) view.findViewById(R.id.eventLocation);
        eventVenue = (EditText) view.findViewById(R.id.eventVenue);
        mode = (TextView) view.findViewById(R.id.mode);

        //input keyboard format
        eventStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDate(eventStartDate);
            }
        });
        eventEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDate(eventEndDate);
            }
        });

        //get eventKey from ActivityControlEvent
        getEventKey = getArguments().getString("eventKey");
        Log.i(TAG,"Get eventKey from ActivityControlEvent " + getEventKey);

        //check valid eventKey to get event
        if(getEventKey != null){
            Log.i(TAG,"Mode: Edit Event");
            helperFirebase.helperEvent().addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    getEvent(dataSnapshot);
                }
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }
            });
        }else{
            Log.i(TAG,"Mode: Create Event");
            mode.setText("Create Event");
        }

        Button edit = (Button) view.findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //edit button to create current event
                String name = eventName.getText().toString();
                String startDate = eventStartDate.getText().toString();
                String endDate = eventEndDate.getText().toString();
                String des = eventDes.getText().toString();
                String location = eventLocation.getText().toString();
                String venue = eventVenue.getText().toString();
                event = new ObjectEvent(name, startDate, endDate, des, location, venue, eventImg);

                //event validation
                if (empty(name, "Event Name") || empty(startDate, "Event Start Date") || empty(des, "Event Description")
                        || empty(location, "Event Location") || empty(venue, "Event Venue")) {
                    new AlertDialog.Builder(getActivity())
                            .setMessage(emptyName +" cannot be empty")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            }).show();
                } else {
                    //create new eventKey and event or update event under exist eventKey
                    if (getEventKey == null) {
                        getEventKey = helperFirebase.helperEvent().push().getKey();
                        helperFirebase.helperEventKey(getEventKey).setValue(event);
                        Log.i(TAG, "New eventKey and event created. EventKey: " + getEventKey);
                    } else {
                        helperFirebase.helperEventKey(getEventKey).updateChildren(event.toHashMap());
                        Log.i(TAG, "event updated. EventKey: " + getEventKey);
                    }
                    backToControl();
                }
            }
        });

        //cancel button back to ActivityControlEvent
        Button cancel = (Button) view.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                backToControl();
            }
        });
        return view;
    }

    //get event from firebase with eventKey and show in editText
    public void getEvent(DataSnapshot dataSnapshot){
        for(DataSnapshot data : dataSnapshot.getChildren()){
            String key = data.getKey();

            if(key.equals(getEventKey)) {
                mode.setText("Edit Event");
                ObjectEvent e = (ObjectEvent)data.getValue(ObjectEvent.class);

                eventName.setText(e.getEventName());
                eventStartDate.setText(e.getEventStartDate());
                eventEndDate.setText(e.getEventEndDate());
                eventDes.setText(e.getEventDes());
                eventLocation.setText(e.getEventLocation());
                eventVenue.setText(e.getEventVenue());
                eventImg = e.getEventImg();
                break;
            }
        }
    }

    //Intent to ActivityControlEvent, default showing event list
    public void backToControl(){
        Intent i = new Intent(getActivity(), ActivityControlEvent.class);
        startActivity(i);
        Log.i(TAG, "Intent to ActivityControlEvent");
    }

    //check object empty attribute
    public boolean empty(String text, String name){
        emptyName = name;
        return text.equals(null)||text.equals("");
    }
}