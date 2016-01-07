package de.nkp_media.vertretungsplanappandroid.gcm;

import android.content.Intent;
import android.os.IBinder;

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
        String token = "";
        try {
            token = instanceID.getToken(this.getString(R.string.gcm_defaultSenderId), GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            new RegistrationIntentService().sendRegistrationToServer(token);
            //TODO Check if correct
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
