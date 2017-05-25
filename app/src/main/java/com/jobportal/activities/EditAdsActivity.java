package com.jobportal.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.jobportal.R;
import com.jobportal.fragments.AdEditFragment;
import com.jobportal.fragments.AdPostFragment;
import com.jobportal.helpers.Utilities;

/**
 * Created by PravinK on 25-05-2017.
 */

public class EditAdsActivity extends AppCompatActivity {

    private Context mContext;
    private Utilities mUtilities;
    private Toolbar mToolbar;
    private TextView mTvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ads_edit);
        bindControls();
        setListeners();
        mUtilities.replaceFragment(this, new AdEditFragment(), R.string.hello_fragment, false);
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

}
