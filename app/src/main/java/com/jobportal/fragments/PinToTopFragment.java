package com.jobportal.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jobportal.R;
import com.jobportal.activities.PinToTopActivity;
import com.jobportal.helpers.Utilities;

/**
 * Created by PravinK on 05-06-2017.
 */

public class PinToTopFragment extends Fragment {

    private Utilities mUtilities;

    public PinToTopFragment() {
        // Required empty public constructor
    }

    public static PinToTopFragment newInstance() {
        PinToTopFragment fragment = new PinToTopFragment();
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
        View mRootView = inflater.inflate(R.layout.fragment_pin_to_top, container, false);
        ((PinToTopActivity)getActivity()).setTitle("Pin To Top");
        bindControls(mRootView);
        return mRootView;
    }

    private void bindControls(View mRootView) {
        mUtilities = new Utilities(getActivity());
    }
}
