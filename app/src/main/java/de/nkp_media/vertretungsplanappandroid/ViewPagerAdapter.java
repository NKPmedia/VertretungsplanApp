package de.nkp_media.vertretungsplanappandroid;

/**
 * Created by paul on 29.08.15.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.io.Serializable;
import java.util.ArrayList;

import de.nkp_media.vertretungsplanappandroid.news.news_tab;
import de.nkp_media.vertretungsplanappandroid.plan.plan_tab;

public class ViewPagerAdapter extends FragmentStatePagerAdapter implements Serializable{

    private final MainActivity mainActivity;
    private final ArrayList<Ausfall2> actualPlanViewList;
    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created
    public plan_tab plan_tab;
    private ArrayList<News> actualNewsViewList;
    public news_tab news_tab;


    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb, MainActivity mainActivity, ArrayList<Ausfall2> actualPlanViewList, ArrayList<News> actualNewsViewList) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;
        this.mainActivity = mainActivity;
        this.actualPlanViewList = actualPlanViewList;
        this.actualNewsViewList = actualNewsViewList;
        this.plan_tab = new plan_tab();
        this.plan_tab.setArguments( this.actualPlanViewList);

    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

        if(position == 0) // if the position is 0 we are returning the First tab
        {

            plan_tab tab1 = new plan_tab();
            tab1.setArguments(this.actualPlanViewList);
            this.plan_tab = tab1;
            return tab1;
        }
        else             // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            news_tab tab2 = new news_tab();
            tab2.setArguments(this.actualNewsViewList);
            this.news_tab = tab2;
            return tab2;
        }


    }

    // This method return the titles for the Tabs in the Tab Strip

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}
