package com.example.owner.internationalaset;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class ActivityControlPasscode extends HelperControl implements FragmentListPasscode.FragmentPasscodelistener{
    private HelperFirebase helperFirebase = new HelperFirebase();
    private Fragment fragment;
    private String passcodeKey = null;
    private static final String TAG = "ActivityControlPasscode";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_fragment);

        //setup default&toolbar
        fragmentSwitch(defaultFragment());
        helperControl(defaultFragment(),"Passcode");

        //BottomNavigationView switch create, edit, delete
        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNavigation);
        bottomNavigation.getMenu().removeItem(R.id.level);
        HelperBottomNavigationView.disableShiftMode(bottomNavigation);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.create:
                        //button to create new passcode, clean selected passcode key, intent to FragmentModifyPasscode
                        passcodeKey = null;
                        modifyPasscode(passcodeKey);
                        Log.i(TAG, "Create button clicked, create new passcode");
                        break;
                    case R.id.edit:
                        //button to edit passcode, check passcode selected, intent to FragmentModifyPasscode
                        if (validKey(passcodeKey)) {
                            modifyPasscode(passcodeKey);
                            Log.i(TAG, "Edit button clicked, passcodeKey: " + passcodeKey);
                        }
                        break;
                    case R.id.delete:
                        //button to delete, check passcode selected, delete selected passcode
                        if (validKey(passcodeKey)) {
                            helperFirebase.helperPasscodeKey(passcodeKey).removeValue();
                            Log.i(TAG, "Delete button clicked, passcodeKey: " + passcodeKey);
                        }
                        break;
                }
                return true;
            }
        });
    }

    //default fragment, showing all passcode
    public Fragment defaultFragment(){
        Bundle bundle = new Bundle();
        fragment = new FragmentListPasscode();
        fragment.setArguments(bundle);
        return fragment;
    }

    public void getPasscodeKey(String k){passcodeKey = k;}

    //Intent to FragmentModifyPasscode
    public void modifyPasscode(String passcodeKey){
        Bundle bundle = new Bundle();
        bundle.putString("passcodeKey", passcodeKey);
        fragment = new FragmentModifyPasscode();
        fragment.setArguments(bundle);
        fragmentSwitch(fragment);
        Log.i(TAG,"Intent to FragmentModifyPasscode with passcodeKey: " + passcodeKey);
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
