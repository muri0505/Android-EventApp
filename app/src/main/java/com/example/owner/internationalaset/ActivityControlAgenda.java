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
    ActivityControlAgenda: admin agenda control, get eventKey from previous activity/fragment,
    default showing all agendas, create&edit button intent to FragmentModifyAgenda, delete button to delete data
    level button intent to ActivityControlSession
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
                        //check agenda select, intent to selected event's ActivityControlSession
                        if(validKey(agendaKey)) {
                            Intent i = new Intent(ActivityControlAgenda.this, ActivityControlSession.class);
                            i.putExtra("eventKey", eventKey);
                            i.putExtra("agendaKey", agendaKey);
                            startActivity(i);
                            Log.i(TAG,"Intent to ActivityControlSession with eventKey: "+ eventKey + " agendaKey: " + agendaKey);
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
        fragment = new FragmentListAgendaTab();
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
}
