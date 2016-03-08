package de.pardertec.medipad;

import android.app.Application;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import de.pardertec.medipad.fahrtenbuch.model.Fahrt;
import de.pardertec.medipad.fahrtenbuch.model.Fahrtenbuch;
import de.pardertec.medipad.fahrtenbuch.model.Fahrtenzettel;

/**
 * Created by Thiemo on 02.03.2016.
 */
public class MedipadApplication extends Application {

    private static final MedipadApplication instance = new MedipadApplication();

    public static final String TAG = "MediPad.Fahrtenbuch";
    public static final String EXTRA_UUID = "uuid";
    public static final String EXTRA_KILOMETERSTAND = "kilometerstand";
    public static final String EXTRA_ADRESSE = "adresse";
    public static final String EXTRA_ABFAHRT = "abfahrtszeit";
    public static final String EXTRA_ANKUNFT = "ankunftszeit";

    public static final String EXTRA_KENNZEICHEN = "kennzeichen";
    public static final String EXTRA_FAHRER = "fahrer";
    public static final String EXTRA_SANI = "sani";

    public static final String EXTRA_INTENTION = "intention";

    public static final DateFormat GERMAN_STD_DATE_FORMAT = new SimpleDateFormat("dd.MM.yy HH:mm", Locale.GERMANY);

    private Fahrtenbuch theFahrtenbuch = new Fahrtenbuch();


    public MedipadApplication() {
        //TODO: Remove dummy data, load actual data
        this.theFahrtenbuch.addFahrtenzettel(getTestFahrtenzettel());
    }

    public static Fahrtenbuch getFahrtenbuch() {
        return getInstance().theFahrtenbuch;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    public static MedipadApplication getInstance(){
        return instance;
    }

    public static Fahrtenzettel getCurrentFahrtenzettel(){
        return getInstance().theFahrtenbuch.getLastFahrtenzettel();
    }

    public static void startNewFahrtenZettel() {
        getInstance().theFahrtenbuch.startFahrtenzettel();
    }

    ///////////////////////////////////////////
    //// DUMMY DATA
    //////////////////////////////////////////

    public static Fahrtenzettel getTestFahrtenzettel() {
        Fahrt ersteFahrt = new Fahrt();
        ersteFahrt.setAbfahrtszeit(System.currentTimeMillis() - 1000000);
        ersteFahrt.setAnkunftszeit(System.currentTimeMillis());
        ersteFahrt.setZiel("Wichtelwerkstatt\n96930 Polarkreis\nFinnland");
        ersteFahrt.setSonderrechte(true);
        ersteFahrt.setKilometerBeginn(42);

        Fahrt zweiteFahrt = new Fahrt();
        zweiteFahrt.setAbfahrtszeit(System.currentTimeMillis());
        zweiteFahrt.setAnkunftszeit(System.currentTimeMillis() + 1000000);
        zweiteFahrt.setZiel("Dr. K. Ruprecht\nDraußen vom Walde\n10459 Neubrandenburg");
        zweiteFahrt.setKilometerBeginn(297);
        zweiteFahrt.setInf(true);

        Fahrt dritteFahrt = new Fahrt();
        dritteFahrt.setAbfahrtszeit(System.currentTimeMillis() + 1000000);
        dritteFahrt.setZiel("Nach Hause");
        dritteFahrt.setKilometerBeginn(1001);

        Fahrtenzettel testFahrtenzettel = new Fahrtenzettel();
        testFahrtenzettel.setFahrer("Rudolf Rentier");
        testFahrtenzettel.setSani("Santa Claus");
        testFahrtenzettel.setKennzeichen("M-ERRY XMAS");

        testFahrtenzettel.addFahrt(ersteFahrt);
        testFahrtenzettel.addFahrt(dritteFahrt);
        testFahrtenzettel.addFahrt(zweiteFahrt);

        return testFahrtenzettel;
    }


}
