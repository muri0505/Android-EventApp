package com.example.owner.internationalaset;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/*
    Activity MainEvent: default fragment shows event detail,
    NavigationView switch fragments/activity: homePage, mainEvent, session etc.
*/

public class MainEvent extends AppCompatActivity implements FragmentListSession.FragmentSessionlistener,
        FragmentListArticle.FragmentArticlelistener, FragmentListKeynote.FragmentKeynotelistener{

    private DrawerLayout menu;
    private ArrayList<ObjectEvent> event;
    private String eventKey;
    private String sessionKey;
    private Fragment fragment;
    private HelperFirebase helperFirebase;
    private ActionBarDrawerToggle toggle;
    private static final String TAG = "MainEvent";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_event);

        //setup NavigationView and actionBar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        menu = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.menu);
        toggle = new ActionBarDrawerToggle(this, menu, R.string.navigation_drawer_open, R.string.navigation_drawer_close){};
        menu.setDrawerListener(toggle);
        toggle.syncState();

        helperFirebase = new HelperFirebase();

        Intent i = getIntent();
        eventKey = i.getStringExtra("eventKey");

        //default fragment, show event detail
        fragment = new FragmentEventDetail();
        withEventKey(fragment);

        //navigationView switch fragment
        navigationView.setNavigationItemSelectedListener(
            new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem menuItem) {
                    menuItem.setChecked(true);
                    menu.closeDrawers();

                    if(menuItem.isChecked()){
                        switch (menuItem.getItemId()) {
                            case R.id.homePage:
                                Intent i = new Intent(MainEvent.this, HomePage.class);
                                startActivity(i);
                                break;
                            case R.id.eventHome:
                                fragment = new FragmentEventDetail();
                                withEventKey(fragment);
                                break;
                            case R.id.agenda:
                                fragment = new FragmentListSessionTab();
                                withEventKey(fragment);
                                break;
                        }
                    }
                    return true;
                }
            });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (toggle.onOptionsItemSelected(item)){return true;}
        return super.onOptionsItemSelected(item);
    }

    //fragmentSwitch
    public void fragmentSwitch(Fragment f) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment, f, "currentFragment");
        transaction.commit();
    }

    //setup fragment with eventKey
    public void withEventKey(Fragment f){
        Bundle bundle = new Bundle();
        bundle.putString("eventKey", eventKey);
        f.setArguments(bundle);
        fragmentSwitch(f);
    }

    //ession listener listens, get Selected sessionKey switch type fragment
    public void getSessionKey(String k){
        sessionKey = k;

        helperFirebase.helperSessionKey(eventKey, k).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String type = dataSnapshot.child("sessionType").getValue().toString();

                Bundle bundle = new Bundle();
                bundle.putString("eventKey", eventKey);
                bundle.putString("sessionKey", sessionKey);

                switch (type){
                    case "General":
                        break;
                    case "Article":
                        fragment = new FragmentListArticle();
                        fragment.setArguments(bundle);
                        fragmentSwitch(fragment);
                        break;
                    case "Keynote Lecture":
                        fragment = new FragmentListKeynote();
                        fragment.setArguments(bundle);
                        fragmentSwitch(fragment);
                        break;
                    case "Official Opening":
                        fragment = new FragmentListOpenClose();
                        fragment.setArguments(bundle);
                        fragmentSwitch(fragment);
                        break;
                    case "Official Closing":
                        fragment = new FragmentListOpenClose();
                        fragment.setArguments(bundle);
                        fragmentSwitch(fragment);
                        break;
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    //listener listens, do nothing
    public void getKeynoteKey(String k){}
    public void getArticleKey(String k){}

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