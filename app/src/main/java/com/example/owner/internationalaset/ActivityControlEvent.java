package com.example.owner.internationalaset;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

/*
    ActivityControlEvent: admin event control,
    default showing all events, create&edit button intent to FragmentModifyEvent, delete button to delete data
    level button intent to ActivityControlSession
*/
public class ActivityControlEvent extends HelperControl implements FragmentListEvents.FragmentEventslistener{
    private HelperFirebase helperFirebase = new HelperFirebase();
    private Fragment fragment;
    private String eventKey = null;
    private static final String TAG = "ActivityControlEvent";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_fragment);

        //setup default&toolbar
        fragmentSwitch(defaultFragment());
        helperControl(defaultFragment(),"Event");

        //BottomNavigationView switch create, edit, delete and next level
        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNavigation);
        HelperBottomNavigationView.disableShiftMode(bottomNavigation);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.create:
                        //button to create new event, clean selected event key, intent to FragmentModifyEvent
                        eventKey = null;
                        modifyEvent(eventKey);
                        Log.i(TAG, "Create button clicked, create new event");
                        break;
                    case R.id.edit:
                        //button to edit event, check event selected, intent to FragmentModifyEvent
                        if (validKey(eventKey)) {
                            modifyEvent(eventKey);
                            Log.i(TAG, "Edit button clicked, eventKey: " + eventKey);
                        }
                        break;
                    case R.id.delete:
                        //button to delete, check event selected, delete selected event
                        if (validKey(eventKey)) {
                            helperFirebase.helperEventKey(eventKey).removeValue();
                            Log.i(TAG, "Delete button clicked, eventKey: " + eventKey);
                        }
                        break;
                    case R.id.level:
                        //check event select, intent to selected event's ActivityControlSession
                        if (validKey(eventKey)) {
                            Intent i = new Intent(ActivityControlEvent.this, ActivityControlSession.class);
                            i.putExtra("eventKey", eventKey);
                            startActivity(i);
                            Log.i(TAG, "Intent to ActivityControlSession with eventKey: " + eventKey);
                        }
                        break;
                    default:
                }
                return true;
            }
        });

        //bottomNavigation level setup
        bottomNavigation.getMenu().findItem(R.id.level).setTitle("session");
    }

    //default fragment, showing all events
    public Fragment defaultFragment(){
        Bundle bundle = new Bundle();
        fragment = new FragmentListEvents();
        fragment.setArguments(bundle);
        return fragment;
    }

    //FragmentListEvents listener, get selected event key
    public void getEventKey(String k){
        eventKey = k;
    }

    //Intent to FragmentModifyEvent
    public void modifyEvent(String eventKey){
        Bundle bundle = new Bundle();
        bundle.putString("eventKey", eventKey);
        fragment = new FragmentModifyEvent();
        fragment.setArguments(bundle);
        fragmentSwitch(fragment);
        Log.i(TAG,"Intent to FragmentModifyEvent with eventKey: " + eventKey);
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
