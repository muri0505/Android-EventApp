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

public class FragmentListAgenda extends Fragment {
    private ListView listView;
    private ArrayList<String> agendaList;
    private ArrayList<String> keyList;
    private ArrayAdapter<String> adapter;
    private HelperFirebase helperFirebase = new HelperFirebase();

    private String getEventKey = null;
    private String getSessionKey = null;
    private static final String TAG = "FragmentListAgenda";

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
        agendaList = new ArrayList<String>();
        keyList = new ArrayList<String>();

        //FirebaseDatabase
        getEventKey = getArguments().getString("eventKey");
        getSessionKey = getArguments().getString("sessionKey");
        helperFirebase.helperAgenda(getEventKey,getSessionKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                getAgenda(dataSnapshot);
                adapter.notifyDataSetChanged();
            }
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        showAgenda(agendaList);

        //click on event switch to activity event home page
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapter, View v, int pos, long a) {
                String key = keyList.get(pos);
                listener.getAgendaKey(key);
            }
        });

        return view;
    }

    //get event data from database
    public void getAgenda(DataSnapshot dataSnapshot){
        agendaList.clear();
        for(DataSnapshot data : dataSnapshot.getChildren()){
            String key = data.getKey();
            ObjectAgenda a = data.getValue(ObjectAgenda.class);
            agendaList.add(a.agendaToString());
            keyList.add(key);
        }
    }

    //show event data in list view
    public void showAgenda( ArrayList<String> list){
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
