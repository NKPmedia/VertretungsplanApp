package de.nkp_media.vertretungsplanappandroid.plan;

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

import java.util.ArrayList;

import de.greenrobot.event.EventBus;
import de.nkp_media.vertretungsplanappandroid.Ausfall2;
import de.nkp_media.vertretungsplanappandroid.R;
import de.nkp_media.vertretungsplanappandroid.RefreshEvent;

/**
 * Created by hp1 on 21-01-2015.
 */
public class plan_tab extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "plan_tab";
    private ArrayList<Ausfall2> actualPlanViewList;
    public RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(savedInstanceState != null) {
            this.actualPlanViewList = (ArrayList<Ausfall2>) savedInstanceState.getSerializable("list_plan");
        }
        super.onCreateView(inflater, container, savedInstanceState);

        View v =inflater.inflate(R.layout.plan_tab,container,false);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.main_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this.getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        MyPlanListAdapter mAdapter = new MyPlanListAdapter(this.actualPlanViewList);
        mRecyclerView.setAdapter(mAdapter);

        mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.activity_plan_swipe_refresh_layout);
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
        MyPlanListAdapter mAdapter = new MyPlanListAdapter(this.actualPlanViewList);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void setArguments(ArrayList<Ausfall2> actualPlanViewList) {
        this.actualPlanViewList = actualPlanViewList;
    }
    @Override
    public void onSaveInstanceState(Bundle saving) {
        Log.e(TAG, "\"onSaveInstanceState\" aufgerufen");
        saving.putSerializable("list_plan", this.actualPlanViewList);
        super.onSaveInstanceState(saving);
    }

    public void onEvent(PlanEvent event){
        this.mRecyclerView.setAdapter(new MyPlanListAdapter(event.ausfallPlanList));
        this.actualPlanViewList = event.ausfallPlanList;
    }

    @Override
    public void onRefresh() {
        EventBus.getDefault().post(new RefreshEvent(this.mSwipeRefreshLayout));

    }

}
