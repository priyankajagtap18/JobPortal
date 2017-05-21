package com.jobportal.sync;

import android.content.Context;

import com.jobportal.helpers.Utilities;
import com.jobportal.network.DownloadListener;

import java.util.ArrayList;

/**
 * Created by PriyankaJ on 01-09-2015.
 */
public class SyncManager implements DownloadListener, ParseListener {

    private Context context;
    private SyncListener listener;
    private int type;
    private Utilities utilities;
    public static final int ALL_ROLES = 1, TOP_ROLES = 2, LOGIN = 3, REGISTRATION = 4;


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


 /*
    public void getChannelSponsors(String strChannelId) {

        System.out.println("====strChannelId = [" + strChannelId + "]");
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(120000);
        ArrayList<ChannelSponsor> arrResult = new ArrayList<>();
        RequestParams requestParams = new RequestParams();
        requestParams.add("ChannelID", strChannelId);
        client.get(ITuluntulu.URL_Base + ITuluntulu.sp_Channel_Sponsors, requestParams, new DownloadHandler(GET_CHANNEL_SPONSORS, SyncManager.this, arrResult));

    }
    public void postChannelViewer(String strDeviceId, String channelId) {
        AsyncHttpClient client = new AsyncHttpClient();
        ArrayList<String> arrResult = new ArrayList<String>();
        StringEntity entity;
        JSONObject jparams = new JSONObject();
        try {
            jparams.put("DeviceId", strDeviceId);
            jparams.put("Channelid", channelId);
            jparams.put("Platform", "Android");
            entity = new StringEntity(jparams.toString());
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            client.post(context, ITuluntulu.URL_Base + ITuluntulu.sp_PostChannelViewer, entity, "application/json", new DownloadHandler(POST_CHANNEL_VIEWER, SyncManager.this, arrResult));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }*/

}
