package com.example.owner.internationalaset;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class FragmentUserRegInfor extends Fragment {
    private EditText userEmail;
    private EditText userPassword;
    private EditText userFirstName;
    private EditText userMiddleName;
    private EditText userLastName;
    private EditText userInstitution;
    private EditText userCountry;
    private Button submit;
    private Button cancel;

    private String emptyName;

    public FragmentUserRegInfor(){}

    //listener listens
    FragmentUserRegInfor.FragmentUserRegInforlistener listener;
    public interface FragmentUserRegInforlistener{
        public void backDefault();
    }

    public void onAttach(Activity activity){
        super.onAttach(activity);
        try{
            listener = (FragmentUserRegInfor.FragmentUserRegInforlistener) activity;
        }catch(ClassCastException e){}
    }
    
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_reg_infor, container, false);

        userEmail = (EditText) view.findViewById(R.id.userEmail);
        userPassword = (EditText) view.findViewById(R.id.userPassword);
        userFirstName = (EditText) view.findViewById(R.id.userFirstName);
        userMiddleName = (EditText) view.findViewById(R.id.userMiddleName);
        userLastName = (EditText) view.findViewById(R.id.userLastName);
        userInstitution = (EditText) view.findViewById(R.id.userInstitution);
        userCountry = (EditText) view.findViewById(R.id.userCountry);
        submit = (Button) view.findViewById(R.id.submit);
        cancel = (Button) view.findViewById(R.id.cancel);

        //submit valid user account information to firebase
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(empty(userEmail.getText().toString(), "Email") || empty(userPassword.getText().toString(), "Password")
                        ||empty(userFirstName.getText().toString(), "First Name") || empty(userLastName.getText().toString(), "Last Name")
                        ||empty(userInstitution.getText().toString(), "Institution") || empty(userCountry.getText().toString(), "Country")){
                    new AlertDialog.Builder(getActivity())
                        .setMessage(emptyName +" cannot be empty")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {}
                        }).show();
                }
            }
        });

        //back to HomePage
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.backDefault();
            }
        });
        return view;
    }

    //check object empty attribute
    public boolean empty(String text, String name){
        emptyName = name;
        return text.equals(null)||text.equals("");
    }
}
