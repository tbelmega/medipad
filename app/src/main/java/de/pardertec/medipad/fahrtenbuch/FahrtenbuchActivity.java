package de.pardertec.medipad.fahrtenbuch;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import de.pardertec.medipad.MedipadApplication;
import de.pardertec.medipad.R;
import de.pardertec.medipad.fahrtenbuch.FahrtenbuchBottomSection.BottomSectionListener;
import de.pardertec.medipad.fahrtenbuch.FahrtenbuchTopSection.TopSectionListener;
import de.pardertec.medipad.fahrtenbuch.input.FahrzeugdatenActivity;
import de.pardertec.medipad.fahrtenbuch.input.ZieleingabeActivity;
import de.pardertec.medipad.fahrtenbuch.model.Fahrt;
import de.pardertec.medipad.fahrtenbuch.model.FahrtenListAdapter;
import de.pardertec.medipad.fahrtenbuch.model.Fahrtenzettel;

import static de.pardertec.medipad.MedipadApplication.EXTRA_ADRESSE;
import static de.pardertec.medipad.MedipadApplication.*;
import static de.pardertec.medipad.MedipadApplication.getCurrentFahrtenzettel;

public class FahrtenbuchActivity extends AppCompatActivity implements BottomSectionListener, TopSectionListener, FahrtenListAdapter.FahrtCardListener {


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
    }


    @Override
    protected void onResume() {
        super.onResume();
        String intent = getIntent().getStringExtra(EXTRA_INTENTION);

        if (intent != null) switch (intent) {
            case "addFahrt" : addFahrt(getIntent()); break;
            case "editFahrt" : editFahrt(getIntent()); break;
        }
        updateFahrtenzettelView();
    }



    private void updateFahrtenzettelView() {
        Fahrtenzettel fahrtenzettel = getCurrentFahrtenzettel();
        if (fahrtenzettel != null) {
            topSection.setFahrtenzettelData(fahrtenzettel);
            middleSection.updateFahrtenListView(fahrtenzettel);
            bottomSection.enableButtonsDependingOnLastFahrt(fahrtenzettel.getLastFahrt());
        }

    }

    public void addFahrt(Intent intent) {
        int kilometer = intent.getIntExtra(EXTRA_KILOMETERSTAND, 0);
        String adresse = intent.getStringExtra(EXTRA_ADRESSE);

        Fahrtenzettel fahrtenzettel = getCurrentFahrtenzettel();
        Fahrt lastFahrt = fahrtenzettel.getLastFahrt();

        if (lastFahrt == null || kilometer > lastFahrt.getKilometerBeginn()) {
            String uuid = intent.getStringExtra(EXTRA_UUID);
            Fahrt nextFahrt = new Fahrt(uuid);
            nextFahrt.setKilometerBeginn(kilometer);
            nextFahrt.setZiel(adresse);

            fahrtenzettel.addFahrt(nextFahrt);
        } else if ( kilometer < lastFahrt.getKilometerBeginn()){
            Toast.makeText(this, "Kilometerstand muss höher sein als bei der letzten Fahrt!", Toast.LENGTH_LONG).show();
        }
    }

    private void editFahrt(Intent intent) {
        String uuid = intent.getStringExtra(EXTRA_UUID);
        Fahrt f = getCurrentFahrtenzettel().getFahrtForId(uuid);
        int kilometer = intent.getIntExtra(EXTRA_KILOMETERSTAND, 0);
        String adresse = intent.getStringExtra(EXTRA_ADRESSE);

        f.setKilometerBeginn(kilometer);
        f.setZiel(adresse);
    }

    ///////////////////////////////////////////
    //// BOTTOM SECTION LISTENER METHODS
    //////////////////////////////////////////

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
        MedipadApplication.getInstance().storeCurrentFahrtenzettelAsPdf(findViewById(R.id.fahrtenbuch_layout));
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

    public void openNewZieleingabe(View view) {
        Intent intent = new Intent(this, ZieleingabeActivity.class);
        startActivity(intent);
    }


    public void editFahrzeugdaten(View view) {
        Intent intent = new Intent(this, FahrzeugdatenActivity.class);
        startActivity(intent);
    }

    @Override
    public void cardClicked(Fahrt f) {
        Intent intent = new Intent(this, ZieleingabeActivity.class);
        intent.putExtra(EXTRA_INTENTION, "editFahrt");
        intent.putExtra(EXTRA_UUID, f.uuid.toString());
        intent.putExtra(EXTRA_ADRESSE, f.getZiel());
        intent.putExtra(EXTRA_KILOMETERSTAND, f.getKilometerBeginn());
        intent.putExtra(EXTRA_ABFAHRT, f.getAbfahrtszeit());
        intent.putExtra(EXTRA_ANKUNFT, f.getAnkunftszeit());
        startActivity(intent);
    }
}
