package com.example.owner.internationalaset;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

/*
    ActivityControlSession: admin session control, get eventKey&agendaKey from previous activity/fragment,
    default showing all session, create&edit button intent to FragmentModifySession, delete button to delete data
*/
public class ActivityControlSession extends HelperControl implements FragmentListSession.FragmentSessionlistener{
    private HelperFirebase helperFirebase = new HelperFirebase();
    private Fragment fragment;
    private String eventKey = null;
    private String agendaKey = null;
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
        agendaKey = i.getString("agendaKey");
        i.putString("eventKey", eventKey);
        i.putString("agendaKey", agendaKey);
        Log.i(TAG,"Session list is under eventKey: "+eventKey + " agendaKey: " + agendaKey);

        //setup default&toolbar
        fragmentSwitch(defaultFragment());
        helperControl(defaultFragment(),"Session");

        //BottomNavigationView switch create, edit, delete and next level
        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNavigation);
        HelperBottomNavigationView.disableShiftMode(bottomNavigation);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.create:
                        //button to create new session, clean selected session key, intent to FragmentModifySession
                        sessionKey = null;
                        modifySession(eventKey,agendaKey,sessionKey);
                        Log.i(TAG,"Create button clicked, create new session");
                        break;
                    case R.id.edit:
                        //button to edit session, check session selected, intent to FragmentModifySession
                        if(validKey(sessionKey)){
                            modifySession(eventKey,agendaKey,sessionKey);
                            Log.i(TAG,"Edit button clicked, sessionKey: " + sessionKey);
                        }
                        break;
                    case R.id.delete:
                        //button to delete, check session selected, delete selected session
                        if(validKey(sessionKey)){
                            helperFirebase.helperSessionKey(eventKey,agendaKey,sessionKey).removeValue();
                            Log.i(TAG,"Delete button clicked, sessionKey: " + sessionKey);
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

    //default fragment, showing all session
    public Fragment defaultFragment(){
        fragment = new FragmentListSession();
        fragment.setArguments(i);
        fragmentSwitch(fragment);
        return fragment;
    }

    //FragmentListSession listener, get selected session key
    public void getSessionKey(String k){sessionKey = k;}

    //Intent to FragmentModifySession
    public void modifySession(String eventKey, String agendaKey, String sessionKey){
        Bundle bundle = new Bundle();
        bundle.putString("eventKey", eventKey);
        bundle.putString("agendaKey", agendaKey);
        bundle.putString("sessionKey", sessionKey);

        fragment = new FragmentModifySession();
        fragment.setArguments(bundle);
        fragmentSwitch(fragment);
        Log.i(TAG,"Intent to FragmentModifySession with eventKey: " + eventKey + " agendaKey: " + agendaKey + "sessionKey: " + sessionKey);
    }
}

