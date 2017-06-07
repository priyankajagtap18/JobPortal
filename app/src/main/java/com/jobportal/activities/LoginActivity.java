package com.jobportal.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.jobportal.R;
import com.jobportal.adapters.CustomPagerAdapter;
import com.jobportal.constants.AppConstants;
import com.jobportal.entities.Login;
import com.jobportal.helpers.PreferenceHandler;
import com.jobportal.helpers.Utilities;
import com.jobportal.listeners.ClickListner;
import com.jobportal.sync.SyncListener;
import com.jobportal.sync.SyncManager;
import com.pixelcan.inkpageindicator.InkPageIndicator;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by pravink on 23-05-2017.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, ClickListner {

    private Context mContext;
    private Utilities mUtilities;
    private ViewPager viewPager;
    private InkPageIndicator inkPageIndicator;
    private int currentPage = 0;
    private AppCompatButton btn_submit;
    private AppCompatEditText et_login_mob_num, et_login_password;
    private TextInputLayout til_login_mobile_num, til_login_password;
    private SyncManager syncManager;
    private SyncListener syncListener;
    private LoginActivity loginActivity;
    private AppCompatTextView tv_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bindControls();
        initPager();
        automateViewPager();
    }

    private void bindControls() {
        mContext = this;
        loginActivity = this;

        mUtilities = new Utilities(mContext);
        viewPager = (ViewPager) findViewById(R.id.pager);
        inkPageIndicator = (InkPageIndicator) findViewById(R.id.indicator);
        btn_submit = (AppCompatButton) findViewById(R.id.btn_submitr);
        et_login_mob_num = (AppCompatEditText) findViewById(R.id.et_login_mob_num);
        et_login_password = (AppCompatEditText) findViewById(R.id.et_login_password);
        til_login_mobile_num = (TextInputLayout) findViewById(R.id.til_login_mobile_num);
        til_login_password = (TextInputLayout) findViewById(R.id.til_login_password);
        tv_register = (AppCompatTextView) findViewById(R.id.tv_register);
        tv_register.setOnClickListener(this);

        syncListener = new SyncListener() {
            @Override
            public void onSyncSuccess(int taskId, String result, ArrayList<?> arrResult) {
                Utilities.hideSoftInputKeypad(loginActivity);
                if (arrResult != null) {
                    if (arrResult.size() > 0) {
                        switch (taskId) {
                            case SyncManager.REGISTRATION_CHECK:

                                ArrayList<String> arrayList = (ArrayList<String>) arrResult;
                                if (arrayList.get(0).equalsIgnoreCase("1")) {
                                    getClick(true);
                                } else if (arrayList.get(0).equalsIgnoreCase("0")) {
                                    doRegister();
                                } else {
                                    onSyncFailure(taskId, arrayList.get(0));
                                }
                                break;
                            case SyncManager.LOGIN:
                                ArrayList<Login> logins = (ArrayList<Login>) arrResult;
                                if (logins.get(0).isSuccess()) {
                                    logins.get(0).setPassword(et_login_password.getText().toString().trim());
                                    PreferenceHandler.writeObject(loginActivity, AppConstants.PREF_LOGIN, logins.get(0));
                                    Intent intent = new Intent(loginActivity, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    onSyncFailure(taskId, logins.get(0).getError());
                                }

                                break;
                            default:
                                onSyncFailure(taskId, getString(R.string.server_error));
                                break;
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
                Utilities.hideSoftInputKeypad(loginActivity);
                mUtilities.showToast(message);
                mUtilities.hideProgressDialog();
            }

            @Override
            public void onSyncProgressUpdate(String message) {

            }
        };

        btn_submit.setOnClickListener(this);
    }

    /**
     * Initializing pager
     */
    private void initPager() {
        ArrayList<Integer> listImages = new ArrayList<>();
        listImages.add(R.drawable.skip_pager_img1);
        listImages.add(R.drawable.skip_pager_img2);
        listImages.add(R.drawable.skip_pager_img3);
        CustomPagerAdapter adapter = new CustomPagerAdapter(this, listImages);
        viewPager.setAdapter(adapter);
        inkPageIndicator.setViewPager(viewPager);
    }

    private void automateViewPager() {
        /*After setting the adapter use the timer */
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == 4 - 1) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 500, 3000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.gc();
    }

    @Override
    public void onClick(View v) {
        Utilities.hideSoftInputKeypad(loginActivity);
        switch (v.getId()) {
            case R.id.tv_register:
                doRegister();
                break;
            case R.id.btn_submitr:
                if (btn_submit.getText().toString().trim().equalsIgnoreCase("Continue")) {
                    if (isValidMobileNo()) {
                        mUtilities.showProgressDialog(getString(R.string.please_wait));
                        syncManager = new SyncManager(loginActivity, SyncManager.REGISTRATION_CHECK, syncListener);
                        syncManager.doRegistrationCheck(et_login_mob_num.getText().toString().trim());
                    }
                } else {
                    if (isValidCredentials()) {
                        mUtilities.showProgressDialog(getString(R.string.please_wait));
                        syncManager = new SyncManager(loginActivity, SyncManager.LOGIN, syncListener);
                        syncManager.login(et_login_mob_num.getText().toString().trim(), et_login_password.getText().toString().trim());
                    }
                }
                break;
        }

    }

    private boolean isValidCredentials() {
        if (et_login_mob_num.getText().toString().trim().length() == 0) {
            mUtilities.showToast("Please enter mobile no");
            return false;
        } else if (et_login_password.getText().toString().trim().length() == 0) {
            mUtilities.showToast("Please enter password");
            return false;
        } else {
            return true;
        }
    }

    private boolean isValidMobileNo() {
        if (et_login_mob_num.getText().toString().trim().length() == 0) {
            mUtilities.showToast("Please enter mobile no");
            return false;
        } else {
            return true;
        }

    }

    private void doRegister() {
        Intent intent = new Intent(this, RegistrationAct.class);
        startActivity(intent);
    }

    @Override
    public void getClick(boolean isClick) {
        btn_submit.setText("Submit");
        til_login_password.setVisibility(View.VISIBLE);
    }

    @Override
    public void getClick(boolean isClick, Bundle bundle) {

    }
}
