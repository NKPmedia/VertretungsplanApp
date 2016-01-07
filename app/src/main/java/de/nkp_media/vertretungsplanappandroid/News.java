package de.nkp_media.vertretungsplanappandroid;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by paul on 30.08.15.
 */
public class News implements Serializable{
    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return date;
    }

    public Date getZieldatum() {
        return zieldatum;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setZieldatum(Date zieldatum) {
        this.zieldatum = zieldatum;
    }

    private String title = "";
    private String content = "";
    private String date = "";
    private Date zieldatum;
}
