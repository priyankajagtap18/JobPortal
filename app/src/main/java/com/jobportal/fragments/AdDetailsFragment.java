package com.jobportal.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jobportal.R;
import com.jobportal.activities.AdDetailsActivity;
import com.jobportal.activities.AdListActivity;
import com.jobportal.activities.MakePremiumActivity;
import com.jobportal.activities.PinToTopActivity;
import com.jobportal.adapters.AdsAdapter;
import com.jobportal.helpers.Utilities;
import com.jobportal.listeners.AdapterResponseInterface;

import java.util.ArrayList;

/**
 * Created by PravinK on 05-06-2017.
 */

public class AdDetailsFragment extends Fragment implements View.OnClickListener {

    private Utilities mUtilities;
    private AppCompatButton btn_make_premium,btn_pin_to_top;

    public AdDetailsFragment() {
        // Required empty public constructor
    }

    public static AdDetailsFragment newInstance() {
        AdDetailsFragment fragment = new AdDetailsFragment();
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
        View mRootView = inflater.inflate(R.layout.fragment_ad_details, container, false);
        ((AdDetailsActivity)getActivity()).setTitle("");
        bindControls(mRootView);
        return mRootView;
    }

    private void bindControls(View mRootView) {
        mUtilities = new Utilities(getActivity());
        btn_make_premium=(AppCompatButton)mRootView.findViewById(R.id.btn_make_premium);
        btn_pin_to_top=(AppCompatButton)mRootView.findViewById(R.id.btn_pin_to_top);

        btn_pin_to_top.setOnClickListener(this);
        btn_make_premium.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId())
        {
            case R.id.btn_pin_to_top:
             intent=new Intent(getActivity(), PinToTopActivity.class);

                break;
            case R.id.btn_make_premium:
                intent=new Intent(getActivity(), MakePremiumActivity.class);
                break;
        }
        startActivity(intent);
    }
}
