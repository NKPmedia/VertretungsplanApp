package de.nkp_media.vertretungsplanappandroid.settings;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import de.nkp_media.vertretungsplanappandroid.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class SettingsActivityFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
    }

}
