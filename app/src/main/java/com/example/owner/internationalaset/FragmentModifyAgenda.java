package com.example.owner.internationalaset;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentModifyAgenda extends Fragment {
    private FirebaseHelper firebaseHelper = new FirebaseHelper();
    private ObjectAgenda agenda;
    private String getEventKey = null;
    private String getSessionKey = null;
    private String getAgendaKey = null;

    private TextView mode;
    private EditText agendaName;
    private EditText agendaInstitution;
    private EditText agendaLocation;
    private EditText agendaStartTime;
    private EditText agendaEndTime;
    private EditText agendaPeople;
    private static final String TAG = "FragmentModifyAgenda";

    public FragmentModifyAgenda(){}

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_modify_agenda, container, false);

        mode = (TextView) view.findViewById(R.id.mode);
        agendaName= (EditText) view.findViewById(R.id.agendaName);
        agendaInstitution = (EditText) view.findViewById(R.id.agendaInstitution);
        agendaLocation = (EditText) view.findViewById(R.id.agendaLocation);
        agendaStartTime = (EditText) view.findViewById(R.id.agendaStartTime);
        agendaEndTime = (EditText) view.findViewById(R.id.agendaEndTime);
        agendaPeople = (EditText) view.findViewById(R.id.agendaPeople);

        getEventKey = getArguments().getString("eventKey");
        getSessionKey = getArguments().getString("sessionKey");
        getAgendaKey = getArguments().getString("agendaKey");

        if(getAgendaKey != null) {
            firebaseHelper.helperAgenda(getEventKey,getSessionKey).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    getAgenda(dataSnapshot);
                }

                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }
            });
        }else{mode.setText("Create Agenda");}

        Button edit = (Button) view.findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String name, institution, location, startTime, endTime, people;
                name = agendaName.getText().toString();
                institution = agendaInstitution.getText().toString();
                location = agendaLocation.getText().toString();
                startTime = agendaStartTime.getText().toString();
                endTime = agendaEndTime.getText().toString();
                people = agendaPeople.getText().toString();

                agenda = new ObjectAgenda(name, institution, location, startTime, endTime, people);
                if(getAgendaKey==null){getAgendaKey = firebaseHelper.helperAgenda(getEventKey,getSessionKey).push().getKey();}
                firebaseHelper.helperAgendaKey(getEventKey,getSessionKey,getAgendaKey).setValue(agenda);
                backToControl();
            }
        });

        Button cancel = (Button) view.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                backToControl();
            }
        });
        return view;
    }

    public void getAgenda(DataSnapshot dataSnapshot){
        for(DataSnapshot data : dataSnapshot.getChildren()){
            String key = data.getKey();

            if(key.equals(getAgendaKey)) {
                mode.setText("Edit Agenda");
                ObjectAgenda a = (ObjectAgenda) data.getValue(ObjectAgenda.class);
                agendaName.setText(a.getAgendaName());
                agendaInstitution.setText(a.getAgendaInstitution());
                agendaLocation.setText(a.getAgendaLocation());
                agendaStartTime.setText(a.getAgendaStartTime());
                agendaEndTime.setText(a.getAgendaEndTime());
                agendaPeople.setText(a.getAgendaPeople());

                break;
            }
        }
    }

    public void backToControl(){
        Intent i = new Intent(getActivity(), ActivityControlAgenda.class);
        i.putExtra("eventKey",getEventKey);
        i.putExtra("sessionKey",getSessionKey);
        startActivity(i);
    }
}