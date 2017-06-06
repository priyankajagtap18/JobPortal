package com.jobportal.sync;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.jobportal.R;
import com.jobportal.entities.AllJob;
import com.jobportal.entities.Authenticate;
import com.jobportal.entities.EditProfile;
import com.jobportal.entities.Login;
import com.jobportal.entities.MyAccount;
import com.jobportal.helpers.Utilities;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * Created by PriyankaJ on 01-09-2015.
 */
public class AsyncParseHelper extends AsyncTask<String, String, ArrayList<?>> {

    private Context context;
    private ParseListener listener;
    private int taskId;
    private ArrayList<?> arrResult;
    private Utilities utilities;

    public AsyncParseHelper(Context context, int taskId, ParseListener listener) {
        this.context = context;
        this.listener = listener;
        this.taskId = taskId;
        utilities = new Utilities(context);

    }

    @Override
    protected ArrayList<?> doInBackground(String... params) {
        String response = params[0];
        ArrayList<Authenticate> result;
        try {
            switch (taskId) {
                case SyncManager.ALL_JOBS:
                    ArrayList<AllJob> jobs = new ArrayList<>();
                    if (isDataPresent(response)) {
                        JSONObject object = ((new JSONObject(response)).getJSONObject(context.getString(R.string.data)));
                        AllJob allJobs = new Gson().fromJson(object.toString(), AllJob.class);
                        jobs = new ArrayList<AllJob>(Arrays.asList(allJobs));
                    }

                    arrResult = jobs;
                  /*  Channel channel = new Channel(context);
                    ArrayList<Channel> alChannel = new ArrayList<Channel>();
                    try {
                        channel.deleteAllRecords(DatabaseHandler.TABLE_CHANNEL);
                    } catch (Exception e) {
                    }
                    if (response != null) {
                        Channel[] channels = new Gson().fromJson(response, Channel[].class);

                        if (channels != null) {
                            int csize = channels.length;
                            if (csize > 0)
                                for (int i = 0; i < csize; i++) {
                                    if (channels[i].getIsActive().equalsIgnoreCase("True")) {
                                        channel.insertRecord(channels[i], DatabaseHandler.TABLE_CHANNEL);
                                        alChannel.add(channels[i]);
                                    }
                                }
                        }

                    }
                    arrResult = alChannel;*/
                    break;
                case SyncManager.REGISTRATION_CHECK:
                    if (response != null) {
                        ArrayList<String> reg_check = new ArrayList<>();

                        if (isDataPresent(response)) {
                            JSONObject object = ((new JSONObject(response)).getJSONObject(context.getString(R.string.data)));
                            reg_check.add(object.getString("account_exist"));
                        } else {
                            JSONObject object = ((new JSONObject(response)).getJSONObject(context.getString(R.string.data)));
                            reg_check.add(object.getString("error"));
                        }
                        arrResult = reg_check;
                    }
                    break;

                case SyncManager.GET_REGISTRATION_OTP:
                    if (response != null) {
                        ArrayList<String> otp = new ArrayList<>();
                        if (isDataPresent(response)) {
                            JSONObject object = ((new JSONObject(response)).getJSONObject(context.getString(R.string.data)));
                            otp.add(object.getString("otp"));
                        } else {
                            JSONObject object = ((new JSONObject(response)).getJSONObject(context.getString(R.string.data)));
                            otp.add(object.getString("error"));
                        }
                        arrResult = otp;
                    }
                    break;
                case SyncManager.LOGIN:
                    if (response != null) {
                        ArrayList<Login> logins = new ArrayList<>();
                        if (isDataPresent(response)) {
                            JSONObject object = ((new JSONObject(response)).getJSONObject(context.getString(R.string.data)));
                            Login login = new Gson().fromJson(object.toString(), Login.class);
                            login.setSuccess(true);
                            logins.add(login);
                        } else {
                            JSONObject object = ((new JSONObject(response)).getJSONObject(context.getString(R.string.data)));
                            Login login = new Login();
                            login.setSuccess(false);
                            login.setError(object.toString());
                            logins.add(login);
                        }
                        arrResult = logins;
                    }
                    break;
                case SyncManager.REGISTRATION:
                    ArrayList<String> register = new ArrayList<>();
                    if (response != null) {

                        if (isDataPresent(response)) {
                            JSONObject object = ((new JSONObject(response)).getJSONObject(context.getString(R.string.data)));
                            //result.add(object.getString("otp"));
                        } else {
                            JSONObject object = ((new JSONObject(response)).getJSONObject(context.getString(R.string.data)));
                            // result.add(object.getString("error"));
                        }
                        arrResult = register;
                    }
                    break;
                case SyncManager.MY_ACCOUNT:
                    ArrayList<MyAccount> myAccounts = new ArrayList<>();
                    if (response != null) {

                        if (isDataPresent(response)) {
                            JSONObject object = ((new JSONObject(response)).getJSONObject(context.getString(R.string.data)));
                            MyAccount account = new Gson().fromJson(object.toString(), MyAccount.class);
                            myAccounts.add(account);
                        } else {
                            JSONObject object = ((new JSONObject(response)).getJSONObject(context.getString(R.string.data)));
                            // result.add(object.getString("error"));
                        }
                        arrResult = myAccounts;
                    }
                    break;
                case SyncManager.EDIT_PROFILE:
                    ArrayList<EditProfile> editProfiles = new ArrayList<>();
                    if (response != null) {

                        if (isDataPresent(response)) {
                            JSONObject object = ((new JSONObject(response)).getJSONObject(context.getString(R.string.data)));
                            EditProfile editProfile = new Gson().fromJson(object.toString(), EditProfile.class);
                            editProfiles.add(editProfile);
                        } else {
                            JSONObject object = ((new JSONObject(response)).getJSONObject(context.getString(R.string.data)));
                            // result.add(object.getString("error"));
                        }
                        arrResult = editProfiles;
                    }
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return arrResult;
    }

    @Override
    protected void onPostExecute(ArrayList<?> arrResult) {
        super.onPostExecute(arrResult);
        listener.onParseSuccess(taskId, "", arrResult);
    }

    private boolean isDataPresent(String response) {
        String jsonObject = null;
        String successMessage = null;
        try {
            jsonObject = ((new JSONObject(response)).getString(context.getString(R.string.status)));
            successMessage = ((new JSONObject(response)).getString(context.getString(R.string.message)));
            if (jsonObject.toString().equalsIgnoreCase(context.getString(R.string.success_code)) && successMessage.toString().equalsIgnoreCase(context.getString(R.string.success))) {
                return true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

}
