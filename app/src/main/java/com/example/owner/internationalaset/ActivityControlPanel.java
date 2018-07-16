package com.example.owner.internationalaset;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/*
    fragment admin control panel
    add/edit/delete events, agenda etc.
 */

public class ActivityControlPanel extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_panel);

        //button switch activity
        Button events = (Button) findViewById(R.id.controlEvent);
        events.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(ActivityControlPanel.this, ActivityControlEvent.class);
                startActivity(i);
            }
        });

        Button home = (Button) findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(ActivityControlPanel.this, HomePage.class);
                startActivity(i);
            }
        });

    }
}
