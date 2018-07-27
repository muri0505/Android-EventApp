package com.example.owner.internationalaset;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/*
    FragmentModifyPasscode: get passcode from ActivityControlPasscode
    check valid passcodeKey and get passcode from firebase or create new passcodeKey and new passcode
    Edit button to push update/new passcode, cancel button back to ActivityControlPasscode
 */
public class FragmentModifyPasscode extends HelperDateTime{
    private HelperFirebase helperFirebase = new HelperFirebase();
    private ObjectPasscode passcode;
    private String getPasscodeKey = null;

    private TextView mode;
    private EditText passcodeName;
    private EditText passcodeCode;
    private Spinner passcodeType;
    private ImageView passcodeAdd;

    private String emptyName;
    private ArrayList<String> eventName;
    private AdapterListView adapterListView;
    private String selectedEventName;
    private static final String TAG = "FragmentModifyPasscode";

    public FragmentModifyPasscode(){}

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_modify_passcode, container, false);

        passcodeName= (EditText) view.findViewById(R.id.passcodeName);
        passcodeCode = (EditText) view.findViewById(R.id.passcodeCode);
        passcodeType = (Spinner) view.findViewById(R.id.passcodeType);
        passcodeAdd = (ImageView) view.findViewById(R.id.passcodeAdd);
        mode = (TextView) view.findViewById(R.id.mode);

        eventName = new ArrayList<String>();
        adapterListView = new AdapterListView(getActivity(), R.layout.layout_list_event_name, eventName);

        //show passcodeAdd if event type selected
        passcodeType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(passcodeType.getSelectedItem().toString().equals("Event")){
                    passcodeAdd.setVisibility(View.VISIBLE);
                }else{
                    passcodeAdd.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        //show event list, get eventName and set passcodeName as eventName
        passcodeAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //AlerDiage window
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = getActivity().getLayoutInflater();
                View eventListView = inflater.inflate(R.layout.fragment_list_style, null);

                ListView listView = (ListView) eventListView.findViewById(R.id.list);
                listView.setAdapter(adapterListView);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> adapter, View v, int pos, long a) {
                        selectedEventName = eventName.get(pos).toString();
                            }
                });

                builder.setView(eventListView);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        passcodeName.setText(selectedEventName);
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {}
                });
                builder.create().show();
            }
        });

        //get events names from firebase
        helperFirebase.helperEvent().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                getEventName(dataSnapshot);
                adapterListView.notifyDataSetChanged();
            }
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

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
            String type = passcodeType.getSelectedItem().toString();
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
                passcodeType.setSelection(spinnerPos(p.getPasscodeType()));
                break;
            }
        }
    }

    //get events names from firebase
    public void getEventName(DataSnapshot dataSnapshot){
        eventName.clear();
        for(DataSnapshot data : dataSnapshot.getChildren()){
            ObjectEvent e = data.getValue(ObjectEvent.class);
            eventName.add(e.getEventName());
        }
    }

    //Intent to ActivityControlPasscode, default showing passcode list
    public void backToControl(){
        Intent i = new Intent(getActivity(), ActivityControlPasscode.class);
        startActivity(i);
        Log.i(TAG, "Intent to ActivityControlPasscode");
    }

    //get passcode type
    public int spinnerPos(String type){
        for(int i = 0; i < passcodeType.getCount(); i++){
            if (passcodeType.getItemAtPosition(i).toString().equals(type))
                return i;
        }
        return 0;
    }

    //check object empty attribute
    public boolean empty(String text, String name){
        emptyName = name;
        return text.equals(null)||text.equals("");
    }
}
