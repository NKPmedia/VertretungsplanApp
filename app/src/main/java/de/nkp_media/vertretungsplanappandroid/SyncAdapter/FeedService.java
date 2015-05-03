package de.nkp_media.vertretungsplanappandroid.SyncAdapter;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import de.nkp_media.vertretungsplanappandroid.MainActivity;

/**
 * Created by paul on 10.04.15.
 */
    public class FeedService extends Service {

        private static final Object sSyncAdapterLock = new Object();
        private static FeedSyncAdapter sSyncAdapter = null;
        private int mId ;

    @Override
        public void onCreate() {
        System.out.println("FeedService");
            synchronized (sSyncAdapterLock) {
                if (sSyncAdapter == null) sSyncAdapter = new FeedSyncAdapter(getApplicationContext(), true);
//                this.makeNotification();
            }
        }

        @Override
        public IBinder onBind(Intent intent) {
            return sSyncAdapter.getSyncAdapterBinder();
        }


        private void makeNotification()
        {
            NotificationCompat.Builder builder =
                    new NotificationCompat.Builder(this)
//                            .setSmallIcon(R.drawable.mylogo)
                            .setContentTitle("My Notification Title")
                            .setContentText("Something interesting happened");
            int NOTIFICATION_ID = 12345;

            Intent targetIntent = new Intent(this, MainActivity.class);
            PendingIntent contentIntent = PendingIntent.getActivity(this, 0, targetIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(contentIntent);
            NotificationManager nManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            nManager.notify(NOTIFICATION_ID, builder.build());
        }
}
