package de.nkp_media.vertretungsplanappandroid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import de.nkp_media.vertretungsplanappandroid.data.FeedDataManager;
import de.nkp_media.vertretungsplanappandroid.fab.FAB;
import de.nkp_media.vertretungsplanappandroid.gcm.RegistrationIntentService;
import de.nkp_media.vertretungsplanappandroid.navbar.NavigationDrawer;
import de.nkp_media.vertretungsplanappandroid.news.NewsListManager;
import de.nkp_media.vertretungsplanappandroid.plan.PlanListManager;
import de.nkp_media.vertretungsplanappandroid.plan.plan_tab;
import de.nkp_media.vertretungsplanappandroid.settings.SettingsActivity;
import de.nkp_media.vertretungsplanappandroid.setup.Setup;

public class MainActivity extends ActionBarActivity {


    private static final String TAG = "MainActivity";
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private NavigationDrawer navigationDrawer;
    private Toolbar toolbar;                              // Declaring the Toolbar Object
    private FAB fab;
    private SharedPreferences.OnSharedPreferenceChangeListener listener;
    CharSequence Titles[]={"Plan","Nachrichten"};
    int Numboftabs =2;
    public ViewPagerAdapter adapter;
    private ViewPager pager;
    private SlidingTabLayout tabs;
    public PlanListManager planListManager;
    public FeedDataManager feedDataManager;
    public NewsListManager newsListManager;
    public plan_tab plan_tab;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreat");

    /* Assinging the toolbar object ot the view
    and setting the the Action bar to our toolbar
     */
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        this.planListManager = new PlanListManager(this);
        this.newsListManager = new NewsListManager(this);
        this.feedDataManager = new FeedDataManager(this,this.planListManager,this.newsListManager);
        this.planListManager.refreshArrayList();
        this.newsListManager.refreshArrayList();

        adapter =  new ViewPagerAdapter(getSupportFragmentManager(),Titles,Numboftabs,this,this.planListManager.getActualPlanViewList(),this.newsListManager.getActualNewsViewList());
        this.plan_tab = adapter.plan_tab;
        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);
        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);
        
        this.navigationDrawer = new NavigationDrawer(this,this.toolbar);

//        this.fab = new FAB(this);



        if (checkPlayServices())
        {
            Log.d(TAG, "Start Registrationservice");
            // Start IntentService to register this application with GCM.
            Intent registrationServiceIntent = new Intent(this, RegistrationIntentService.class);
            startService(registrationServiceIntent);
        }

    }


    @Override
    public void onResume()
    {
        super.onResume();
        this.checkPassword();
        this.checkShowStartInfo();
    }

    private void checkShowStartInfo() {

    }


    private void checkPassword() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        String password = settings.getString("password","");
        Boolean start = settings.getBoolean("startSetup", false);
        if(!password.equals("wichern")|| !start)
        {
            Intent setupScreen = new Intent(getApplicationContext(), Setup.class);
            startActivity(setupScreen);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        this.getSupportActionBar().setTitle("Heute");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent settingsScreen = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(settingsScreen);
            return true;
        }
        else if(id == R.id.action_mail)
        {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", "vertretungsplan@nkp-media.de", null));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Vertretungsplan");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "");
            startActivity(Intent.createChooser(emailIntent, "Send email..."));
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

/*    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(navigationDrawer);
        dest.writeValue(toolbar);
        dest.writeValue(fab);
        dest.writeValue(Titles);
        dest.writeValue(Numboftabs);
        dest.writeValue(adapter);
        dest.writeValue(pager);
        dest.writeValue(tabs);
        dest.writeValue(planListManager);
        dest.writeValue(newsListManager);
        dest.writeValue(feedDataManager);
    }

    MainActivity(Parcel in) {
        in.readValue(navigationDrawer);
        in.rea
        in.writeValue(toolbar);
        in.writeValue(fab);
        in.writeValue(Titles);
        in.writeValue(Numboftabs);
        in.writeValue(adapter);
        in.writeValue(pager);
        in.writeValue(tabs);
        in.writeValue(planListManager);
        in.writeValue(newsListManager);
        in.writeValue(feedDataManager);
    }*/
}
