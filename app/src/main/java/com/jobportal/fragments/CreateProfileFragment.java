package com.jobportal.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.jobportal.R;
import com.jobportal.databases.DatabaseHelper;
import com.jobportal.helpers.Utilities;

import java.util.ArrayList;

/**
 * Created by pita on 5/24/2017.
 */
public class CreateProfileFragment extends Fragment {
    private Utilities mUtilities;
    private AppCompatSpinner sp_city;
    private DatabaseHelper databaseHelper;

    public CreateProfileFragment() {
        // Required empty public constructor
    }


    public static CreateProfileFragment newInstance() {
       /* FragmentHome fragment = new FragmentHome();
        Bundle args = new Bundle();
        fragment.setArguments(args);*/
        return new CreateProfileFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseHelper = DatabaseHelper.getInstance(getActivity());
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mRootView = inflater.inflate(R.layout.fragment_create_profile, container, false);
        bindControls(mRootView);
        return mRootView;
    }

    private void bindControls(View mRootView) {
        mUtilities = new Utilities(getActivity());
        sp_city = (AppCompatSpinner) mRootView.findViewById(R.id.sp_city);
        ArrayList<String> cities = databaseHelper.getAllCity();
        setCityAdapter(cities);
    }

    private void setCityAdapter(ArrayList<String> cities) {
        if (cities != null) {
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, cities); //selected item will look like a spinner set from XML
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp_city.setAdapter(spinnerArrayAdapter);
        }
    }
}
