package com.example.owner.internationalaset;

import java.util.HashMap;

public class ObjectAccount {
    private String accountEmail;
    private String accountPassword;

    public ObjectAccount(){}

    public ObjectAccount(String e, String p){
        accountEmail = e;
        accountPassword = p;
    }

    public String getAccountEmail() { return accountEmail; }
    public String getAccountPassword(){ return accountPassword; }

    public HashMap<String,Object> toHashMap(){
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("accountEmail",accountEmail);
        hashMap.put("accountPassword", accountPassword);
        return hashMap;
    }
}
