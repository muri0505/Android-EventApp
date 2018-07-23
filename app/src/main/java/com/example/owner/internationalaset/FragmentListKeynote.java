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

public class FragmentListKeynote extends Fragment {
    private ListView listView;
    private ArrayList<ObjectKeynote> keynoteList;
    private ArrayList<String> keyList;
    private AdapterListView adapterListView;
    private HelperFirebase helperFirebase = new HelperFirebase();
    private static final String TAG = "FragmentListKeynote";

    private String getEventKey = null;
    private String getSessionKey = null;

    public FragmentListKeynote(){}

    FragmentListKeynote.FragmentKeynotelistener listener;
    public interface FragmentKeynotelistener{
        public void getKeynoteKey(String keynoteKey);
    }

    public void onAttach(Activity activity){
        super.onAttach(activity);
        try{
            listener = (FragmentListKeynote.FragmentKeynotelistener) activity;
        }catch(ClassCastException e){
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_style, container, false);
        listView = (ListView) view.findViewById(R.id.list);
        keynoteList = new ArrayList<ObjectKeynote>();
        keyList = new ArrayList<String>();
        adapterListView = new AdapterListView(getActivity(), R.layout.layout_list_keynote, keynoteList);

        //FirebaseDatabase
        getEventKey = getArguments().getString("eventKey");
        getSessionKey = getArguments().getString("sessionKey");
        helperFirebase.helperKeynote(getEventKey,getSessionKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                getKeynote(dataSnapshot);
                adapterListView.notifyDataSetChanged();
            }
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        listView.setAdapter(adapterListView);

        //click on event switch to activity event homepage page
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapter, View v, int pos, long a) {
                String key = keyList.get(pos);
                listener.getKeynoteKey(key);
            }
        });

        return view;
    }

    //get event data from database
    public void getKeynote(DataSnapshot dataSnapshot){
        keynoteList.clear();
        for(DataSnapshot data : dataSnapshot.getChildren()){
            String key = data.getKey();
            ObjectKeynote s = data.getValue(ObjectKeynote.class);
            keynoteList.add(s);
            keyList.add(key);
        }
    }
}
