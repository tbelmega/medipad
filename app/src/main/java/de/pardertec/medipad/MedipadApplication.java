package de.pardertec.medipad;

import android.app.Application;
import android.widget.Toast;

import de.pardertec.medipad.fahrtenbuch.model.Fahrt;
import de.pardertec.medipad.fahrtenbuch.model.Fahrtenzettel;

/**
 * Created by Thiemo on 02.03.2016.
 */
public class MedipadApplication extends Application {

    public static final String TAG = "MediPad.Fahrtenbuch";
    public static final String EXTRA_KILOMETERSTAND = "kilometerstand";
    public static final String EXTRA_ADRESSE = "adresse";

    @Override
    public void onCreate() {
        super.onCreate();
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
