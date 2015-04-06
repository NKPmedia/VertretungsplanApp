package de.nkp_media.vertretungsplanappandroid;

import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by paul on 20.03.15.
 */
public class FeedUpdate extends Thread{

    private final Handler uiHandler;
    private final String klasse;
    private final MainActivity mainActivity;

    public FeedUpdate(Handler uihandler, String klasse, MainActivity mainActivity) {
        this.uiHandler = uihandler;
        this.klasse = klasse;
        this.mainActivity = mainActivity;
    }

    @Override
    public void run()
    {
//        RSSFeedParser XmlParser = new RSSFeedParser();
        RSSFeedParser XmlParser = new RSSFeedParser();
        InputStream stream = null;
        try {
            System.out.println("Started FeedUpdater");
            stream = downloadUrl("http://winet.no-ip.info/blackboard/rss/get_android_rss_debug.php?klasse="+this.klasse);
//            stream = downloadUrl("http://winet.no-ip.info/blackboard/rss/get_android_rss.php?klasse="+this.klasse);

//            BufferedReader buff = new BufferedReader(new InputStreamReader(stream));
//            String line;
//            StringBuilder total = new StringBuilder();
//            while ((line = buff.readLine()) != null) {
//                total.append(line);
//            }
//
//            System.out.println(total.toString());

            System.out.println("Parsing");
            ArrayList<Ausfall2> ausfaelle = XmlParser.parse(stream);

            Message msg = Message.obtain(uiHandler);
            msg.obj =ausfaelle;
            if(msg.obj == null || msg == null)
            {
                System.out.println("Null obj");
            }
            else
            {
                this.uiHandler.sendMessage(msg);
            }

        } catch (IOException e) {
            e.printStackTrace();
            this.mainActivity.ringProgressDialog.dismiss();
        }
    }

    // Given a string representation of a URL, sets up a connection and gets
    // an input stream.
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

}
