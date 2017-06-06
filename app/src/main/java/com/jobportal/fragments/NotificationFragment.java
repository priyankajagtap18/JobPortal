package com.jobportal.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jobportal.R;
import com.jobportal.activities.AdListActivity;
import com.jobportal.activities.NotificationActivity;
import com.jobportal.adapters.AdsAdapter;
import com.jobportal.adapters.NotificationAdapter;
import com.jobportal.helpers.Utilities;
import com.jobportal.listeners.AdapterResponseInterface;

import java.util.ArrayList;

/**
 * Created by PravinK on 06-06-2017.
 */

public class NotificationFragment extends Fragment  {

    private Utilities mUtilities;
    private RecyclerView mRvNotification;
    private NotificationAdapter notificationAdapter;

    public NotificationFragment() {
        // Required empty public constructor
    }

    public static NotificationFragment newInstance() {
        NotificationFragment fragment = new NotificationFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mRootView = inflater.inflate(R.layout.fragment_notification, container, false);
        ((NotificationActivity)getActivity()).setTitle("Notifications");
        bindControls(mRootView);
        setRolesAdapter();
        return mRootView;
    }

    private void bindControls(View mRootView) {
        mUtilities = new Utilities(getActivity());
        mRvNotification = (RecyclerView) mRootView.findViewById(R.id.rv_notification);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvNotification.setLayoutManager(layoutManager);
    }

    private void setRolesAdapter() {
        ArrayList<String> aList=new ArrayList<>();
        notificationAdapter = new NotificationAdapter(getActivity(), aList, new AdapterResponseInterface() {
            @Override
            public void getAdapterResponse(Bundle bundle) {
                if (bundle != null) {
                    //  aList.get(bundle.getInt(AppConstants.ADAPTER_POSITION));


                }
            }
        });
        mRvNotification.setAdapter(notificationAdapter);
    }
}