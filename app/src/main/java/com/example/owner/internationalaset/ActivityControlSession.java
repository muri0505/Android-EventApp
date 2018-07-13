package com.example.owner.internationalaset;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/*
    ActivityControlSession: admin session control, get eventKey from previous activity/fragment,
    default showing all sessions, create&edit button intent to FragmentModifySession, delete button to delete data
    level button intent to ActivityControlAgenda
*/
public class ActivityControlSession extends AppCompatActivity implements FragmentListSession.FragmentSessionlistener{
    private FirebaseHelper firebaseHelper = new FirebaseHelper();
    private Fragment fragment;
    private String eventKey = null;
    private String sessionKey = null;
    private static final String TAG = "ActivityControlSession";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_fragment);

        //get&put database key
        Bundle i = getIntent().getExtras();
        eventKey = i.getString("eventKey");
        i.putString("eventKey", eventKey);
        Log.i(TAG,"Session list is under eventKey: "+eventKey);

        //default fragment, showing all sessions
        fragment = new FragmentListSession();
        fragment.setArguments(i);
        fragmentSwitch(fragment);

        //BottomNavigationView switch create, edit, delete and next level
        BottomNavigationView bottomNavigation = findViewById(R.id.toolbarBottom);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigation);
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
                            firebaseHelper.helperSessionKey(eventKey, sessionKey).removeValue();
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

    //fragmentSwitch
    public void fragmentSwitch(Fragment f) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment, f, "currentFragment");
        transaction.commit();
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
