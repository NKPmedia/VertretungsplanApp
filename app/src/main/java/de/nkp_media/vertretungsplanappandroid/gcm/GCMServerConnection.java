package de.nkp_media.vertretungsplanappandroid.gcm;

import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by paul on 16.01.16.
 */
public class GCMServerConnection {

    private static String TAG ="GCMServerConnection";

    /**
     * Persist registration to third-party servers.
     *
     * Modify this method to associate the user's GCM registration token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     * @param randomDeviceId
     */
    public static String sendRegistrationToServer(String token, String randomDeviceId) {
        try {
            Log.d(TAG, "Sendig token to server");
            URL url = new URL("http://winet-ag.ddns.net/blackboard/rss/reg_gcm_id.php?action=reg&deviceID="+randomDeviceId+"&gcmid="+token );
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
            Log.d(TAG,"Respons (Sending): "+response);
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "1";
    }

    public static String checkIdsOnServer(String deviceId, String gcmId) {
        try {
            Log.d(TAG, "Sendig both IDs to server");
            URL url = new URL("http://winet-ag.ddns.net/blackboard/rss/reg_gcm_id.php?action=checkID&deviceID="+deviceId+"&gcmid="+gcmId );
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
            Log.d(TAG,"Respons (Checking): "+response);
            return response;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "1";
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return "1";
        } catch (ProtocolException e) {
            e.printStackTrace();
            return "1";
        } catch (IOException e) {
            e.printStackTrace();
            return "1";
        }

    }

    public static String updateRegistrationToServer(String gcmId, String deviceId) {
        try {
            Log.d(TAG, "Update reg id with existing device id");
            URL url = new URL("http://winet-ag.ddns.net/blackboard/rss/reg_gcm_id.php?action=updateRegId&deviceID="+deviceId+"&gcmid="+gcmId );
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
            Log.d(TAG,"Respons (Updating): "+response);
            return response;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "1";
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return "1";
        } catch (ProtocolException e) {
            e.printStackTrace();
            return "1";
        } catch (IOException e) {
            e.printStackTrace();
            return "1";
        }
    }

    public static String updateDeviceIdToServer(String gcmId, String deviceId) {
        try {
            Log.d(TAG, "Update device id with existing reg id");
            URL url = new URL("http://winet-ag.ddns.net/blackboard/rss/reg_gcm_id.php?action=updateDeviceId&deviceID="+deviceId+"&gcmid="+gcmId );
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
            Log.d(TAG,"Respons (Updating): "+response);
            return response;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "1";
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return "1";
        } catch (ProtocolException e) {
            e.printStackTrace();
            return "1";
        } catch (IOException e) {
            e.printStackTrace();
            return "1";
        }
    }
}
