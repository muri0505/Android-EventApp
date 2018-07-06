package com.example.owner.internationalaset;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
/*
    fragment show all events
 */

public class FragmentListEvents extends Fragment {
    private ListView listView;
    private ArrayList<String> eventList;
    private ArrayList<String> keyList;
    private ArrayAdapter<String> adapter;

    public FragmentListEvents(){}

    FragmentEventslistener listener;
    public interface FragmentEventslistener{
        public void getEventKey(String eventKey);
    }

    public void onAttach(Activity activity){
        super.onAttach(activity);
        try{
            listener = (FragmentEventslistener) activity;
        }catch(ClassCastException e){
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_events, container, false);
        listView = (ListView) view.findViewById(R.id.events);
        eventList = new ArrayList<String>();
        keyList = new ArrayList<String>();

        //FirebaseDatabase
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Events");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                getEvent(dataSnapshot);
                adapter.notifyDataSetChanged();
            }
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        showEvent(eventList);

        //click on event switch to activity event home page
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapter, View v, int pos, long a) {
                String key = keyList.get(pos);
                listener.getEventKey(key);
            }
        });

        return view;
    }

    //get event data from database
    public void getEvent(DataSnapshot dataSnapshot){
        eventList.clear();
        for(DataSnapshot data : dataSnapshot.getChildren()){
            String key = data.getKey();
            String eventName = (String)data.child("eventName").getValue();
            String eventDate = (String)data.child("eventDate").getValue();
            String eventDes = (String)data.child("eventDes").getValue();

            ObjectEvent objectEvent = new ObjectEvent(eventName, eventDate, eventDes);
            eventList.add(objectEvent.getEvent());
            keyList.add(key);
        }
    }

    //show event data in list view
    public void showEvent( ArrayList<String> list){
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, list) {
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                textView.setTextColor(Color.WHITE);
                return view;
            }
        };
        listView.setAdapter(adapter);
    }
}
