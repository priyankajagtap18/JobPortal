package com.jobportal.sync;

import android.content.Context;

import com.jobportal.constants.AppUrls;
import com.jobportal.entities.AllJob;
import com.jobportal.entities.City;
import com.jobportal.entities.EditProfile;
import com.jobportal.entities.GetEditProfile;
import com.jobportal.entities.Login;
import com.jobportal.entities.MyAccount;
import com.jobportal.helpers.Utilities;
import com.jobportal.network.DownloadHandler;
import com.jobportal.network.DownloadListener;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;

/**
 * Created by PriyankaJ on 01-09-2015.
 */
public class SyncManager implements DownloadListener, ParseListener {

    private Context context;
    private SyncListener listener;
    private int type;
    private Utilities utilities;
    public static final int ALL_JOBS = 1, REGISTRATION = 2, REGISTRATION_CHECK = 3, LOGIN = 4, GET_REGISTRATION_OTP = 5, MY_ACCOUNT = 6,
            EDIT_PROFILE = 7, CITY = 8, CHECK_CITY_UPDATE = 9, CHECK_JOB_UPDATE = 10, GET_EDIT_PROFILE = 11, VERIFY_MOBILE = 12;


    public SyncManager(Context context, int type, SyncListener listener) {

        System.out.println("===type = [" + type + "]");
        this.context = context;
        this.listener = listener;
        this.type = type;
        utilities = new Utilities(context);
    }

    @Override
    public void onDownloadSuccess(int taskId, String strResponse) {

        AsyncParseHelper parseHelper = new AsyncParseHelper(context, taskId, SyncManager.this);
        parseHelper.execute(strResponse);
    }

    @Override
    public void onDownloadFailure(int taskId, String strResponse) {
        listener.onSyncFailure(taskId, strResponse);
    }

    @Override
    public void onParseSuccess(int taskId, String strResponse, ArrayList<?> arrResult) {
        listener.onSyncSuccess(taskId, strResponse, arrResult);
    }

    @Override
    public void onParseFailure(String str, Throwable error) {

    }

    public void getAllJobs() {

        AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);
        client.setTimeout(120000);
        ArrayList<AllJob> arrResult = new ArrayList<>();
        RequestParams requestParams = new RequestParams();
        requestParams.add("job_categories", "1");
        client.post(context, AppUrls.sALLJobsURL, requestParams, new DownloadHandler(ALL_JOBS, SyncManager.this, arrResult));
    }

    public void doRegistration(String name, String email, String mobile, String password) {

        AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);
        client.setTimeout(120000);
        ArrayList<String> arrResult = new ArrayList<>();
        RequestParams requestParams = new RequestParams();
        requestParams.add("sign_up_step2", "1");
        requestParams.add("mobile", mobile);
        requestParams.add("name", name);
        requestParams.add("email", email);
        requestParams.add("password", password);
        client.post(context, AppUrls.sRegisterURL, requestParams, new DownloadHandler(REGISTRATION, SyncManager.this, arrResult));
    }

    public void doRegistrationCheck(String value) {

        AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);
        client.setTimeout(120000);
        ArrayList<String> arrResult = new ArrayList<>();
        RequestParams requestParams = new RequestParams();
        requestParams.add("check_sign_up", "1");
        requestParams.add("login_credentials", value);
        client.post(context, AppUrls.sRegisterURL, requestParams, new DownloadHandler(REGISTRATION_CHECK, SyncManager.this, arrResult));
    }

    public void login(String value, String password) {

        AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);
        client.setTimeout(120000);
        ArrayList<Login> arrResult = new ArrayList<>();
        RequestParams requestParams = new RequestParams();
        requestParams.add("login_check", "1");
        requestParams.add("login_credentials", value);
        requestParams.add("password", password);
        client.post(context, AppUrls.sRegisterURL, requestParams, new DownloadHandler(LOGIN, SyncManager.this, arrResult));
    }

    public void getRegistrationOTP(String mobile) {

        AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);
        client.setTimeout(120000);
        ArrayList<String> arrResult = new ArrayList<>();
        RequestParams requestParams = new RequestParams();
        requestParams.add("sign_up_step1", "1");
        requestParams.add("mobile", mobile);
        client.post(context, AppUrls.sRegisterURL, requestParams, new DownloadHandler(GET_REGISTRATION_OTP, SyncManager.this, arrResult));
    }

    public void getMyAccount(String custId) {
        AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);
        client.setTimeout(120000);
        ArrayList<MyAccount> arrResult = new ArrayList<>();
        RequestParams requestParams = new RequestParams();
        requestParams.add("my_account", "1");
        requestParams.add("cust_id", custId);
        client.post(context, AppUrls.sRegisterURL, requestParams, new DownloadHandler(MY_ACCOUNT, SyncManager.this, arrResult));
    }

    public void editProfile(String custId, String profilePic, String name, String address, String mobile,
                            String email, String emailChange, String password, String passwordChange) {
        AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);
        client.setTimeout(120000);
        ArrayList<EditProfile> arrResult = new ArrayList<>();
        RequestParams requestParams = new RequestParams();
        requestParams.add("edit_profile", "1");
        requestParams.add("profile_pic", profilePic);
        requestParams.add("name", name);
        requestParams.add("cust_id", custId);
        requestParams.add("address", address);
        requestParams.add("mobile", mobile);
        requestParams.add("email", email);
        requestParams.add("email_change", emailChange);
        requestParams.add("password", passwordChange);
        requestParams.add("current_password", password);
        client.post(context, AppUrls.sRegisterURL, requestParams, new DownloadHandler(EDIT_PROFILE, SyncManager.this, arrResult));
    }

    public void getAllCity() {
        AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);
        client.setTimeout(120000);
        ArrayList<City> arrResult = new ArrayList<>();
        RequestParams requestParams = new RequestParams();
        requestParams.add("all_cities", "1");
        client.post(context, AppUrls.sALLJobsURL, requestParams, new DownloadHandler(CITY, SyncManager.this, arrResult));
    }

    public void checkCityUpdate() {
        AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);
        client.setTimeout(120000);
        ArrayList<String> arrResult = new ArrayList<>();
        RequestParams requestParams = new RequestParams();
        requestParams.add("check_city_update", "1");
        client.post(context, AppUrls.sALLJobsURL, requestParams, new DownloadHandler(CHECK_CITY_UPDATE, SyncManager.this, arrResult));
    }

    public void checkJobUpdate() {
        AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);
        client.setTimeout(120000);
        ArrayList<String> arrResult = new ArrayList<>();
        RequestParams requestParams = new RequestParams();
        requestParams.add("check_job_update", "1");
        client.post(context, AppUrls.sALLJobsURL, requestParams, new DownloadHandler(CHECK_JOB_UPDATE, SyncManager.this, arrResult));
    }

    public void getEditProfile(String custId) {
        AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);
        client.setTimeout(120000);
        ArrayList<GetEditProfile> arrResult = new ArrayList<>();
        RequestParams requestParams = new RequestParams();
        requestParams.add("get_edit_profile", "1");
        requestParams.add("cust_id", custId);
        client.post(context, AppUrls.sRegisterURL, requestParams, new DownloadHandler(GET_EDIT_PROFILE, SyncManager.this, arrResult));
    }

    public void verifyMobile(String custId, String mobile) {
        AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);
        client.setTimeout(120000);
        ArrayList<String> arrResult = new ArrayList<>();
        RequestParams requestParams = new RequestParams();
        requestParams.add("sign_up_step1_varify", "1");
        requestParams.add("cust_id", custId);
        requestParams.add("mobile", mobile);
        client.post(context, AppUrls.sRegisterURL, requestParams, new DownloadHandler(VERIFY_MOBILE, SyncManager.this, arrResult));
    }
}
