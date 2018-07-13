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

public class FragmentModifySession extends Fragment {
    private FirebaseHelper firebaseHelper = new FirebaseHelper();
    private ObjectSession session;
    private String getEventKey = null;
    private String getSessionKey = null;

    private TextView mode;
    private EditText sessionType;
    private EditText sessionName;
    private EditText sessionStartTime;
    private EditText sessionEndTime;
    private EditText sessionDes;
    private static final String TAG = "FragmentModifySession";

    public FragmentModifySession(){}

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_modify_session, container, false);

        mode = (TextView) view.findViewById(R.id.mode);
        sessionType= (EditText) view.findViewById(R.id.sessionType);
        sessionName = (EditText) view.findViewById(R.id.sessionName);
        sessionStartTime = (EditText) view.findViewById(R.id.sessionStartTime);
        sessionEndTime = (EditText) view.findViewById(R.id.sessionEndTime);
        sessionDes = (EditText) view.findViewById(R.id.sessionDes);

        getEventKey = getArguments().getString("eventKey");
        getSessionKey = getArguments().getString("sessionKey");

        if(getSessionKey != null) {
            firebaseHelper.helperSession(getEventKey).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    getSession(dataSnapshot);
                }

                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }
            });
        }else{mode.setText("Create Session");}

        Button edit = (Button) view.findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String type, name,startTime, endTime, des;
                type = sessionType.getText().toString();
                name = sessionName.getText().toString();
                startTime = sessionStartTime.getText().toString();
                endTime = sessionEndTime.getText().toString();
                des = sessionDes.getText().toString();

                session = new ObjectSession(type, name, startTime, endTime, des);

                if(getSessionKey==null) {
                    getSessionKey = firebaseHelper.helperSession(getEventKey).push().getKey();
                    firebaseHelper.helperSessionKey(getEventKey, getSessionKey).setValue(session);
                }else{
                    firebaseHelper.helperSessionKey(getEventKey, getSessionKey).updateChildren(session.toHashMap());
                }
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

    public void getSession(DataSnapshot dataSnapshot){
        for(DataSnapshot data : dataSnapshot.getChildren()){
            String key = data.getKey();

            if(key.equals(getSessionKey)) {
                mode.setText("Edit Session");
                ObjectSession s = (ObjectSession) data.getValue(ObjectSession.class);
                sessionType.setText(s.getSessionType());
                sessionName.setText(s.getSessionName());
                sessionStartTime.setText(s.getSessionStartTime());
                sessionEndTime.setText(s.getSessionEndTime());
                sessionDes.setText(s.getSessionDes());

                break;
            }
        }
    }

    public void backToControl(){
        Intent i = new Intent(getActivity(), ActivityControlSession.class);
        i.putExtra("eventKey",getEventKey);
        startActivity(i);
    }
}