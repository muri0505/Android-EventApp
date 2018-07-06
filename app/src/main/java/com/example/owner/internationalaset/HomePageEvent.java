package com.example.owner.internationalaset;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
/*
    activity event home page
 */

public class HomePageEvent extends AppCompatActivity {
    private DrawerLayout menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_event);

        menu = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.menu);

        navigationView.setNavigationItemSelectedListener(
            new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem menuItem) {
                    menuItem.setChecked(true);
                    menu.closeDrawers();

                    if(menuItem.isChecked()){
                        switch (menuItem.getItemId()) {

                        }
                    }
                    return true;
                }
            });
    }
}