package com.example.owner.internationalaset;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class FragmentModifyPasscode extends HelperDateTime{
    private HelperFirebase helperFirebase = new HelperFirebase();
    private ObjectPasscode passcode;
    private String getPasscodeKey = null;

    private TextView mode;
    private EditText passcodeName;
    private EditText passcodeCode;
    private EditText passcodeType;

    private String emptyName;
    private static final String TAG = "FragmentModifyPasscode";

    public FragmentModifyPasscode(){}

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_modify_passcode, container, false);

        passcodeName= (EditText) view.findViewById(R.id.passcodeName);
        passcodeCode = (EditText) view.findViewById(R.id.passcodeCode);
        passcodeType = (EditText) view.findViewById(R.id.passcodeType);
        mode = (TextView) view.findViewById(R.id.mode);

        //get passcodeKey from ActivityControlPasscode
        getPasscodeKey = getArguments().getString("passcodeKey");
        Log.i(TAG,"Get passcodeKey from ActivityControlPasscode " + getPasscodeKey);

        //check valid passcodeKey to get passcode
        if(getPasscodeKey != null){
            Log.i(TAG,"Mode: Edit Passcode");
            helperFirebase.helperPasscode().addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    getPasscode(dataSnapshot);
                }
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }
            });
        }else{
            Log.i(TAG,"Mode: Create Passcode");
            mode.setText("Create Passcode");
        }

        Button edit = (Button) view.findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //edit button to create current passcode
                String name = passcodeName.getText().toString();
                String code = passcodeCode.getText().toString();
                String type = passcodeType.getText().toString();
                passcode = new ObjectPasscode(name, code, type);

                //passcode validation
                if (empty(name, "Passcode Name") || empty(code, "Passcode Code") ||empty(type, "Passcode Type") ) {
                    new AlertDialog.Builder(getActivity())
                            .setMessage(emptyName +" cannot be empty")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            }).show();
                } else {
                    //create new passcodeKey and passcode or update passcode under exist passcodeKey
                    if (getPasscodeKey == null) {
                        getPasscodeKey = helperFirebase.helperPasscode().push().getKey();
                        helperFirebase.helperPasscodeKey(getPasscodeKey).setValue(passcode);
                        Log.i(TAG, "New passcodeKey and passcode created. PasscodeKey: " + getPasscodeKey);
                    } else {
                        helperFirebase.helperPasscodeKey(getPasscodeKey).updateChildren(passcode.toHashMap());
                        Log.i(TAG, "passcode updated. PasscodeKey: " + getPasscodeKey);
                    }
                    backToControl();
                }
            }
        });

        //cancel button back to ActivityControlPasscode
        Button cancel = (Button) view.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                backToControl();
            }
        });
        return view;
    }

    //get passcode from firebase with passcodeKey and show in editText
    public void getPasscode(DataSnapshot dataSnapshot){
        for(DataSnapshot data : dataSnapshot.getChildren()){
            String key = data.getKey();

            if(key.equals(getPasscodeKey)) {
                mode.setText("Edit Passcode");
                ObjectPasscode p = (ObjectPasscode)data.getValue(ObjectPasscode.class);

                passcodeName.setText(p.getPasscodeName());
                passcodeCode.setText(p.getPasscodeCode());
                passcodeType.setText(p.getPasscodeType());
                break;
            }
        }
    }

    //Intent to ActivityControlPasscode, default showing passcode list
    public void backToControl(){
        Intent i = new Intent(getActivity(), ActivityControlPasscode.class);
        startActivity(i);
        Log.i(TAG, "Intent to ActivityControlPasscode");
    }

    //check object empty attribute
    public boolean empty(String text, String name){
        emptyName = name;
        return text.equals(null)||text.equals("");
    }
}
