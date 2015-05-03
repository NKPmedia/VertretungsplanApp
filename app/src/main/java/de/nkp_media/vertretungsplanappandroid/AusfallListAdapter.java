package de.nkp_media.vertretungsplanappandroid;

/**
* Created by paul on 26.03.15.
*/

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AusfallListAdapter extends ArrayAdapter<Ausfall2> {

    private ArrayList<Ausfall2> data;
    Context context;
    int layoutResourceId;

    public AusfallListAdapter(Context context, int layoutResourceId, ArrayList<Ausfall2> data) {
        super(context, layoutResourceId,data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        Ausfall2 ausfall = (Ausfall2) this.data.get(position);
        if(!ausfall.isEntfall()) {
            row = LayoutInflater.from(context).inflate(R.layout.customrowlayout, parent, false);
        }
        if(ausfall.isEntfall())
        {
            row = LayoutInflater.from(context).inflate(R.layout.customrowlayout2, parent, false);
        }
        if(ausfall.isEntfall())
        {
            TextView stunde = (TextView) row.findViewById(R.id.stunde);
            TextView lehrer = (TextView) row.findViewById(R.id.lehrer);
            TextView fach = (TextView) row.findViewById(R.id.fach);

            stunde.setText(String.valueOf(ausfall.getStunde()));
            lehrer.setText(ausfall.getLehrer().replaceAll("chrom",""));
            fach.setText(ausfall.getFach());
        }
        else
        {
            TextView stunde = (TextView) row.findViewById(R.id.stunde);
            TextView lehrer = (TextView) row.findViewById(R.id.lehrer);
            TextView fach = (TextView) row.findViewById(R.id.fach);
            TextView vertretung = (TextView) row.findViewById(R.id.vertretung);
            TextView vfach = (TextView) row.findViewById(R.id.vfach);

            try {
                stunde.setText(String.valueOf(ausfall.getStunde()));
                lehrer.setText(ausfall.getLehrer().replaceAll("chrom",""));
                fach.setText(ausfall.getFach());
                vertretung.setText(ausfall.getVertretung());
                vfach.setText(ausfall.getZielfach());
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }


        }
        return row;
    }
}
