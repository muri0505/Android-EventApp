package com.example.owner.internationalaset;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.stats.WakeLock;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FragmentUserLogin extends Fragment {
    HelperFirebase helperFirebase = new HelperFirebase();
    String emptyName;
    ArrayList<ObjectAccount> accounts;
    private static final String TAG = "FragmentUserLogin";

    public FragmentUserLogin(){}

    //listener listens
    FragmentUserLogin.FragmentUserLoginlistener listener;
    public interface FragmentUserLoginlistener{
        public void backDefault();
        public void navigationView();
    }

    public void onAttach(Activity activity){
        super.onAttach(activity);
        try{
            listener = (FragmentUserLogin.FragmentUserLoginlistener) activity;
        }catch(ClassCastException e){}
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_login, container, false);

        final EditText loginEmail = (EditText) view.findViewById(R.id.loginEmail);
        final EditText loginPassword = (EditText) view.findViewById(R.id.loginPassword);
        Button login = (Button) view.findViewById(R.id.login);
        Button cancel = (Button) view.findViewById(R.id.cancel);

        accounts = new ArrayList<>();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //check fields empty
                if(empty(loginEmail.getText().toString(), "Email") || empty(loginPassword.getText().toString(), "Password")){
                    new AlertDialog.Builder(getActivity())
                            .setMessage(emptyName + " cannot be empty")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            }).show();
                }

                helperFirebase.helperUser().addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot data: dataSnapshot.getChildren()){
                            accounts.add(data.getValue(ObjectAccount.class));
                        }

                        for(ObjectAccount account: accounts){
                            if(account.getAccountEmail().equals(loginEmail.getText().toString())
                                    && account.getAccountEmail().equals(loginPassword.getText().toString())){
                                listener.backDefault();
                                listener.navigationView();
                                break;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {}
                });
            }
        });

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
