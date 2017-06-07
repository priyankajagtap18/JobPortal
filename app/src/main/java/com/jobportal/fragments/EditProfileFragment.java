package com.jobportal.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.jobportal.R;
import com.jobportal.activities.EditProfileActivity;
import com.jobportal.constants.AppConstants;
import com.jobportal.databases.DatabaseHelper;
import com.jobportal.entities.EditProfile;
import com.jobportal.entities.Login;
import com.jobportal.helpers.PreferenceHandler;
import com.jobportal.helpers.Utilities;
import com.jobportal.sync.SyncListener;
import com.jobportal.sync.SyncManager;

import java.util.ArrayList;

/**
 * Created by pravink on 24-05-2017.
 */

public class EditProfileFragment extends Fragment implements View.OnClickListener {

    private Utilities mUtilities;
    private SyncManager syncManager;
    private SyncListener syncListener;
    private EditProfileActivity editProfileActivity;
    private AppCompatEditText et_login_name, et_login_mob_num, et_login_email, et_login_current_password, et_login_new_password;
    private AppCompatSpinner sp_apna_service_categories;
    private Login login;
    private DatabaseHelper databaseHelper;

    public EditProfileFragment() {
        // Required empty public constructor
    }

    public static EditProfileFragment newInstance() {
        EditProfileFragment fragment = new EditProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        editProfileActivity = (EditProfileActivity) getActivity();
        databaseHelper = DatabaseHelper.getInstance(editProfileActivity);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mRootView = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        bindControls(mRootView);
        return mRootView;
    }

    private void bindControls(View mRootView) {

        login = (Login) PreferenceHandler.readObject(editProfileActivity, AppConstants.PREF_LOGIN, null, Login.class);
        mUtilities = new Utilities(getActivity());
        et_login_name = (AppCompatEditText) mRootView.findViewById(R.id.et_login_name);
        et_login_current_password = (AppCompatEditText) mRootView.findViewById(R.id.et_login_current_password);
        et_login_mob_num = (AppCompatEditText) mRootView.findViewById(R.id.et_login_mob_num);
        et_login_email = (AppCompatEditText) mRootView.findViewById(R.id.et_login_email);
        et_login_name = (AppCompatEditText) mRootView.findViewById(R.id.et_login_name);
        et_login_new_password = (AppCompatEditText) mRootView.findViewById(R.id.et_login_new_password);
        sp_apna_service_categories = (AppCompatSpinner) mRootView.findViewById(R.id.sp_apna_service_categories);
        mRootView.findViewById(R.id.btn_save).setOnClickListener(this);
        syncListener = new SyncListener() {
            @Override
            public void onSyncSuccess(int taskId, String result, ArrayList<?> arrResult) {
                Utilities.hideSoftInputKeypad(editProfileActivity);
                if (arrResult != null) {
                    if (arrResult.size() > 0) {
                        switch (taskId) {
                            case SyncManager.EDIT_PROFILE:
                                ArrayList<EditProfile> arrayList = (ArrayList<EditProfile>) arrResult;
                                mUtilities.showToast("Profile updated successfully");
                                getActivity().getSupportFragmentManager().popBackStackImmediate();
                                break;
                            case SyncManager.CITY:
                                ArrayList<String> cities = databaseHelper.getAllCity();
                                setCityAdapter(cities);
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
                Utilities.hideSoftInputKeypad(editProfileActivity);
                mUtilities.showToast(message);
                mUtilities.hideProgressDialog();
            }

            @Override
            public void onSyncProgressUpdate(String message) {

            }
        };
        if (login != null) {
            et_login_name.setText(login.getName());
            et_login_current_password.setText(login.getPassword());
            et_login_email.setText(login.getEmail());
            et_login_mob_num.setText(login.getPhone());
        }
        ArrayList<String> cities = databaseHelper.getAllCity();
        if (cities != null) {
            setCityAdapter(cities);
        } else {
            getAllCities();
        }
    }

    private void setCityAdapter(ArrayList<String> cities) {
        if (cities != null) {
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(editProfileActivity, android.R.layout.simple_spinner_item, cities); //selected item will look like a spinner set from XML
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp_apna_service_categories.setAdapter(spinnerArrayAdapter);
        }
    }

    private void getAllCities() {
        mUtilities.showProgressDialog(getString(R.string.please_wait));
        syncManager = new SyncManager(editProfileActivity, SyncManager.CITY, syncListener);
        syncManager.getAllCity();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save:
                if (login != null) {
                    mUtilities.showProgressDialog(getString(R.string.please_wait));
                    syncManager = new SyncManager(editProfileActivity, SyncManager.EDIT_PROFILE, syncListener);
                    syncManager.editProfile(login.getCust_id(), "", et_login_name.getText().toString().trim(), sp_apna_service_categories.getSelectedItem().toString(),
                            et_login_mob_num.getText().toString().trim(), login.getEmail(), et_login_email.getText().toString().trim(),
                            et_login_current_password.getText().toString().trim(), et_login_new_password.getText().toString().trim());

                }
                break;
        }
    }
}