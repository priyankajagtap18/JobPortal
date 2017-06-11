package com.jobportal.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jobportal.R;
import com.jobportal.activities.MainActivity;
import com.jobportal.activities.RegistrationAct;
import com.jobportal.constants.AppConstants;
import com.jobportal.entities.Authenticate;
import com.jobportal.entities.Login;
import com.jobportal.helpers.PreferenceHandler;
import com.jobportal.helpers.Utilities;
import com.jobportal.sync.SyncListener;
import com.jobportal.sync.SyncManager;

import java.util.ArrayList;

/**
 * Created by pravink on 22-05-2017.
 */

public class OTPFragment extends Fragment implements View.OnClickListener {

    private Utilities mUtilities;
    private String name, mobile, email, password,OTP;
    private SyncManager syncManager;
    private SyncListener syncListener;
    private RegistrationAct registrationAct;
    private AppCompatEditText et_otp;


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
            OTP = getArguments().getString("OTP");
            Log.i("otp", OTP);
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
        et_otp=(AppCompatEditText)mRootView.findViewById(R.id.et_otp);
        syncListener = new SyncListener() {
            @Override
            public void onSyncSuccess(int taskId, String result, ArrayList<?> arrResult) {
                Utilities.hideSoftInputKeypad(registrationAct);
                if (arrResult != null) {
                    if (arrResult.size() > 0) {
                        if (taskId == SyncManager.REGISTRATION) {
                            ArrayList<Authenticate> arrayList = (ArrayList<Authenticate>) arrResult;
                            if (!arrayList.get(0).getValue().isEmpty()) {
                                Login login = new Login();
                                login.setPassword(password);
                                login.setName(name);
                                login.setEmail(email);
                                login.setPhone(mobile);
                                PreferenceHandler.writeObject(registrationAct, AppConstants.PREF_LOGIN, login);
                                // mUtilities.showToast("Registration successful. Please login to continue.");
                                Intent intent = new Intent(registrationAct, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
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
                String enterOTP=et_otp.getText().toString().trim();
                if(OTP.equalsIgnoreCase(enterOTP)) {
                    mUtilities.showProgressDialog(getString(R.string.please_wait));
                    syncManager = new SyncManager(registrationAct, SyncManager.REGISTRATION, syncListener);
                    syncManager.doRegistration(name, email, mobile, password);
                }
                else
                {
                    mUtilities.replaceFragment(AppConstants.MAIN_CONTAINER, getActivity(), new RegistrationFragment(), R.string.register, false);
                }
                break;
        }
    }
}
