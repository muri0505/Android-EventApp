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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FragmentListSession extends Fragment {
    private ListView listView;
    private ArrayList<ObjectSession> sessionList;
    private ArrayList<String> keyList;
    private Adapter adapter;
    private HelperFirebase helperFirebase = new HelperFirebase();
    private static final String TAG = "FragmentListSession";

    private String getEventKey = null;
    private String getAgendaKey = null;

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
        View view = inflater.inflate(R.layout.fragment_list_style, container, false);
        listView = (ListView) view.findViewById(R.id.list);
        sessionList = new ArrayList<ObjectSession>();
        keyList = new ArrayList<String>();
        adapter = new Adapter(getActivity(), R.layout.layout_list_session, sessionList);

        //FirebaseDatabase
        getEventKey = getArguments().getString("eventKey");
        getAgendaKey = getArguments().getString("agendaKey");
        helperFirebase.helperSession(getEventKey,getAgendaKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                getSession(dataSnapshot);
                adapter.notifyDataSetChanged();
            }
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        listView.setAdapter(adapter);

        //click on event switch to activity event home page
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapter, View v, int pos, long a) {
                String key = keyList.get(pos);
                listener.getSessionKey(key);
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
            sessionList.add(s);
            keyList.add(key);
        }
    }
}
