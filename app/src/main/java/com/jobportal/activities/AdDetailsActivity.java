package com.jobportal.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.jobportal.R;
import com.jobportal.constants.AppConstants;
import com.jobportal.fragments.AdDetailsFragment;
import com.jobportal.fragments.AdListFragment;
import com.jobportal.helpers.Utilities;

/**
 * Created by PravinK on 05-06-2017.
 */

public class AdDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private Utilities mUtilities;
    private Toolbar mToolbar;
    private TextView mTvTitle;
    private AppCompatImageView toolbar_iv_edit_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_details);
        bindControls();
        setListeners();
        mUtilities.replaceFragment(AppConstants.MAIN_CONTAINER, this, new AdDetailsFragment(), R.string.hello_fragment, false);
    }

    private void setListeners() {
    }

    private void bindControls() {
        mContext = this;
        mUtilities = new Utilities(mContext);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTvTitle = (TextView) mToolbar.findViewById(R.id.tv_title);
        toolbar_iv_edit_profile = (AppCompatImageView) mToolbar.findViewById(R.id.toolbar_iv_edit_profile);
        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        toolbar_iv_edit_profile.setOnClickListener(this);
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
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.toolbar_iv_edit_profile:
                Intent intent=new Intent(this,EditAdsActivity.class);
                startActivity(intent);
                break;
        }
    }
}