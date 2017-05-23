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
import android.view.View;
import android.widget.Button;

import com.jobportal.R;
import com.jobportal.adapters.CustomPagerAdapter;
import com.jobportal.fragments.RegistrationFragment;
import com.jobportal.helpers.Utilities;
import com.jobportal.listeners.ClickListner;
import com.pixelcan.inkpageindicator.InkPageIndicator;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by pravink on 23-05-2017.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener,ClickListner {

    private Context mContext;
    private Utilities mUtilities;
    private ViewPager viewPager;
    private InkPageIndicator inkPageIndicator;
    private int currentPage = 0;
    private AppCompatButton btn_submit;
    private AppCompatEditText et_login_mob_num,et_login_password;
    private TextInputLayout til_login_mobile_num,til_login_password;

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
        mUtilities = new Utilities(mContext);
        viewPager = (ViewPager) findViewById(R.id.pager);
        inkPageIndicator = (InkPageIndicator) findViewById(R.id.indicator);
        btn_submit=(AppCompatButton) findViewById(R.id.btn_submitr);
        et_login_mob_num=(AppCompatEditText) findViewById(R.id.et_login_mob_num);
        et_login_password=(AppCompatEditText) findViewById(R.id.et_login_confirm_password);
        til_login_mobile_num=(TextInputLayout) findViewById(R.id.til_login_mobile_num);
        til_login_password=(TextInputLayout) findViewById(R.id.til_login_password);


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
        if(btn_submit.getText().toString().trim().equalsIgnoreCase("Continue"))
        {
            this.getClick(true);
        }
        else
        {
            Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void getClick(boolean isClick) {
        btn_submit.setText("Submit");
        til_login_password.setVisibility(View.VISIBLE);
    }
}
