package com.jobportal.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.jobportal.R;
import com.jobportal.constants.AppConstants;
import com.jobportal.fragments.MyAccountFragment;
import com.jobportal.helpers.Utilities;
import com.jobportal.utils.UIUtils;

/**
 * Created by pravink on 23-05-2017.
 */

public class ProfileActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener, View.OnClickListener {

    private Context mContext;
    private Utilities mUtilities;
    private Toolbar mToolbar;
    private CollapsingToolbarLayout collapsingToolbar;
    private AppBarLayout appBarLayout;
    private TextView mTvTitle;
    private AppCompatImageView toolbar_iv_logout,toolbar_iv_edit_profile,toolbar_iv_user;
    private boolean isShow      = false;
    private int     scrollRange = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        bindControls();
        setTitle("My Account");
        setListeners();
        mUtilities.replaceFragment(AppConstants.MAIN_CONTAINER, this, new MyAccountFragment(), R.string.hello_fragment, false);
    }

    private void setListeners() {
    }

    private void bindControls() {
        mContext = this;
        mUtilities = new Utilities(mContext);
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        mTvTitle=(TextView) findViewById(R.id.tv_title);
        toolbar_iv_logout=(AppCompatImageView)findViewById(R.id.toolbar_iv_logout);
        toolbar_iv_edit_profile=(AppCompatImageView)findViewById(R.id.toolbar_iv_edit_profile);
        toolbar_iv_user=(AppCompatImageView)findViewById(R.id.toolbar_iv_user);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        toolbar_iv_logout.setOnClickListener(this);
        toolbar_iv_edit_profile.setOnClickListener(this);
        toolbar_iv_user.setOnClickListener(this);
    }

    public void setTitle(String title) {
        mTvTitle.setText("My Account");
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

    /**
     * This will required when need to set logo or title to toolbar at the time of collapse
     */
    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (scrollRange == -1) {
            scrollRange = appBarLayout.getTotalScrollRange();
        }
        if (scrollRange + verticalOffset == 0) {
            collapsingToolbar.setTitle("My Account");
            isShow = true;
            UIUtils.showToast(this,"HI");
            // scroll.setEnabled(false);
        } else if (isShow) {
            collapsingToolbar.setTitle(" ");//carefull there should a space between double quote otherwise it wont work
            UIUtils.showToast(this,"HI");
            // scroll.setEnabled(true);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.toolbar_iv_logout:
                finish();
                break;
            case R.id.toolbar_iv_edit_profile:
                Intent intent=new Intent(this,EditProfileActivity.class);
                startActivity(intent);
                break;
            case R.id.toolbar_iv_user:
                UIUtils.showToast(this,"User");
                break;

        }
    }
}