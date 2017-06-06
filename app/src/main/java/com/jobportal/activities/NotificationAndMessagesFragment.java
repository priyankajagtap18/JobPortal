package com.jobportal.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jobportal.R;
import com.jobportal.fragments.MyAccountFragment;
import com.jobportal.helpers.Utilities;

/**
 * Created by PravinK on 06-06-2017.
 */

public class NotificationAndMessagesFragment extends Fragment  {

    private Utilities mUtilities;

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

    }

}