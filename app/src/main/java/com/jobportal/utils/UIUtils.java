package com.jobportal.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.IBinder;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.jobportal.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by PriyankaJ on 09-03-2016.
 */
public class UIUtils {
    public static DateFormat displayDate = new SimpleDateFormat("dd-MMM-yyyy");
    public static DateFormat formatedDate = new SimpleDateFormat("yyyy-MM-dd");
    private ProgressDialog pgdialog;



    /**
     * Show progress dialog
     *
     * @param mContext
     * @param message
     */
    public void showProgressDialog(Context mContext, String message) {
        if (pgdialog != null)
            if (pgdialog.isShowing()) {
                pgdialog.dismiss();
                pgdialog.cancel();
            }
        pgdialog = ProgressDialog.show(mContext,
                mContext.getString(R.string.app_name), message, true);

    }

    /**
     * Hide progress dialog
     */
    public void hideProgressDialog() {
        try {
            if (pgdialog != null) {
                if (pgdialog.isShowing()) {
                    pgdialog.dismiss();
                    pgdialog.cancel();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * method to hide soft keypad
     */
    public static void hideSoftInputKeypad(Context _context, Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) _context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View focusView = activity.getCurrentFocus();
        if (focusView != null) {
            IBinder iBinder = focusView.getWindowToken();
            if (iBinder != null)
                inputMethodManager.hideSoftInputFromWindow(iBinder, 0);
        }
    }

    // hide soft keyboard
    public static void hideKeyboard(Context context, View editText) {
        try {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(
                    editText.getWindowToken(),
                    0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * method to show soft keypad
     */
    public static void showSoftInputKeypad(final Activity mActivity, final EditText etText) {
        etText.requestFocus();
        InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

    }

    /**
     * This function is used to show toast throughout the application
     *
     * @param context
     */
    public static void showToast(Context context, String message) {

        Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     * Returns date with outputDateFormat format specified. Pass the user input date format
     *
     * @param strDate
     * @param inputDateFormat
     * @param outputDateFormat
     * @return
     */
    public static String getFormattedDate(String strDate, DateFormat inputDateFormat, DateFormat outputDateFormat) {

        String formattedDate = null;
        try {
            Date myDate = inputDateFormat.parse(strDate);
            formattedDate = outputDateFormat.format(myDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return formattedDate;
    }

    /**
     * Returns date with outputDateFormat specified
     *
     * @param strDate
     * @param inputDateFormat
     * @param outputDateFormat
     * @return
     */
    public static String getFormattedDateTimeStamp(String strDate, DateFormat inputDateFormat, DateFormat outputDateFormat) {

        String formattedDate = null;
        strDate = strDate.replace("T", " ");
        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date myDate = inputDateFormat.parse(strDate);
            formattedDate = outputDateFormat.format(myDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return formattedDate;
    }

    /**
     * Returns previous date from calender
     *
     * @return
     */
    public String getPreviousDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        return displayDate.format(calendar.getTime());
    }
}
