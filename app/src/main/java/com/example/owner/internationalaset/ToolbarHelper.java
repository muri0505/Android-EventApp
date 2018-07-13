package com.example.owner.internationalaset;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class ToolbarHelper extends AppCompatActivity {
    private android.support.v7.widget.Toolbar toolbar;
    private int resource;

    protected void onCreate(Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
    }

    public void initToolbar(int r){
        toolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.toolbarTop);
        setSupportActionBar(toolbar);
        resource = r;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(resource, menu);
        return true;
    }

    public Toolbar getToolbar(){return toolbar;}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
            case R.id.backControl:

                break;

            case R.id.backHome:

                break;
            case R.id.backlevel:

                break;
            default:

        }
        return true;
    }
}
