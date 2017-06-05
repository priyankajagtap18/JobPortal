package com.jobportal.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.jobportal.R;
import com.jobportal.constants.AppConstants;
import com.jobportal.fragments.OTPFragment;
import com.jobportal.fragments.RegistrationFragment;
import com.jobportal.helpers.Utilities;
import com.jobportal.listeners.ClickListner;

/**
 * Created by pravink on 22-05-2017.
 */

public class RegistrationAct extends AppCompatActivity implements ClickListner {

    private Context mContext;
    private Utilities mUtilities;
    private Toolbar mToolbar;
    private TextView mTvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        bindControls();
        setTitle(getString(R.string.register));
        setListeners();
        mUtilities.replaceFragment(AppConstants.MAIN_CONTAINER, this, new RegistrationFragment(), R.string.register, false);
    }

    private void setListeners() {
    }

    private void bindControls() {
        mContext = this;
        mUtilities = new Utilities(mContext);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTvTitle = (TextView) mToolbar.findViewById(R.id.tv_title);
        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
    }

    public void setTitle(String title) {
        mTvTitle.setText(title);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.gc();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void getClick(boolean isClick) {
        if (isClick) {
            // mUtilities.replaceFragment(AppConstants.MAIN_CONTAINER, this, new OTPFragment(), R.string.OTP, true);
            //setTitle(R.string.OTP);
        }
    }

    @Override
    public void getClick(boolean isClick, Bundle bundle) {
        if (isClick) {
            mUtilities.replaceFragment(AppConstants.MAIN_CONTAINER, this, new OTPFragment().newInstance(bundle), R.string.OTP, true);
        }

    }
}