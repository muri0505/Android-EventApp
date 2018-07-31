package com.example.owner.internationalaset;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.MenuItem;

public class ActivityControlUser extends HelperControl implements FragmentListUser.FragmentUserlistener, FragmentUserRegInfor.FragmentUserRegInforlistener{
    private Fragment fragment;
    private HelperFirebase helperFirebase = new HelperFirebase();
    private String userKey = null;
    private static final String TAG = "ActivityControlUser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_fragment);

        //setup default&toolbar
        fragmentSwitch(defaultFragment());
        helperControl(defaultFragment(),"User");

        //BottomNavigationView switch create, delete
        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNavigation);
        bottomNavigation.getMenu().removeItem(R.id.level);
        bottomNavigation.getMenu().removeItem(R.id.edit);
        HelperBottomNavigationView.disableShiftMode(bottomNavigation);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.create:
                        fragment = new FragmentUserRegInfor();
                        fragmentSwitch(fragment);
                        break;
                    case R.id.delete:
                        if (validKey(userKey)) {
                            helperFirebase.helperUserKey(userKey).removeValue();
                            Log.i(TAG, "Delete button clicked, userKey: " + userKey);
                        }
                        break;
                    default:
                }
                return true;
            }
        });
    }

    //default fragment, showing all user account
    public Fragment defaultFragment(){
        fragment = new FragmentListUser();
        fragmentSwitch(fragment);
        return fragment;
    }


    //listener
    public void getUserKey(String k ){userKey = k;}
    public void backDefault(){
        fragmentSwitch(defaultFragment());
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

