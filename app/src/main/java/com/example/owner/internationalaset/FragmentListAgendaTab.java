package com.example.owner.internationalaset;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FragmentListAgendaTab extends Fragment{
    String eventKey;
    ArrayList<String> agendaDate;
    HelperFirebase helperFirebase = new HelperFirebase();
    Bundle bundle = new Bundle();
    boolean controlMode = false;

    private FragmentTabHost fragmentTabHost;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentTabHost = new FragmentTabHost(getActivity());
        fragmentTabHost.setup(getActivity(), getChildFragmentManager(), R.layout.fragment_list_style);

        eventKey = getArguments().getString("eventKey");
        controlMode = getArguments().getBoolean("controlMode");
        bundle.putString("eventKey", eventKey);
        bundle.putBoolean("controlMode", controlMode);

        agendaDate = new ArrayList<String>();
        helperFirebase.helperAgenda(eventKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    ObjectAgenda s = data.getValue(ObjectAgenda.class);

                    if(!agendaDate.contains(s.getAgendaDate())) {
                        agendaDate.add(s.getAgendaDate());
                        fragmentTabHost.addTab(fragmentTabHost.newTabSpec(s.getAgendaDate()).setIndicator(s.getAgendaDate()),FragmentListAgenda.class, bundle);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        return fragmentTabHost;
    }
}
