package de.nkp_media.vertretungsplanappandroid.plan;

import java.util.ArrayList;

import de.nkp_media.vertretungsplanappandroid.Ausfall2;

/**
 * Created by paul on 08.09.15.
 */
public class PlanEvent {

    public ArrayList<Ausfall2> ausfallPlanList;
    public String kklasse;

    public PlanEvent(ArrayList<Ausfall2> valueListForPlanView) {
        this.ausfallPlanList = valueListForPlanView;
    }
}
