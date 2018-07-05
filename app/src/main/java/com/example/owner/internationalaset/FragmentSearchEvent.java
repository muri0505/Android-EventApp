package com.example.owner.internationalaset;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
/*
    fragment search event
 */

public class FragmentSearchEvent extends Fragment {
    EditText searchEvent;

    public FragmentSearchEvent(){}

    //submit button listener
    FragmentSearchEventListener listener;
    public interface FragmentSearchEventListener{
        public void SearchListeren(String eventName);
    }

    public void onAttach(Activity activity){
        super.onAttach(activity);
        try{
            listener = (FragmentSearchEventListener) activity;
        }catch(ClassCastException e){
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_event, container, false);
        searchEvent  = (EditText) view.findViewById(R.id.eventName);
        Button submit  = (Button) view.findViewById(R.id.submit);

        //pass nonempty event name to fragment event information
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String eventName = searchEvent.getText().toString();

                if(eventName.equals("")) {
                    Snackbar.make(getActivity().findViewById(android.R.id.content), "Please enter event name", Snackbar.LENGTH_LONG).show();
                }else{
                    listener.SearchListeren(eventName);
                }
            }
        });

        return view;
    }
}
