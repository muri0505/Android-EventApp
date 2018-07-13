package com.example.owner.internationalaset;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

/*
    ActivityControlSession: admin session control, get eventKey from previous activity/fragment,
    default showing all sessions, create&edit button intent to FragmentModifySession, delete button to delete data
    level button intent to ActivityControlAgenda
*/
public class ActivityControlSession extends HelperControl implements FragmentListSession.FragmentSessionlistener{
    private HelperFirebase helperFirebase = new HelperFirebase();
    private Fragment fragment;
    private String eventKey = null;
    private String sessionKey = null;
    private Bundle i;
    private static final String TAG = "ActivityControlSession";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_fragment);

        //get&put database key
        i = getIntent().getExtras();
        eventKey = i.getString("eventKey");
        i.putString("eventKey", eventKey);
        Log.i(TAG,"Session list is under eventKey: "+eventKey);

        fragmentSwitch(defaultFragment());
        toolbarTop(defaultFragment());

        //BottomNavigationView switch create, edit, delete and next level
        BottomNavigationView bottomNavigation = findViewById(R.id.toolbarBottom);
        HelperBottomNavigationView.disableShiftMode(bottomNavigation);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.create:
                        //button to create new session, clean selected session key, intent to FragmentModifySession
                        sessionKey = null;
                        modifySession(eventKey,sessionKey);
                        Log.i(TAG,"Create button clicked, create new session");
                        break;
                    case R.id.edit:
                        //button to edit session, check session selected, intent to FragmentModifySession
                        if(validKey(sessionKey)){
                            modifySession(eventKey, sessionKey);
                            Log.i(TAG,"Edit button clicked, sessionKey: " + sessionKey);
                        }
                        break;
                    case R.id.delete:
                        //button to delete, check session selected, delete selected session
                        if(validKey(sessionKey)){
                            helperFirebase.helperSessionKey(eventKey, sessionKey).removeValue();
                            Log.i(TAG,"Delete button clicked, sessionKey: " + sessionKey);
                        }
                        break;
                    case R.id.level:
                        //check session select, intent to selected event's ActivityControlAgenda
                        if(validKey(sessionKey)) {
                            Intent i = new Intent(ActivityControlSession.this, ActivityControlAgenda.class);
                            i.putExtra("eventKey", eventKey);
                            i.putExtra("sessionKey", sessionKey);
                            startActivity(i);
                            Log.i(TAG,"Intent to ActivityControlAgenda with eventKey: "+ eventKey + " sessionKey: " + sessionKey);
                        }
                        break;
                    default:
                }
                return true;
            }
        });
    }

    //default fragment, showing all sessions
    public Fragment defaultFragment(){
        fragment = new FragmentListSession();
        fragment.setArguments(i);
        fragmentSwitch(fragment);
        return fragment;
    }

    //FragmentListSession listener, get selected session key
    public void getSessionKey(String k){sessionKey = k;}

    //intent to FragmentModifySession
    public void modifySession(String eventKey, String sessionKey){
        Bundle bundle = new Bundle();
        bundle.putString("eventKey", eventKey);
        bundle.putString("sessionKey", sessionKey);

        fragment = new FragmentModifySession();
        fragment.setArguments(bundle);
        fragmentSwitch(fragment);
        Log.i(TAG,"Intent to FragmentModifySession with eventKey:" + eventKey + " sessionKey: " + sessionKey);
    }

    //check session selected
    public boolean validKey(String key){
        if(key == null){
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(ActivityControlSession.this, "Please select a session", duration);
            toast.show();
            return false;
        }
        return true;
    }
}
