package com.example.owner.internationalaset;

import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ActivityControlSession extends AppCompatActivity implements FragmentListSession.FragmentSessionlistener{
    private Fragment fragment;
    String eventKey = null;
    String sessionKey = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_fragment);

        Bundle i = getIntent().getExtras();
        eventKey = i.getString("eventKey");

        Bundle bundle = new Bundle();
        bundle.putString("eventKey", eventKey);

        fragment = new FragmentListSession();
        fragment.setArguments(bundle);
        fragmentSwitch(fragment);

        Button create = (Button) findViewById(R.id.create);
        create.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sessionKey = null;
                modifySession(eventKey,sessionKey);
            }
        });

        Button edit = (Button) findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(validKey(sessionKey)){
                    modifySession(eventKey, sessionKey);
                }
            }
        });

        Button delete = (Button) findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(validKey(sessionKey)){
                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Events").child(eventKey).child("Sessions").child(sessionKey);
                    mDatabase.removeValue();
                }
            }
        });

        Button level = (Button) findViewById(R.id.level);
        level.setText("Agendda");
        level.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(validKey(sessionKey)) {

                }
            }
        });
    }

    public void fragmentSwitch(Fragment f) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment, f, "currentFragment");
        transaction.commit();
    }

    public void getSessionKey(String k){sessionKey = k;}

    public void modifySession(String eventKey, String sessionKey){
        Bundle bundle = new Bundle();
        bundle.putString("eventKey", eventKey);
        bundle.putString("sessionKey", sessionKey);

        fragment = new FragmentModifySession();
        fragment.setArguments(bundle);
        fragmentSwitch(fragment);
    }

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
