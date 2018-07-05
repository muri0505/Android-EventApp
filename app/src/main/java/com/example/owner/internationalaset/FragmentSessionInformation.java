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

public class FragmentSessionInformation extends Fragment {
    DatabaseReference mDatabase;
    String getEventName;
    String key;
    ObjectEvent event;

    TextView mode;
    EditText sessionType;
    EditText sessionName;
    EditText sessionStartTime;
    EditText sessionEndTime;
    EditText sessionDes;
    Button editadd;

    public FragmentSessionInformation(){}

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_session_information, container, false);

        mode = (TextView) view.findViewById(R.id.mode);
        sessionType= (EditText) view.findViewById(R.id.sessionType);
        sessionName = (EditText) view.findViewById(R.id.sessionName);
        sessionStartTime = (EditText) view.findViewById(R.id.sessionStartTime);
        sessionEndTime = (EditText) view.findViewById(R.id.sessionEndTime);
        sessionDes = (EditText) view.findViewById(R.id.sessionDes);
        editadd = (Button) view.findViewById(R.id.editadd);

        getEventName = getArguments().getString("eventName");



        return view;
    }

}