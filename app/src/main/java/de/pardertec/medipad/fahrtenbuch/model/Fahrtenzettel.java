package de.pardertec.medipad.fahrtenbuch.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Thiemo on 23.02.2016.
 */
public class Fahrtenzettel {
    private String kennzeichen;
    private String fahrer;
    private String sani;
    private List<Fahrt> fahrten = new LinkedList<>();

    public void setKennzeichen(String kennzeichen) {
        this.kennzeichen = kennzeichen;
    }

    public void setFahrer(String fahrer) {
        this.fahrer = fahrer;
    }

    public void setSani(String sani) {
        this.sani = sani;
    }

    public String getKennzeichen() {
        return kennzeichen;
    }

    public String getFahrer() {
        return fahrer;
    }

    public String getSani() {
        return sani;
    }

    public void addFahrt(Fahrt fahrt) {
        this.fahrten.add(fahrt);
    }

    public List<Fahrt> getFahrten() {
        Collections.sort(fahrten);
        return new LinkedList<>(fahrten);
    }

    public Fahrt getLastFahrt() {
        if (this.fahrten.size() == 0) {
            return null;
        }

        Collections.sort(fahrten);
        return this.fahrten.get(this.fahrten.size() - 1);
    }
}
