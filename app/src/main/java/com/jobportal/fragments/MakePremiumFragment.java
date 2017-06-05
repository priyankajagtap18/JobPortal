package com.jobportal.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jobportal.R;
import com.jobportal.activities.AdDetailsActivity;
import com.jobportal.activities.MakePremiumActivity;
import com.jobportal.helpers.Utilities;

/**
 * Created by PravinK on 05-06-2017.
 */

public class MakePremiumFragment extends Fragment {

    private Utilities mUtilities;

    public MakePremiumFragment() {
        // Required empty public constructor
    }

    public static MakePremiumFragment newInstance() {
        MakePremiumFragment fragment = new MakePremiumFragment();
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
        View mRootView = inflater.inflate(R.layout.fragment_make_premium, container, false);
        ((MakePremiumActivity)getActivity()).setTitle("Make Premium");
        bindControls(mRootView);
        return mRootView;
    }

    private void bindControls(View mRootView) {
        mUtilities = new Utilities(getActivity());
    }
}