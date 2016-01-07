package de.nkp_media.vertretungsplanappandroid.Sync;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by paul on 20.03.15.
 */
public class FeedUpdate extends Thread{

    private static final String TAG = "FeedUpdater";
    private final Handler uiHandler;
    private final String klasse;
    private final Context context;
    private final boolean debugSite;

    public FeedUpdate(Handler uihandler, String klasse, Context context, boolean debugSite) {
        this.uiHandler = uihandler;
        this.klasse = klasse;
        this.context = context;
        this.debugSite = debugSite;
    }
    @Override
    public void run()
    {
        RSSFeedParser XmlParser = new RSSFeedParser();
        InputStream stream = null;
        try {
            Log.d(TAG,"Start FeedUpdater");
            if(this.debugSite) {
                stream = downloadUrl("http://winet-ag.ddns.net/blackboard/rss/get_android_rss_debug.php?klasse=" + this.klasse);
            }
            else
            {
                stream = downloadUrl("http://winet-ag.ddns.net/blackboard/rss/get_android_rss.php?klasse="+this.klasse);
            }


//            BufferedReader buff = new BufferedReader(new InputStreamReader(stream));
//            String line;
//            StringBuilder total = new StringBuilder();
//            while ((line = buff.readLine()) != null) {
//                total.append(line);
//            }
//
//            System.out.println(total.toString());

            Log.d(TAG,"Parsing serverdata");
            ArrayList<ArrayList> back = XmlParser.parse(stream);

            Message msg = Message.obtain(uiHandler);
            msg.arg1 = 0;
            msg.obj =back;
            if(msg.obj == null || msg == null)
            {
                Log.e(TAG,"Msg is null");
                msg = Message.obtain(uiHandler);
                msg.arg1 = 1;
                this.uiHandler.sendMessage(msg);
            }
            else
            {
                this.uiHandler.sendMessage(msg);
            }

        } catch (IOException e) {
            e.printStackTrace();
            Message msg = Message.obtain(uiHandler);
            msg.arg1 = 1;
            this.uiHandler.sendMessage(msg);
        }
    }

    // Given a string representation of a URL, sets up a connection and gets
    // an input stream.
    static public InputStream downloadUrl(String urlString) throws IOException {
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

}
