package de.nkp_media.vertretungsplanappandroid.plan;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import de.greenrobot.event.EventBus;
import de.nkp_media.vertretungsplanappandroid.Ausfall2;
import de.nkp_media.vertretungsplanappandroid.MainActivity;

/**
 * Created by paul on 17.08.15.
 */
public class PlanListManager {
    private static final String TAG = "PlanListManager";
    private final MainActivity mainActivity;
    private MyPlanListAdapter mAdapter;
    public int displayPlanDay;
    private ArrayList<Ausfall2> valueListForPlanView = new ArrayList<Ausfall2>();

    public PlanListManager( MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }


    public void refreshPlan() {
        ArrayList<Ausfall2> ausfallListe =  this.mainActivity.feedDataManager.getPlanData();

        String anzeigeDatum = "";
        if(this.displayPlanDay == 0)
        {
            anzeigeDatum = this.getTodayPlusDay(0);
        }
        else if(this.displayPlanDay == 1)
        {
            anzeigeDatum = this.getTodayPlusDay(1);
        }
        else if(this.displayPlanDay == 2)
        {
            anzeigeDatum = this.getTodayPlusDay(2);
        }


        ArrayList<String> valueList = new ArrayList<String>();
        valueListForPlanView = new ArrayList<Ausfall2>();

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this.mainActivity);
        boolean debugList = settings.getBoolean("debug_list",false);

        //tage werden nicht berücksichtigt
        if(debugList)
        {
            for (Ausfall2 ausfall : ausfallListe) {
                valueListForPlanView.add(ausfall);
            }
        }
        //Tage werden brücksichtigt
        else
        {
            for (Ausfall2 ausfall : ausfallListe) {
                if(ausfall.getFach().matches(".*P[1-9].*") && PreferenceManager.getDefaultSharedPreferences(this.mainActivity).getBoolean("profil_aktiv", false)) {
                    if (ausfall.getZieldatumString().equals(anzeigeDatum) && ausfall.getFach().matches(".*"+PreferenceManager.getDefaultSharedPreferences(this.mainActivity).getString("profil","")+".*")) {
                        valueListForPlanView.add(ausfall);
                    }
                }
                else {
                    if (ausfall.getZieldatumString().equals(anzeigeDatum)) {
                        valueListForPlanView.add(ausfall);
                    }
                }
            }
        }

        EventBus.getDefault().post(new PlanEvent(this.valueListForPlanView));
    }


    private String getTodayPlusDay(int days)
    {
        Date heute = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(heute);
        c.add(Calendar.DATE, days);  // number of days to add
        return sdf.format(c.getTime());
    }

    public ArrayList<Ausfall2> getActualPlanViewList() {
        return this.valueListForPlanView;
    }

    public void refreshArrayList() {
        ArrayList<Ausfall2> ausfallListe =  this.mainActivity.feedDataManager.getPlanData();

        String anzeigeDatum = "";
        if(this.displayPlanDay == 0)
        {
            anzeigeDatum = this.getTodayPlusDay(0);
        }
        else if(this.displayPlanDay == 1)
        {
            anzeigeDatum = this.getTodayPlusDay(1);
        }
        else if(this.displayPlanDay == 2)
        {
            anzeigeDatum = this.getTodayPlusDay(2);
        }


        ArrayList<String> valueList = new ArrayList<String>();
        valueListForPlanView = new ArrayList<Ausfall2>();

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this.mainActivity);
        boolean debugList = settings.getBoolean("debug_list",false);

        //tage werden nicht berücksichtigt
        if(debugList)
        {
            for (Ausfall2 ausfall : ausfallListe) {
                valueListForPlanView.add(ausfall);
            }
        }
        //Tage werden brücksichtigt
        else
        {
            for (Ausfall2 ausfall : ausfallListe) {
                if(ausfall.getFach().matches(".*P[1-9].*") && PreferenceManager.getDefaultSharedPreferences(this.mainActivity).getBoolean("profil_aktiv", false)) {
                    Log.d(TAG, String.valueOf(PreferenceManager.getDefaultSharedPreferences(this.mainActivity).getBoolean("profil_aktiv", false)));
                    if (ausfall.getZieldatumString().equals(anzeigeDatum) && ausfall.getFach().matches(".*"+PreferenceManager.getDefaultSharedPreferences(this.mainActivity).getString("profil","")+".*")) {
                        Log.d(TAG, String.valueOf(PreferenceManager.getDefaultSharedPreferences(this.mainActivity).getString("profil", "")));
                        valueListForPlanView.add(ausfall);
                    }
                }
                else {
                    if (ausfall.getZieldatumString().equals(anzeigeDatum)) {
                        valueListForPlanView.add(ausfall);
                    }
                }
            }
        }
    }
}
