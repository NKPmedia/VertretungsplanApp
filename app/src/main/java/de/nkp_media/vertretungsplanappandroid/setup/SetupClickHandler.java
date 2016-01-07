package de.nkp_media.vertretungsplanappandroid.setup;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;

import de.nkp_media.vertretungsplanappandroid.R;

/**
 * Created by paul on 24.08.15.
 */
public class SetupClickHandler implements View.OnClickListener {

    private final Setup setupActivity;

    public SetupClickHandler(Setup setup) {
        this.setupActivity = setup;
    }

    @Override
    public void onClick(View v) {
         SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this.setupActivity);
        SharedPreferences.Editor preferenceEdit = settings.edit();
        EditText password = (EditText) this.setupActivity.findViewById(R.id.passwort_text);
        EditText klasse = (EditText) this.setupActivity.findViewById(R.id.klasse_text);
        preferenceEdit.putString("klasse",klasse.getText().toString()).commit();
        preferenceEdit.putString("password",password.getText().toString()).commit();
        preferenceEdit.putBoolean("startSetup",true).commit();

        this.setupActivity.finish();

    }
}
