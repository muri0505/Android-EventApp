package com.example.owner.internationalaset;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/*
    FragmentListEvents: showing all events with adapter, click on event to get eventKey,
    intent to MainEvent if not control mode or ActivityControlEvent listens eventKey.
*/
public class FragmentListEvents extends Fragment {
    private ListView listView;
    private ArrayList<ObjectEvent> eventList;
    private ArrayList<String> keyList;
    private Adapter adapter;
    private HelperFirebase helperFirebase = new HelperFirebase();
    private Boolean controlMode = false;
    private static final String TAG = "FragmentListEvents";

    public FragmentListEvents(){}

    //listener listens eventKey
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
        View view = inflater.inflate(R.layout.fragment_list_style, container, false);
        listView = (ListView) view.findViewById(R.id.list);
        eventList = new ArrayList<ObjectEvent>();
        keyList = new ArrayList<String>();
        adapter = new Adapter(getActivity(), R.layout.layout_list_event, eventList);
        controlMode = getArguments().getBoolean("controlMode");

        //get events from firebase
        helperFirebase.helperEvent().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                getEvent(dataSnapshot);
                adapter.notifyDataSetChanged();
            }
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        //show events in listview with adapter
        listView.setAdapter(adapter);

        //get selected eventKey and intent to MainEvent if not control mode
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapter, View v, int pos, long a) {
                String key = keyList.get(pos);
                listener.getEventKey(key);
                Log.i(TAG, "EventKey clicked " + key);
                if(controlMode==false){
                    Intent i = new Intent(getActivity(), MainEvent.class);
                    i.putExtra("eventKey", key);
                    startActivity(i);
                }
            }
        });

        return view;
    }

    //get events and eventKeys from firebase
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