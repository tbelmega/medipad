package de.pardertec.medipad.fahrtenbuch.input;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import de.pardertec.medipad.R;
import de.pardertec.medipad.fahrtenbuch.FahrtenbuchActivity;

import static de.pardertec.medipad.MedipadApplication.EXTRA_ADRESSE;
import static de.pardertec.medipad.MedipadApplication.*;

public class ZieleingabeActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zieleingabe);
   }

    ///////////////////////////////////////////
    //// INTENT METHODS
    //////////////////////////////////////////

    public void addFahrt(View view) {
        //Read adresse input
        EditText adresseInput = (EditText)findViewById(R.id.adresse_edit);

        //Read kilometer input
        EditText kilometerInput = (EditText)findViewById(R.id.kilometerstand_edit);

        if (notEmpty(adresseInput, kilometerInput)) {
            startFahrtenbuchActivityWithData(adresseInput, kilometerInput);
        }
    }

    private boolean notEmpty(EditText adresseInput, EditText kilometerInput) {
        return !kilometerInput.getText().toString().equals("") && !adresseInput.getText().toString().equals("");
    }

    private void startFahrtenbuchActivityWithData(EditText adresseInput, EditText kilometerInput) {
        //parse input
        String adresse = adresseInput.getText().toString();
        int eingegebenerKilometerstand = Integer.parseInt(kilometerInput.getText().toString());

        //start fahrtenbuch activity with data
        Intent intent = new Intent(this, FahrtenbuchActivity.class);
        intent.putExtra(EXTRA_INTENTION, "addFahrt");
        intent.putExtra(EXTRA_KILOMETERSTAND, eingegebenerKilometerstand);
        intent.putExtra(EXTRA_ADRESSE, adresse);
        startActivity(intent);
    }

    public  void abort(View view) {
        Intent intent = new Intent(this, FahrtenbuchActivity.class);
        startActivity(intent);
    }

}
