package com.jobportal.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
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
import com.jobportal.listeners.ClickListner;

import java.util.ArrayList;

/**
 * Created by pravink on 22-05-2017.
 */

public class RegistrationFragment extends Fragment implements View.OnClickListener {

    private Utilities mUtilities;
    private AppCompatButton btn_register;

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
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mRootView = inflater.inflate(R.layout.fragment_registration, container, false);
        bindControls(mRootView);
        return mRootView;
    }

    private void bindControls(View mRootView) {
        mUtilities = new Utilities(getActivity());
        btn_register= (AppCompatButton) mRootView.findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        ((ClickListner)getActivity()).getClick(true);
    }
}