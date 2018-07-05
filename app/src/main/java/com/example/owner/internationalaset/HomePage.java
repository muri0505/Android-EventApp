package com.example.owner.internationalaset;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class HomePage extends AppCompatActivity {
    private DrawerLayout menu;
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        menu = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.menu);
        fragment = new Events();

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        menu.closeDrawers();

                        if(menuItem.isChecked()){
                            switch (menuItem.getItemId()) {
                                case R.id.events:
                                    fragmentSwitch(fragment);
                                    break;
                                case R.id.addEvent:
                                    Intent i = new Intent(HomePage.this, AddEvent.class);
                                    startActivity(i);
                            }
                        }
                        return true;
                    }
                });
    }

    public void fragmentSwitch(Fragment f) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment, f, "currentFragment");
        transaction.commit();
    }
}