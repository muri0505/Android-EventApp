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
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

/*
    FragmentModifySession: get eventKey&sessionKey from ActivityControlSession
    check valid sessionKey and get session from firebase or create new sessionKey and new session
    Edit button to push update/new session, cancel button back to ActivityControlSession
 */
public class FragmentModifySession extends HelperDateTime {
    private HelperFirebase helperFirebase = new HelperFirebase();
    private ObjectSession session;
    private String getEventKey = null;
    private String getSessionKey = null;

    private TextView mode;
    private EditText sessionDate;
    private Spinner sessionType;
    private EditText sessionName;
    private EditText sessionStartTime;
    private EditText sessionEndTime;
    private EditText sessionDes;
    private static final String TAG = "FragmentModifySession";

    public FragmentModifySession(){}

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_modify_session, container, false);

        mode = (TextView) view.findViewById(R.id.mode);
        sessionDate= (EditText) view.findViewById(R.id.sessionDate);
        sessionType= (Spinner) view.findViewById(R.id.sessionType);
        sessionName = (EditText) view.findViewById(R.id.sessionName);
        sessionStartTime = (EditText) view.findViewById(R.id.sessionStartTime);
        sessionEndTime = (EditText) view.findViewById(R.id.sessionEndTime);
        sessionDes = (EditText) view.findViewById(R.id.sessionDes);

        //input keyboard format
        sessionDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDate(sessionDate);
            }
        });
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

        //get eventKey&sessionKey from ActivityControlSession
        getEventKey = getArguments().getString("eventKey");
        getSessionKey = getArguments().getString("sessionKey");
        Log.i(TAG,"Get eventKey and sessionKey from ActivityControlSession " + getEventKey + ", " + getSessionKey);

        //check valid sessionKey to get session
        if(getSessionKey != null) {
            Log.i(TAG,"Mode: Edit Session");
            helperFirebase.helperSession(getEventKey).addValueEventListener(new ValueEventListener() {
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
                String date, type, name,startTime, endTime, des;
                date = sessionDate.getText().toString();
                type = sessionType.getSelectedItem().toString();
                name = sessionName.getText().toString();
                startTime = sessionStartTime.getText().toString();
                endTime = sessionEndTime.getText().toString();
                des = sessionDes.getText().toString();
                session = new ObjectSession(date, type, name, startTime, endTime, des);

                //create new sessionKey and session or update session under exist sessionKey
                if(getSessionKey==null) {
                    getSessionKey = helperFirebase.helperSession(getEventKey).push().getKey();
                    helperFirebase.helperSessionKey(getEventKey, getSessionKey).setValue(session);
                    Log.i(TAG, "New sessionKey and session created. sessionKey: " + getSessionKey);
                }else{
                    helperFirebase.helperSessionKey(getEventKey, getSessionKey).updateChildren(session.toHashMap());
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
                ObjectSession s = (ObjectSession) data.getValue(ObjectSession.class);
                sessionDate.setText(s.getSessionDate());
                sessionType.setSelection(spinnerPos(s.getSessionType()));
                sessionName.setText(s.getSessionName());
                sessionStartTime.setText(s.getSessionStartTime());
                sessionEndTime.setText(s.getSessionEndTime());
                sessionDes.setText(s.getSessionDes());
                break;
            }
        }
    }

    //Intent to ActivityControlSession, default showing session list
    public void backToControl(){
        Intent i = new Intent(getActivity(), ActivityControlSession.class);
        i.putExtra("eventKey",getEventKey);
        startActivity(i);
        Log.i(TAG, "Intent to ActivityControlSession");
    }

    //get session type
    public int spinnerPos(String type){
        for(int i = 0; i < sessionType.getCount(); i++){
            if (sessionType.getItemAtPosition(i).toString().equals(type))
                    return i;
        }
        return 0;
    }
}