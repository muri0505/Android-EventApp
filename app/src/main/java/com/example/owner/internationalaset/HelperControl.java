package com.example.owner.internationalaset;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class HelperControl extends AppCompatActivity {
    private Fragment fragment;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //fragmentSwitch
    public void fragmentSwitch(Fragment f) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment, f, "currentFragment");
        transaction.commit();
    }

    public void toolbarTop(Fragment f){
        fragment = f;

        /*
        BottomNavigationView topNavigation = findViewById(R.id.toolbarTop);
        HelperBottomNavigationView.disableShiftMode(topNavigation);
        topNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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
        });
*/
    }
}
