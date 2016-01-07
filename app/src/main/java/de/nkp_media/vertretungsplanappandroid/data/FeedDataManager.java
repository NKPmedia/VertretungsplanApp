package de.nkp_media.vertretungsplanappandroid.data;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;
import de.nkp_media.vertretungsplanappandroid.Ausfall2;
import de.nkp_media.vertretungsplanappandroid.MainActivity;
import de.nkp_media.vertretungsplanappandroid.News;
import de.nkp_media.vertretungsplanappandroid.RefreshEvent;
import de.nkp_media.vertretungsplanappandroid.Sync.FeedDataHandler;
import de.nkp_media.vertretungsplanappandroid.Sync.FeedUpdate;
import de.nkp_media.vertretungsplanappandroid.news.NewsListManager;
import de.nkp_media.vertretungsplanappandroid.plan.PlanListManager;

/**
 * Created by paul on 18.08.15.
 */
public class FeedDataManager implements FeedDataManagerInterface{


    private static final String TAG = "BackgroundFDM";
    private final MainActivity mainActivity;
    private final FeedDataHandler feedDataHandler;
    private final FeedDataProvider dataProvider;
    private final PlanListManager planListManager;
    private final NewsListManager newsListManager;
    private ImageView fabAnimation;
    private boolean loadingDataFromServer;
    private ArrayList<Ausfall2> planAusfallList = new ArrayList<Ausfall2>();
    private ArrayList<News> newsList;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public FeedDataManager(MainActivity mainActivity, PlanListManager planListView, NewsListManager newsListManager) {
        this.mainActivity = mainActivity;
        this.planListManager = planListView;
        this.newsListManager = newsListManager;
        this.feedDataHandler = new FeedDataHandler(this);
        this.dataProvider = new FeedDataProvider(this.mainActivity);

        EventBus.getDefault().register(this);
    }

    public void onEvent(RefreshEvent event){
        this.loadDataFromServer(event.mSwipeRefreshLayout);
    }

    private void loadDataFromServer(SwipeRefreshLayout mSwipeRefreshLayout) {
        if(this.loadingDataFromServer)
        {
            Toast toast = Toast.makeText(this.mainActivity, "Allready refreshing", Toast.LENGTH_SHORT);
            toast.show();
            mSwipeRefreshLayout.setRefreshing(false);
        }
        else
        {
            Log.d(TAG,"Loading data from server");
            this.loadingDataFromServer = true;
            this.mSwipeRefreshLayout = mSwipeRefreshLayout;
            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this.mainActivity);
            String klasse = settings.getString("klasse", "S4");
            boolean debugSite = settings.getBoolean("debug_site",false);
            FeedUpdate update = new FeedUpdate(this.feedDataHandler,klasse,this.mainActivity,debugSite);
            update.start();
        }
    }

    public void loadDataFromServer(ImageView iv)
    {
        if(this.loadingDataFromServer)
        {
            Toast toast = Toast.makeText(this.mainActivity, "Allready refreshing", Toast.LENGTH_SHORT);
            toast.show();
        }
        else
        {
            Log.d(TAG,"Loading data from server");
            this.loadingDataFromServer = true;
            this.fabAnimation = iv;
            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this.mainActivity);
            String klasse = settings.getString("klasse", "S4");
            boolean debugSite = settings.getBoolean("debug_site",false);
            FeedUpdate update = new FeedUpdate(this.feedDataHandler,klasse,this.mainActivity,debugSite);
            update.start();
        }
    }

    public void refreshPlanData(ArrayList<Ausfall2> ausfallList) {
        Log.d(TAG,"Refresh Plan Database");
        this.planAusfallList = ausfallList;
        System.out.println(this.planAusfallList.size());
//        this.fabAnimation.clearAnimation();
        this.mSwipeRefreshLayout.setRefreshing(false);
        this.loadingDataFromServer = false;
        this.dataProvider.updateDatabase(ausfallList);
        this.planListManager.refreshPlan();
        
    }

    public ArrayList getPlanData()
    {
        Log.d(TAG, "Returns PlanData");
        this.planAusfallList = this.dataProvider.getPlanData();
        return this.planAusfallList;
    }

    public void errorWhileLoadFeedFromServer() {
        this.loadingDataFromServer = false;
        Log.e(TAG, "error While Load Feed From Server");
//        this.fabAnimation.clearAnimation();
        this.mSwipeRefreshLayout.setRefreshing(false);
        Toast toast = Toast.makeText(this.mainActivity, "Cant load Feed", Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void refreshNewsData(ArrayList<News> newsList) {
        Log.d(TAG, "Refresh News Database");
        this.newsList = newsList;
        this.dataProvider.updateNewsDatabase(newsList);
        this.newsListManager.refreshNews();
    }

    public ArrayList<News> getNewsData() {
        Log.d(TAG, "Returns NewsData");
        this.newsList = this.dataProvider.getNewsData();
        return this.newsList;
    }
}
