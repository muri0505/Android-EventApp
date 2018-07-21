package com.example.owner.internationalaset;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainEvent extends AppCompatActivity implements FragmentListSession.FragmentSessionlistener{
    private DrawerLayout menu;
    private ArrayList<ObjectEvent> event;
    private String eventKey;
    private String sessionKey;
    private Fragment fragment;
    private HelperFirebase helperFirebase = new HelperFirebase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_event);

        menu = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.menu);

        Intent i = getIntent();
        eventKey = i.getStringExtra("eventKey");

        fragment = new FragmentEventDetail();
        withEventKey(fragment);

        navigationView.setNavigationItemSelectedListener(
            new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem menuItem) {
                    menuItem.setChecked(true);
                    menu.closeDrawers();

                    if(menuItem.isChecked()){
                        switch (menuItem.getItemId()) {
                            case R.id.eventHome:
                                fragment = new FragmentEventDetail();
                                withEventKey(fragment);
                                break;
                            case R.id.agenda:
                                fragment = new FragmentListSession();
                                withEventKey(fragment);
                                break;
                        }
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

    public void withEventKey(Fragment f){
        Bundle bundle = new Bundle();
        bundle.putString("eventKey", eventKey);
        f.setArguments(bundle);
        fragmentSwitch(f);
    }

    public void getSessionKey(String k){
        sessionKey = k;

        helperFirebase.helperSessionKey(eventKey, k).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String type = dataSnapshot.child("sessionType").getValue().toString();

                    Bundle bundle = new Bundle();
                    bundle.putString("eventKey", eventKey);
                    bundle.putString("sessionKey", sessionKey);

                    //switch session type
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
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {}
            });
        }

}