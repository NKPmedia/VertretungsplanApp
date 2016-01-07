package de.nkp_media.vertretungsplanappandroid.plan;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import de.nkp_media.vertretungsplanappandroid.Ausfall2;
import de.nkp_media.vertretungsplanappandroid.R;

/**
 * Created by paul on 17.08.15.
 */
public class MyPlanListAdapter extends RecyclerView.Adapter<MyPlanListAdapter.ViewHolder> {
    private static final String TAG = "PlanListAdapter";
    private int position;
    private ArrayList<Ausfall2> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View mTextView;

        public ViewHolder(View v) {
            super(v);
            mTextView = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyPlanListAdapter(ArrayList<Ausfall2> myDataset) {
        mDataset = myDataset;
        position = 0;
        Log.d(TAG,"Got dataset with "+this.mDataset.size()+" items" );
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyPlanListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.customrowlayout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        if(this.mDataset.get(this.position).isEntfall())
        {
            View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.customrowlayout2, parent, false);
            vh = new ViewHolder(row);
        }
        this.position++;
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Ausfall2 ausfall = (Ausfall2) this.mDataset.get(position);
        View row = holder.itemView;
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
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}

