package de.nkp_media.vertretungsplanappandroid.gcm;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.android.gms.gcm.GcmPubSub;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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
                Log.d(TAG,"Try to get the token");
                // [START register_for_gcm]
                // Initially this call goes out to the network to retrieve the token, subsequent calls
                // are local.
                // [START get_token]
                InstanceID instanceID = InstanceID.getInstance(this);
                String token = instanceID.getToken(getString(R.string.GCMdefaultSenderId), GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
                // [END get_token]
                Log.i(TAG, "GCM Registration Token: " + token);

                String status = "0";
                if(!sharedPreferences.getBoolean("serverToken",false)) {
                    status = sendRegistrationToServer(token);


                    // Subscribe to topic channels
                    subscribeTopics(token);

                    // You should store a boolean that indicates whether the generated token has been
                    // sent to your server. If the boolean is false, send the token to your server,
                    // otherwise your server should have already received the token.
                    if (status.equals("0")) {
                        sharedPreferences.edit().putBoolean("serverToken", true).apply();
                    } else {
                        sharedPreferences.edit().putBoolean("serverToken", false).apply();
                    }
                    // [END register_for_gcm]
                }
            }
        } catch (Exception e) {
            Log.d(TAG, "Failed to complete token refresh", e);
            // If an exception happens while fetching the new token or updating our registration data
            // on a third-party server, this ensures that we'll attempt the update at a later time.
            sharedPreferences.edit().putBoolean("serverToken", false).apply();
        }
//        // Notify UI that registration has completed, so the progress indicator can be hidden.
//        Intent registrationComplete = new Intent(QuickstartPreferences.REGISTRATION_COMPLETE);
//        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }

    /**
     * Persist registration to third-party servers.
     *
     * Modify this method to associate the user's GCM registration token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    public String sendRegistrationToServer(String token) {
        try {
            Log.d(TAG,"Sendig token to server");
            URL url = new URL("http://winet-ag.ddns.net/blackboard/rss/reg_gcm_id.php?gcmid="+token );
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            InputStream stream = conn.getInputStream();
            InputStreamReader isReader = new InputStreamReader(stream);
            BufferedReader br = new BufferedReader(isReader );
            String response = br.readLine();
            Log.d(TAG,"Respons "+response);
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "1";
    }

    /**
     * Subscribe to any GCM topics of interest, as defined by the TOPICS constant.
     *
     * @param token GCM token
     * @throws IOException if unable to reach the GCM PubSub service
     */
    // [START subscribe_topics]
    private void subscribeTopics(String token) throws IOException {
        for (String topic : TOPICS) {
            GcmPubSub pubSub = GcmPubSub.getInstance(this);
            pubSub.subscribe(token, "/topics/" + topic, null);
        }
    }
    // [END subscribe_topics]

}