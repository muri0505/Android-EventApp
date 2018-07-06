package com.example.owner.internationalaset;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class ActivityControlSession extends AppCompatActivity {
    private Fragment fragment;
    String eventKey = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_fragment);

        fragment = new FragmentModisySession();
        fragmentSwitch(fragment);

        Bundle i = getIntent().getExtras();
        eventKey = i.getString("eventKey");
    }

    public void fragmentSwitch(Fragment f) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment, f, "currentFragment");
        transaction.commit();
    }
}
