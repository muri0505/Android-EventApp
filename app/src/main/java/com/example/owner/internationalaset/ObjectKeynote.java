package com.example.owner.internationalaset;

import java.util.HashMap;

public class ObjectKeynote {
    private String keynoteName;
    private String keynotePresenter;
    private String keynoteInstitution;
    private String keynoteDes;
    private String keynoteStartTime;
    private String keynoteEndTime;
    private String keynoteImg;

    public ObjectKeynote(){}

    public ObjectKeynote(String n, String p, String i, String d, String s, String e, String img){
        keynoteName = n;
        keynotePresenter = p;
        keynoteInstitution = i;
        keynoteDes = d;
        keynoteStartTime = s;
        keynoteEndTime = e;
        keynoteImg = img;
    }

    public String getKeynoteName() {return keynoteName;}
    public String getKeynotePresenter() {return keynotePresenter;}
    public String getKeynoteInstitution() {return keynoteInstitution;}
    public String getKeynoteDes() {return keynoteDes;}
    public String getKeynoteStartTime() {return keynoteStartTime;}
    public String getKeynoteEndTime() {return keynoteEndTime;}
    public String getKeynoteImg() {return keynoteImg;}

    public HashMap<String,Object> toHashMap(){
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("keynoteName",keynoteName);
        hashMap.put("keynotePresenter", keynotePresenter);
        hashMap.put("keynoteInstitution", keynoteInstitution);
        hashMap.put("keynoteDes",keynoteDes);
        hashMap.put("keynoteStartTime",keynoteStartTime);
        hashMap.put("keynoteEndTime",keynoteEndTime);
        hashMap.put("keynoteImg",keynoteImg);
        return hashMap;
    }
}
