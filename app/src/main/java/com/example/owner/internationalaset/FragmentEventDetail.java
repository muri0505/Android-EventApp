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

/*
    FragmentEventDeatil: showing event detail information in MainEvent
 */
public class FragmentEventDetail extends Fragment {
    private String eventKey;
    private ArrayList<ObjectEvent> event;
    private HelperFirebase helperFirebase;
    private AdapterListView adapterListView;

    private ListView listView;

    public FragmentEventDetail() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_style, container, false);

        eventKey = getArguments().getString("eventKey");
        event = new ArrayList<>();
        helperFirebase = new HelperFirebase();
        adapterListView = new AdapterListView(getActivity(),R.layout.layout_detail_event, event);
        listView = (ListView)view.findViewById(R.id.list) ;

        helperFirebase.helperEvent().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                getEvent(dataSnapshot);
                adapterListView.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        listView.setAdapter(adapterListView);
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
