package de.nkp_media.vertretungsplanappandroid.Sync;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.ArrayList;

import de.nkp_media.vertretungsplanappandroid.Ausfall2;
import de.nkp_media.vertretungsplanappandroid.News;
import de.nkp_media.vertretungsplanappandroid.data.FeedDataManagerInterface;

/**
 * Created by paul on 18.08.15.
 */
public class FeedDataHandler extends Handler {

    private static final String TAG = "FeedDataHandler";
    private final FeedDataManagerInterface feedDataManager;
    private ArrayList<Ausfall2> ausfallList;
    private ArrayList<News> NewsList;

    public FeedDataHandler(FeedDataManagerInterface feedDataManager) {
        this.feedDataManager = feedDataManager;
    }

    @Override
    public void handleMessage(Message msg) {
        Log.d(TAG,"Got a message");
        if(msg.arg1 == 0) {
            ArrayList back = (ArrayList) msg.obj;
            ausfallList = (ArrayList<Ausfall2>) back.get(0);
            NewsList = (ArrayList<News>) back.get(1);
            System.out.println(((ArrayList<Ausfall2>) back.get(0)).size());
            System.out.println(((ArrayList<News>) back.get(1)).size());
            this.feedDataManager.refreshPlanData(ausfallList);
            this.feedDataManager.refreshNewsData(NewsList);
        }
        else if(msg.arg1 == 1)
        {
            this.feedDataManager.errorWhileLoadFeedFromServer();
        }

        super.handleMessage(msg);
    }

}
