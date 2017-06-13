package com.jobportal.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jobportal.R;
import com.jobportal.activities.ProfileActivity;
import com.jobportal.helpers.Utilities;
import com.jobportal.sync.SyncListener;
import com.jobportal.sync.SyncManager;

import java.util.ArrayList;

/**
 * Created by pravink on 22-05-2017.
 */

public class VerifyMobileOTPFragment extends Fragment implements View.OnClickListener {

    private Utilities mUtilities;
    private SyncManager syncManager;
    private SyncListener syncListener;
    private ProfileActivity profileActivity;
    private AppCompatEditText et_otp;
    private static String otp;


    public VerifyMobileOTPFragment() {
        // Required empty public constructor
    }

    public static VerifyMobileOTPFragment newInstance(Bundle bundle) {
        VerifyMobileOTPFragment fragment = new VerifyMobileOTPFragment();
        otp = bundle.getString("otp");
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        profileActivity = (ProfileActivity) getActivity();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mRootView = inflater.inflate(R.layout.fragment_otp, container, false);
        bindControls(mRootView);
        return mRootView;
    }

    private void bindControls(View mRootView) {
        mUtilities = new Utilities(getActivity());
        mRootView.findViewById(R.id.btn_register).setOnClickListener(this);
        et_otp = (AppCompatEditText) mRootView.findViewById(R.id.et_otp);
        syncListener = new SyncListener() {
            @Override
            public void onSyncSuccess(int taskId, String result, ArrayList<?> arrResult) {
                Utilities.hideSoftInputKeypad(profileActivity);
                if (arrResult != null) {
                    if (arrResult.size() > 0) {

                    } else {
                        onSyncFailure(taskId, getString(R.string.server_error));
                    }
                } else {
                    onSyncFailure(taskId, getString(R.string.server_error));
                }
                mUtilities.hideProgressDialog();
            }

            @Override
            public void onSyncFailure(int taskId, String message) {
                Utilities.hideSoftInputKeypad(profileActivity);
                mUtilities.showToast(message);
                mUtilities.hideProgressDialog();
            }

            @Override
            public void onSyncProgressUpdate(String message) {

            }
        };
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                String enterOTP = et_otp.getText().toString().trim();
                if (otp.equalsIgnoreCase(enterOTP)) {

                } else {

                }
                break;
        }
    }
}
