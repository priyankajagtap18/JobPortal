package com.jobportal.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jobportal.R;
import com.jobportal.constants.AppConstants;
import com.jobportal.entities.Login;
import com.jobportal.entities.TopRoles;
import com.jobportal.fragments.FragmentHome;
import com.jobportal.helpers.PreferenceHandler;
import com.jobportal.helpers.Utilities;
import com.jobportal.sync.SyncListener;
import com.jobportal.sync.SyncManager;
import com.jobportal.utils.UIUtils;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private Context mContext;
    private Utilities mUtilities;
    private SyncManager syncManager;
    private SyncListener syncListener;
    private Toolbar mToolbar;
    private TextView mTvTitle;
    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    boolean doubleBackToExitPressedOnce = false;
    private ArrayList<TopRoles> alRoles;
    // private TopRoleAdapter adapter;
    // private RecyclerView mRvRoles;
    private RelativeLayout rel_profile;
    private TextView tv_ads, tv_shortlisted_ads, tv_notifications;
    public static boolean isJobCategoryAPICalledOnce, isCityAPICalledOnce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindControls();
        setListeners();
        setUpNavigationView();
//        askForPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, WRITE_EXST);
//        askForPermission(Manifest.permission.READ_EXTERNAL_STORAGE, READ_EXST);
//        setRolesAdapter();
        mUtilities.replaceFragment(AppConstants.MAIN_CONTAINER, this, new FragmentHome(), R.string.hello_fragment, false);
    }

    private void setListeners() {
        tv_ads.setOnClickListener(this);
        tv_shortlisted_ads.setOnClickListener(this);
        tv_notifications.setOnClickListener(this);
    }

    private void bindControls() {
        mContext = this;
        mUtilities = new Utilities(mContext);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        mTvTitle = (TextView) mToolbar.findViewById(R.id.tv_title);
        rel_profile = (RelativeLayout) findViewById(R.id.rel_profile);
        tv_ads = (TextView) mNavigationView.findViewById(R.id.tv_ads);
        tv_shortlisted_ads = (TextView) mNavigationView.findViewById(R.id.tv_shortlisted_ads);
        tv_notifications = (TextView) mNavigationView.findViewById(R.id.tv_notifications);
        Login login = (Login) PreferenceHandler.readObject(mContext, AppConstants.PREF_LOGIN, null, Login.class);
        //mRvRoles = (RecyclerView) mNavigationView.findViewById(R.id.rv_category);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        mRvRoles.setLayoutManager(layoutManager);
        setSupportActionBar(mToolbar);
        syncListener = new SyncListener() {
            @Override
            public void onSyncSuccess(int taskId, String result, ArrayList<?> arrResult) {
                Utilities.hideSoftInputKeypad(MainActivity.this);
                if (taskId == SyncManager.ALL_JOBS) {

                } else {
                    onSyncFailure(taskId, getString(R.string.server_error));
                }
                mUtilities.hideProgressDialog();
            }

            @Override
            public void onSyncFailure(int taskId, String message) {
                Utilities.hideSoftInputKeypad(MainActivity.this);
                mUtilities.hideProgressDialog();
            }

            @Override
            public void onSyncProgressUpdate(String message) {

            }
        };
        callAPI();
        rel_profile.setOnClickListener(this);
    }

    private void callAPI() {

    }

    public void setTitle(String title) {
        mTvTitle.setText(title);
    }

    /**
     * Initilize navigation view
     */
    private void setUpNavigationView() {

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        mNavigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

  /*  @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            //handleBackPress();
        }
    }*/

    private void handleBackPress() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        UIUtils.showToast(mContext, getString(R.string.msg_exit_app));
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceHandler.clearPreferenec(this);
        System.gc();
    }

    private void closeDrawer() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.rel_profile:
                intent = new Intent(this, ProfileActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_ads:
                intent = new Intent(this, AdsPostActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_shortlisted_ads:
                intent = new Intent(this, AdListActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_notifications:
                intent = new Intent(this, MessagesAndNotificationActivity.class);
                startActivity(intent);
                break;
        }
    }



    /* private void setRolesAdapter() {
        alRoles = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            TopRoles roles = new TopRoles();
            roles.setRole("Job " + i);
            alRoles.add(roles);
        }

        if (alRoles != null && alRoles.size() > 0) {
            adapter = new TopRoleAdapter(mContext, alRoles, new AdapterResponseInterface() {
                @Override
                public void getAdapterResponse(Bundle bundle) {
                    if (bundle != null) {
                        alRoles.get(bundle.getInt(AppConstants.ADAPTER_POSITION));
                    }
                }
            });
            mRvRoles.setAdapter(adapter);
        }
    }*/

   /* private void askForPermission(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        READ_EXST);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case WRITE_EXST: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }*/
}
