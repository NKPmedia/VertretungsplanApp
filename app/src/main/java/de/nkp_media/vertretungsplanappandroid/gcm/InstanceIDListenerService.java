package de.nkp_media.vertretungsplanappandroid.gcm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

import de.nkp_media.vertretungsplanappandroid.R;

public class InstanceIDListenerService extends com.google.android.gms.iid.InstanceIDListenerService {
    public InstanceIDListenerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onTokenRefresh() {
        InstanceID instanceID = InstanceID.getInstance(this);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String token = "";
        try {
            token = instanceID.getToken(this.getString(R.string.gcm_defaultSenderId), GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            GCMServerConnection.sendRegistrationToServer(token, sharedPreferences.getString("randomDeviceId", ""));
            //TODO Check if correct
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
