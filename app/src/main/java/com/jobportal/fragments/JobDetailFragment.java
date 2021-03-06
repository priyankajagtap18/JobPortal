package com.jobportal.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jobportal.R;
import com.jobportal.constants.AppConstants;
import com.jobportal.helpers.Utilities;

/**
 * Created by pita on 5/24/2017.
 */
public class JobDetailFragment extends Fragment implements View.OnClickListener {
    private Utilities mUtilities;

    public JobDetailFragment() {
        // Required empty public constructor
    }


    public static JobDetailFragment newInstance() {
       /* FragmentHome fragment = new FragmentHome();
        Bundle args = new Bundle();
        fragment.setArguments(args);*/
        return new JobDetailFragment();
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
        View mRootView = inflater.inflate(R.layout.fragment_job_detail, container, false);
        bindControls(mRootView);
        return mRootView;
    }

    private void bindControls(View mRootView) {
        mUtilities = new Utilities(getActivity());
        mRootView.findViewById(R.id.btn_apply).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_apply:
                mUtilities.replaceFragment(AppConstants.MAIN_CONTAINER, getActivity(), JobApplyFragment.newInstance(), R.string.job_apply, true);
                break;
        }
    }
}
