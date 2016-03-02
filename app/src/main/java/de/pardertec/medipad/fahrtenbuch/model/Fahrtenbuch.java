package de.pardertec.medipad.fahrtenbuch.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Thiemo on 02.03.2016.
 */
public class Fahrtenbuch {

    private final List<Fahrtenzettel> fahrtenzettel;

    public Fahrtenbuch() {
        this.fahrtenzettel = new LinkedList<>();
        this.fahrtenzettel.add(new Fahrtenzettel());
    }

    public Fahrtenzettel getLastFahrtenzettel() {
        if (fahrtenzettel.size() == 0) return null;
        return this.fahrtenzettel.get(fahrtenzettel.size() - 1);
    }

    public void addFahrtenzettel(Fahrtenzettel fahrtenzettel) {
        this.fahrtenzettel.add(fahrtenzettel);
    }

    public void startFahrtenzettel() {
        this.addFahrtenzettel(new Fahrtenzettel());
    }

    public int size() {
        return fahrtenzettel.size();
    }
}
