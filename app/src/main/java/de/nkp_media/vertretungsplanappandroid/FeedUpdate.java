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

    public FeedUpdate(Handler uihandler) {
        this.uiHandler = uihandler;
    }

    @Override
    public void run()
    {
        RSSFeedParser XmlParser = new RSSFeedParser();
        InputStream stream = null;
        try {
            System.out.println("Started FeedUpdater");
            stream = downloadUrl("http://winet.no-ip.info/blackboard/rss/get_rss.php?klasse=S4");

//            BufferedReader buff = new BufferedReader(new InputStreamReader(stream));
//            String line;
//            StringBuilder total = new StringBuilder();
//            while ((line = buff.readLine()) != null) {
//                total.append(line);
//            }
//
//            System.out.println(total.toString());

            System.out.println("Parsing");
            ArrayList<Ausfall> ausfaelle = XmlParser.parse(stream);

            Message msg = Message.obtain(uiHandler);
            msg.obj =ausfaelle;
            this.uiHandler.sendMessage(msg);

        } catch (IOException e) {
            e.printStackTrace();
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
