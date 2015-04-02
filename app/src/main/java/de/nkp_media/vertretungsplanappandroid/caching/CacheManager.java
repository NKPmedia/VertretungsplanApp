package de.nkp_media.vertretungsplanappandroid.caching;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import de.nkp_media.vertretungsplanappandroid.Ausfall2;
import de.nkp_media.vertretungsplanappandroid.FeedUpdate;
import de.nkp_media.vertretungsplanappandroid.NavigationDrawerFragment;

public class CacheManager {

    private Context androidContext;
    private NavigationDrawerFragment fragment;

    private FullCache cache = null;
    private File cacheFile = null;

    public CacheManager(Context context, NavigationDrawerFragment fragment, ArrayList<Ausfall2> initialData) {
        this.androidContext = context;
        this.fragment = fragment;
        this.init(initialData);
    }

    private void init(ArrayList<Ausfall2> initialData) {
        this.cacheFile = new File(androidContext.getCacheDir(), "cache.txt");
        if (!cacheFile.exists()) {
            try {
                cacheFile.createNewFile();
                this.createCache(initialData);
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void createCache(ArrayList<Ausfall2> initialData) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(cacheFile));
            out.writeObject(new FullCache(initialData));
            out.close();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public ArrayList<Ausfall2> getContent() {
        if (cache == null) {
            try {
                ObjectInputStream in = new ObjectInputStream(new FileInputStream(cacheFile));
                this.cache = (FullCache) in.readObject();
                if (!this.cache.isValid()) {
                    this.refresh();
                }
                return this.cache.getContent();
            }
            catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
                return null;
            }
        }
        else {
            return cache.getContent();
        }
    }

    private void refresh() {
        this.fragment.updateFeed();
        try {
            this.wait();

        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
