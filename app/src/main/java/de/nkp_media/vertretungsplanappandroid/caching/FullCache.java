package de.nkp_media.vertretungsplanappandroid.caching;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import de.nkp_media.vertretungsplanappandroid.Ausfall2;

public class FullCache implements Serializable {

    private ArrayList<Ausfall2> ausfaelle;
    private Date generationDate;

    public FullCache(ArrayList<Ausfall2> ausfaelle) {
        this.ausfaelle = ausfaelle;
        this.generationDate = new Date(System.currentTimeMillis());
    }

    public ArrayList<Ausfall2> getContent() {
        return ausfaelle;
    }

    public void update() {
        this.generationDate = new Date(System.currentTimeMillis());
    }

    public boolean isValid() {
        return (generationDate.getTime() - System.currentTimeMillis()) < 3600000;
    }
}
