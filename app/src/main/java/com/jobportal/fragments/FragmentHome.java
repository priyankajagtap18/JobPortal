package com.jobportal.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jobportal.R;
import com.jobportal.adapters.TopRoleAdapter;
import com.jobportal.constants.AppConstants;
import com.jobportal.entities.TopRoles;
import com.jobportal.helpers.Utilities;
import com.jobportal.listeners.AdapterResponseInterface;

import java.util.ArrayList;


public class FragmentHome extends Fragment implements View.OnClickListener {

    private TopRoleAdapter adapter;
    private RecyclerView mRvRoles;
    private Utilities mUtilities;
    private ArrayList<TopRoles> alRoles;

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
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mRootView = inflater.inflate(R.layout.fragment_home, container, false);
        bindControls(mRootView);
        setRolesAdapter();
        return mRootView;
    }

    private void bindControls(View mRootView) {
        mUtilities = new Utilities(getActivity());
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
    }

    private void setRolesAdapter() {
        alRoles = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            TopRoles roles = new TopRoles();
            roles.setRole("Job " + i);
            alRoles.add(roles);
        }

        if (alRoles != null && alRoles.size() > 0) {
            adapter = new TopRoleAdapter(getActivity(), alRoles, new AdapterResponseInterface() {
                @Override
                public void getAdapterResponse(Bundle bundle) {
                    if (bundle != null) {
                        alRoles.get(bundle.getInt(AppConstants.ADAPTER_POSITION));
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
                mUtilities.replaceFragment(AppConstants.MAIN_CONTAINER, getActivity(), FilterProfileFragment.newInstance(), R.string.filter_profile, true);
                break;
        }
    }
}
