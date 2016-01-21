package de.nkp_media.vertretungsplanappandroid.news;

/**
 * Created by paul on 29.08.15.
 */

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;
import java.util.ArrayList;

import de.greenrobot.event.EventBus;
import de.nkp_media.vertretungsplanappandroid.News;
import de.nkp_media.vertretungsplanappandroid.R;
import de.nkp_media.vertretungsplanappandroid.RefreshEvent;

/**
 * Created by hp1 on 21-01-2015.
 */
public class news_tab extends Fragment implements Serializable, SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "tab_news";
    public ArrayList<News> actualNewsViewList;
    public RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(savedInstanceState != null) {
            this.actualNewsViewList = (ArrayList<News>) savedInstanceState.getSerializable("list_news");
        }
        View v = inflater.inflate(R.layout.news_tab,container,false);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.main_recycler_view_news);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this.getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        MyNewsListAdapter mAdapter = new MyNewsListAdapter(this.actualNewsViewList);
        mRecyclerView.setAdapter(mAdapter);

        mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.activity_news_swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        return v;
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        MyNewsListAdapter mAdapter = new MyNewsListAdapter(this.actualNewsViewList);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void setArguments(ArrayList<News> actualNewsViewList) {
        this.actualNewsViewList = actualNewsViewList;
    }
    @Override
    public void onSaveInstanceState(Bundle saving) {
        Log.e(TAG, "\"onSaveInstanceState\" aufgerufen");
        saving.putSerializable("list_news",this.actualNewsViewList);
        super.onSaveInstanceState(saving);
    }

    public void onEvent(NewsEvent event){
        this.mRecyclerView.setAdapter(new MyNewsListAdapter(event.newsList));
        this.actualNewsViewList = event.newsList;
    }

    @Override
    public void onRefresh() {
        EventBus.getDefault().post(new RefreshEvent(this.mSwipeRefreshLayout));
        Log.d(TAG, "Pulled Swipe-Refresh");

    }
}