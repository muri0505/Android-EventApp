package com.example.owner.internationalaset;

import java.util.HashMap;

public class ObjectPasscode {
    private String passcodeName;
    private String passcodeCode;
    private String passcodeType;

    public ObjectPasscode(){}

    public ObjectPasscode(String name, String code, String type){
        passcodeName = name;
        passcodeCode = code;
        passcodeType = type;
    }

    public String getPasscodeName() { return passcodeName; }
    public String getPasscodeCode() { return passcodeCode; }
    public String getPasscodeType() { return passcodeType; }

    public HashMap<String,Object> toHashMap(){
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("passcodeName",passcodeName);
        hashMap.put("passcodeCode",passcodeCode);
        hashMap.put("passcodeType",passcodeType);
        return hashMap;
    }
}
