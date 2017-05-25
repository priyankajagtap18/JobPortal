package com.jobportal.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jobportal.R;
import com.jobportal.activities.AdsPostActivity;
import com.jobportal.activities.EditAdsActivity;
import com.jobportal.helpers.Utilities;

/**
 * Created by PravinK on 25-05-2017.
 */

public class AdEditFragment extends Fragment implements View.OnClickListener {

    private Utilities mUtilities;
    private AppCompatButton btn_update_ad;

    public AdEditFragment() {
        // Required empty public constructor
    }

    public static AdEditFragment newInstance() {
        AdEditFragment fragment = new AdEditFragment();
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
        View mRootView = inflater.inflate(R.layout.fragment_ad_edit, container, false);
        ((EditAdsActivity)getActivity()).setTitle("Edit Ad");
        bindControls(mRootView);
        return mRootView;
    }

    private void bindControls(View mRootView) {
        mUtilities = new Utilities(getActivity());
        btn_update_ad= (AppCompatButton) mRootView.findViewById(R.id.btn_update_ad);
        btn_update_ad.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }
}
