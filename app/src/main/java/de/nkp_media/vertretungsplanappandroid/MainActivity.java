package de.nkp_media.vertretungsplanappandroid;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import de.nkp_media.vertretungsplanappandroid.caching.CacheManager;

public class MainActivity extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    private Handler uiHandler;
    private String currentDate ="heute";
    private ArrayList<Ausfall2> ausfallList = new ArrayList<>();
    private AusfallListAdapter listViewAdapter = null;
    private CacheManager cacheManager;
    public ProgressDialog ringProgressDialog;

    public MainActivity() {
        this.uiHandler = new UIHandler(this.cacheManager);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();
        mNavigationDrawerFragment.setHandler(this.uiHandler);
        mNavigationDrawerFragment.setMainActivity(this);
        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
        mNavigationDrawerFragment.updateFeed();
        this.checkStartSetup();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
    }

    private void updateListView() {
        ListView listView = (ListView) findViewById(R.id.listView);
        ArrayList<String> valueList = new ArrayList<String>();
        ArrayList<Ausfall2> valueList2 = new ArrayList<Ausfall2>();

        String anzeigeDatum = "";
        if(this.currentDate.equals("heute"))
        {
            anzeigeDatum = this.getTodayPlusDay(0);
        }
        else if(this.currentDate.equals("morgen"))
        {
            anzeigeDatum = this.getTodayPlusDay(1);
        }
        else if(this.currentDate.equals("uebermorgen"))
        {
            anzeigeDatum = this.getTodayPlusDay(2);
        }


        for(Ausfall2 ausfall : this.ausfallList)
        {
            System.out.println("Item");
            /*if(ausfall.getZieldatumString().equals(anzeigeDatum)) {
                if (ausfall.isEntfall())
                {
                    valueList.add(String.valueOf(ausfall.getStunde()) + "A " + ausfall.getFach() + " (" + ausfall.getLehrer().replaceAll("chrom","") + ")");
                    valueList2.add(ausfall);
                }
                else
                {
                    valueList.add(String.valueOf(ausfall.getStunde()) + "V " + ausfall.getFach() + " (" + ausfall.getLehrer().replaceAll("chrom","") + ") \n-> "+ausfall.getZielfach()+" ("+ausfall.getVertretung().replaceAll("chrom","")+")");
                    valueList2.add(ausfall);
                }
            }*/
            if (ausfall.isEntfall())
            {
                valueList.add(String.valueOf(ausfall.getStunde()) + "A " + ausfall.getFach() + " (" + ausfall.getLehrer().replaceAll("chrom","") + ")");
                valueList2.add(ausfall);
            }
            else
            {
                valueList.add(String.valueOf(ausfall.getStunde()) + "V " + ausfall.getFach() + " (" + ausfall.getLehrer().replaceAll("chrom","") + ") \n-> "+ausfall.getZielfach()+" ("+ausfall.getVertretung().replaceAll("chrom","")+")");
                valueList2.add(ausfall);
            }
        }

        listViewAdapter = new AusfallListAdapter(getApplicationContext(),R.layout.customrowlayout,valueList2);
        listView.setAdapter(listViewAdapter);
    }

    public String getTodayPlusDay(int days)
    {
        Date heute = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(heute);
        c.add(Calendar.DATE, days);  // number of days to add
        return sdf.format(c.getTime());
    }


    private void checkStartSetup() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        boolean startSetup = settings.getBoolean("startSetup", false);
        if(!startSetup)
        {
            Intent nextScreen = new Intent(getApplicationContext(), Setup.class);
            startActivity(nextScreen);
        }

    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                Toast.makeText(this, "Heute", Toast.LENGTH_SHORT).show();
                this.currentDate = "heute";
                if(mNavigationDrawerFragment != null)  mNavigationDrawerFragment.updateFeed();
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                Toast.makeText(this, "Morgen", Toast.LENGTH_SHORT).show();
                this.currentDate = "morgen";
                if(mNavigationDrawerFragment != null)  mNavigationDrawerFragment.updateFeed();
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                Toast.makeText(this, "Übermorgen", Toast.LENGTH_SHORT).show();
                this.currentDate = "uebermorgen";
                if(mNavigationDrawerFragment != null)  mNavigationDrawerFragment.updateFeed();
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent nextScreen = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(nextScreen);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

    class UIHandler extends Handler {

        CacheManager cacheManager;

        public UIHandler(CacheManager manager) {
            this.cacheManager = manager;
        }

        @Override
        public void handleMessage(Message msg) {
            // a message is received; update UI text view
//            textView.setText(msg.obj.toString());
            System.out.println("Message");
            ausfallList = (ArrayList<Ausfall2>) msg.obj;
//            cacheManager.notifyAll();
            if(ausfallList == null)
            {
                System.out.println("Null List");
            }
            updateListView();
            ringProgressDialog.dismiss();
            super.handleMessage(msg);
        }

    }

}
