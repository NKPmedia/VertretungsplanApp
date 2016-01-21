package de.nkp_media.vertretungsplanappandroid.navbar;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import de.nkp_media.vertretungsplanappandroid.MainActivity;
import de.nkp_media.vertretungsplanappandroid.R;


/**
 * Created by paul on 17.08.15.
 */
public class NavigationDrawer {
    private static final String TAG = "NaigationDrawer";
    private final MainActivity parentActivity;
    private final Toolbar toolbar;

    //First We Declare Titles And Icons For Our Navigation Drawer List View
    //This Icons And Titles Are holded in an Array as you can see

    String TITLES[] = {"Heute","Morgen","Übermorgen","Einstellungen","Spenden"};
    int ICONS[] = {R.drawable.ic_arrow,
            R.drawable.ic_arrow,
            R.drawable.ic_arrow,
            R.drawable.ic_settings_dark,
            R.drawable.ic_herz};
    String TITLESNODONATE[] = {"Heute","Morgen","Übermorgen","Einstellungen"};
    int ICONSNODONATE[] = {R.drawable.ic_arrow,
            R.drawable.ic_arrow,
            R.drawable.ic_arrow,
            R.drawable.ic_settings_dark};

    //Similarly we Create a String Resource for the name and email in the header view
    //And we also create a int resource for profile picture in the header view

    String NAME = "Vertretungsplan";
    String EMAIL = "Created by WiNet";
    int PROFILE = R.drawable.ic_action;

    RecyclerView mRecyclerView;                           // Declaring RecyclerView
    MyNavigationDrawerAdapter mAdapter;                        // Declaring Adapter For Recycler View
    RecyclerView.LayoutManager mLayoutManager;            // Declaring Layout Manager as a linear layout manager
    DrawerLayout Drawer;                                  // Declaring DrawerLayout

    ActionBarDrawerToggle mDrawerToggle;                  // Declaring Action Bar Drawer Toggle

    public NavigationDrawer(MainActivity mainActivity, Toolbar toolbar)
    {

        this.parentActivity = mainActivity;
        this.toolbar = toolbar;
        mRecyclerView = (RecyclerView) this.parentActivity.findViewById(R.id.RecyclerView); // Assigning the RecyclerView Object to the xml View

        mRecyclerView.setHasFixedSize(true);                            // Letting the system know that the list objects are of fixed size

        if(PreferenceManager.getDefaultSharedPreferences(this.parentActivity).getBoolean("donateButton",true)) {
            mAdapter = new MyNavigationDrawerAdapter(TITLES, ICONS, NAME, EMAIL, PROFILE, this.parentActivity);       // Creating the Adapter of MyAdapter class(which we are going to see in a bit)
            // And passing the titles,icons,header view name, header view email,
            // and header view profile picture
        }
        else
        {
            mAdapter = new MyNavigationDrawerAdapter(TITLESNODONATE, ICONSNODONATE, NAME, EMAIL, PROFILE, this.parentActivity);
        }

        mRecyclerView.setAdapter(mAdapter);                              // Setting the adapter to RecyclerView

        ///////////////////Touchhandling
        final GestureDetector mGestureDetector = new GestureDetector(this.parentActivity, new GestureDetector.SimpleOnGestureListener() {

            @Override public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

        });

        Drawer = (DrawerLayout) this.parentActivity.findViewById(R.id.DrawerLayout);        // Drawer object Assigned to the view

        mRecyclerView.addOnItemTouchListener(new NavigationDrawerOnItemTouchListener(this.parentActivity,mGestureDetector,Drawer));
        //////Touchhandlig end

        mLayoutManager = new LinearLayoutManager(this.parentActivity);                 // Creating a layout Manager

        mRecyclerView.setLayoutManager(mLayoutManager);                 // Setting the layout Manager


        mDrawerToggle = new ActionBarDrawerToggle(this.parentActivity,Drawer,this.toolbar, R.string.openDrawer, R.string.closeDrawer){

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // code here will execute once the drawer is opened( As I dont want anything happened whe drawer is
                // open I am not going to put anything here)
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                // Code here will execute once drawer is closed
            }



        }; // Drawer Toggle Object Made
        Drawer.setDrawerListener(mDrawerToggle); // Drawer Listener set to the Drawer toggle
        mDrawerToggle.syncState();               // Finally we set the drawer toggle sync State

        SharedPreferences.OnSharedPreferenceChangeListener spChanged = new
                SharedPreferences.OnSharedPreferenceChangeListener() {
                    @Override
                    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                        Log.d(TAG, "Changed: " + key);
                        if(key.equals("klasse"))
                        {
                            mAdapter.notifyDisplayClassChanged();
                        }
                    }
                };
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.parentActivity);
        prefs.registerOnSharedPreferenceChangeListener(spChanged);
    }
}
