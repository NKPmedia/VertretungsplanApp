package de.nkp_media.vertretungsplanappandroid;

import java.util.Date;

public class Ausfall2 {

    int stunde;
    private String lehrer;
    private String vertretung;
    private Date zieldatum;
    private String zieldatumString;
    private String fach;
    private String zielfach;
    private String raum;
    private boolean entfall;

    public int getStunde() {
        return stunde;
    }

    public void setStunde(int stunde) {
        this.stunde = stunde;
    }

    public String getLehrer() {
        return lehrer;
    }

    public void setLehrer(String lehrer) {
        this.lehrer = lehrer;
    }

    public String getVertretung() {
        return vertretung;
    }

    public void setVertretung(String vertretung) {
        this.vertretung = vertretung;
    }

    public Date getZieldatum() {
        return zieldatum;
    }

    public void setZieldatum(Date zieldatum) {
        this.zieldatum = zieldatum;
    }

    public String getZieldatumString() {
        return zieldatumString;
    }

    public void setZieldatumString(String zieldatum) {
        this.zieldatumString = zieldatum;
    }

    public String getFach() {
        return fach;
    }

    public void setFach(String fach) {
        this.fach = fach;
    }

    public String getZielfach() {
        return zielfach;
    }

    public void setZielfach(String zielfach) {
        this.zielfach = zielfach;
    }

    public String getRaum() {
        return raum;
    }

    public void setRaum(String raum) {
        this.raum = raum;
    }

    public boolean isEntfall() {
        return entfall;
    }

    public void setEntfall(boolean entfall) {
        this.entfall = entfall;
    }
}
