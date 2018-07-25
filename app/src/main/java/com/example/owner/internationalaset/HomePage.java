package com.example.owner.internationalaset;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

/*
    activity home page

    BottomNavigationView switch depend on different user type
    general user: news, journals, events
    admin: news, journals, events, control

    events fragment: click on event intent to Activity MainEvent
    control fragment: intent to Activity ControlPanel
 */

public class HomePage extends AppCompatActivity implements FragmentListEvents.FragmentEventslistener{
    private Fragment fragment;
    private static final String TAG = "HomePage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        fragment = null;

        //BottomNavigationView switch fragments, news, events, journals, control panel
        BottomNavigationView bottomNavigation = findViewById(R.id.toolbarBottom);
        HelperBottomNavigationView.disableShiftMode(bottomNavigation);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.events:
                        Bundle bundle = new Bundle();
                        fragment = new FragmentListEvents();
                        fragment.setArguments(bundle);
                        fragmentSwitch(fragment);
                        break;
                    case R.id.control:
                        Intent i = new Intent(HomePage.this, ActivityControlPanel.class);
                        startActivity(i);
                    default:
                }
                return true;
            }
        });

        //navigationView switch fragment
        NavigationView navigationView = findViewById(R.id.user);
        navigationView.setNavigationItemSelectedListener(
            new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem menuItem) {
                    menuItem.setChecked(true);

                    if(menuItem.isChecked()){
                        switch (menuItem.getItemId()) {

                        }
                    }
                    return true;
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

    //event listener listens event fragment, not in control mode, intent to activity mainEvent
    public void getEventKey(String k){
        Intent i = new Intent(HomePage.this, MainEvent.class);
        i.putExtra("eventKey", k);
        startActivity(i);
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
