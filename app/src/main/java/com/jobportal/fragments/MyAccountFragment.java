package com.jobportal.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jobportal.R;
import com.jobportal.activities.AdsPostActivity;
import com.jobportal.helpers.Utilities;
import com.jobportal.listeners.ClickListner;

/**
 * Created by pravink on 24-05-2017.
 */

public class MyAccountFragment extends Fragment implements View.OnClickListener {

    private Utilities mUtilities;
    private AppCompatButton btn_post_ad;

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
        btn_post_ad=(AppCompatButton)mRootView.findViewById(R.id.btn_post_ad);
        btn_post_ad.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
       switch (v.getId())
       {
           case R.id.btn_post_ad:
               Intent intent=new Intent(getActivity(), AdsPostActivity.class);
               startActivity(intent);
               break;
       }
    }
}