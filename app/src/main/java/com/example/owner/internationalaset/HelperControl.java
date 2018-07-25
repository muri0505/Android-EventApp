package com.example.owner.internationalaset;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class HelperControl extends AppCompatActivity {
    private Fragment fragment;
    private String level;
    private String TAG;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // Inflate control toolbar event_menu, set title
    public boolean onCreateOptionsMenu(Menu menu) {
        setTitle(level + " Control");
        getMenuInflater().inflate(R.menu.control_top, menu);
        return true;
    }

    //toolbar helps to show current list, intent to homepage and control panel
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.control:
                Intent i = new Intent(getApplicationContext(), ActivityControlPanel.class);
                startActivity(i);
                break;
            case R.id.home:
                i = new Intent(getApplicationContext(), HomePage.class);
                startActivity(i);
                break;
            case R.id.list:
                fragmentSwitch(fragment);
                break;
        }
        return true;
    }

    //current toolbar status
    public void helperControl(Fragment f, String l){
        fragment = f;
        level = l;
    }

    //fragmentSwitch
    public void fragmentSwitch(Fragment f) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment, f, "currentFragment");
        transaction.commit();
    }

    //check if key selected
    public boolean validKey(String key){
        if(key == null){
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(this, "Please select an " + level, duration);
            toast.show();
            return false;
        }
        return true;
    }
}


