package com.example.owner.internationalaset;

import java.util.HashMap;

public class ObjectSession {
    private String sessionName;
    private String sessionPresenter;
    private String sessionAuthor;
    private String sessionLocation;
    private String sessionStartTime;
    private String sessionEndTime;

    public ObjectSession(){}

    public ObjectSession(String n, String p, String a, String l, String s, String e){
        sessionName = n;
        sessionPresenter = p;
        sessionAuthor = a;
        sessionLocation = l;
        sessionStartTime = s;
        sessionEndTime = e;
    }

    public String getSessionName() {return sessionName;}
    public String getSessionPresenter() {return sessionPresenter; }
    public String getSessionAuthor() {return sessionAuthor; }
    public String getSessionLocation() {return sessionLocation;}
    public String getSessionStartTime() {return sessionStartTime;}
    public String getSessionEndTime() {return sessionEndTime;}

    public HashMap<String,Object> toHashMap(){
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("sessionName",sessionName);
        hashMap.put("sessionPresenter", sessionPresenter);
        hashMap.put("sessionAuthor", sessionAuthor);
        hashMap.put("sessionLocation",sessionLocation);
        hashMap.put("sessionStartTime",sessionStartTime);
        hashMap.put("sessionEndTime",sessionEndTime);
        return hashMap;
    }
}
