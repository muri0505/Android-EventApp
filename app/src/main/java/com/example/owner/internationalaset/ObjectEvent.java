package com.example.owner.internationalaset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ObjectEvent {
    private String eventName;
    private String eventStartDate;
    private String eventEndDate;
    private String eventDes;
    private String eventLocation;
    private String eventImg;

    public ObjectEvent(){}
    public ObjectEvent(String eventName, String eventStartDate, String eventEndDate, String eventDes, String eventLocation, String eventImg){
        this.eventName = eventName;
        this.eventStartDate = eventStartDate;
        this.eventEndDate = eventEndDate;
        this.eventDes = eventDes;
        this.eventLocation = eventLocation;
        this.eventImg = eventImg;
    }

    public String getEventName(){return eventName;}
    public String getEventStartDate() {return eventStartDate;}
    public String getEventEndDate() {return eventEndDate;}
    public String getEventDes(){return eventDes;}
    public String getEventLocation() {return eventLocation;}
    public String getEventImg() {return eventImg;}

    public HashMap<String,Object> toHashMap(){
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("eventName",eventName);
        hashMap.put("eventStartDate",eventStartDate);
        hashMap.put("eventEndDate",eventEndDate);
        hashMap.put("eventDes",eventDes);
        hashMap.put("eventImg",eventImg);
        return hashMap;
    }
}
