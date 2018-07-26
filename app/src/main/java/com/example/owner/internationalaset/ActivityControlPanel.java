package com.example.owner.internationalaset;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/*
    fragment admin control panel
    add/edit/delete firebase data.
 */

public class ActivityControlPanel extends AppCompatActivity {
    private static final String TAG = "ActivityControlPanel";

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

        Button passcode = (Button) findViewById(R.id.passcode);
        passcode.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(ActivityControlPanel.this, ActivityControlPasscode.class);
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

    @Override
    protected void onStart(){
        super.onStart();
        Log.i(TAG, "start");
    }

    @Override
    protected  void onStop(){
        super.onStop();
        Log.i(TAG, "stop");
    }
}
