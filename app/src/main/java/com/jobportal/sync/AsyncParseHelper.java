package com.jobportal.sync;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.jobportal.R;
import com.jobportal.constants.DBConstants;
import com.jobportal.databases.DatabaseHelper;
import com.jobportal.entities.AllJob;
import com.jobportal.entities.Authenticate;
import com.jobportal.entities.City;
import com.jobportal.entities.EditProfile;
import com.jobportal.entities.GetEditProfile;
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
                        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
                        databaseHelper.deleteRecord(DBConstants.TableJobCategory, null, null);
                        for (String jobname : jobs.get(0).getJobCategories()) {
                            databaseHelper.insertJobCategory(jobname);
                        }
                    }

                    arrResult = jobs;

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
                            String object = ((new JSONObject(response)).getString(context.getString(R.string.data)));
                            otp.add(object);
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
                            String object = ((new JSONObject(response)).getString(context.getString(R.string.data)));
                            Login login = new Login();
                            login.setSuccess(false);
                            login.setError(object);
                            logins.add(login);
                        }
                        arrResult = logins;
                    }
                    break;
                case SyncManager.REGISTRATION:
                    result = new ArrayList<>();
                    if (response != null) {
                        Authenticate authenticate = new Authenticate();
                        if (isDataPresent(response)) {
                            JSONObject object = ((new JSONObject(response)).getJSONObject(context.getString(R.string.data)));
                            authenticate.setValue(object.getString("acccount_create"));
                        } else {
                            String object = ((new JSONObject(response)).getString(context.getString(R.string.data)));
                            authenticate.setMessage(object);
                        }
                        result.add(authenticate);
                        arrResult = result;
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
                            String object = ((new JSONObject(response)).getString(context.getString(R.string.data)));
                            // result.add(object);
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
                            String object = ((new JSONObject(response)).getString(context.getString(R.string.data)));
                            // result.add(object);
                        }
                        arrResult = editProfiles;
                    }
                    break;
                case SyncManager.CITY:
                    ArrayList<City> cities = new ArrayList<>();
                    if (response != null) {

                        if (isDataPresent(response)) {
                            JSONObject object = ((new JSONObject(response)).getJSONObject(context.getString(R.string.data)));
                            City city = new Gson().fromJson(object.toString(), City.class);
                            cities.add(city);
                            DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
                            databaseHelper.deleteRecord(DBConstants.TableCity, null, null);
                            for (String cityname : cities.get(0).getAllCities()) {
                                databaseHelper.insertCity(cityname);
                            }
                        } else {
                            String object = ((new JSONObject(response)).getString(context.getString(R.string.data)));
                            // result.add(object);
                        }
                        arrResult = cities;
                    }
                    break;
                case SyncManager.CHECK_CITY_UPDATE:
                case SyncManager.CHECK_JOB_UPDATE:
                    if (response != null) {
                        ArrayList<String> list = new ArrayList<>();
                        if (isDataPresent(response)) {
                            JSONObject object = ((new JSONObject(response)).getJSONObject(context.getString(R.string.data)));
                            list.add(object.getString("updates"));
                        } else {
                            String object = ((new JSONObject(response)).getString(context.getString(R.string.data)));
                            list.add(object);
                        }
                        arrResult = list;
                    }
                    break;
                case SyncManager.GET_EDIT_PROFILE:
                    ArrayList<GetEditProfile> profiles = new ArrayList<>();
                    if (isDataPresent(response)) {
                        JSONObject object = ((new JSONObject(response)).getJSONObject(context.getString(R.string.data)));
                        GetEditProfile profile = new Gson().fromJson(object.toString(), GetEditProfile.class);
                        profiles.add(profile);
                    } else {

                    }

                    arrResult = profiles;

                    break;
                case SyncManager.VERIFY_MOBILE:
                    if (response != null) {
                        ArrayList<String> list = new ArrayList<>();
                        if (isDataPresent(response)) {
                            JSONObject object = ((new JSONObject(response)).getJSONObject(context.getString(R.string.data)));
                            list.add(object.getString("otp"));
                        } else {
                            String object = ((new JSONObject(response)).getString(context.getString(R.string.data)));
                            list.add(object);
                        }
                        arrResult = list;
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
