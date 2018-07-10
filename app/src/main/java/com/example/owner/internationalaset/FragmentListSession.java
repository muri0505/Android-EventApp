package com.example.owner.internationalaset;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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

public class FragmentListSession extends Fragment{
    private ListView listView;
    private ArrayList<String> sessionList;
    private ArrayList<String> keyList;
    private ArrayAdapter<String> adapter;

    String getEventKey = null;

    public FragmentListSession(){}

    FragmentListSession.FragmentSessionlistener listener;
    public interface FragmentSessionlistener{
        public void getSessionKey(String sessionKey);
    }

    public void onAttach(Activity activity){
        super.onAttach(activity);
        try{
            listener = (FragmentListSession.FragmentSessionlistener) activity;
        }catch(ClassCastException e){
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        listView = (ListView) view.findViewById(R.id.list);
        sessionList = new ArrayList<String>();
        keyList = new ArrayList<String>();

        //FirebaseDatabase
        getEventKey = getArguments().getString("eventKey");
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Events").child(getEventKey).child("Session");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                getSession(dataSnapshot);
                adapter.notifyDataSetChanged();
            }
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        showSession(sessionList);

        //click on event switch to activity event home page
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapter, View v, int pos, long a) {
                String key = keyList.get(pos);
                listener.getSessionKey(key);
                Log.i("sessionList", key);
            }
        });

        return view;
    }

    //get event data from database
    public void getSession(DataSnapshot dataSnapshot){
        sessionList.clear();
        for(DataSnapshot data : dataSnapshot.getChildren()){
            String key = data.getKey();
            ObjectSession s = data.getValue(ObjectSession.class);
            sessionList.add(s.sessionToString());
            keyList.add(key);
        }
    }

    //show event data in list view
    public void showSession( ArrayList<String> list){
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