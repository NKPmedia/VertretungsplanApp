package de.nkp_media.vertretungsplanappandroid.data;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import de.nkp_media.vertretungsplanappandroid.Ausfall2;
import de.nkp_media.vertretungsplanappandroid.MainActivity;
import de.nkp_media.vertretungsplanappandroid.News;
import de.nkp_media.vertretungsplanappandroid.R;
import de.nkp_media.vertretungsplanappandroid.Sync.FeedUpdate;
import de.nkp_media.vertretungsplanappandroid.Sync.RSSFeedParser;

/**
 * Created by paul on 24.08.15.
 */
public class BackfroundFeedDataManager implements FeedDataManagerInterface{
    private static final String TAG = "BackgroundUpdate";
    private Service service;
    private FeedDataProvider dataProvider;
    private boolean loadingDataFromServer;
    private ArrayList<Ausfall2> planAusfallList;

    public BackfroundFeedDataManager(Service myGcmListenerService) {
        this.service = myGcmListenerService;
        this.dataProvider = new FeedDataProvider(this.service);
        this.planAusfallList = this.dataProvider.getPlanData();
    }

    public void loadDataFromServer()
    {
        if(this.loadingDataFromServer)
        {
            //do nothing
        }
        else
        {
            Log.d(TAG, "Loading data from server");
            this.loadingDataFromServer = true;
            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this.service);
            String klasse = settings.getString("klasse", "S4");
            boolean debugSite = settings.getBoolean("debug_site",false);
            this.ladeData(debugSite,klasse);
        }
    }

    public void refreshPlanData(ArrayList<Ausfall2> ausfallList)
    {
        Log.d(TAG, "Refresh Plan Database");
        boolean isNeu = this.dataProvider.isNewData(ausfallList);
        if(isNeu)
        {
            Log.d(TAG, "New data got with the background service ower gcm");
            if(PreferenceManager.getDefaultSharedPreferences(this.service).getBoolean("notifications_new_message",true))
            {
                this.sendNotification("Es gibt neue Daten im Vertretungsplan");
            }
        }
        else
        {
            Log.d(TAG, "No new data got with the background service ower gcm");
        }
        this.planAusfallList = ausfallList;
        this.loadingDataFromServer = false;
        this.dataProvider.updateDatabase(ausfallList);
    }
    
    public void errorWhileLoadFeedFromServer() {
        this.loadingDataFromServer = false;
        Log.e(TAG,"error While Load Feed From Server");
        this.sendNotification("Failed update because of GCM-Message");
    }

    @Override
    public void refreshNewsData(ArrayList<News> newsList) {
        Log.d(TAG, "Refresh News Database");
        this.dataProvider.updateNewsDatabase(newsList);
    }

    private void ladeData(boolean debugSite, String klasse) {
        RSSFeedParser XmlParser = new RSSFeedParser();
        InputStream stream = null;
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this.service);
        try {
            Log.d(TAG, "Start FeedUpdater");
            if (debugSite) {
                stream = FeedUpdate.downloadUrl("http://"+pref.getString("server_domain", "winet-ag.ddns.net")+pref.getString("server_path", "/blackboard/rss/")+"get_android_rss_debug.php?klasse=" + klasse);
            } else {
                stream = FeedUpdate.downloadUrl("http://"+pref.getString("server_domain", "winet-ag.ddns.net")+pref.getString("server_path", "/blackboard/rss/")+"get_android_rss.php?klasse=" + klasse);
            }

            Log.d(TAG, "Parsing serverdata");
            ArrayList<ArrayList> back = XmlParser.parse(stream);
            ArrayList<Ausfall2> ausfaelle = back.get(0);
            this.refreshPlanData(ausfaelle);
        } catch (IOException e) {
            e.printStackTrace();
            this.errorWhileLoadFeedFromServer();
        }
    }

    private void sendNotification(String message) {
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this.service)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Backgroundupdater")
                        .setSound(defaultSoundUri)
                        .setContentText(message);
        int NOTIFICATION_ID = 12345;

        Intent targetIntent = new Intent(this.service, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this.service, 0, targetIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);
        NotificationManager nManager = (NotificationManager) this.service.getSystemService(this.service.NOTIFICATION_SERVICE);
        nManager.notify(NOTIFICATION_ID, builder.build());
    }
}
