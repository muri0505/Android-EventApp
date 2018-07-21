package com.example.owner.internationalaset;

import java.util.HashMap;
import java.util.Map;

public class ObjectSession{
    private String sessionDate;
    private String sessionType;
    private String sessionName;
    private String sessionStartTime;
    private String sessionEndTime;
    private String sessionDes;

    public ObjectSession(){}
    public ObjectSession(String date, String type, String name, String start, String end, String des){
        sessionDate = date;
        sessionType = type;
        sessionName = name;
        sessionStartTime = start;
        sessionEndTime = end;
        sessionDes = des;
    }

    public String getSessionDate(){return sessionDate;}
    public String getSessionType() {return sessionType;}
    public String getSessionName() {return sessionName;}
    public String getSessionStartTime() {return sessionStartTime;}
    public String getSessionEndTime() {return sessionEndTime;}
    public String getSessionDes() {return sessionDes;}

    public HashMap<String,Object> toHashMap(){
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("sessionDate",sessionDate);
        hashMap.put("sessionType",sessionType);
        hashMap.put("sessionName",sessionName);
        hashMap.put("sessionStartTime",sessionStartTime);
        hashMap.put("sessionEndTime",sessionEndTime);
        hashMap.put("sessionDes",sessionDes);
        return hashMap;
    }
}
