package com.example.owner.internationalaset;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

/*
    ActivityControlAgenda: admin agenda control, get eventKey&sessionKey from previous activity/fragment,
    default showing all agenda, create&edit button intent to FragmentModifyAgenda, delete button to delete data
*/
public class ActivityControlAgenda extends HelperControl implements FragmentListAgenda.FragmentAgendalistener{
    private HelperFirebase helperFirebase = new HelperFirebase();
    private Fragment fragment;
    private String eventKey = null;
    private String sessionKey = null;
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
        sessionKey = i.getString("sessionKey");
        i.putString("eventKey", eventKey);
        i.putString("sessionKey", sessionKey);
        Log.i(TAG,"Agenda list is under eventKey: "+eventKey + " sessionKey: " + sessionKey);

        fragmentSwitch(defaultFragment());
        toolbarTop(defaultFragment());

        //BottomNavigationView switch create, edit, delete and next level
        BottomNavigationView bottomNavigation = findViewById(R.id.toolbarBottom);
        HelperBottomNavigationView.disableShiftMode(bottomNavigation);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.create:
                        //button to create new agenda, clean selected agenda key, intent to FragmentModifyAgenda
                        agendaKey = null;
                        modifyAgenda(eventKey,sessionKey,agendaKey);
                        Log.i(TAG,"Create button clicked, create new agenda");
                        break;
                    case R.id.edit:
                        //button to edit agenda, check agenda selected, intent to FragmentModifyAgenda
                        if(validKey(agendaKey)){
                            modifyAgenda(eventKey,sessionKey,agendaKey);
                            Log.i(TAG,"Edit button clicked, agendaKey: " + agendaKey);
                        }
                        break;
                    case R.id.delete:
                        //button to delete, check agenda selected, delete selected agenda
                        if(validKey(agendaKey)){
                            helperFirebase.helperAgendaKey(eventKey,sessionKey,agendaKey).removeValue();
                            Log.i(TAG,"Delete button clicked, agendaKey: " + agendaKey);
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

    //default fragment, showing all agenda
    public Fragment defaultFragment(){
        fragment = new FragmentListAgenda();
        fragment.setArguments(i);
        fragmentSwitch(fragment);
        return fragment;
    }

    //FragmentListAgenda listener, get selected agenda key
    public void getAgendaKey(String k){agendaKey = k;}

    //intent to FragmentModifyAgenda
    public void modifyAgenda(String eventKey, String sessionKey, String agendaKey){
        Bundle bundle = new Bundle();
        bundle.putString("eventKey", eventKey);
        bundle.putString("sessionKey", sessionKey);
        bundle.putString("agendaKey", agendaKey);

        fragment = new FragmentModifyAgenda();
        fragment.setArguments(bundle);
        fragmentSwitch(fragment);
        Log.i(TAG,"Intent to FragmentModifyAgenda with eventKey: " + eventKey + " sessionKey: " + sessionKey + "agendaKey: " + agendaKey);
    }

    //check agenda selected
    public boolean validKey(String key){
        if(key == null){
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(ActivityControlAgenda.this, "Please select an agenda", duration);
            toast.show();
            return false;
        }
        return true;
    }
}

