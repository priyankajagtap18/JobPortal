package com.jobportal.activities;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.jobportal.R;
import com.jobportal.adapters.TopRoleAdapter;
import com.jobportal.constants.AppConstants;
import com.jobportal.entities.TopRoles;
import com.jobportal.fragments.FragmentHome;
import com.jobportal.fragments.OTPFragment;
import com.jobportal.fragments.RegistrationFragment;
import com.jobportal.helpers.Utilities;
import com.jobportal.listeners.AdapterResponseInterface;
import com.jobportal.listeners.ClickListner;
import com.jobportal.sync.SyncListener;
import com.jobportal.sync.SyncManager;
import com.jobportal.utils.UIUtils;

import java.util.ArrayList;

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
        setTitle("Register");
        setListeners();
        mUtilities.replaceFragment(this, new RegistrationFragment(), R.string.hello_fragment);
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

    public void setTitle(String title)
    {
        mTvTitle.setText(title);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        System.gc();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        if (menuItem.getItemId() == android.R.id.home)
        {
            onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void getClick(boolean isClick)
    {
        if(isClick)
        {
            mUtilities.replaceFragment(this, new OTPFragment(), R.string.hello_fragment);
            setTitle("OTP");
        }
    }
}