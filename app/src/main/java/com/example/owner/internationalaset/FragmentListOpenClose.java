package com.example.owner.internationalaset;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

/*
    FragmentListOpenClose: showing session opening&closing information in MainEvent
 */
public class FragmentListOpenClose extends Fragment{
    private TextView sessionType;
    private TextView sessionName;
    private TextView sessionStartTime;
    private TextView sessionEndTime;
    private TextView sessionDes;
    private HelperFirebase helperFirebase = new HelperFirebase();
    private static final String TAG = "FragmentListOpenClose";

    private String getEventKey;
    private String getSessionKey;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_list_opening_closing, container, false);

        sessionType = (TextView) view.findViewById(R.id.sessionType);
        sessionName = (TextView) view.findViewById(R.id.sessionName);
        sessionStartTime = (TextView) view.findViewById(R.id.sessionStartTime);
        sessionEndTime = (TextView) view.findViewById(R.id.sessionEndTime);
        sessionDes = (TextView) view.findViewById(R.id.sessionDes);

        getEventKey = getArguments().getString("eventKey");
        getSessionKey = getArguments().getString("sessionKey");

        helperFirebase.helperSessionKey(getEventKey,getSessionKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ObjectSession s = dataSnapshot.getValue(ObjectSession.class);
                sessionType.setText(s.getSessionType());
                sessionName.setText(s.getSessionName());
                sessionStartTime.setText(s.getSessionStartTime());
                sessionDes.setText(s.getSessionDes());

                if(sessionEndTime != null){
                    sessionEndTime.setText("");
                    if(!sessionEndTime.equals("")){
                        sessionEndTime.setText(" To " + s.getSessionEndTime());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        return view;
    }
}
