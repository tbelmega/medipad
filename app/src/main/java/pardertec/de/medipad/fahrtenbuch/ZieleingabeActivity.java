package pardertec.de.medipad.fahrtenbuch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import pardertec.de.medipad.R;

public class ZieleingabeActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zieleingabe_activity);

   }

    ///////////////////////////////////////////
    //// INTENT METHODS
    //////////////////////////////////////////


    public void addFahrt(View view) {
        Intent intent = new Intent(this, FahrtenbuchActivity.class);

        EditText kilometerInput = (EditText)findViewById(R.id.kilometerstand_edit);
        int eingegebenerKilometerstand = Integer.parseInt(kilometerInput.getText().toString());
        intent.putExtra("kilometerstand", eingegebenerKilometerstand);

        EditText adresseInput = (EditText)findViewById(R.id.adresse_edit);
        String adresse = adresseInput.getText().toString();
        intent.putExtra("adresse", adresse);

        startActivity(intent);
    }

}
