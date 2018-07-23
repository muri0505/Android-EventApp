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

public class FragmentListSessionTab extends Fragment{
    String eventKey;
    ArrayList<String> sessionDate;
    HelperFirebase helperFirebase = new HelperFirebase();
    Bundle bundle;

    private FragmentTabHost fragmentTabHost;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentTabHost = new FragmentTabHost(getActivity());
        fragmentTabHost.setup(getActivity(), getChildFragmentManager(), R.layout.fragment_list_style);

        eventKey = getArguments().getString("eventKey");

        sessionDate = new ArrayList<String>();
        helperFirebase.helperSession(eventKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    ObjectSession s = data.getValue(ObjectSession.class);

                    if(!sessionDate.contains(s.getSessionDate())) {
                        sessionDate.add(s.getSessionDate());

                        bundle = new Bundle();
                        bundle.putString("eventKey", eventKey);
                        bundle.putString("sessionDate", s.getSessionDate());
                        fragmentTabHost.addTab(fragmentTabHost.newTabSpec(s.getSessionDate()).setIndicator(s.getSessionDate()),FragmentListSession.class, bundle);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        return fragmentTabHost;
    }
}
