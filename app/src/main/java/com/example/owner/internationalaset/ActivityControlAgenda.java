package com.example.owner.internationalaset;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/*
    ActivityControlAgenda: admin agenda control, get eventKey&sessionKey from previous activity/fragment,
    default showing all agenda, create&edit button intent to FragmentModifyAgenda, delete button to delete data
*/
public class ActivityControlAgenda extends AppCompatActivity implements FragmentListAgenda.FragmentAgendalistener{
    FirebaseHelper firebaseHelper = new FirebaseHelper();
    private Fragment fragment;
    String eventKey = null;
    String sessionKey = null;
    String agendaKey = null;
    private static final String TAG = "ActivityControlAgenda";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_fragment);

        //get&put database key
        Bundle i = getIntent().getExtras();
        eventKey = i.getString("eventKey");
        sessionKey = i.getString("sessionKey");
        i.putString("eventKey", eventKey);
        i.putString("sessionKey", sessionKey);
        Log.i(TAG,"Agenda list is under eventKey: "+eventKey + " sessionKey: " + sessionKey);

        //default fragment, showing all agenda
        fragment = new FragmentListAgenda();
        fragment.setArguments(i);
        fragmentSwitch(fragment);

        //button to create new agenda, clean selected agenda key, intent to FragmentModifyAgenda
        Button create = (Button) findViewById(R.id.create);
        create.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                agendaKey = null;
                modifyAgenda(eventKey,sessionKey,agendaKey);
                Log.i(TAG,"Create button clicked, create new agenda");
            }
        });

        //button to edit agenda, check agenda selected, intent to FragmentModifyAgenda
        Button edit = (Button) findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(validKey(agendaKey)){
                    modifyAgenda(eventKey,sessionKey,agendaKey);
                    Log.i(TAG,"Edit button clicked, agendaKey: " + agendaKey);
                }
            }
        });

        //button to delete, check agenda selected, delete selected agenda
        Button delete = (Button) findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(validKey(agendaKey)){
                    firebaseHelper.helperAgendaKey(eventKey,sessionKey,agendaKey).removeValue();
                    Log.i(TAG,"Delete button clicked, agendaKey: " + agendaKey);
                }
            }
        });

        //
        Button level = (Button) findViewById(R.id.level);
        level.setText("level");
        level.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
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

