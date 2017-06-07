package com.jobportal.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jobportal.R;
import com.jobportal.activities.RegistrationAct;
import com.jobportal.entities.Authenticate;
import com.jobportal.helpers.Utilities;
import com.jobportal.sync.SyncListener;
import com.jobportal.sync.SyncManager;

import java.util.ArrayList;

/**
 * Created by pravink on 22-05-2017.
 */

public class OTPFragment extends Fragment implements View.OnClickListener {

    private Utilities mUtilities;
    private String name, mobile, email, password;
    private SyncManager syncManager;
    private SyncListener syncListener;
    private RegistrationAct registrationAct;


    public OTPFragment() {
        // Required empty public constructor
    }

    public static OTPFragment newInstance(Bundle bundle) {
        OTPFragment fragment = new OTPFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registrationAct = (RegistrationAct) getActivity();
        if (getArguments() != null) {
            name = getArguments().getString("name");
            mobile = getArguments().getString("mobile");
            email = getArguments().getString("email");
            password = getArguments().getString("password");
        }
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
        syncListener = new SyncListener() {
            @Override
            public void onSyncSuccess(int taskId, String result, ArrayList<?> arrResult) {
                Utilities.hideSoftInputKeypad(registrationAct);
                if (arrResult != null) {
                    if (arrResult.size() > 0) {
                        if (taskId == SyncManager.REGISTRATION) {
                            ArrayList<Authenticate> arrayList = (ArrayList<Authenticate>) arrResult;
                            if (!arrayList.get(0).getValue().isEmpty()) {
                                mUtilities.showToast("Registration successful. Please login to continue.");
                                registrationAct.finish();
                            } else {
                                onSyncFailure(taskId, arrayList.get(0).getMessage());
                            }

                        }
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
                Utilities.hideSoftInputKeypad(registrationAct);
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
                mUtilities.showProgressDialog(getString(R.string.please_wait));
                syncManager = new SyncManager(registrationAct, SyncManager.REGISTRATION, syncListener);
                syncManager.doRegistration(name, email, mobile, password);
                break;
        }
    }
}
