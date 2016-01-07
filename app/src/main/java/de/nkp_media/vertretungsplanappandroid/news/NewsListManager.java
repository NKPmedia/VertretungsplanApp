package de.nkp_media.vertretungsplanappandroid.news;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;
import de.nkp_media.vertretungsplanappandroid.MainActivity;
import de.nkp_media.vertretungsplanappandroid.News;
import de.nkp_media.vertretungsplanappandroid.plan.MyPlanListAdapter;

/**
 * Created by paul on 17.08.15.
 */
public class NewsListManager {
    private final MainActivity mainActivity;
    private MyPlanListAdapter mAdapter;
    public int displayPlanDay;
    private ArrayList<News> valueListForNewsView = new ArrayList<News>();

    public NewsListManager(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }


    public void refreshNews() {
        ArrayList<News> newsListe =  this.mainActivity.feedDataManager.getNewsData();



        this.valueListForNewsView = new ArrayList<News>();
        for (News news : newsListe) {
            valueListForNewsView.add(news);
        }

        EventBus.getDefault().post(new NewsEvent(this.valueListForNewsView));
    }

    public ArrayList<News> getActualNewsViewList() {
        return this.valueListForNewsView;
    }

    public void refreshArrayList() {
        ArrayList<News> newsListe =  this.mainActivity.feedDataManager.getNewsData();



        this.valueListForNewsView = new ArrayList<News>();
        for (News news : newsListe) {
            valueListForNewsView.add(news);
        }
    }
}
