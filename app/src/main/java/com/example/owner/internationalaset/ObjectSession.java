package com.example.owner.internationalaset;

import java.util.HashMap;
import java.util.Map;

public class ObjectSession {
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

    public String getSession(){
        return sessionType  + "\n" + sessionName + "\n" + sessionStartTime + "\n" + sessionEndTime + "\n" + sessionDes;
    }

    public Map<String, Object> addSession() {
        HashMap<String, Object> session = new HashMap<>();
        session.put("sessionType", sessionType);
        session.put("sessionName", sessionName);
        session.put("sessionStartTime", sessionStartTime);
        session.put("sessionEndTime", sessionEndTime);
        session.put("sessionDes", sessionDes);
        return session;
    }
}
