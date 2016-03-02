package de.pardertec.medipad.fahrtenbuch;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import de.pardertec.medipad.R;
import de.pardertec.medipad.fahrtenbuch.FahrtenbuchBottomSection.BottomSectionListener;
import de.pardertec.medipad.fahrtenbuch.FahrtenbuchMiddleSection.MiddleSectionListener;
import de.pardertec.medipad.fahrtenbuch.FahrtenbuchTopSection.TopSectionListener;
import de.pardertec.medipad.fahrtenbuch.model.Fahrt;
import de.pardertec.medipad.fahrtenbuch.model.Fahrtenzettel;

import static de.pardertec.medipad.MedipadApplication.EXTRA_ADRESSE;
import static de.pardertec.medipad.MedipadApplication.EXTRA_KILOMETERSTAND;
import static de.pardertec.medipad.MedipadApplication.*;

public class FahrtenbuchActivity extends AppCompatActivity implements BottomSectionListener, TopSectionListener, MiddleSectionListener {


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

        int kilometer = intent.getIntExtra(EXTRA_KILOMETERSTAND, 0);
        String adresse = intent.getStringExtra(EXTRA_ADRESSE);

        if (adresse != null) addFahrt(kilometer, adresse);
    }


    private void updateFahrtenzettelView() {
        middleSection.updateFahrtenzettelView(fahrtenzettel);
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
