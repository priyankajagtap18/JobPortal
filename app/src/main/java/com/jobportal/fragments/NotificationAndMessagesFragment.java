package com.jobportal.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.jobportal.R;
import com.jobportal.activities.AlertInfoActivity;
import com.jobportal.activities.NotificationActivity;
import com.jobportal.helpers.Utilities;

/**
 * Created by PravinK on 06-06-2017.
 */

public class NotificationAndMessagesFragment extends Fragment implements View.OnClickListener {

    private Utilities mUtilities;
    private RelativeLayout rl_chat,rl_alerts,rl_notification,rl_doorsteps;

    public NotificationAndMessagesFragment() {
        // Required empty public constructor
    }

    public static NotificationAndMessagesFragment newInstance() {
        NotificationAndMessagesFragment fragment = new NotificationAndMessagesFragment();
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
        View mRootView = inflater.inflate(R.layout.fragment_notification_messages, container, false);
        bindControls(mRootView);
        return mRootView;
    }

    private void bindControls(View mRootView) {
        mUtilities = new Utilities(getActivity());
        rl_chat=(RelativeLayout)mRootView.findViewById(R.id.rl_chat);
        rl_alerts=(RelativeLayout)mRootView.findViewById(R.id.rl_alerts);
        rl_notification=(RelativeLayout)mRootView.findViewById(R.id.rl_notification);
        rl_doorsteps=(RelativeLayout)mRootView.findViewById(R.id.rl_doorsteps);

        rl_chat.setOnClickListener(this);
        rl_alerts.setOnClickListener(this);
        rl_notification.setOnClickListener(this);
        rl_doorsteps.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent=null;
        switch (v.getId())
        {
            case R.id.rl_chat:

                break;
            case R.id.rl_alerts:
                intent=new Intent(getActivity(), AlertInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_notification:
                intent=new Intent(getActivity(), NotificationActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_doorsteps:
                break;
        }

    }
}