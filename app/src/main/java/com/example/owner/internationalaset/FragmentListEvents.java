package com.example.owner.internationalaset;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FragmentListEvents extends Fragment {
    private ListView listView;
    private ArrayList<ObjectEvent> eventList;
    private ArrayList<String> keyList;
    private AdapterEvent adapter;
    private FirebaseHelper firebaseHelper = new FirebaseHelper();
    private static final String TAG = "FragmentListEvents";

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
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        listView = (ListView) view.findViewById(R.id.list);
        eventList = new ArrayList<ObjectEvent>();
        keyList = new ArrayList<String>();
        adapter = new AdapterEvent(getActivity(), R.layout.layout_list_event, eventList);

        //FirebaseDatabase
        firebaseHelper.helperEvent().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                getEvent(dataSnapshot);
                adapter.notifyDataSetChanged();
            }
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        //showEvent(eventList);
        listView.setAdapter(adapter);

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
            ObjectEvent e = data.getValue(ObjectEvent.class);
            eventList.add(e);
            keyList.add(key);
        }
    }
}