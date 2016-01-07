package de.nkp_media.vertretungsplanappandroid.navbar;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import de.nkp_media.vertretungsplanappandroid.MainActivity;
import de.nkp_media.vertretungsplanappandroid.settings.SettingsActivity;

/**
 * Created by paul on 18.08.15.
 */
public class NavigationDrawerOnItemTouchListener implements RecyclerView.OnItemTouchListener  {
    private static final String TAG = "NavDrawerTouchListener";
    private final GestureDetector mGestureDetector;
    private final MainActivity mainActivity;
    private DrawerLayout drawer;

    public NavigationDrawerOnItemTouchListener(MainActivity parentActivity, GestureDetector mGestureDetector, DrawerLayout drawer) {
        this.mainActivity = parentActivity;
        this.mGestureDetector = mGestureDetector;
        this.drawer = drawer;
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
        View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());


        if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {
            int number = recyclerView.getChildPosition(child);
            if(number == 1)
            {
                this.drawer.closeDrawers();
                this.mainActivity.planListManager.displayPlanDay = 0;
                this.mainActivity.planListManager.refreshPlan();
                this.mainActivity.getSupportActionBar().setTitle("Heute");
                Log.d(TAG, "Clickted " + number);
            }
            else if(number == 2)
            {
                this.drawer.closeDrawers();
                this.mainActivity.planListManager.displayPlanDay = 1;
                this.mainActivity.planListManager.refreshPlan();
                this.mainActivity.getSupportActionBar().setTitle("Morgen");
                Log.d(TAG, "Clickted " + number);
            }
            else if(number == 3)
            {
                this.drawer.closeDrawers();
                this.mainActivity.planListManager.displayPlanDay = 2;
                this.mainActivity.planListManager.refreshPlan();
                this.mainActivity.getSupportActionBar().setTitle("Übermorgen");
                Log.d(TAG, "Clickted " + number);
            }
            else if(number == 4)
            {
                this.drawer.closeDrawers();
                Log.d(TAG, "Clickted " + number);
                Intent settingsScreen = new Intent(this.mainActivity.getApplicationContext(), SettingsActivity.class);
                this.mainActivity.startActivity(settingsScreen);
            }
            else if(number == 5)
            {
                this.drawer.closeDrawers();
                Log.d(TAG, "Clickted " + number);
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=BF9F7ZDJ79ZQG"));
                this.mainActivity.startActivity(browserIntent);
            }
            else
            return true;

        }

        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean b) {

    }
}