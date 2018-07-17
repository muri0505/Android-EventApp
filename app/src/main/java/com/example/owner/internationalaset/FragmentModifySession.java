package com.example.owner.internationalaset;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

/*
    FragmentModifySession: get eventKey&agendaKey&sessionKey from ActivityControlSession
    check valid sessionKey and get session from firebase or create new sessionKey and new session
    Edit button to push update/new session, cancel button back to ActivityControlSession
 */
public class FragmentModifySession extends HelperDateTime {
    private HelperFirebase helperFirebase = new HelperFirebase();
    private ObjectSession session;
    private String getEventKey = null;
    private String getAgendaKey = null;
    private String getSessionKey = null;

    private TextView mode;
    private EditText sessionName;
    private EditText sessionInstitution;
    private EditText sessionLocation;
    private EditText sessionStartTime;
    private EditText sessionEndTime;
    private EditText sessionPeople;
    private static final String TAG = "FragmentModifySession";

    public FragmentModifySession(){}

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_modify_session, container, false);

        mode = (TextView) view.findViewById(R.id.mode);
        sessionName= (EditText) view.findViewById(R.id.sessionName);
        sessionInstitution = (EditText) view.findViewById(R.id.sessionInstitution);
        sessionLocation = (EditText) view.findViewById(R.id.sessionLocation);
        sessionStartTime = (EditText) view.findViewById(R.id.sessionStartTime);
        sessionEndTime = (EditText) view.findViewById(R.id.sessionEndTime);
        sessionPeople = (EditText) view.findViewById(R.id.sessionPeople);

        sessionStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {setTime(sessionStartTime);    }
        });
        sessionEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTime(sessionEndTime);
            }
        });

        //get eventKey&agendaKey&sessionKey from ActivityControlSession
        getEventKey = getArguments().getString("eventKey");
        getAgendaKey = getArguments().getString("agendaKey");
        getSessionKey = getArguments().getString("sessionKey");
        Log.i(TAG,"Get eventKey, agendaKey and sessionKey from ActivityControlAgenda " + getEventKey + ", " + getAgendaKey + ", " + getSessionKey);

        //check valid sessionKey to get session
        if(getSessionKey != null) {
            Log.i(TAG,"Mode: Edit Session");
            helperFirebase.helperSession(getEventKey,getAgendaKey).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    getSession(dataSnapshot);
                }
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }
            });
        }else{
            Log.i(TAG,"Mode: Create Session");
            mode.setText("Create Session");
        }

        Button edit = (Button) view.findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //edit button to create current session
                String name, institution, location, startTime, endTime, people;
                name = sessionName.getText().toString();
                institution = sessionInstitution.getText().toString();
                location = sessionLocation.getText().toString();
                startTime = sessionStartTime.getText().toString();
                endTime = sessionEndTime.getText().toString();
                people = sessionPeople.getText().toString();
                session = new ObjectSession(name, institution, location, startTime, endTime, people);

                //create new sessionKey and session or update session under exist sessionKey
                if(getSessionKey==null) {
                    getSessionKey = helperFirebase.helperSession(getEventKey, getAgendaKey).push().getKey();
                    helperFirebase.helperSessionKey(getEventKey, getAgendaKey, getSessionKey).setValue(session);
                    Log.i(TAG, "New sessionKey and session created. sessionKey: " + getSessionKey);
                }else{
                    helperFirebase.helperSessionKey(getEventKey, getAgendaKey, getSessionKey).updateChildren(session.toHashMap());
                    Log.i(TAG, "session updated. sessionKey: " + getSessionKey);
                }
                backToControl();
            }
        });

        //cancel button back to ActivityControlSession
        Button cancel = (Button) view.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                backToControl();
            }
        });
        return view;
    }

    //get session from firebase with sessionKey and show in editText
    public void getSession(DataSnapshot dataSnapshot){
        for(DataSnapshot data : dataSnapshot.getChildren()){
            String key = data.getKey();

            if(key.equals(getSessionKey)) {
                mode.setText("Edit Session");
                ObjectSession a = (ObjectSession) data.getValue(ObjectSession.class);
                sessionName.setText(a.getSessionName());
                sessionInstitution.setText(a.getSessionInstitution());
                sessionLocation.setText(a.getSessionLocation());
                sessionStartTime.setText(a.getSessionStartTime());
                sessionEndTime.setText(a.getSessionEndTime());
                sessionPeople.setText(a.getSessionPeople());
                break;
            }
        }
    }

    //Intent to ActivityControlSession, default showing session list
    public void backToControl(){
        Intent i = new Intent(getActivity(), ActivityControlSession.class);
        i.putExtra("eventKey",getEventKey);
        i.putExtra("agendaKey",getAgendaKey);
        startActivity(i);
    }
}