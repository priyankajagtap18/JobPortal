package com.jobportal.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jobportal.R;
import com.jobportal.activities.AdsPostActivity;
import com.jobportal.helpers.Utilities;

/**
 * Created by PravinK on 25-05-2017.
 */

public class AdPostFragment extends Fragment implements View.OnClickListener {

    private Utilities mUtilities;
    private AppCompatButton btn_post_ad;

    public AdPostFragment() {
        // Required empty public constructor
    }

    public static AdPostFragment newInstance() {
        AdPostFragment fragment = new AdPostFragment();
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
        View mRootView = inflater.inflate(R.layout.fragment_ad_post, container, false);
        ((AdsPostActivity)getActivity()).setTitle("Post Free Ad");
        bindControls(mRootView);
        return mRootView;
    }

    private void bindControls(View mRootView) {
        mUtilities = new Utilities(getActivity());
        btn_post_ad= (AppCompatButton) mRootView.findViewById(R.id.btn_post_ad);
        btn_post_ad.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mUtilities.replaceFragment(getActivity(), new AdPostSuccessFragment(), R.string.hello_fragment, false);
    }
}
