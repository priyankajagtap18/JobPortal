package com.jobportal.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jobportal.R;
import com.jobportal.activities.AdsPostActivity;
import com.jobportal.activities.ProfileActivity;
import com.jobportal.constants.AppConstants;
import com.jobportal.entities.Login;
import com.jobportal.entities.MyAccount;
import com.jobportal.helpers.PreferenceHandler;
import com.jobportal.helpers.Utilities;
import com.jobportal.sync.SyncListener;
import com.jobportal.sync.SyncManager;

import java.util.ArrayList;

/**
 * Created by pravink on 24-05-2017.
 */

public class MyAccountFragment extends Fragment implements View.OnClickListener {

    private Utilities mUtilities;
    private AppCompatButton btn_post_ad;
    private SyncManager syncManager;
    private SyncListener syncListener;
    private ProfileActivity profileActivity;
    private AppCompatTextView tvaddPosted, tvaddResponded, tvActiveSince, tvName, tvEmail, tvMobile;

    public MyAccountFragment() {
        // Required empty public constructor
    }

    public static MyAccountFragment newInstance() {
        MyAccountFragment fragment = new MyAccountFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        profileActivity = (ProfileActivity) getActivity();
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mRootView = inflater.inflate(R.layout.fragment_my_account, container, false);
        bindControls(mRootView);
        return mRootView;
    }

    private void bindControls(View mRootView) {
        mUtilities = new Utilities(getActivity());
        btn_post_ad = (AppCompatButton) mRootView.findViewById(R.id.btn_post_ad);
        tvaddPosted = (AppCompatTextView) mRootView.findViewById(R.id.tvaddPosted);
        tvaddResponded = (AppCompatTextView) mRootView.findViewById(R.id.tvaddResponded);
        tvActiveSince = (AppCompatTextView) mRootView.findViewById(R.id.tvActiveSince);
        tvName = (AppCompatTextView) mRootView.findViewById(R.id.tvName);
        tvEmail = (AppCompatTextView) mRootView.findViewById(R.id.tvEmail);
        tvMobile = (AppCompatTextView) mRootView.findViewById(R.id.tvMobile);
        btn_post_ad.setOnClickListener(this);
        syncListener = new SyncListener() {
            @Override
            public void onSyncSuccess(int taskId, String result, ArrayList<?> arrResult) {
                Utilities.hideSoftInputKeypad(profileActivity);
                if (arrResult != null) {
                    if (arrResult.size() > 0) {
                        switch (taskId) {
                            case SyncManager.MY_ACCOUNT:
                                ArrayList<MyAccount> arrayList = (ArrayList<MyAccount>) arrResult;
                                setValues(arrayList.get(0));
                                break;
                            default:
                                onSyncFailure(taskId, getString(R.string.server_error));
                                break;
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
                Utilities.hideSoftInputKeypad(profileActivity);
                mUtilities.hideProgressDialog();
            }

            @Override
            public void onSyncProgressUpdate(String message) {

            }
        };
        Login login = (Login) PreferenceHandler.readObject(profileActivity, AppConstants.PREF_LOGIN, null, Login.class);
        mUtilities.showProgressDialog(getString(R.string.please_wait));
        syncManager = new SyncManager(profileActivity, SyncManager.MY_ACCOUNT, syncListener);
        syncManager.getMyAccount(login.getCust_id());

        tvName.setText(login.getName());
        tvEmail.setText(login.getEmail());
        tvMobile.setText(login.getPhone());
    }

    private void setValues(MyAccount myAccount) {
        tvaddPosted.setText(myAccount.getAdd_posted());
        tvaddResponded.setText(myAccount.getAdd_responded());
        tvActiveSince.setText(myAccount.getActive_since());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_post_ad:
                Intent intent = new Intent(getActivity(), AdsPostActivity.class);
                startActivity(intent);
                break;
        }
    }
}