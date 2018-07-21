package com.example.owner.internationalaset;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.MenuItem;

public class ActivityControlKeynote extends HelperControl implements FragmentListKeynote.FragmentKeynotelistener{
    private HelperFirebase helperFirebase = new HelperFirebase();
    private Fragment fragment;
    private String eventKey = null;
    private String sessionKey = null;
    private String keynoteKey = null;
    private Bundle i;
    private static final String TAG = "ActivityControlKeynote";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_fragment);

        //get&put database key
        i = getIntent().getExtras();
        eventKey = i.getString("eventKey");
        sessionKey = i.getString("sessionKey");
        i.putString("eventKey", eventKey);
        i.putString("sessionKey", sessionKey);
        Log.i(TAG,"Keynote is under eventKey: "+eventKey + " sessionKey: " + sessionKey);

        //setup default&toolbar
        fragmentSwitch(defaultFragment());
        helperControl(defaultFragment(),"Keynote");

        //BottomNavigationView switch create, edit, delete and next level
        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNavigation);
        HelperBottomNavigationView.disableShiftMode(bottomNavigation);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.create:
                        //button to create new keynote, clean selected keynote key, intent to FragmentModifyKeynote
                        keynoteKey = null;
                        modifyKeynote(eventKey,sessionKey,keynoteKey);
                        Log.i(TAG,"Create button clicked, create new keyNote");
                        break;
                    case R.id.edit:
                        //button to edit keynote, check keynote selected, intent to FragmentModifyKeynote
                        if(validKey(keynoteKey)){
                            modifyKeynote(eventKey,sessionKey,keynoteKey);
                            Log.i(TAG,"Edit button clicked, keynoteKey: " + keynoteKey);
                        }
                        break;
                    case R.id.delete:
                        //button to delete, check keynote selected, delete selected keynote
                        if(validKey(keynoteKey)){
                            helperFirebase.helperKeynoteKey(eventKey,sessionKey,keynoteKey).removeValue();
                            Log.i(TAG,"Delete button clicked, keynotKey: " + keynoteKey);
                        }
                        break;
                    case R.id.level:

                        break;
                    default:
                }
                return true;
            }
        });
    }

    //default fragment, showing all keynote
    public Fragment defaultFragment(){
        fragment = new FragmentListKeynote();
        fragment.setArguments(i);
        fragmentSwitch(fragment);
        return fragment;
    }

    //FragmentListKeynote listener, get selected keynote key
    public void getKeynoteKey(String k){keynoteKey = k;}

    //Intent to FragmentModifyKeynote
    public void modifyKeynote(String eventKey, String sessionKey, String keynoteKey){
        Bundle bundle = new Bundle();
        bundle.putString("eventKey", eventKey);
        bundle.putString("sessionKey", sessionKey);
        bundle.putString("keynoteKey", keynoteKey);

        fragment = new FragmentModifyKeynote();
        fragment.setArguments(bundle);
        fragmentSwitch(fragment);
        Log.i(TAG,"Intent to FragmentModifyKeynote with eventKey: " + eventKey + " sessionKey: " + sessionKey + "keynoteKey: " + keynoteKey);
    }
}

