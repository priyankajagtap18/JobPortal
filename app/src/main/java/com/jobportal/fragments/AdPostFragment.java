package com.jobportal.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.jobportal.R;
import com.jobportal.activities.AdsPostActivity;
import com.jobportal.constants.AppConstants;
import com.jobportal.databases.DatabaseHelper;
import com.jobportal.helpers.Utilities;

import java.util.ArrayList;

/**
 * Created by PravinK on 25-05-2017.
 */

public class AdPostFragment extends Fragment implements View.OnClickListener {

    private Utilities mUtilities;
    private AppCompatButton btn_post_ad;
    private AppCompatSpinner sp_role;
    private DatabaseHelper databaseHelper;
    private AdsPostActivity adsPostActivity;

    public AdPostFragment() {
        // Required empty public constructor
    }

    public static AdPostFragment newInstance() {
        AdPostFragment fragment = new AdPostFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adsPostActivity = (AdsPostActivity) getActivity();
        databaseHelper = DatabaseHelper.getInstance(adsPostActivity);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mRootView = inflater.inflate(R.layout.fragment_ad_post, container, false);
        adsPostActivity.setTitle("Post Free Ad");
        bindControls(mRootView);
        return mRootView;
    }

    private void bindControls(View mRootView) {
        mUtilities = new Utilities(getActivity());
        btn_post_ad= (AppCompatButton) mRootView.findViewById(R.id.btn_post_ad);
        sp_role = (AppCompatSpinner) mRootView.findViewById(R.id.sp_role);
        btn_post_ad.setOnClickListener(this);
        ArrayList<String> alRoles = databaseHelper.getAllJobCategory();
        if (alRoles != null) {
            setRolesAdapter(alRoles);
        }
    }

    private void setRolesAdapter(ArrayList<String> alRoles) {
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(adsPostActivity, android.R.layout.simple_spinner_item, alRoles); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_role.setAdapter(spinnerArrayAdapter);
    }

    @Override
    public void onClick(View v) {
        mUtilities.replaceFragment(AppConstants.MAIN_CONTAINER, getActivity(), new AdPostSuccessFragment(), R.string.hello_fragment, false);
    }
}
