package com.example.owner.internationalaset;

import android.app.Activity;
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

public class FragmentListPasscode extends Fragment {
    private ListView listView;
    private ArrayList<ObjectPasscode> passcodeList;
    private ArrayList<String> keyList;
    private AdapterListView adapterListView;
    private HelperFirebase helperFirebase = new HelperFirebase();
    private static final String TAG = "FragmentListPasscode";

    public FragmentListPasscode(){}

    //listener listens passcodeKey
    FragmentPasscodelistener listener;
    public interface FragmentPasscodelistener{
        public void getPasscodeKey(String passcodeKey);
    }

    public void onAttach(Activity activity){
        super.onAttach(activity);
        try{
            listener = (FragmentPasscodelistener) activity;
        }catch(ClassCastException e){
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_style, container, false);
        listView = (ListView) view.findViewById(R.id.list);
        passcodeList = new ArrayList<ObjectPasscode>();
        keyList = new ArrayList<String>();
        adapterListView = new AdapterListView(getActivity(), R.layout.layout_list_passcode, passcodeList);

        //get passcode from firebase
        helperFirebase.helperPasscode().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                getPasscode(dataSnapshot);
                adapterListView.notifyDataSetChanged();
            }
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        //show passcode in listview with adapterListView
        listView.setAdapter(adapterListView);

        //get selected passcodeKey and intent to MainPasscode if not control mode
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapter, View v, int pos, long a) {
                String key = keyList.get(pos);
                listener.getPasscodeKey(key);
                Log.i(TAG, "PasscodeKey clicked " + key);
            }
        });

        return view;
    }

    //get passcode and passcodeKeys from firebase
    public void getPasscode(DataSnapshot dataSnapshot){
        passcodeList.clear();
        for(DataSnapshot data : dataSnapshot.getChildren()){
            String key = data.getKey();
            ObjectPasscode e = data.getValue(ObjectPasscode.class);
            passcodeList.add(e);
            keyList.add(key);
        }
    }
}