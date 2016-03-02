package de.pardertec.medipad.fahrtenbuch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import de.pardertec.medipad.R;

import static de.pardertec.medipad.MedipadApplication.EXTRA_ADRESSE;
import static de.pardertec.medipad.MedipadApplication.EXTRA_KILOMETERSTAND;

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
        Intent intent = new Intent(this, FahrtenbuchActivity.class);

        EditText kilometerInput = (EditText)findViewById(R.id.kilometerstand_edit);
        int eingegebenerKilometerstand = Integer.parseInt(kilometerInput.getText().toString());
        intent.putExtra(EXTRA_KILOMETERSTAND, eingegebenerKilometerstand);

        EditText adresseInput = (EditText)findViewById(R.id.adresse_edit);
        String adresse = adresseInput.getText().toString();
        intent.putExtra(EXTRA_ADRESSE, adresse);

        startActivity(intent);
    }

}
