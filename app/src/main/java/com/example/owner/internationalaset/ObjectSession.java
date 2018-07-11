package com.example.owner.internationalaset;

import java.util.HashMap;
import java.util.Map;

public class ObjectSession{
    private String sessionType;
    private String sessionName;
    private String sessionStartTime;
    private String sessionEndTime;
    private String sessionDes;

    public ObjectSession(){}
    public ObjectSession(String type, String name, String start, String end, String des){
        sessionType = type;
        sessionName = name;
        sessionStartTime = start;
        sessionEndTime = end;
        sessionDes = des;
    }

    public String getSessionType() {return sessionType;}
    public String getSessionName() {return sessionName;}
    public String getSessionStartTime() {return sessionStartTime;}
    public String getSessionEndTime() {return sessionEndTime;}
    public String getSessionDes() {return sessionDes;}

    public String sessionToString(){
        return sessionType  + "\n" + sessionName + "\n" + sessionStartTime + "\n" + sessionEndTime + "\n" + sessionDes;
    }

}
