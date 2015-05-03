package de.nkp_media.vertretungsplanappandroid.SyncAdapter;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;

/**
 * Created by paul on 06.04.15.
 */
public class FeedSyncAdapter extends AbstractThreadedSyncAdapter {

    public FeedSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        System.out.println("Started Sync Adapter");
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        try {
            System.out.println("Perform Sync");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}