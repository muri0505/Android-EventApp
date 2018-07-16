package com.example.owner.internationalaset;

import java.util.HashMap;

public class ObjectSession {
    private String sessionName;
    private String sessionInstitution;
    private String sessionLocation;
    private String sessionStartTime;
    private String sessionEndTime;
    private String sessionPeople;

    public ObjectSession(){}

    public ObjectSession(String n, String i, String l, String s, String e, String p){
        sessionName = n;
        sessionInstitution = i;
        sessionLocation = l;
        sessionStartTime = s;
        sessionEndTime = e;
        sessionPeople = p;
    }

    public String getSessionName() {return sessionName;}
    public String getSessionInstitution() {return sessionInstitution;}
    public String getSessionLocation() {return sessionLocation;}
    public String getSessionStartTime() {return sessionStartTime;}
    public String getSessionEndTime() {return sessionEndTime;}
    public String getSessionPeople() {return sessionPeople;}

    public HashMap<String,Object> toHashMap(){
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("sessionName",sessionName);
        hashMap.put("sessionInstitution",sessionInstitution);
        hashMap.put("sessionLocation",sessionLocation);
        hashMap.put("sessionStartTime",sessionStartTime);
        hashMap.put("sessionEndTime",sessionEndTime);
        hashMap.put("sessionPeople",sessionPeople);
        return hashMap;
    }

    public String sessionToString(){
        return sessionName + "\n" + sessionInstitution + "\n" + sessionLocation + "\n" +
                sessionStartTime + "\n" + sessionEndTime + "\n" + sessionPeople;}
}
