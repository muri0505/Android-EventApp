package com.example.owner.internationalaset;

import java.util.HashMap;
import java.util.Map;

public class ObjectEvent {
    private String eventName;
    private String eventDate;
    private String eventDes;
    private Map<String, Boolean> stars = new HashMap<>();

    public ObjectEvent(){}
    public ObjectEvent(String name, String date, String des){
        eventName = name;
        eventDate = date;
        eventDes = des;
    }

    public String getEvent(){
        return eventName + "\n" + eventDate +  "\n" + eventDes;
    }

    public Map<String, Object> addEvent() {
        HashMap<String, Object> event = new HashMap<>();
        event.put("eventName", eventName);
        event.put("eventDate", eventDate);
        event.put("eventDes", eventDes);
        event.put("stars", stars);
        return event;
    }
}
