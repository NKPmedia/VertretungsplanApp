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
import de.nkp_media.vertretungsplanappandroid.NavigationDrawerFragment;

public class CacheManager {

    private Context androidContext;

    private FullCache cache = null;
    private File cacheFile = null;
    private ArrayList<Ausfall2> ausfallList;

    public CacheManager(Context mainActivity) {
        this.androidContext = mainActivity;
    }

    private void init() {
        this.cacheFile = new File(androidContext.getCacheDir(), "cache.txt");
        if (!cacheFile.exists()) {
            try {
                cacheFile.createNewFile();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void createCache(ArrayList<Ausfall2> initialData) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(cacheFile));
            this.cache = new FullCache(initialData);
            out.writeObject(this.cache);
            out.close();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void refresh(NavigationDrawerFragment mNavigationDrawerFragment) {
        mNavigationDrawerFragment.updateFeed();
    }

    public void updateCach(ArrayList<Ausfall2> ausfallList) {
        System.out.println("Update Cach");
        this.init();
        this.createCache(ausfallList);
    }

    public ArrayList<Ausfall2> getAusfallList(NavigationDrawerFragment mNavigationDrawerFragment) {
        if (cache == null) {
            System.out.println("Get cach File");
            try {
                this.cacheFile = new File(androidContext.getCacheDir(), "cache.txt");
                if (cacheFile.exists()) {
                    ObjectInputStream in = new ObjectInputStream(new FileInputStream(this.cacheFile));
                    this.cache = (FullCache) in.readObject();
                    if (!this.cache.isValid()) {
                        this.refresh(mNavigationDrawerFragment);
                    }
                    return this.cache.getContent();
                }
                else
                {
                    System.out.println("Cach file is too old");
                    this.refresh(mNavigationDrawerFragment);
                }
            }
            catch (IOException | ClassNotFoundException ex) {
                this.refresh(mNavigationDrawerFragment);
                System.out.println("Cant read cach file");
                ex.printStackTrace();
                return null;
            }
        }
        else if (this.cache.isValid()) {
            System.out.println("Get Cach");
            return cache.getContent();
        }
        else
        {
            System.out.println("Cach is too old");
            this.refresh(mNavigationDrawerFragment);
        }
        return ausfallList;
    }
}
