package com.example.owner.internationalaset;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/*
    activity general home page
 */

public class HomePage extends AppCompatActivity implements FragmentListEvents.FragmentEventslistener{
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        fragment = null;

        final Button events = (Button) findViewById(R.id.events);
        events.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                fragment = new FragmentListEvents();
                fragmentSwitch(fragment);
            }
        });

        final Button news = (Button) findViewById(R.id.news);
        news.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                fragment = new FragmentControlPanel();
                fragmentSwitch(fragment);
            }
        });

    }

    //fragment changes
    public void fragmentSwitch(Fragment f) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment, f, "currentFragment");
        transaction.commit();
    }

    public void getEventKey(String eventKey){}
}
