package de.pardertec.medipad.fahrtenbuch.model;

import java.util.UUID;

/**
 * Created by Thiemo on 23.02.2016.
 */
public class Fahrt implements Comparable<Fahrt> {


    private String ziel = "";
    private Integer kilometerBeginn = 0;
    private Long abfahrtszeit = null;
    private Long ankunftszeit = null;
    private boolean sonderrechte = false;
    private boolean inf = false;
    public final UUID uuid;

    public Fahrt() {
        this.uuid = UUID.randomUUID();
    }

    public Fahrt(String uuid) {
        this.uuid = UUID.fromString(uuid);
    }

    public void setZiel(String ziel) {
        this.ziel = ziel;
    }

    public void setKilometerBeginn(int kilometerBeginn) {
        this.kilometerBeginn = kilometerBeginn;
    }

    public int getKilometerBeginn() {
        return kilometerBeginn;
    }

    public String getZiel() {
        return ziel;
    }

    public void setAbfahrtszeit(long abfahrtszeit) {
        this.abfahrtszeit = abfahrtszeit;
    }

    public void setAnkunftszeit(long ankunftszeit) {
        this.ankunftszeit = ankunftszeit;
    }

    public Long getAbfahrtszeit() {
        return abfahrtszeit;
    }

    public Long getAnkunftszeit() {
        return ankunftszeit;
    }

    public void setSonderrechte(boolean sonderrechte) {
        this.sonderrechte = sonderrechte;
    }

    public void setInf(boolean inf) {
        this.inf = inf;
    }

    public boolean getSonderrechte() {
        return sonderrechte;
    }

    public boolean getInf() {
        return inf;
    }


    /**
     * Fahrten are ordered by their kilometer property.
     * @param another
     * @return
     */
    @Override
    public int compareTo(Fahrt another) {
        return this.kilometerBeginn.compareTo(another.kilometerBeginn);
    }

    public boolean arrived() {
        return this.ankunftszeit != null;
    }

    public boolean departed() {
        return this.abfahrtszeit != null && this.ankunftszeit == null;
    }

}
