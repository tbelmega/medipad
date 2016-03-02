package pardertec.de.medipad.fahrtenbuch;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import pardertec.de.medipad.R;
import pardertec.de.medipad.fahrtenbuch.model.Fahrt;
import pardertec.de.medipad.fahrtenbuch.model.Fahrtenzettel;

import pardertec.de.medipad.fahrtenbuch.FahrtenbuchTopSection.TopSectionListener;
import pardertec.de.medipad.fahrtenbuch.FahrtenbuchBottomSection.BottomSectionListener;
import pardertec.de.medipad.fahrtenbuch.FahrtenbuchMiddleSection.MiddleSectionListener;

public class FahrtenbuchActivity extends AppCompatActivity implements BottomSectionListener, TopSectionListener, MiddleSectionListener {

    public static final String TAG = "MediPad.Fahrtenbuch";

    //just for testing purposes. should load existing data
    private Fahrtenzettel fahrtenzettel = getTestFahrtenzettel();

    private FahrtenbuchBottomSection bottomSection;
    private FahrtenbuchMiddleSection middleSection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fahrtenbuch);

        bottomSection = (FahrtenbuchBottomSection) getFragmentManager().findFragmentById(R.id.bottom_fragment);
        middleSection = (FahrtenbuchMiddleSection) getFragmentManager().findFragmentById(R.id.middle_fragment);

        updateFahrtenzettelView();
    }


    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();

        int kilometer = intent.getIntExtra("kilometerstand", 0);
        String adresse = intent.getStringExtra("adresse");

        if (adresse != null) addFahrt(kilometer, adresse);
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

    public void addFahrt(int kilometer, String adresse) {
        Fahrt nextFahrt = new Fahrt();

        nextFahrt.setKilometerBeginn(kilometer);
        nextFahrt.setZiel(adresse);

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


    ///////////////////////////////////////////
    //// INTENT METHODS
    //////////////////////////////////////////

    public void sendMessage(View view) {
        Intent intent = new Intent(this, ZieleingabeActivity.class);
        startActivity(intent);
    }

}
