package com.example.owner.internationalaset;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

public class FragmentModisySession extends Fragment {
    DatabaseReference mDatabase;
    String getEventName;

    TextView mode;
    EditText sessionType;
    EditText sessionName;
    EditText sessionStartTime;
    EditText sessionEndTime;
    EditText sessionDes;
    Button edit;

    public FragmentModisySession(){}

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_modify_session, container, false);

        mode = (TextView) view.findViewById(R.id.mode);
        sessionType= (EditText) view.findViewById(R.id.sessionType);
        sessionName = (EditText) view.findViewById(R.id.sessionName);
        sessionStartTime = (EditText) view.findViewById(R.id.sessionStartTime);
        sessionEndTime = (EditText) view.findViewById(R.id.sessionEndTime);
        sessionDes = (EditText) view.findViewById(R.id.sessionDes);
        edit = (Button) view.findViewById(R.id.edit);
        
        Button edit = (Button) view.findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });
        return view;
    }
}