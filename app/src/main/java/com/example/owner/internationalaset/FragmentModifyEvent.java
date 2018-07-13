package com.example.owner.internationalaset;

import android.content.Intent;
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
import com.google.firebase.database.ValueEventListener;

/*
    fragment event information
    search database with event name, add new event if not exist, Fragment Event Modify if exist
 */
public class FragmentModifyEvent extends Fragment {
    private HelperFirebase helperFirebase = new HelperFirebase();
    private String getEventKey = null;
    private ObjectEvent event;

    private TextView mode;
    private EditText eventName;
    private EditText eventStartDate;
    private EditText eventEndDate;
    private EditText eventDes;
    private EditText eventLocation;
    private String eventImg = " ";
    private static final String TAG = "FragmentModifyEvent";

    public FragmentModifyEvent(){}

    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_modify_event, container, false);

        eventName= (EditText) view.findViewById(R.id.eventName);
        eventStartDate = (EditText) view.findViewById(R.id.eventStartDate);
        eventEndDate = (EditText) view.findViewById(R.id.eventEndDate);
        eventDes = (EditText) view.findViewById(R.id.eventDes);
        eventLocation = (EditText) view.findViewById(R.id.eventLocation);
        mode = (TextView) view.findViewById(R.id.mode);

        getEventKey = getArguments().getString("eventKey");

        if(getEventKey != null){
            helperFirebase.helperEvent().addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    getEvent(dataSnapshot);
                }
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }
            });}else{mode.setText("Create Event");}

        //create key and add event data/get key and modify event data
        Button edit = (Button) view.findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String name = eventName.getText().toString();
                String startDate = eventStartDate.getText().toString();
                String endDate = eventEndDate.getText().toString();
                String des = eventDes.getText().toString();
                String location = eventLocation.getText().toString();
                event = new ObjectEvent(name, startDate, endDate, des, location,eventImg);

                if(getEventKey==null){
                    getEventKey = helperFirebase.helperEvent().push().getKey();
                    helperFirebase.helperEventKey(getEventKey).setValue(event);
                }else{
                    helperFirebase.helperEventKey(getEventKey).updateChildren(event.toHashMap());
                }
                backToControl();
            }
        });

        Button cancel = (Button) view.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                backToControl();
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
                ObjectEvent e = (ObjectEvent)data.getValue(ObjectEvent.class);

                eventName.setText(e.getEventName());
                eventStartDate.setText(e.getEventStartDate());
                eventEndDate.setText(e.getEventEndDate());
                eventDes.setText(e.getEventDes());
                eventLocation.setText(e.getEventLocation());
                eventImg = e.getEventImg();
                break;
            }
        }
    }

    public void backToControl(){
        Intent i = new Intent(getActivity(), ActivityControlEvent.class);
        startActivity(i);
    }
}