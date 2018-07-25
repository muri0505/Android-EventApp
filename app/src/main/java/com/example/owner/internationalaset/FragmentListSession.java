package com.example.owner.internationalaset;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
    FragmentListSession: showing all sessions with adapterListView, click on session to get sessionKey,
    intent to type fragment(article/keynote/opening/closing) if not control mode or ActivityControlSession listens sessionKey.
*/
public class FragmentListSession extends Fragment{
    private ListView listView;
    private ArrayList<ObjectSession> sessionList;
    private ArrayList<String> keyList;
    private AdapterListView adapterListView;
    private HelperFirebase helperFirebase = new HelperFirebase();
    private static final String TAG = "FragmentListSession";

    private String getEventKey = null;
    private String sessionKey;
    private String sessionDate;

    public FragmentListSession(){}

    //listener listens sessionKey
    FragmentListSession.FragmentSessionlistener listener;
    public interface FragmentSessionlistener{
        public void getSessionKey(String sessionKey);
    }

    public void onAttach(Activity activity){
        super.onAttach(activity);
        try{
            listener = (FragmentListSession.FragmentSessionlistener) activity;
        }catch(ClassCastException e){}
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_style, container, false);
        listView = (ListView) view.findViewById(R.id.list);
        sessionList = new ArrayList<ObjectSession>();
        keyList = new ArrayList<String>();
        adapterListView = new AdapterListView(getActivity(), R.layout.layout_list_session, sessionList);

        getEventKey = getArguments().getString("eventKey");
        sessionDate = getArguments().getString("sessionDate");

        //get sessions from firebase
        helperFirebase.helperSession(getEventKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                getSession(dataSnapshot);
                adapterListView.notifyDataSetChanged();
            }
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        //show sessions in listview with adapterListView
        listView.setAdapter(adapterListView);

        //get selected sessionKey and intent to type fragment if not control mode
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapter, View v, int pos, long a) {
                sessionKey = keyList.get(pos);
                listener.getSessionKey(sessionKey);
            }
        });

        return view;
    }

    //get sessions and sessionKeys from firebase
    public void getSession(DataSnapshot dataSnapshot){
        sessionList.clear();
        for(DataSnapshot data : dataSnapshot.getChildren()){
            String key = data.getKey();
            ObjectSession s = data.getValue(ObjectSession.class);
            if (s.getSessionDate().equals(sessionDate)) {
                sessionList.add(s);
                keyList.add(key);
            }
        }
    }
}
