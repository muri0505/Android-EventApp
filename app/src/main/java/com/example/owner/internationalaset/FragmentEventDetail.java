package com.example.owner.internationalaset;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class FragmentEventDetail extends Fragment {
    private String eventKey;
    private ArrayList<ObjectEvent> event;
    private FirebaseHelper firebaseHelper;
    private Adapter adapter;

    private ListView listView;

    public FragmentEventDetail() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_style, container, false);

        eventKey = getArguments().getString("eventKey");
        event = new ArrayList<>();
        firebaseHelper = new FirebaseHelper();
        adapter = new Adapter(getActivity(),R.layout.layout_event_detail, event);
        listView = (ListView)view.findViewById(R.id.list) ;

        firebaseHelper.helperEvent().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                getEvent(dataSnapshot);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        listView.setAdapter(adapter);
        return view;
    }

    public void getEvent(DataSnapshot dataSnapshot){
        event.clear();
        for(DataSnapshot data : dataSnapshot.getChildren()){
            if(data.getKey().equals(eventKey)) {
                event.add(data.getValue(ObjectEvent.class));
            }
        }
    }
}
