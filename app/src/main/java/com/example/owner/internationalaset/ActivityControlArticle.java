package com.example.owner.internationalaset;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

/*
    ActivityControlArticle: admin article control, get eventKey&sessionKey from previous activity/fragment,
    default showing all article, create&edit button intent to FragmentModifyArticle, delete button to delete data
*/
public class ActivityControlArticle extends HelperControl implements FragmentListArticle.FragmentArticlelistener{
    private HelperFirebase helperFirebase = new HelperFirebase();
    private Fragment fragment;
    private String eventKey = null;
    private String sessionKey = null;
    private String articleKey = null;
    private Bundle i;
    private static final String TAG = "ActivityControlArticle";

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
        Log.i(TAG,"Article list is under eventKey: "+eventKey + " sessionKey: " + sessionKey);

        //setup default&toolbar
        fragmentSwitch(defaultFragment());
        helperControl(defaultFragment(),"Article");

        //BottomNavigationView switch create, edit, delete and next level
        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNavigation);
        HelperBottomNavigationView.disableShiftMode(bottomNavigation);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.create:
                        //button to create new article, clean selected article key, intent to FragmentModifyArticle
                        articleKey = null;
                        modifyArticle(eventKey,sessionKey,articleKey);
                        Log.i(TAG,"Create button clicked, create new article");
                        break;
                    case R.id.edit:
                        //button to edit article, check article selected, intent to FragmentModifyArticle
                        if(validKey(articleKey)){
                            modifyArticle(eventKey,sessionKey,articleKey);
                            Log.i(TAG,"Edit button clicked, articleKey: " + articleKey);
                        }
                        break;
                    case R.id.delete:
                        //button to delete, check article selected, delete selected article
                        if(validKey(articleKey)){
                            helperFirebase.helperArticleKey(eventKey,sessionKey,articleKey).removeValue();
                            Log.i(TAG,"Delete button clicked, articleKey: " + articleKey);
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

    //default fragment, showing all article
    public Fragment defaultFragment(){
        fragment = new FragmentListArticle();
        fragment.setArguments(i);
        fragmentSwitch(fragment);
        return fragment;
    }

    //FragmentListArticle listener, get selected article key
    public void getArticleKey(String k){articleKey = k;}

    //Intent to FragmentModifyArticle
    public void modifyArticle(String eventKey, String sessionKey, String articleKey){
        Bundle bundle = new Bundle();
        bundle.putString("eventKey", eventKey);
        bundle.putString("sessionKey", sessionKey);
        bundle.putString("articleKey", articleKey);

        fragment = new FragmentModifyArticle();
        fragment.setArguments(bundle);
        fragmentSwitch(fragment);
        Log.i(TAG,"Intent to FragmentModifyArticle with eventKey: " + eventKey + " sessionKey: " + sessionKey + "articleKey: " + articleKey);
    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.i(TAG, "start");
    }

    @Override
    protected  void onStop(){
        super.onStop();
        Log.i(TAG, "stop");
    }
}

