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

public class FragmentListUser extends Fragment {
    private ListView listView;
    private ArrayList<ObjectUser> userList;
    private ArrayList<String> keyList;
    private AdapterListView adapterListView;
    private HelperFirebase helperFirebase = new HelperFirebase();
    private static final String TAG = "FragmentListUser";

    private String userKey;

    public FragmentListUser(){}

    //listener listens userKey
    FragmentListUser.FragmentUserlistener listener;
    public interface FragmentUserlistener{
        public void getUserKey(String userKey);
    }

    public void onAttach(Activity activity){
        super.onAttach(activity);
        try{
            listener = (FragmentListUser.FragmentUserlistener) activity;
        }catch(ClassCastException e){}
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_style, container, false);
        listView = (ListView) view.findViewById(R.id.list);
        userList = new ArrayList<ObjectUser>();
        keyList = new ArrayList<String>();
        adapterListView = new AdapterListView(getActivity(), R.layout.layout_list_user, userList);

        //get users from firebase
        helperFirebase.helperUser().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                getUser(dataSnapshot);
                adapterListView.notifyDataSetChanged();
            }
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        //show users in listview with adapterListView
        listView.setAdapter(adapterListView);

        //get selected userKey and intent to type fragment if not control mode
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapter, View v, int pos, long a) {
                userKey = keyList.get(pos);
                listener.getUserKey(userKey);
            }
        });

        return view;
    }

    //get users and userKeys from firebase
    public void getUser(DataSnapshot dataSnapshot){
        userList.clear();
        for(DataSnapshot data : dataSnapshot.getChildren()){
            String key = data.getKey();
            ObjectUser s = data.getValue(ObjectUser.class);
            userList.add(s);
            keyList.add(key);
        }
    }
}

