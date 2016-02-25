package pardertec.de.medipad.fahrtenbuch;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import pardertec.de.medipad.R;
import pardertec.de.medipad.fahrtenbuch.model.Fahrt;
import pardertec.de.medipad.fahrtenbuch.model.Fahrtenzettel;

import pardertec.de.medipad.fahrtenbuch.FahrtenbuchTopSection.TopSectionListener;
import pardertec.de.medipad.fahrtenbuch.FahrtenbuchBottomSection.BottomSectionListener;
import pardertec.de.medipad.fahrtenbuch.FahrtenbuchMiddleSection.MiddleSectionListener;

public class FahrtenbuchActivity extends AppCompatActivity implements BottomSectionListener, TopSectionListener, MiddleSectionListener {

    public static final String TAG = "MediPad.Fahrtenbuch";


    private Fahrtenzettel fahrtenzettel;
    private FahrtenbuchBottomSection bottomSection;
    private FahrtenbuchMiddleSection middleSection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fahrtenbuch);

        bottomSection = (FahrtenbuchBottomSection) getFragmentManager().findFragmentById(R.id.bottom_fragment);
        middleSection = (FahrtenbuchMiddleSection) getFragmentManager().findFragmentById(R.id.middle_fragment);

        //just for testing purposes. should load existing data
        fahrtenzettel = getTestFahrtenzettel();

        updateFahrtenzettelView();
    }


    private void updateFahrtenzettelView() {
        middleSection.updateFahrtenzettelView(fahrtenzettel);
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


    ///////////////////////////////////////////
    //// BOTTOM SECTION LISTENER METHODS
    //////////////////////////////////////////

    @Override
    public void addFahrt() {
        Fahrt nextFahrt = new Fahrt();

        //TODO Eingabe von Werten; zwischenzeitlich Demo-Daten
        int kilometer = fahrtenzettel.getLastFahrt() == null? 0 : fahrtenzettel.getLastFahrt().getKilometerBeginn();
        nextFahrt.setKilometerBeginn(kilometer + 15);
        nextFahrt.setZiel(Integer.toString(nextFahrt.hashCode()));

        this.fahrtenzettel.addFahrt(nextFahrt);

        this.updateFahrtenzettelView();
    }

    @Override
    public void departNow() {
        this.fahrtenzettel.getLastFahrt().setAbfahrtszeit(System.currentTimeMillis());
        this.updateFahrtenzettelView();
    }

    @Override
    public void arriveNow() {
        this.fahrtenzettel.getLastFahrt().setAnkunftszeit(System.currentTimeMillis());
        this.updateFahrtenzettelView();
    }


    ///////////////////////////////////////////
    //// TOP SECTION LISTENER METHODS
    //////////////////////////////////////////

    @Override
    public void clearSheet() {
        this.fahrtenzettel = new Fahrtenzettel();
        this.updateFahrtenzettelView();
    }

    @Override
    public void modifySheetData() {
        //TODO open new activity
    }

    @Override
    public void playHymn() {
        //TODO play music
    }


    ///////////////////////////////////////////
    //// MIDDLE SECTION LISTENER METHODS
    //////////////////////////////////////////

    @Override
    public void activateBottomButtons() {
        Fahrt lastFahrt = fahrtenzettel.getLastFahrt();
        bottomSection.enableButtonsDependingOnLastFahrt(lastFahrt);
    }
}
