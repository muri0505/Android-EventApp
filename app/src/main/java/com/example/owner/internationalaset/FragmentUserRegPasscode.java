package com.example.owner.internationalaset;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/*
    FragmentUserRegPasscode: get input passcode from user, check passcode validation
    if passcode valid, process to FragmentUserRegInfor
    cancel button back to listener default
 */
public class FragmentUserRegPasscode extends Fragment {
    HelperFirebase helperFirebase = new HelperFirebase();
    ArrayList<String> passcodeCodeList;
    EditText passcodeCode;
    private static final String TAG = "FragmentUserRegPasscode";

    public FragmentUserRegPasscode(){}

    //listener listens
    FragmentUserRegPasscode.FragmentUserRegPasscodelistener listener;
    public interface FragmentUserRegPasscodelistener{
        public void backDefault();
        public void userRegInfor();
    }

    public void onAttach(Activity activity){
        super.onAttach(activity);
        try{
            listener = (FragmentUserRegPasscode.FragmentUserRegPasscodelistener) activity;
        }catch(ClassCastException e){}
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_reg_passcode, container, false);
        passcodeCode = (EditText) view.findViewById(R.id.passcodeCode);
        passcodeCodeList = new ArrayList<>();

        //get all registration passcodeCode
        helperFirebase.helperPasscode().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                getPasscode(dataSnapshot);
            }
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        //check valid input passcodeCode, process to FragmentUserRegInfor
        Button submit = (Button) view.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(passcodeCodeList.contains(passcodeCode.getText().toString())){
                    listener.userRegInfor();
                }else{
                    Toast.makeText(getActivity(), "Invalid Passcode", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //back to homePage
        Button cancel = (Button) view.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.backDefault();
            }
        });

        return view;
    }

    //get all registration passcodeCode
    public void getPasscode(DataSnapshot dataSnapshot){
        passcodeCodeList.clear();
        for(DataSnapshot data : dataSnapshot.getChildren()){
            ObjectPasscode p = data.getValue(ObjectPasscode.class);
            if(p.getPasscodeType().equals("Registration")){
                passcodeCodeList.add(p.getPasscodeCode());
            }
        }
    }
}
