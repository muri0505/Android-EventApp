package com.example.owner.internationalaset;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;


public class ControlSession extends AppCompatActivity implements FragmentSearchEvent.FragmentSearchEventListener{
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_fragment);

        //deafult search event fragment
        fragment = new FragmentSearchEvent();
        fragmentSwitch(fragment);
    }

    //fragment switch
    public void fragmentSwitch(Fragment f) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment, f, "currentFragment");
        transaction.commit();
    }

    //listen on search event submit button, pass event name to event information
    public void SearchListeren(String eventName) {
        Bundle bundle = new Bundle();
        bundle.putString("eventName", eventName);
        fragment = new FragmentEventInformation();
        fragment.setArguments(bundle);
        fragmentSwitch(fragment);
    }
}
