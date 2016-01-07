package de.nkp_media.vertretungsplanappandroid.news;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import de.nkp_media.vertretungsplanappandroid.News;
import de.nkp_media.vertretungsplanappandroid.R;

/**
 * Created by paul on 17.08.15.
 */
public class MyNewsListAdapter extends RecyclerView.Adapter<MyNewsListAdapter.ViewHolder>  {
    private static final String TAG = "NewsListAdapter";
    public FragmentActivity mainActivity;
    private int position;
    public ArrayList<News> mDataset;
    private int index = 0;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ArrayList<News> mDataset;
        private FragmentActivity mainActivity;
        // each data item is just a string in this case
        public View mTextView;

        public ViewHolder(View v, ArrayList<News> mDataset) {
            super(v);
            mTextView = v;
            this.mDataset = mDataset;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyNewsListAdapter(ArrayList<News> myDataset) {
        mDataset = myDataset;
        position = 0;
        Log.d(TAG,"Got dataset with "+this.mDataset.size()+" items" );
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        ViewHolder vh;
        if(index % 2 == 0)
        {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.newstitlerowlayout, parent, false);
            vh = new ViewHolder(v,this.mDataset);
        }
        else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.newsrowlayout, parent, false);
             vh = new ViewHolder(v,  this.mDataset);
        }
        this.index++;
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if(position % 2 == 0)
        {
            News news = (News) this.mDataset.get(position/2);
            View row = holder.itemView;
            TextView newsName = (TextView) row.findViewById(R.id.newstitle);
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMAN);
            Date result = new Date();
            try {
                 result =  df.parse(this.mDataset.get(position/2).getDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            DateFormat df2 = new SimpleDateFormat("EEEE - d.");
            newsName.setText(df2.format(result));
        }
        else {
            News news = (News) this.mDataset.get(position/2);
            View row = holder.itemView;
            TextView newsName = (TextView) row.findViewById(R.id.newsName);
            newsName.setText(this.mDataset.get(position/2).getContent().replace(";","\n-  ").replaceFirst("\n",""));
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size()*2;
    }
}

