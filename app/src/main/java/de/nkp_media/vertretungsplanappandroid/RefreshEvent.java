package de.nkp_media.vertretungsplanappandroid;

import android.support.v4.widget.SwipeRefreshLayout;

/**
 * Created by paul on 15.09.15.
 */
public class RefreshEvent {
    public SwipeRefreshLayout mSwipeRefreshLayout;

    public RefreshEvent(SwipeRefreshLayout mSwipeRefreshLayout) {
        this.mSwipeRefreshLayout = mSwipeRefreshLayout;
    }
}
