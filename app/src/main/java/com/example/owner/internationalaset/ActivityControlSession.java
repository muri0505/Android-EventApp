package com.example.owner.internationalaset;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

/*
    ActivityControlSession: admin session control, get eventKey from previous activity/fragment,
    default showing all sessions, create&edit button intent to FragmentModifySession, delete button to delete data
    level button intent to article/keynote/general/opening/closing activity
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

        //setup default&toolbar
        fragmentSwitch(defaultFragment());
        helperControl(defaultFragment(),"Agenda");

        //BottomNavigationView switch create, edit, delete and next level
        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNavigation);
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
                    //check session select, intent to selected event's Activity based on session type
                    if(validKey(sessionKey)) {
                        helperFirebase.helperSessionKey(eventKey, sessionKey).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                 String type = dataSnapshot.child("sessionType").getValue().toString();
                                 //switch session type
                                 switch (type){
                                     case "General":
                                         break;
                                     case "Article":
                                         typeIntent(ActivityControlArticle.class, eventKey, sessionKey);
                                         break;
                                     case "Keynote Lecture":
                                         typeIntent(ActivityControlKeynote.class, eventKey, sessionKey);
                                         break;
                                 }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {}
                        });
                    }
                    break;
                default:
            }
            return true;
            }
        });
    }

    //default fragment, showing all sessions group by session date and switch with tabs
    public Fragment defaultFragment(){
        fragment = new FragmentListSessionTab();
        fragment.setArguments(i);
        fragmentSwitch(fragment);
        return fragment;
    }

    //FragmentListSession listener, get selected session key
    public void getSessionKey(String k){sessionKey = k;}

    //Intent to FragmentModifySession
    public void modifySession(String eventKey, String sessionKey){
        Bundle bundle = new Bundle();
        bundle.putString("eventKey", eventKey);
        bundle.putString("sessionKey", sessionKey);

        fragment = new FragmentModifySession();
        fragment.setArguments(bundle);
        fragmentSwitch(fragment);
        Log.i(TAG,"Intent to FragmentModifySession with eventKey:" + eventKey + " sessionKey: " + sessionKey);
    }

    //Intent to session type control
    public void typeIntent(Class activity, String eKey, String aKey){
        Intent i = new Intent(this, activity);
        i.putExtra("eventKey", eKey);
        i.putExtra("sessionKey", aKey);
        startActivity(i);
        Log.i(TAG,"Intent to " + activity + " with eventKey: "+ eKey + " sessionKey: " + aKey);
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
