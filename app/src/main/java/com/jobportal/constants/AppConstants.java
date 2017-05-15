package com.jobportal.constants;

import android.content.Context;

/**
 * Created by PriyankaJ on 09-03-2016.
 */
public class AppConstants {
    public static String sTagFragment;
    private Context context;
    private static AppConstants sDataControllerInstance;

    public static synchronized AppConstants getInstance() {
        if (sDataControllerInstance == null) {
            sDataControllerInstance = new AppConstants();
        }
        return sDataControllerInstance;
    }

    public Context getContext() {
        return context;
    }


    public void setContext(Context context) {
        this.context = context;
    }
}
