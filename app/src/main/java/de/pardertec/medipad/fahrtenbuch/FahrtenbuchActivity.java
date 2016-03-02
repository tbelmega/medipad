package de.pardertec.medipad.fahrtenbuch;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import de.pardertec.medipad.MedipadApplication;
import de.pardertec.medipad.R;
import de.pardertec.medipad.fahrtenbuch.FahrtenbuchBottomSection.BottomSectionListener;
import de.pardertec.medipad.fahrtenbuch.FahrtenbuchTopSection.TopSectionListener;
import de.pardertec.medipad.fahrtenbuch.model.Fahrt;
import de.pardertec.medipad.fahrtenbuch.model.Fahrtenzettel;

import static de.pardertec.medipad.MedipadApplication.EXTRA_ADRESSE;
import static de.pardertec.medipad.MedipadApplication.EXTRA_KILOMETERSTAND;
import static de.pardertec.medipad.MedipadApplication.getCurrentFahrtenzettel;

public class FahrtenbuchActivity extends AppCompatActivity implements BottomSectionListener, TopSectionListener {

    private FahrtenbuchTopSection topSection;
    private FahrtenbuchMiddleSection middleSection;
    private FahrtenbuchBottomSection bottomSection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fahrtenbuch);

        topSection = (FahrtenbuchTopSection) getFragmentManager().findFragmentById(R.id.top_fragment);
        middleSection = (FahrtenbuchMiddleSection) getFragmentManager().findFragmentById(R.id.middle_fragment);
        bottomSection = (FahrtenbuchBottomSection) getFragmentManager().findFragmentById(R.id.bottom_fragment);

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
        Fahrtenzettel fahrtenzettel = getCurrentFahrtenzettel();
        if (fahrtenzettel != null) {
            topSection.setFahrtenzettelData(fahrtenzettel);
            middleSection.updateFahrtenListView(fahrtenzettel);
            bottomSection.enableButtonsDependingOnLastFahrt(fahrtenzettel.getLastFahrt());
        }

    }

    ///////////////////////////////////////////
    //// BOTTOM SECTION LISTENER METHODS
    //////////////////////////////////////////

    public void addFahrt(int kilometer, String adresse) {
        Fahrtenzettel fahrtenzettel = getCurrentFahrtenzettel();
        Fahrt lastFahrt = fahrtenzettel.getLastFahrt();

        if (kilometer > lastFahrt.getKilometerBeginn()) {

            Fahrt nextFahrt = new Fahrt();

            nextFahrt.setKilometerBeginn(kilometer);
            nextFahrt.setZiel(adresse);

            fahrtenzettel.addFahrt(nextFahrt);

            this.updateFahrtenzettelView();
        }
    }

    @Override
    public void departNow() {
        getCurrentFahrtenzettel().getLastFahrt().setAbfahrtszeit(System.currentTimeMillis());
        this.updateFahrtenzettelView();
    }

    @Override
    public void arriveNow() {
        getCurrentFahrtenzettel().getLastFahrt().setAnkunftszeit(System.currentTimeMillis());
        this.updateFahrtenzettelView();
    }


    ///////////////////////////////////////////
    //// TOP SECTION LISTENER METHODS
    //////////////////////////////////////////

    @Override
    public void clearSheet() {
        MedipadApplication.startNewFahrtenZettel();

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
    //// INTENT METHODS
    //////////////////////////////////////////

    public void sendMessage(View view) {
        Intent intent = new Intent(this, ZieleingabeActivity.class);
        startActivity(intent);
    }

}
