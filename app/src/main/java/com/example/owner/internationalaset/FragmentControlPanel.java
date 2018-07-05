package com.example.owner.internationalaset;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/*
    fragment admin control panel
    add/edit/delete events, session etc.
 */

public class FragmentControlPanel extends Fragment {
    public FragmentControlPanel(){}

    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_control_panel, container, false);

        //button switch activity
        Button events = (Button) view.findViewById(R.id.controlEvent);
        events.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ControlEvent.class);
                startActivity(i);
            }
        });

        Button session = (Button) view.findViewById(R.id.controlSession);
        session.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ControlSession.class);
                startActivity(i);
            }
        });

        return view;
    }
}
