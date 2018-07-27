package com.example.owner.internationalaset;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

/*
    activity home page

    BottomNavigationView switch depend on different user type
    general user: news, journals, events
    admin: news, journals, events, control

    events fragment: click on event intent to Activity MainEvent
    control fragment: intent to Activity ControlPanel
 */

public class HomePage extends HelperUserNavigationView implements FragmentListEvents.FragmentEventslistener,
        FragmentUserLogin.FragmentUserLoginlistener, FragmentUserRegPasscode.FragmentUserRegPasscodelistener,
        FragmentUserRegInfor.FragmentUserRegInforlistener{
    private Fragment fragment;
    private BottomNavigationView bottomNavigation;
    private static final String TAG = "HomePage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_drawer);
        fragment = null;

        userNavigation();

        //BottomNavigationView switch fragments, news, events, journals, control panel
        bottomNavigation = findViewById(R.id.toolbarBottom);
        HelperBottomNavigationView.disableShiftMode(bottomNavigation);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.news:
                    break;
                case R.id.events:
                    defaultFragment();
                    break;
                case R.id.control:
                    Intent i = new Intent(HomePage.this, ActivityControlPanel.class);
                    startActivity(i);
                default:
            }
            return true;
            }
        });

        backDefault();
    }

    //event listener listens event fragment, not in control mode, intent to activity mainEvent
    public void getEventKey(String k){
        Intent i = new Intent(HomePage.this, MainEvent.class);
        i.putExtra("eventKey", k);
        startActivity(i);
    }

    //set default fragment to event list
    public void defaultFragment(){
        Bundle bundle = new Bundle();
        fragment = new FragmentListEvents();
        fragment.setArguments(bundle);
        fragmentSwitch(fragment);
    }

    //FragmentUserLogin listener, back to default fragment
    public void backDefault(){
        defaultFragment();
        bottomNavigation.getMenu().getItem(1).setChecked(true);
    }

    public void userRegInfor(){
        fragment = new FragmentUserRegInfor();
        fragmentSwitch(fragment);
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
