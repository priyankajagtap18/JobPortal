package com.jobportal.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jobportal.R;
import com.jobportal.helpers.Utilities;
import com.jobportal.sync.SyncListener;
import com.jobportal.sync.SyncManager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Context mContext;
    private Utilities mUtilities;
    private SyncManager syncManager;
    private SyncListener syncListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindControls();
        setListeners();
    }

    private void setListeners() {

    }

    private void bindControls() {
        mContext = this;
        mUtilities = new Utilities(mContext);
        syncListener = new SyncListener() {
            @Override
            public void onSyncSuccess(int taskId, String result, ArrayList<?> arrResult) {
                Utilities.hideSoftInputKeypad(mContext, MainActivity.this);
                if (taskId == SyncManager.ALL_CHANNEL) {

                } else {
                    onSyncFailure(taskId, getString(R.string.server_error));
                }
                mUtilities.hideProgressDialog();
            }

            @Override
            public void onSyncFailure(int taskId, String message) {
                Utilities.hideSoftInputKeypad(mContext, MainActivity.this);
                mUtilities.hideProgressDialog();
            }

            @Override
            public void onSyncProgressUpdate(String message) {

            }
        };
        callAPI();
    }

    private void callAPI() {

    }
}
