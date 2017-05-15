package com.jobportal.utils;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import com.jobportal.constants.AppConstants;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by PriyankaJ on 09-03-2016.
 */
public class CommonCode {
    private ProgressDialog progressDialog;
    private Context context;

    public CommonCode(Context context, Activity activity) {
        this.context = context;
    }

    /**
     * Check whether device is tablet or phone
     *
     * @param context
     * @return
     */
    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * Set color to view
     *
     * @param mContext
     * @param mView
     * @param mColor
     */
    public void setViewColor(Context mContext, View mView, int mColor) {
        GradientDrawable bgShape = (GradientDrawable) mView.getBackground();
        bgShape.setColor(ContextCompat.getColor(mContext, mColor));
    }


    /**
     * Returns device width pixels
     *
     * @param mActivity
     * @return
     */
    public int getDeviceWidth(Activity mActivity) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        return displaymetrics.widthPixels;
    }

    //Convert pixel to dip
    public int getDipsFromPixel(float pixels, Context mContext) {
        // Get the screen's density scale
        final float scale = mContext.getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }

    /**
     * Replace fragment and set title with resource id mentioned in string.xml
     *
     * @param mActivity
     * @param mFragment
     * @param resID
     */
    public void replaceFragment(FragmentActivity mActivity, Fragment mFragment, int resID) {
        if (mActivity != null && context != null) {
            String strTitle = context.getString(resID);
            AppConstants.sTagFragment = strTitle;
            //mActivity.getSupportFragmentManager().beginTransaction().replace(R.id.container, mFragment, AppConstants.sTagFragment).commit();
            // HomeActivity.mTvTitle.setText(strTitle);
        }
    }

    /**
     * Replace fragment with bundle object and set title with resource id mentioned in string.xml.
     *
     * @param mActivity
     * @param mFragment
     * @param container
     * @param resID
     * @param mKey
     * @param mObject
     */
    public void replaceFragmentWithArg(FragmentActivity mActivity, Fragment mFragment, int container, int resID, String mKey, Object mObject) {
        if (mActivity != null && context != null) {
            AppConstants.sTagFragment = context.getString(resID);
            Bundle bundle = new Bundle();
            bundle.putString(mKey, mObject.toString());
            mFragment.setArguments(bundle);
            mActivity.getSupportFragmentManager().beginTransaction().replace(container, mFragment, AppConstants.sTagFragment).commit();
        }
    }

    /**
     * Replace fragment and set string title
     *
     * @param mActivity
     * @param mFragment
     * @param resID
     */
    public void replaceFragment(FragmentActivity mActivity, Fragment mFragment, String resID) {
        if (mActivity != null) {
            AppConstants.sTagFragment = resID;
            // mActivity.getSupportFragmentManager().beginTransaction().replace(R.id.container, mFragment, AppConstants.sTagFragment).commit();
            // HomeActivity.mTvTitle.setText(resID);
        }
    }

    /**
     * Set horizontal animation for grid chart
     *
     * @param mView
     * @param perComplete
     */
    public void setProgressBarAnimation(View mView, int perComplete) {
        ObjectAnimator progressAnimator = ObjectAnimator.ofInt(mView, "progress", 0, perComplete);
        progressAnimator.setDuration(300); // 0.5 second
        progressAnimator.setInterpolator(new AccelerateInterpolator());
        progressAnimator.start();
    }


    /**
     * Returns current date time
     *
     * @param fecha
     * @return
     */
    public static Date getZeroTimeDate(Date fecha) {
        Date res;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        res = calendar.getTime();
        return res;
    }

    /**
     * Returns current date time
     *
     * @return
     */
    public String getCurrentDateTime() {
        DateFormat df = new SimpleDateFormat("MMM d yyyy, HH:mm a");
        return df.format(Calendar.getInstance().getTime());
    }

    /**
     * Round to certain number of decimals
     *
     * @param floatQuantity
     * @param decimalPlace
     * @return
     */
    public static String roundValue(float floatQuantity, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(floatQuantity));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return "" + bd.floatValue();
    }


}
