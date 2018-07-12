package com.example.owner.internationalaset;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;


public class FragmentMainEventDetail extends Fragment {
    private String eventKey;
    private ObjectEvent event;
    private FirebaseHelper firebaseHelper;

    private TextView eventName;
    private TextView eventStartDate;
    private TextView eventEndDate;
    private TextView eventDes;
    private TextView eventLocation;

    public FragmentMainEventDetail() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_event_detail, container, false);

        eventKey = getArguments().getString("eventKey");
        event = new ObjectEvent();
        firebaseHelper = new FirebaseHelper();

        eventName = (TextView)view.findViewById(R.id.eventName);
        eventStartDate = (TextView)view.findViewById(R.id.eventStartDate);
        eventEndDate = (TextView)view.findViewById(R.id.eventEndDate);
        eventDes = (TextView)view.findViewById(R.id.eventDes);
        eventLocation = (TextView)view.findViewById(R.id.eventLocation);

        firebaseHelper.helperEvent().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                getEvent(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        return view;
    }

    public void getEvent(DataSnapshot dataSnapshot){
        for(DataSnapshot data : dataSnapshot.getChildren()){
            if(data.getKey().equals(eventKey)) {
                event = data.getValue(ObjectEvent.class);

                setText(eventName, event.getEventName());
                setText(eventStartDate, event.getEventStartDate());
                setDate(eventEndDate, event.getEventEndDate());
                setText(eventDes, event.getEventDes());
                setText(eventLocation, event.getEventLocation());
            }
        }
    }

    public void setText(TextView textView, String string){
        if(!string.equals("")) {
            textView.setText(string);
        }else{textView.setText("Not Available");}
    }

    public void setDate(TextView textView, String string){
        if(!string.equals("")) {
            textView.setText(" To " + string);
        }else{textView.setText("");}
    }
}
