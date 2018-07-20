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
    ActivityControlAgenda: admin agenda control, get eventKey from previous activity/fragment,
    default showing all agendas, create&edit button intent to FragmentModifyAgenda, delete button to delete data
    level button intent to session/keynote/general/opening/closing activity
*/
public class ActivityControlAgenda extends HelperControl implements FragmentListAgenda.FragmentAgendalistener{
    private HelperFirebase helperFirebase = new HelperFirebase();
    private Fragment fragment;
    private String eventKey = null;
    private String agendaKey = null;
    private Bundle i;
    private static final String TAG = "ActivityControlAgenda";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_fragment);

        //get&put database key
        i = getIntent().getExtras();
        eventKey = i.getString("eventKey");
        i.putString("eventKey", eventKey);
        Log.i(TAG,"Agenda list is under eventKey: "+eventKey);

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
                        //button to create new agenda, clean selected agenda key, intent to FragmentModifyAgenda
                        agendaKey = null;
                        modifyAgenda(eventKey,agendaKey);
                        Log.i(TAG,"Create button clicked, create new agenda");
                        break;
                    case R.id.edit:
                        //button to edit agenda, check agenda selected, intent to FragmentModifyAgenda
                        if(validKey(agendaKey)){
                            modifyAgenda(eventKey, agendaKey);
                            Log.i(TAG,"Edit button clicked, agendaKey: " + agendaKey);
                        }
                        break;
                    case R.id.delete:
                        //button to delete, check agenda selected, delete selected agenda
                        if(validKey(agendaKey)){
                            helperFirebase.helperAgendaKey(eventKey, agendaKey).removeValue();
                            Log.i(TAG,"Delete button clicked, agendaKey: " + agendaKey);
                        }
                        break;
                    case R.id.level:
                        //check agenda select, intent to selected event's Activity based on agenda type
                        if(validKey(agendaKey)) {
                            helperFirebase.helperAgendaKey(eventKey, agendaKey).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                     String type = dataSnapshot.child("agendaType").getValue().toString();
                                     //switch agenda type
                                     switch (type){
                                         case "General":
                                             break;
                                         case "Session":
                                             typeIntent(ActivityControlSession.class, eventKey, agendaKey);
                                             break;
                                         case "Keynote Lecture":
                                             typeIntent(ActivityControlKeynote.class, eventKey, agendaKey);
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

    //default fragment, showing all agendas
    public Fragment defaultFragment(){
        fragment = new FragmentListAgenda();
        i.putBoolean("controlMode", true);
        fragment.setArguments(i);
        fragmentSwitch(fragment);
        return fragment;
    }

    //FragmentListAgenda listener, get selected agenda key
    public void getAgendaKey(String k){agendaKey = k;}

    //Intent to FragmentModifyAgenda
    public void modifyAgenda(String eventKey, String agendaKey){
        Bundle bundle = new Bundle();
        bundle.putString("eventKey", eventKey);
        bundle.putString("agendaKey", agendaKey);

        fragment = new FragmentModifyAgenda();
        fragment.setArguments(bundle);
        fragmentSwitch(fragment);
        Log.i(TAG,"Intent to FragmentModifyAgenda with eventKey:" + eventKey + " agendaKey: " + agendaKey);
    }

    //Intent to agenda type control
    public void typeIntent(Class activity, String eKey, String aKey){
        Intent i = new Intent(this, activity);
        i.putExtra("eventKey", eKey);
        i.putExtra("agendaKey", aKey);
        startActivity(i);
        Log.i(TAG,"Intent to " + activity + " with eventKey: "+ eKey + " agendaKey: " + aKey);
    }
}
