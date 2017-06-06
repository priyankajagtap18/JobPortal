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
import com.jobportal.activities.AdsPostActivity;
import com.jobportal.adapters.AdsAdapter;
import com.jobportal.adapters.TopRoleAdapter;
import com.jobportal.constants.AppConstants;
import com.jobportal.entities.TopRoles;
import com.jobportal.helpers.Utilities;
import com.jobportal.listeners.AdapterResponseInterface;

import java.util.ArrayList;

/**
 * Created by PravinK on 25-05-2017.
 */

public class AdListFragment extends Fragment  {

    private Utilities mUtilities;
    private RecyclerView mRvAds;
    private AdsAdapter adsAdapter;

    public AdListFragment() {
        // Required empty public constructor
    }

    public static AdListFragment newInstance() {
        AdListFragment fragment = new AdListFragment();
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
        View mRootView = inflater.inflate(R.layout.fragment_ad_list, container, false);
        ((AdListActivity)getActivity()).setTitle("Jobs");
        bindControls(mRootView);
        setRolesAdapter();
        return mRootView;
    }

    private void bindControls(View mRootView) {
        mUtilities = new Utilities(getActivity());
        mRvAds = (RecyclerView) mRootView.findViewById(R.id.rv_ads);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvAds.setLayoutManager(layoutManager);
    }

    private void setRolesAdapter() {

        ArrayList<String> aList=new ArrayList<>();
            adsAdapter = new AdsAdapter(getActivity(), aList, new AdapterResponseInterface() {
                @Override
                public void getAdapterResponse(Bundle bundle) {
                    if (bundle != null) {
                        Intent intent=new Intent(getActivity(), AdDetailsActivity.class);
                        startActivity(intent);
                    }
                }
            });
            mRvAds.setAdapter(adsAdapter);


    }
}