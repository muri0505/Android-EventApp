package com.example.owner.internationalaset;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class HelperUserNavigationView extends AppCompatActivity {
    private ActionBarDrawerToggle toggle;
    private DrawerLayout menu;
    private Fragment fragment;

    public void userNavigation(){
        //setup NavigationView and actionBar
        menu = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.user);
        toggle = new ActionBarDrawerToggle(this, menu, R.string.navigation_drawer_open, R.string.navigation_drawer_close){};
        menu.setDrawerListener(toggle);
        toggle.syncState();

        //navigationView switch fragment
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                menu.closeDrawers();

                if(menuItem.isChecked()){
                    switch (menuItem.getItemId()) {
                        case R.id.login:
                            fragment = new FragmentUserLogin();
                            fragmentSwitch(fragment);
                            break;
                    }
                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_actionbar_icon, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (menu.isDrawerOpen(Gravity.RIGHT)) { menu.closeDrawer(Gravity.RIGHT);}
        else{ menu.openDrawer(Gravity.RIGHT);}
        return false;
    }

    //fragment changes
    public void fragmentSwitch(Fragment f) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment, f, "currentFragment");
        transaction.commit();
    }

}
