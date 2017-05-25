package com.jobportal.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jobportal.R;
import com.jobportal.activities.AdsPostActivity;
import com.jobportal.helpers.Utilities;

/**
 * Created by PravinK on 25-05-2017.
 */

public class AdPostSuccessFragment extends Fragment {

    private Utilities mUtilities;


    public AdPostSuccessFragment() {
        // Required empty public constructor
    }

    public static AdPostSuccessFragment newInstance() {
        AdPostSuccessFragment fragment = new AdPostSuccessFragment();
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
        View mRootView = inflater.inflate(R.layout.fragment_ad_post_success, container, false);
        ((AdsPostActivity)getActivity()).setTitle("Post Ad Success");
        bindControls(mRootView);
        return mRootView;
    }

    private void bindControls(View mRootView) {
        mUtilities = new Utilities(getActivity());

    }

}