package de.nkp_media.vertretungsplanappandroid.Sync;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import de.nkp_media.vertretungsplanappandroid.Ausfall2;
import de.nkp_media.vertretungsplanappandroid.MainActivity;
import de.nkp_media.vertretungsplanappandroid.R;
import de.nkp_media.vertretungsplanappandroid.caching.CacheManager;
import de.nkp_media.vertretungsplanappandroid.data.FeedDataProvider;

/**
 * Created by paul on 23.04.15.
 */
public class SyncThread extends Thread{

    private final FeedDataProvider dataProvider;
    private Sync syncService;
    private boolean doSync = true;

    public SyncThread(String serviceStartArguments, Sync sync)
    {
        this.syncService = sync;
        this.dataProvider = new FeedDataProvider(sync);
    }

    @Override
    public void run()
    {
        while(this.doSync)
        {
                try {
                    SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this.syncService);
                    String frequency = settings.getString("sync_frequency", "-1");
                    System.out.println(frequency);
                    if (frequency.equals("-1")) {
                        this.sleep(600000);
                    } else {
                        System.out.println("Sync");
                        RSSFeedParser XmlParser = new RSSFeedParser();
                        settings = PreferenceManager.getDefaultSharedPreferences(this.syncService);
                        String klasse = settings.getString("klasse", "S4");
                        System.out.println("Started FeedUpdater");
//                        InputStream stream = downloadUrl("http://winet.no-ip.info/blackboard/rss/get_android_rss_debug.php?klasse=" + klasse);
                        InputStream stream = downloadUrl("http://winet.no-ip.info/blackboard/rss/get_android_rss.php?klasse=" + klasse);

                        System.out.println("Parsing");
                        ArrayList<Ausfall2> ausfaelle = XmlParser.parse(stream);
                        if(this.dataProvider.newData(ausfaelle))
                        {
                            this.makeNotification();
                        }

                        this.dataProvider.updateDatabase(ausfaelle);
                        new CacheManager(this.syncService).updateCach(ausfaelle);


                        this.sleep(Integer.valueOf(frequency) * 60 * 1000);
                    }
                }catch(InterruptedException e){
                    e.printStackTrace();
                }catch(IOException e) {
                    e.printStackTrace();
                }
        }
    }



    private InputStream downloadUrl(String urlString) throws IOException {
        System.out.println("Downloaiding");
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000 /* milliseconds */);
        conn.setConnectTimeout(15000 /* milliseconds */);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        // Starts the query
        conn.connect();
        return conn.getInputStream();
    }

    public void makeNotification()
    {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this.syncService)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("My Notification Title")
                        .setContentText("Something interesting happened");
        int NOTIFICATION_ID = 12345;

        Intent targetIntent = new Intent(this.syncService, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this.syncService, 0, targetIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);
        NotificationManager nManager = (NotificationManager) this.syncService.getSystemService(this.syncService.NOTIFICATION_SERVICE);
        nManager.notify(NOTIFICATION_ID, builder.build());
    }
}
