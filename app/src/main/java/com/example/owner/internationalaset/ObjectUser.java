package com.example.owner.internationalaset;

import android.accounts.Account;
import java.util.HashMap;

public class ObjectUser extends Account {
    private String userFirstName;
    private String userMiddleName;
    private String userLastName;
    private String userInstitution;
    private String userCountry;

    public ObjectUser(){
        super("","");
    }

    public ObjectUser(String email, String password, String firstName, String middleName, String lastName, String institution, String country){
        super(email, password);
        userFirstName = firstName;
        userMiddleName = middleName;
        userLastName = lastName;
        userInstitution = institution;
        userCountry = country;
    }

    public String getUserFirstName() {return userFirstName;}
    public String getUserMiddleName() {return userMiddleName;}
    public String getUserLastName() {return userLastName;}
    public String getUserInstitution() {return userInstitution;}
    public String getUserCountry() {return userCountry;}

    public HashMap<String,Object> toHashMap(){
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("userFirstName",userFirstName);
        hashMap.put("userMiddleName", userMiddleName);
        hashMap.put("userLastName", userLastName);
        hashMap.put("userInstitution", userInstitution);
        hashMap.put("userCountry", userCountry);
        return hashMap;
    }
}
