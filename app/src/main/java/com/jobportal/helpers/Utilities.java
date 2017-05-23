package com.jobportal.helpers;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.jobportal.R;
import com.squareup.picasso.Picasso;

import java.net.NetworkInterface;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by PriyankaJ on 26-08-2015.
 */
public class Utilities {
    private Context context;
    public static FragmentManager fragmentManager;
    private static FragmentTransaction fragmentTransaction;
    private ProgressDialog pgdialog;
    private AlertDialog alertDialog;
    private SharedPreferences shared_Preference;
    private SharedPreferences.Editor toEdit;
    public static SimpleDateFormat ddmmyyyy = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    public static SimpleDateFormat yyyymmdd = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    public static String sTagFragment;

    public Utilities(Context context) {
        this.context = context;
    }

    public static void replaceFragment(Activity activity, Fragment fragment, boolean isBackStackNull) {
        fragmentManager = activity.getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        //fragmentManager.sett(R.anim.down_from_top);
        // fragmentTransaction.setCustomAnimations(R.animator.slide_in, 0);
        //  fragmentTransaction.replace(R.id.container, fragment);
        if (!isBackStackNull) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    public void showProgressDialog(String message) {
        if (pgdialog != null)
            if (pgdialog.isShowing()) {
                pgdialog.dismiss();
                pgdialog.cancel();
            }
        pgdialog = ProgressDialog.show(context, context.getString(R.string.app_name), message, true);

    }

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

    public boolean isOnline() {
        ConnectivityManager conMgr = null;
        conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (conMgr.getActiveNetworkInfo() != null && conMgr.getActiveNetworkInfo().isAvailable()
                && conMgr.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    public void showDialog(String Message) {
        hideProgressDialog();
        if (!((Activity) context).isFinishing()) {
            alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle(R.string.app_name);
            alertDialog.setMessage(Message);
            alertDialog.setCancelable(false);
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {

                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP && !event.isCanceled())

                    {
                        dialog.dismiss();
                        return true;
                    }
                    return false;
                }
            });

            alertDialog.show();
        }
    }


    public static void showToast(Context mContext, String strMessage) {
        Toast toast;
        toast = Toast.makeText(mContext.getApplicationContext(), strMessage, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 0);
        toast.show();
    }

    // hide soft keyboard
    public void hideKeyboard(View editText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(
                editText.getWindowToken(),
                0);
    }

    public void hideSoftKeyboard() {
        ((Activity) context).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
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

    /**
     * Check if there is any connectivity to a Wifi network
     *
     * @param context
     * @return
     */
    public static boolean isConnectedWifi(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_WIFI);
    }


    public void loadImage(String url, ImageView imageView) {
        if (url.contains(" ")) {
            url = url.replaceAll(" ", "%20");
        }
        Picasso.with(context).load(url)
                .error(R.mipmap.ic_launcher)
                .placeholder(R.mipmap.ic_launcher)
                .into(imageView);
    }

    public void loadBannerImage(String url, ImageView imageView) {
        if (url.contains(" ")) {
            url = url.replaceAll(" ", "%20");
        }
        Picasso.with(context).load(url)
                .into(imageView);

    }

    public String convertUTCToLocalTime(String strInputDate, SimpleDateFormat inputDateFormat, SimpleDateFormat outputdateFormat) {
        String result = "";
        //  SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        TimeZone utcZone = TimeZone.getTimeZone("UTC");
        inputDateFormat.setTimeZone(utcZone);
        Date myDate = null;
        try {
            myDate = inputDateFormat.parse(strInputDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String strDate = inputDateFormat.format(myDate);
        Log.d("UTC ", strDate);
        Calendar cl = Calendar.getInstance();
        System.out.println("current: " + cl.getTime());
        TimeZone z = cl.getTimeZone();
        //converting UTC to CURRENT
        //SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        outputdateFormat.setTimeZone(z);
        result = outputdateFormat.format(myDate);

        return result;
    }

    public String displayTimeInAMPMFormat(String strInput) {
        String strResult = "";
        StringTokenizer tk = new StringTokenizer(strInput);
        String date = tk.nextToken();
        String time = tk.nextToken();
        SimpleDateFormat sdfInput = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat sdfOutput = new SimpleDateFormat("h:mm a");
        Date dt = null;
        try {
            dt = sdfInput.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        strResult = sdfOutput.format(dt);
        return strResult;
    }


    /**
     * This method removes last comma of input string.
     *
     * @param strInput
     * @return
     */

    public static String removeLastComma(String strInput) {

        String newstr = "";
        if (null != strInput && strInput.length() > 0) {
            int endIndex = strInput.lastIndexOf(", ");
            if (endIndex != -1) {
                newstr = strInput.substring(0, endIndex); // not forgot to put check if(endIndex != -1)
            }
        }
        return newstr;
    }

    public static boolean isEditTextEmpty(EditText mEditText) {
        if (mEditText == null || mEditText.getText().toString().trim().length() == 0) {
            return true;
        }
        return false;
    }

    /**
     * Use for validating inserted email_id whether it is right or not.
     *
     * @param email
     * @return
     */
    public static boolean isEmailValid(String email) {
        boolean isValid = false;
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }


    public void setLayoutParamForDialog(Activity activity, Dialog dialog) {
        Display display;
        display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public String getMACAddress() {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String strDeviceId = telephonyManager.getDeviceId(); //for sending device id to server
        if (strDeviceId == null || strDeviceId == "") {
            strDeviceId = getDeviceMacAddressForTablet();
        }
        return strDeviceId;
    }

    /**
     * Returns MAC address of the given interface name.
     *
     * @return mac address or empty string
     */
    public static String getDeviceMacAddressForTablet() {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                if ("wlan0" != null) {
                    if (!intf.getName().equalsIgnoreCase("wlan0")) {
                        continue;
                    }
                }
                byte[] mac = intf.getHardwareAddress();
                if (mac == null) {
                    return "";
                }
                StringBuilder buf = new StringBuilder();
                for (int idx = 0; idx < mac.length; idx++) {
                    buf.append(String.format("%02X:", mac[idx]));
                }
                if (buf.length() > 0) {
                    buf.deleteCharAt(buf.length() - 1);
                }
                return buf.toString().replace(":", "");
            }
        } catch (Exception ex) {
        } // for now eat exceptions
        return "0099867E319B";
    }

    /**
     * Replace fragment and set title with resource id mentioned in string.xml
     *
     * @param mActivity
     * @param mFragment
     * @param resID
     */
    public void replaceFragment(FragmentActivity mActivity, android.support.v4.app.Fragment mFragment, int resID, boolean isBackStackNull) {
        if (mActivity != null && context != null) {
            String strTitle = context.getString(resID);
            sTagFragment = strTitle;
            if (!isBackStackNull) {
                mActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fl_container, mFragment, sTagFragment).addToBackStack(null).commit();
            } else {
                mActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fl_container, mFragment, sTagFragment).commit();
            }
        }
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
}
