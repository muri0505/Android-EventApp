package com.example.owner.internationalaset;

import java.util.HashMap;

public class ObjectArticle {
    private String articleName;
    private String articlePresenter;
    private String articleAuthor;
    private String articleLocation;
    private String articleStartTime;
    private String articleEndTime;

    public ObjectArticle(){}

    public ObjectArticle(String n, String p, String a, String l, String s, String e){
        articleName = n;
        articlePresenter = p;
        articleAuthor = a;
        articleLocation = l;
        articleStartTime = s;
        articleEndTime = e;
    }

    public String getArticleName() {return articleName;}
    public String getArticlePresenter() {return articlePresenter; }
    public String getArticleAuthor() {return articleAuthor; }
    public String getArticleLocation() {return articleLocation;}
    public String getArticleStartTime() {return articleStartTime;}
    public String getArticleEndTime() {return articleEndTime;}

    public HashMap<String,Object> toHashMap(){
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("articleName",articleName);
        hashMap.put("articlePresenter", articlePresenter);
        hashMap.put("articleAuthor", articleAuthor);
        hashMap.put("articleLocation",articleLocation);
        hashMap.put("articleStartTime",articleStartTime);
        hashMap.put("articleEndTime",articleEndTime);
        return hashMap;
    }
}
