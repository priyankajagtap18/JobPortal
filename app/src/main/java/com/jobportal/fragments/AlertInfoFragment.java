package com.jobportal.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jobportal.R;
import com.jobportal.activities.AdDetailsActivity;
import com.jobportal.activities.AlertInfoActivity;
import com.jobportal.helpers.Utilities;

/**
 * Created by PravinK on 06-06-2017.
 */

public class AlertInfoFragment  extends Fragment {

    private Utilities mUtilities;

    public AlertInfoFragment() {
        // Required empty public constructor
    }

    public static AlertInfoFragment newInstance() {
        AlertInfoFragment fragment = new AlertInfoFragment();
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
        View mRootView = inflater.inflate(R.layout.fragment_alert_info, container, false);
        ((AlertInfoActivity)getActivity()).setTitle("Alert Info");
        bindControls(mRootView);
        return mRootView;
    }

    private void bindControls(View mRootView) {
        mUtilities = new Utilities(getActivity());
    }


}