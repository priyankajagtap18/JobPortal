package com.jobportal.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jobportal.R;
import com.jobportal.activities.RegistrationAct;
import com.jobportal.helpers.Utilities;
import com.jobportal.listeners.ClickListner;
import com.jobportal.sync.SyncListener;
import com.jobportal.sync.SyncManager;

import java.util.ArrayList;

/**
 * Created by pravink on 22-05-2017.
 */

public class RegistrationFragment extends Fragment implements View.OnClickListener {

    private Utilities mUtilities;
    private AppCompatButton btn_register;
    private AppCompatEditText et_login_mob_num, et_login_name, et_login_email, et_login_password, et_login_confirm_password;
    private SyncManager syncManager;
    private SyncListener syncListener;
    private RegistrationAct mainActivity;
    private String name, mobile, email, password;

    public RegistrationFragment() {
        // Required empty public constructor
    }

    public static RegistrationFragment newInstance() {
        RegistrationFragment fragment = new RegistrationFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (RegistrationAct) getActivity();
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mRootView = inflater.inflate(R.layout.fragment_registration, container, false);
        bindControls(mRootView);
        return mRootView;
    }

    private void bindControls(View mRootView) {
        mUtilities = new Utilities(getActivity());
        btn_register = (AppCompatButton) mRootView.findViewById(R.id.btn_register);
        et_login_mob_num = (AppCompatEditText) mRootView.findViewById(R.id.et_login_mob_num);
        et_login_name = (AppCompatEditText) mRootView.findViewById(R.id.et_login_name);
        et_login_email = (AppCompatEditText) mRootView.findViewById(R.id.et_login_email);
        et_login_password = (AppCompatEditText) mRootView.findViewById(R.id.et_login_password);
        et_login_confirm_password = (AppCompatEditText) mRootView.findViewById(R.id.et_login_confirm_password);
        btn_register.setOnClickListener(this);
        syncListener = new SyncListener() {
            @Override
            public void onSyncSuccess(int taskId, String result, ArrayList<?> arrResult) {
                Utilities.hideSoftInputKeypad(mainActivity);
                if (taskId == SyncManager.GET_REGISTRATION_OTP) {
                    ArrayList<String> arrayList = (ArrayList<String>) arrResult;
                    if (!arrayList.get(0).isEmpty()) {

                        Bundle bundle = new Bundle();
                        bundle.putString("name", name);
                        bundle.putString("mobile", mobile);
                        bundle.putString("email", email);
                        bundle.putString("password", password);
                        bundle.putString("OTP", arrayList.get(0));
                        ((ClickListner) getActivity()).getClick(true, bundle);
                    } else {
                        onSyncFailure(taskId, arrayList.get(0));
                    }

                } else {
                    onSyncFailure(taskId, getString(R.string.server_error));
                }
                mUtilities.hideProgressDialog();
            }

            @Override
            public void onSyncFailure(int taskId, String message) {
                Utilities.hideSoftInputKeypad(mainActivity);
                mUtilities.showToast(message);
                mUtilities.hideProgressDialog();
            }

            @Override
            public void onSyncProgressUpdate(String message) {

            }
        };

    }

    @Override
    public void onClick(View v) {
        Utilities.hideSoftInputKeypad(mainActivity);
        switch (v.getId()) {
            case R.id.btn_register:
                name = et_login_name.getText().toString().trim();
                mobile = et_login_mob_num.getText().toString().trim();
                email = et_login_email.getText().toString().trim();
                password = et_login_password.getText().toString().trim();
                if (isValidCredentials()) {
                    mUtilities.showProgressDialog(getString(R.string.please_wait));
                    syncManager = new SyncManager(mainActivity, SyncManager.GET_REGISTRATION_OTP, syncListener);
                    syncManager.getRegistrationOTP(mobile);
                }
                break;
        }

    }

    private boolean isValidCredentials() {
        if (name.length() == 0) {
            mUtilities.showToast("Please enter name");
            return false;
        } else if (mobile.length() == 0) {
            mUtilities.showToast("Please enter mobile no");
            return false;
        } else if (email.length() == 0) {
            mUtilities.showToast("Please enter email id");
            return false;
        } else if (password.length() == 0) {
            mUtilities.showToast("Please enter password");
            return false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mUtilities.showToast("Please enter valid email id");
            return false;
        } else {
            return true;
        }

    }
}