package com.jobportal.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.jobportal.R;
import com.jobportal.activities.MainActivity;
import com.jobportal.adapters.JobListAdapter;
import com.jobportal.constants.AppConstants;
import com.jobportal.databases.DatabaseHelper;
import com.jobportal.entities.TopRoles;
import com.jobportal.helpers.Utilities;
import com.jobportal.listeners.AdapterResponseInterface;
import com.jobportal.sync.SyncListener;
import com.jobportal.sync.SyncManager;

import java.util.ArrayList;

/**
 * Created by pita on 5/22/2017.
 */
public class JobTypeFragment extends Fragment implements View.OnClickListener {

    private JobListAdapter adapter;
    private RecyclerView mRvJobList;
    private Utilities mUtilities;
    private ArrayList<TopRoles> alRoles;
    private SyncListener syncListener;
    private SyncManager syncManager;
    private MainActivity mainActivity;


    public JobTypeFragment() {
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
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mRootView = inflater.inflate(R.layout.fragment_job_type, container, false);
        bindControls(mRootView);
        setRolesAdapter();
        return mRootView;
    }

    private void bindControls(View mRootView) {
        mUtilities = new Utilities(getActivity());
        mRvJobList = (RecyclerView) mRootView.findViewById(R.id.rv_job_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvJobList.setLayoutManager(layoutManager);
        mRootView.findViewById(R.id.tv_create_profile).setOnClickListener(this);
        mRootView.findViewById(R.id.tv_sort).setOnClickListener(this);
        syncListener = new SyncListener() {
            @Override
            public void onSyncSuccess(int taskId, String result, ArrayList<?> arrResult) {
                Utilities.hideSoftInputKeypad(mainActivity);
                if (arrResult != null) {
                    if (arrResult.size() > 0) {
                        switch (taskId) {
                            case SyncManager.CITY:

                                break;
                            case SyncManager.CHECK_CITY_UPDATE:
                                ArrayList<String> arrayList = (ArrayList<String>) arrResult;
                                if (arrayList.get(0).equalsIgnoreCase("1")) {
                                    getAllCities();
                                }
                                MainActivity.isCityAPICalledOnce = true;
                                break;
                            default:
                                onSyncFailure(taskId, getString(R.string.server_error));
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
                Utilities.hideSoftInputKeypad(mainActivity);
                mUtilities.showToast(message);
                mUtilities.hideProgressDialog();
            }

            @Override
            public void onSyncProgressUpdate(String message) {

            }
        };

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(mainActivity);
        if (databaseHelper.getAllCity() == null) {
            getAllCities();
        }
        callCheckCityUpdateAPI();
    }


    private void callCheckCityUpdateAPI() {
        if (!MainActivity.isCityAPICalledOnce) {
            mUtilities.showProgressDialog(getString(R.string.please_wait));
            syncManager = new SyncManager(mainActivity, SyncManager.CHECK_CITY_UPDATE, syncListener);
            syncManager.checkCityUpdate();
        }
    }

    private void getAllCities() {
        mUtilities.showProgressDialog(getString(R.string.please_wait));
        syncManager = new SyncManager(mainActivity, SyncManager.CITY, syncListener);
        syncManager.getAllCity();
    }

    private void setRolesAdapter() {
        alRoles = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            TopRoles roles = new TopRoles();
            roles.setRole("Job " + i);
            alRoles.add(roles);
        }

        if (alRoles != null && alRoles.size() > 0) {
            adapter = new JobListAdapter(getActivity(), alRoles, new AdapterResponseInterface() {
                @Override
                public void getAdapterResponse(Bundle bundle) {
                    if (bundle != null) {
                        if (bundle.getString(AppConstants.TYPE_CLICKED).equalsIgnoreCase(AppConstants.TYPE_LAYOUT)) {
                            alRoles.get(bundle.getInt(AppConstants.ADAPTER_POSITION));
                            mUtilities.replaceFragment(AppConstants.MAIN_CONTAINER, getActivity(), JobDetailFragment.newInstance(), R.string.job_detail, true);
                        } else {
                            mUtilities.replaceFragment(AppConstants.MAIN_CONTAINER, getActivity(), JobApplyFragment.newInstance(), R.string.job_apply, true);
                        }
                    }
                }
            });
            mRvJobList.setAdapter(adapter);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_create_profile:
                mUtilities.replaceFragment(AppConstants.MAIN_CONTAINER, getActivity(), CreateProfileFragment.newInstance(), R.string.job_detail, true);
                break;
            case R.id.tv_sort:
                showSortDialog();
                break;
        }
    }

    private void showSortDialog() {
        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.sort_dialog);
        dialog.setCanceledOnTouchOutside(true);
        final RadioGroup rgSort = (RadioGroup) dialog.findViewById(R.id.rgSort);
        final RadioButton rbtnRelevant = (RadioButton) dialog.findViewById(R.id.rbtn_relevant);
        final RadioButton rbtnNewest = (RadioButton) dialog.findViewById(R.id.rbtn_newest);

        rgSort.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                              @Override
                                              public void onCheckedChanged(RadioGroup radioGroup, int i) {
                                                  rbtnRelevant.setChecked(false);
                                                  rbtnNewest.setChecked(false);
                                                  rbtnRelevant.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                                                  rbtnNewest.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                                                  int selectedId = radioGroup.getCheckedRadioButtonId();
                                                  RadioButton radioButton = (RadioButton) radioGroup.findViewById(selectedId);
                                                  radioButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.checkbox_on_background, 0);
                                              }
                                          }
        );

        dialog.show();
        //mCommonCode.setLayoutParamForDialog(getActivity(), exitDialog);
    }
}
