package com.example.owner.internationalaset;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/*
    FragmentListArticle: showing all articles with adapterListView, click on article to get articleKey,
*/
public class FragmentListArticle extends Fragment {
    private ListView listView;
    private ArrayList<ObjectArticle> articleList;
    private ArrayList<String> keyList;
    private AdapterListView adapterListView;
    private HelperFirebase helperFirebase = new HelperFirebase();
    private static final String TAG = "FragmentListArticle";

    private String getEventKey = null;
    private String getSessionKey = null;

    public FragmentListArticle(){}

    //listener listens articleKey
    FragmentListArticle.FragmentArticlelistener listener;
    public interface FragmentArticlelistener{
        public void getArticleKey(String articleKey);
    }

    public void onAttach(Activity activity){
        super.onAttach(activity);
        try{
            listener = (FragmentListArticle.FragmentArticlelistener) activity;
        }catch(ClassCastException e){
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_style, container, false);
        listView = (ListView) view.findViewById(R.id.list);
        articleList = new ArrayList<ObjectArticle>();
        keyList = new ArrayList<String>();
        adapterListView = new AdapterListView(getActivity(), R.layout.layout_list_article, articleList);

        getEventKey = getArguments().getString("eventKey");
        getSessionKey = getArguments().getString("sessionKey");

        //get articles from firebase
        helperFirebase.helperArticle(getEventKey,getSessionKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                getArticle(dataSnapshot);
                adapterListView.notifyDataSetChanged();
            }
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        //show articles in listview with adapterListView
        listView.setAdapter(adapterListView);

        //click on article get articleKey
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapter, View v, int pos, long a) {
                String key = keyList.get(pos);
                listener.getArticleKey(key);
            }
        });

        return view;
    }

    //get articles data from firebase
    public void getArticle(DataSnapshot dataSnapshot){
        articleList.clear();
        for(DataSnapshot data : dataSnapshot.getChildren()){
            String key = data.getKey();
            ObjectArticle s = data.getValue(ObjectArticle.class);
            articleList.add(s);
            keyList.add(key);
        }
    }
}
