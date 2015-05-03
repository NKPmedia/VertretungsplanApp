package de.nkp_media.vertretungsplanappandroid;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class Setup extends ActionBarActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        Button button = (Button)findViewById(R.id.setupweiter);
        button.setOnClickListener(this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_setup, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.setupweiter)
        {
            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
            settings.edit().putBoolean("startSetup",true).commit();

            EditText mEdit   = (EditText)findViewById(R.id.setupKlasse);
            settings.edit().putString("klasse", mEdit.getText().toString()).commit();

            EditText mPass   = (EditText)findViewById(R.id.setupPasswort);
            settings.edit().putString("password",mPass.getText().toString()).commit();

            this.finish();
        }
    }
}
