package com.jobportal.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.jobportal.R;


public class SplashAct extends Activity {
    int mDelay = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        Thread timerThread = new Thread() {
            public void run() {
                try {
                    sleep(mDelay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    startActivity(new Intent(SplashAct.this, LoginActivity.class));
                }
            }
        };
        timerThread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.gc();
        finish();
    }

}
