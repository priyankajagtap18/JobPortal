package com.jobportal.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jobportal.R;
import com.jobportal.activities.MainActivity;
import com.jobportal.adapters.TopRoleAdapter;
import com.jobportal.constants.AppConstants;
import com.jobportal.databases.DatabaseHelper;
import com.jobportal.entities.AllJob;
import com.jobportal.helpers.Utilities;
import com.jobportal.listeners.AdapterResponseInterface;
import com.jobportal.sync.SyncListener;
import com.jobportal.sync.SyncManager;

import java.util.ArrayList;


public class FragmentHome extends Fragment implements View.OnClickListener {

    private TopRoleAdapter adapter;
    private RecyclerView mRvRoles;
    private Utilities mUtilities;
    private ArrayList<String> alRoles;
    private SyncManager syncManager;
    private SyncListener syncListener;
    private MainActivity mainActivity;
    private DatabaseHelper databaseHelper;

    public FragmentHome() {
        // Required empty public constructor
    }


    public static FragmentHome newInstance(String param1, String param2) {
        FragmentHome fragment = new FragmentHome();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
        mUtilities = new Utilities(mainActivity);
        databaseHelper = DatabaseHelper.getInstance(mainActivity);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mRootView = inflater.inflate(R.layout.fragment_home, container, false);
        bindControls(mRootView);
        return mRootView;
    }

    private void bindControls(View mRootView) {

        mRvRoles = (RecyclerView) mRootView.findViewById(R.id.rv_top_roles);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvRoles.setLayoutManager(layoutManager);
        mRootView.findViewById(R.id.tv_work_from_home).setOnClickListener(this);
        mRootView.findViewById(R.id.tv_part_time_job).setOnClickListener(this);
        mRootView.findViewById(R.id.tv_full_time_jobs).setOnClickListener(this);
        mRootView.findViewById(R.id.internship_job).setOnClickListener(this);
        mRootView.findViewById(R.id.tv_hire_candidates).setOnClickListener(this);
        mRootView.findViewById(R.id.tv_premiun_job_seeker).setOnClickListener(this);
        syncListener = new SyncListener() {
            @Override
            public void onSyncSuccess(int taskId, String result, ArrayList<?> arrResult) {
                Utilities.hideSoftInputKeypad(mainActivity);
                if (arrResult != null) {
                    if (arrResult.size() > 0) {
                        switch (taskId) {
                            case SyncManager.ALL_JOBS:
                                alRoles = (ArrayList<String>) arrResult;
                                setRolesAdapter();

                                break;
                            case SyncManager.CHECK_JOB_UPDATE:
                                ArrayList<String> arrayList = (ArrayList<String>) arrResult;
                                if (arrayList.get(0).equalsIgnoreCase("1")) {
                                    callGetAllCityAPI();
                                }
                                MainActivity.isJobCategoryAPICalledOnce = true;
                                break;
                        }
                        if (taskId == SyncManager.ALL_JOBS) {
                            ArrayList<AllJob> arrayList = (ArrayList<AllJob>) arrResult;

                            if (arrayList != null && arrayList.size() > 0) {
                                alRoles = arrayList.get(0).getJobCategories();
                                setRolesAdapter();
                            } else {
                                onSyncFailure(taskId, getString(R.string.server_error));
                            }
                        }
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
        callCheckJobUpdateAPI();
        alRoles = databaseHelper.getAllJobCategory();
        if (alRoles == null) {
            callGetAllCityAPI();
        } else {
            setRolesAdapter();
        }
    }

    private void callGetAllCityAPI() {
        mUtilities.showProgressDialog(getString(R.string.please_wait));
        syncManager = new SyncManager(mainActivity, SyncManager.ALL_JOBS, syncListener);
        syncManager.getAllJobs();
    }

    private void callCheckJobUpdateAPI() {
        if (!MainActivity.isJobCategoryAPICalledOnce) {
            mUtilities.showProgressDialog(getString(R.string.please_wait));
            syncManager = new SyncManager(mainActivity, SyncManager.CHECK_JOB_UPDATE, syncListener);
            syncManager.checkJobUpdate();
        }
    }

    private void setRolesAdapter() {
        if (alRoles != null && alRoles.size() > 0) {
            adapter = new TopRoleAdapter(getActivity(), alRoles, new AdapterResponseInterface() {
                @Override
                public void getAdapterResponse(Bundle bundle) {
                    if (bundle != null) {
                        alRoles.get(bundle.getInt(AppConstants.ADAPTER_POSITION));
                        mUtilities.replaceFragment(AppConstants.MAIN_CONTAINER, getActivity(), new JobTypeFragment(), R.string.job_type, true);
                    }
                }
            });
            mRvRoles.setAdapter(adapter);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_work_from_home:
                mUtilities.replaceFragment(AppConstants.MAIN_CONTAINER, getActivity(), new JobTypeFragment(), R.string.job_type, true);
                break;
            case R.id.tv_part_time_job:
                mUtilities.replaceFragment(AppConstants.MAIN_CONTAINER, getActivity(), new JobTypeFragment(), R.string.job_type, true);
                break;
            case R.id.tv_full_time_jobs:
                mUtilities.replaceFragment(AppConstants.MAIN_CONTAINER, getActivity(), new JobTypeFragment(), R.string.job_type, true);
                break;
            case R.id.internship_job:
                mUtilities.replaceFragment(AppConstants.MAIN_CONTAINER, getActivity(), new JobTypeFragment(), R.string.job_type, true);
                break;

            case R.id.tv_hire_candidates:
                mUtilities.replaceFragment(AppConstants.MAIN_CONTAINER, getActivity(), HireCandidatesFragment.newInstance(), R.string.hire_candidates, true);
                break;
        }
    }
}
