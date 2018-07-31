package com.example.owner.internationalaset;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.constraint.solver.widgets.Snapshot;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/*
    FragmentUserRegInfor: get user information from input,
    check validation and email registration
    if information valid, push new user account to firebase
    cancel button back to listener deaflut
 */
public class FragmentUserRegInfor extends Fragment {
    HelperFirebase helperFirebase = new HelperFirebase();
    private TextView emailValidation;
    private EditText accountEmail;
    private EditText accountPassword;
    private EditText userFirstName;
    private EditText userMiddleName;
    private EditText userLastName;
    private EditText userInstitution;
    private EditText userCountry;
    private Button submit;
    private Button cancel;

    private String emptyName;
    private String email;
    private String password;
    private String firstName;
    private String middleName;
    private String lastName;
    private String institution;
    private String country;

    private ArrayList<String> emailList;

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
        emailList = new ArrayList<>();

        emailValidation = (TextView) view.findViewById(R.id.emailValidation);
        accountEmail = (EditText) view.findViewById(R.id.accountEmail);
        accountPassword = (EditText) view.findViewById(R.id.accountPassword);
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
                email = accountEmail.getText().toString();
                password = accountPassword.getText().toString();
                firstName = userFirstName.getText().toString();
                middleName = userMiddleName.getText().toString();
                lastName = userLastName.getText().toString();
                institution = userInstitution.getText().toString();
                country = userCountry.getText().toString();
    
                //check empty fields
                if(empty(email, "Email") || empty( password, "Password")
                        ||empty(firstName, "First Name") || empty(lastName, "Last Name")
                        ||empty(institution, "Institution") || empty( country, "Country")){
                    new AlertDialog.Builder(getActivity())
                        .setMessage(emptyName +" cannot be empty")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {}
                        }).show();
                }else {
                    //email validation
                    helperFirebase.helperUser().addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot data: dataSnapshot.getChildren()){
                                ObjectUser u = data.getValue(ObjectUser.class);
                                if(!emailList.contains(u.getAccountEmail())){
                                    emailList.add(u.getAccountEmail());
                                }
                            }

                            //push new user account to firebase if email valid
                            if(emailList.contains(accountEmail.getText().toString())){
                                emailValidation.setVisibility(View.VISIBLE);
                            }else{
                                emailValidation.setVisibility(View.INVISIBLE);
                                String key = helperFirebase.helperUser().push().getKey();
                                ObjectUser user = new ObjectUser(email, password, firstName, middleName, lastName, institution, country);
                                helperFirebase.helperUserKey(key).setValue(user);
                                listener.backDefault();
                            }
                        }
                        public void onCancelled(DatabaseError databaseError) {
                            System.out.println("The read failed: " + databaseError.getCode());
                        }
                    });
                }
            }
        });

        //listener back to default
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
