package com.example.owner.internationalaset;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

/*
    FragmentModifyArticle: get eventKey&sessionKey&articleKey from ActivityControlArticle
    check valid articleKey and get article from firebase or create new articleKey and new article
    Edit button to push update/new article, cancel button back to ActivityControlArticle
 */
public class FragmentModifyArticle extends HelperDateTime {
    private HelperFirebase helperFirebase = new HelperFirebase();
    private ObjectArticle article;
    private String getEventKey = null;
    private String getSessionKey = null;
    private String getArticleKey = null;

    private TextView mode;
    private EditText articleName;
    private EditText articlePresenter;
    private EditText articleAuthor;
    private EditText articleLocation;
    private EditText articleStartTime;
    private EditText articleEndTime;
    private String emptyName;
    private static final String TAG = "FragmentModifyArticle";

    public FragmentModifyArticle(){}

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_modify_article, container, false);

        mode = (TextView) view.findViewById(R.id.mode);
        articleName= (EditText) view.findViewById(R.id.articleName);
        articlePresenter= (EditText) view.findViewById(R.id.articlePresenter);
        articleAuthor= (EditText) view.findViewById(R.id.articleAuthor);
        articleLocation = (EditText) view.findViewById(R.id.articleLocation);
        articleStartTime = (EditText) view.findViewById(R.id.articleStartTime);
        articleEndTime = (EditText) view.findViewById(R.id.articleEndTime);

        //input keyboard format
        articleStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {setTime(articleStartTime);    }
        });
        articleEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTime(articleEndTime);
            }
        });

        //get eventKey&sessionKey&articleKey from ActivityControlArticle
        getEventKey = getArguments().getString("eventKey");
        getSessionKey = getArguments().getString("sessionKey");
        getArticleKey = getArguments().getString("articleKey");
        Log.i(TAG,"Get eventKey, sessionKey and articleKey from ActivityControlSession " + getEventKey + ", " + getSessionKey + ", " + getArticleKey);

        //check valid articleKey to get article
        if(getArticleKey != null) {
            Log.i(TAG,"Mode: Edit Article");
            helperFirebase.helperArticle(getEventKey,getSessionKey).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    getArticle(dataSnapshot);
                }
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }
            });
        }else{
            Log.i(TAG,"Mode: Create Article");
            mode.setText("Create Article");
        }

        Button edit = (Button) view.findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //edit button to create current article
                String name, presenter, author, location, startTime, endTime;
                name = articleName.getText().toString();
                presenter = articlePresenter.getText().toString();
                author = articleAuthor.getText().toString();
                location = articleLocation.getText().toString();
                startTime = articleStartTime.getText().toString();
                endTime = articleEndTime.getText().toString();
                article = new ObjectArticle(name, presenter, author, location, startTime, endTime);

                //article validation
                if (empty(name, "Article Name") || empty(presenter, "Article Presenter") || empty(author, "Article Author") ||
                        empty(location, "Article Location") || empty(startTime, "Article StartTime")) {
                    new AlertDialog.Builder(getActivity())
                            .setMessage(emptyName + " cannot be empty")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            }).show();
                } else {
                    //create new articleKey and article or update article under exist articleKey
                    if (getArticleKey == null) {
                        getArticleKey = helperFirebase.helperArticle(getEventKey, getSessionKey).push().getKey();
                        helperFirebase.helperArticleKey(getEventKey, getSessionKey, getArticleKey).setValue(article);
                        Log.i(TAG, "New articleKey and article created. articleKey: " + getArticleKey);
                    } else {
                        helperFirebase.helperArticleKey(getEventKey, getSessionKey, getArticleKey).updateChildren(article.toHashMap());
                        Log.i(TAG, "article updated. articleKey: " + getArticleKey);
                    }
                    backToControl();
                }
            }
        });

        //cancel button back to ActivityControlArticle
        Button cancel = (Button) view.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                backToControl();
            }
        });
        return view;
    }

    //get article from firebase with articleKey and show in editText
    public void getArticle(DataSnapshot dataSnapshot){
        for(DataSnapshot data : dataSnapshot.getChildren()){
            String key = data.getKey();

            if(key.equals(getArticleKey)) {
                mode.setText("Edit Article");
                ObjectArticle a = (ObjectArticle) data.getValue(ObjectArticle.class);
                articleName.setText(a.getArticleName());
                articlePresenter.setText(a.getArticlePresenter());
                articleAuthor.setText(a.getArticleAuthor());
                articleLocation.setText(a.getArticleLocation());
                articleStartTime.setText(a.getArticleStartTime());
                articleEndTime.setText(a.getArticleEndTime());
                break;
            }
        }
    }

    //Intent to ActivityControlArticle, default showing article list
    public void backToControl(){
        Intent i = new Intent(getActivity(), ActivityControlArticle.class);
        i.putExtra("eventKey",getEventKey);
        i.putExtra("sessionKey",getSessionKey);
        startActivity(i);
    }

    //check object empty attribute
    public boolean empty(String text, String name){
        emptyName = name;
        return text.equals(null)||text.equals("");
    }
}