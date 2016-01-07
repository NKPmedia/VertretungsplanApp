package de.nkp_media.vertretungsplanappandroid.fab;

import android.support.design.widget.FloatingActionButton;

import de.nkp_media.vertretungsplanappandroid.MainActivity;

/**
 * Created by paul on 18.08.15.
 */
public class FAB {
    public MainActivity parentActivity;

    FloatingActionButton fab;

    public FAB(MainActivity mainActivity) {
        this.parentActivity = mainActivity;
//        fab = (android.support.design.widget.FloatingActionButton) this.parentActivity.findViewById(R.id.imageButton);
        fab.setOnClickListener(new FABListener(this.parentActivity));
    }
}
