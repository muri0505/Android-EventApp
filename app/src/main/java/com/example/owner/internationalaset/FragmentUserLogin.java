package com.example.owner.internationalaset;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class FragmentUserLogin extends Fragment {

    public FragmentUserLogin(){}

    //listener listens
    FragmentUserLogin.FragmentUserLoginlistener listener;
    public interface FragmentUserLoginlistener{
        public void backDefault();
    }

    public void onAttach(Activity activity){
        super.onAttach(activity);
        try{
            listener = (FragmentUserLogin.FragmentUserLoginlistener) activity;
        }catch(ClassCastException e){}
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_login, container, false);

        EditText loginEmail = (EditText) view.findViewById(R.id.loginEmail);
        EditText loginPassword = (EditText) view.findViewById(R.id.loginPassword);
        Button login = (Button) view.findViewById(R.id.login);
        Button cancel = (Button) view.findViewById(R.id.cancel);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.backDefault();
            }
        });

        return view;
    }
}
