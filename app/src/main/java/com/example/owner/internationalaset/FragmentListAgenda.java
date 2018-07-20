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

public class FragmentListAgenda extends Fragment{
    private ListView listView;
    private ArrayList<ObjectAgenda> agendaList;
    private ArrayList<String> keyList;
    private AdapterListView adapterListView;
    private HelperFirebase helperFirebase = new HelperFirebase();
    private Boolean controlMode = false;
    private static final String TAG = "FragmentListAgenda";

    private String getEventKey = null;
    private String agendaKey;

    public FragmentListAgenda(){}

    FragmentListAgenda.FragmentAgendalistener listener;
    public interface FragmentAgendalistener{
        public void getAgendaKey(String agendaKey);
    }

    public void onAttach(Activity activity){
        super.onAttach(activity);
        try{
            listener = (FragmentListAgenda.FragmentAgendalistener) activity;
        }catch(ClassCastException e){}
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_style, container, false);
        listView = (ListView) view.findViewById(R.id.list);
        agendaList = new ArrayList<ObjectAgenda>();
        keyList = new ArrayList<String>();
        adapterListView = new AdapterListView(getActivity(), R.layout.layout_list_agenda, agendaList);

        controlMode = getArguments().getBoolean("controlMode");
        getEventKey = getArguments().getString("eventKey");

        helperFirebase.helperAgenda(getEventKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                getAgenda(dataSnapshot);
                adapterListView.notifyDataSetChanged();
            }
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        listView.setAdapter(adapterListView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapter, View v, int pos, long a) {
                agendaKey = keyList.get(pos);
                listener.getAgendaKey(agendaKey);

                if(controlMode==false){
                    helperFirebase.helperAgendaKey(getEventKey, agendaKey).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String type = dataSnapshot.child("agendaType").getValue().toString();
                            //switch agenda type
                            switch (type){
                                case "General":
                                    break;
                                case "Session":
                                    fragmentSwitch(new FragmentListSession());
                                    break;
                                case "Keynote Lecture":
                                    fragmentSwitch(new FragmentListKeynote());
                                    break;
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {}
                    });
                }
            }
        });

        return view;
    }

    public void getAgenda(DataSnapshot dataSnapshot){
        agendaList.clear();
        for(DataSnapshot data : dataSnapshot.getChildren()){
            String key = data.getKey();
            ObjectAgenda s = data.getValue(ObjectAgenda.class);
            agendaList.add(s);
            keyList.add(key);
        }
    }

    //fragmentSwitch
    public void fragmentSwitch(Fragment f) {
        Bundle bundle = new Bundle();
        bundle.putString("eventKey", getEventKey);
        bundle.putString("agendaKey",agendaKey);
        f.setArguments(bundle);
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment, f, "currentFragment");
        transaction.commit();
    }
}
