package com.example.owner.internationalaset;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/*
    control event fragment
    set default to show events fragment, pass input event name to event information fragment
 */
public class ControlEvent extends AppCompatActivity implements FragmentEvents.FragmentEventslistener{
    private Fragment fragment;
    String eventKey = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_fragment);

        //deafult show events fragment
        fragment = new FragmentEvents();
        fragmentSwitch(fragment);

        Button create = (Button) findViewById(R.id.create);
        create.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                eventKey = null;
                modifyEvent(eventKey);
            }
        });

        Button edit = (Button) findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(eventKey == null){
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(ControlEvent.this, "Please select a event", duration);
                    toast.show();
                }else {
                    modifyEvent(eventKey);
                }
            }
        });

        Button delete = (Button) findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(eventKey == null){
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(ControlEvent.this, "Please select a event", duration);
                    toast.show();
                }else {
                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Events").child(eventKey);
                    mDatabase.removeValue();
                }
            }
        });

        Button level = (Button) findViewById(R.id.level);
        level.setText("Session");
        level.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });
    }

    //fragment switch
    public void fragmentSwitch(Fragment f) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment, f, "currentFragment");
        transaction.commit();
    }

    public void getEventKey(String k){
        eventKey = k;
    }

    public void modifyEvent(String eventKey){
        Bundle bundle = new Bundle();
        bundle.putString("eventKey", eventKey);
        fragment = new FragmentEventModify();
        fragment.setArguments(bundle);
        fragmentSwitch(fragment);
    }
}
