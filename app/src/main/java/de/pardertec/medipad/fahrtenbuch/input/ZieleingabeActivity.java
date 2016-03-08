package de.pardertec.medipad.fahrtenbuch.input;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.UUID;

import de.pardertec.medipad.R;
import de.pardertec.medipad.fahrtenbuch.FahrtenbuchActivity;

import static de.pardertec.medipad.MedipadApplication.EXTRA_ABFAHRT;
import static de.pardertec.medipad.MedipadApplication.EXTRA_ADRESSE;
import static de.pardertec.medipad.MedipadApplication.EXTRA_ANKUNFT;
import static de.pardertec.medipad.MedipadApplication.EXTRA_INTENTION;
import static de.pardertec.medipad.MedipadApplication.EXTRA_KILOMETERSTAND;
import static de.pardertec.medipad.MedipadApplication.EXTRA_UUID;

public class ZieleingabeActivity extends Activity {


    private EditText adresseInput;
    private EditText kilometerInput;
    private String currentUuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zieleingabe);

        adresseInput = (EditText)findViewById(R.id.adresse_edit);
        kilometerInput = (EditText)findViewById(R.id.kilometerstand_edit);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent i = getIntent();
        String intent = getIntent().getStringExtra(EXTRA_INTENTION);

        if ("editFahrt".equals(intent)){
            currentUuid = i.getStringExtra(EXTRA_UUID);
            String adresse = i.getStringExtra(EXTRA_ADRESSE);
            int kilometerstand = i.getIntExtra(EXTRA_KILOMETERSTAND, -1);
            long abfahrt = i.getLongExtra(EXTRA_ABFAHRT, -1);
            long ankunft = i.getLongExtra(EXTRA_ANKUNFT, -1);

            if (adresse != null) adresseInput.setText(adresse);
            if (kilometerstand > -1) kilometerInput.setText(String.valueOf(kilometerstand));

            findViewById(R.id.btn_add_fahrt).setEnabled(false);
            findViewById(R.id.btn_edit_fahrt).setEnabled(true);
        } else {
            findViewById(R.id.btn_add_fahrt).setEnabled(true);
            findViewById(R.id.btn_edit_fahrt).setEnabled(false);
        }
    }

    ///////////////////////////////////////////
    //// INTENT METHODS
    //////////////////////////////////////////

    public void addFahrt(View view) {
        if (editTextFieldsHaveData()) {
            startFahrtenbuchActivityWithData("addFahrt", UUID.randomUUID().toString());
        }
    }

    public void editFahrt(View view) {
        if (editTextFieldsHaveData()) {
            startFahrtenbuchActivityWithData("editFahrt", currentUuid);
        }
    }

    private boolean editTextFieldsHaveData() {
        return !kilometerInput.getText().toString().isEmpty() && !adresseInput.getText().toString().isEmpty();
    }

    private void startFahrtenbuchActivityWithData(String intention, String currentFahrtId) {
        //parse input
        String adresse = adresseInput.getText().toString();
        int eingegebenerKilometerstand = Integer.parseInt(kilometerInput.getText().toString());

        //start fahrtenbuch activity with data
        Intent intent = new Intent(this, FahrtenbuchActivity.class);
        intent.putExtra(EXTRA_INTENTION, intention);
        intent.putExtra(EXTRA_UUID, currentFahrtId);
        intent.putExtra(EXTRA_KILOMETERSTAND, eingegebenerKilometerstand);
        intent.putExtra(EXTRA_ADRESSE, adresse);
        startActivity(intent);
    }

    public  void abort(View view) {
        Intent intent = new Intent(this, FahrtenbuchActivity.class);
        startActivity(intent);
    }

}
