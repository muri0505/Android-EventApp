package com.example.owner.internationalaset;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FragmentModifySession extends Fragment {
    DatabaseReference mDatabase;
    ObjectSession session;
    String getEventKey;
    String getSessionKey = null;
    String type;
    String name;
    String startTime;
    String endTime;
    String des;

    TextView mode;
    EditText sessionType;
    EditText sessionName;
    EditText sessionStartTime;
    EditText sessionEndTime;
    EditText sessionDes;
    Button edit;

    public FragmentModifySession(){}

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_modify_session, container, false);

        mode = (TextView) view.findViewById(R.id.mode);
        sessionType= (EditText) view.findViewById(R.id.sessionType);
        sessionName = (EditText) view.findViewById(R.id.sessionName);
        sessionStartTime = (EditText) view.findViewById(R.id.sessionStartTime);
        sessionEndTime = (EditText) view.findViewById(R.id.sessionEndTime);
        sessionDes = (EditText) view.findViewById(R.id.sessionDes);
        edit = (Button) view.findViewById(R.id.edit);

        getEventKey = getArguments().getString("eventKey");
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Events").child(getEventKey).child("Session");

        Button edit = (Button) view.findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                type = sessionType.getText().toString();
                name = sessionName.getText().toString();
                startTime = sessionStartTime.getText().toString();
                endTime = sessionEndTime.getText().toString();
                des = sessionDes.getText().toString();

                session = new ObjectSession(type, name, startTime, endTime, des);
                if(getSessionKey==null){getSessionKey = mDatabase.push().getKey();}
                mDatabase.child(getSessionKey).setValue(session);
                getActivity().finish();
            }
        });
        return view;
    }
}