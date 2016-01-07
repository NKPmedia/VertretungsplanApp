package de.nkp_media.vertretungsplanappandroid.data;

import java.util.ArrayList;

import de.nkp_media.vertretungsplanappandroid.Ausfall2;
import de.nkp_media.vertretungsplanappandroid.News;

/**
 * Created by paul on 24.08.15.
 */
public interface FeedDataManagerInterface {
    public void refreshPlanData(ArrayList<Ausfall2> ausfallList) ;
    public void errorWhileLoadFeedFromServer() ;

    public void refreshNewsData(ArrayList<News> newsList);
}
