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

public class FragmentListAgenda extends Fragment{
    private ListView listView;
    private ArrayList<ObjectAgenda> agendaList;
    private ArrayList<String> keyList;
    private Adapter adapter;
    private HelperFirebase helperFirebase = new HelperFirebase();
    private static final String TAG = "FragmentListAgenda";

    private String getEventKey = null;

    public FragmentListAgenda(){}

    FragmentListAgenda.FragmentAgendalistener listener;
    public interface FragmentAgendalistener{
        public void getAgendaKey(String agendaKey);
    }

    public void onAttach(Activity activity){
        super.onAttach(activity);
        try{
            listener = (FragmentListAgenda.FragmentAgendalistener) activity;
        }catch(ClassCastException e){
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_style, container, false);
        listView = (ListView) view.findViewById(R.id.list);
        agendaList = new ArrayList<ObjectAgenda>();
        keyList = new ArrayList<String>();
        adapter = new Adapter(getActivity(), R.layout.layout_agenda_list, agendaList);

        getEventKey = getArguments().getString("eventKey");
        helperFirebase.helperAgenda(getEventKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                getAgenda(dataSnapshot);
                adapter.notifyDataSetChanged();
            }
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapter, View v, int pos, long a) {
                String key = keyList.get(pos);
                listener.getAgendaKey(key);
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
}
