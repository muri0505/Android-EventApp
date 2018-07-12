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

    public ObjectEvent(){}
    public ObjectEvent(String n, String s, String e, String d, String l){
        eventName = n;
        eventStartDate = s;
        eventEndDate = e;
        eventDes = d;
        eventLocation = l;
    }

    public String getEventName(){return eventName;}
    public String getEventStartDate() {return eventStartDate;}
    public String getEventEndDate() {return eventEndDate;}
    public String getEventDes(){return eventDes;}
    public String getEventLocation() {return eventLocation;}
}
