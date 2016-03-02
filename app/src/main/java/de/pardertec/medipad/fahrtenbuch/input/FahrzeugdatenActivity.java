package de.pardertec.medipad.fahrtenbuch.input;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import de.pardertec.medipad.R;
import de.pardertec.medipad.fahrtenbuch.FahrtenbuchActivity;

import static de.pardertec.medipad.MedipadApplication.*;

/**
 * Created by Thiemo on 02.03.2016.
 */
public class FahrzeugdatenActivity extends Activity {


    EditText kennzeichenInput;
    EditText fahrerInput;
    EditText saniInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fahrzeugdaten);

        kennzeichenInput = (EditText)findViewById(R.id.kennzeichen_edit);
        kennzeichenInput.setText(getCurrentFahrtenzettel().getKennzeichen());

        fahrerInput = (EditText)findViewById(R.id.fahrer_edit);
        fahrerInput.setText(getCurrentFahrtenzettel().getFahrer());

        saniInput = (EditText)findViewById(R.id.sani_edit);
        saniInput.setText(getCurrentFahrtenzettel().getSani());
    }

    public void submitData(View view) {
        Intent intent = new Intent(this, FahrtenbuchActivity.class);

        String kennzeichen = kennzeichenInput.getText().toString();
        getCurrentFahrtenzettel().setKennzeichen(kennzeichen);

        String fahrer = fahrerInput.getText().toString();
        getCurrentFahrtenzettel().setFahrer(fahrer);

        String sani = saniInput.getText().toString();
        getCurrentFahrtenzettel().setSani(sani);

        startActivity(intent);
    }

    public void abort(View view) {
        Intent intent = new Intent(this, FahrtenbuchActivity.class);
        startActivity(intent);
    }
}
