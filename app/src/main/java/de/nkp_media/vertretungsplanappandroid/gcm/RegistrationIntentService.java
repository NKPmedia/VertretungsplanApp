package de.nkp_media.vertretungsplanappandroid.gcm;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.android.gms.gcm.GcmPubSub;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;
import java.util.Random;

import de.nkp_media.vertretungsplanappandroid.R;


public class RegistrationIntentService extends IntentService {

    private static final String TAG = "RegIntentService";
    private static final String[] TOPICS = {"global"};

    public RegistrationIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        try {
            // In the (unlikely) event that multiple refresh operations occur simultaneously,
            // ensure that they are processed sequentially.
            synchronized (TAG) {

                checkIfRandomDeviceIDExits();

                //get the gcmId from google
                Log.d(TAG, "Try to get the gcmId");
                InstanceID instanceID = InstanceID.getInstance(this);
                String gcmId = instanceID.getToken(getString(R.string.GCMdefaultSenderId), GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
                Log.i(TAG, "GCM Registration Token: " + gcmId);

                String serverInSync = checkIfDeviceIdAndGcmIdOnServer(gcmId);
                Log.d(TAG,"Respons of sny check: "+serverInSync);

                if(serverInSync.equals("1"))
                {
                    Log.e(TAG, "Error while sync gcm. Retry at next start.");
                    sharedPreferences.edit().putBoolean("serverToken", false).apply();
                }else if(serverInSync.equals("0_found")){
                    sharedPreferences.edit().putBoolean("serverToken", true).apply();
                    sharedPreferences.edit().putString("gcmId",gcmId).commit();
                    Log.d(TAG,"Token allready send and up to date");
                }
                else if(serverInSync.equals("0_d_id_found")){
                    String status = GCMServerConnection.updateRegistrationToServer(gcmId, sharedPreferences.getString("randomDeviceId", ""));
                    if(status.equals("0"))
                    {
                        Log.d(TAG,"Updated reg id with existing device id");
                    }else
                    {
                        Log.e(TAG,"Reg id update failed");
                    }
                }
                else if(serverInSync.equals("0_reg_id_found")){
                    String status = GCMServerConnection.updateDeviceIdToServer(gcmId, sharedPreferences.getString("randomDeviceId", ""));
                    if(status.equals("0"))
                    {
                        Log.d(TAG,"Updated device id with existing reg id");
                    }else
                    {
                        Log.e(TAG,"Device id update failed");
                    }
                }
                else if(!sharedPreferences.getBoolean("serverToken",false) || serverInSync.equals("404_no_ids")) {
                    String status = GCMServerConnection.sendRegistrationToServer(gcmId,sharedPreferences.getString("randomDeviceId",""));
                    // Subscribe to topic channels
                    subscribeTopics(gcmId);
                    // stores servertoken and an value thats indicates that the gcmId is on the server
                    if (status.equals("0")) {
                        sharedPreferences.edit().putBoolean("serverToken", true).apply();
                        sharedPreferences.edit().putString("gcmId",gcmId).commit();
                    } else {
                        sharedPreferences.edit().putBoolean("serverToken", false).apply();
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Failed to complete token refresh");
            // If an exception happens while fetching the new token or updating our registration data
            // on a third-party server, this ensures that we'll attempt the update at a later time.
            sharedPreferences.edit().putBoolean("serverToken", false).apply();
        }
//        // Notify UI that registration has completed, so the progress indicator can be hidden.
//        Intent registrationComplete = new Intent(QuickstartPreferences.REGISTRATION_COMPLETE);
//        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }

    private String checkIfDeviceIdAndGcmIdOnServer(String gcmId) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String deviceId = sharedPreferences.getString("randomDeviceId", "");

        String status = GCMServerConnection.checkIdsOnServer(deviceId,gcmId);

        return status;
    }

    private void checkIfRandomDeviceIDExits() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if(sharedPreferences.getString("randomDeviceId","").equals(""))
        {
            Random r = new Random();
            long random = r.nextLong();
            sharedPreferences.edit().putString("randomDeviceId", String.valueOf(random)).commit();
            Log.d(TAG,"Created a randomDeviceId: "+random);
        }
        else
        {
            Log.d(TAG,"Found randomDeviceID: "+sharedPreferences.getString("randomDeviceId",""));
        }
    }


    /**
     * Subscribe to any GCM topics of interest, as defined by the TOPICS constant.
     *
     * @param gcmId GCM gcmId
     * @throws IOException if unable to reach the GCM PubSub service
     */
    // [START subscribe_topics]
    private void subscribeTopics(String gcmId) throws IOException {
        for (String topic : TOPICS) {
            GcmPubSub pubSub = GcmPubSub.getInstance(this);
            pubSub.subscribe(gcmId, "/topics/" + topic, null);
        }
    }
    // [END subscribe_topics]

}