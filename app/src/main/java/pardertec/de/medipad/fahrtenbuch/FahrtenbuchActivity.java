package pardertec.de.medipad.fahrtenbuch;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pardertec.de.medipad.R;
import pardertec.de.medipad.fahrtenbuch.FahrtenbuchTopSection.TopSectionListener;
import pardertec.de.medipad.fahrtenbuch.model.Fahrt;
import pardertec.de.medipad.fahrtenbuch.model.Fahrtenzettel;
import static pardertec.de.medipad.fahrtenbuch.FahrtenbuchBottomSection.BottomSectionListener;

public class FahrtenbuchActivity extends AppCompatActivity implements BottomSectionListener, TopSectionListener {

    public static final String TAG = "MediPad.Fahrtenbuch";

    public static final int MAX_ITEMS_PER_LINE = 20;
    public static final int SONDERRECHTE_INDEX = 5;
    public static final int INF_INDEX = 6;
    private Map<Integer, Fahrt> lineTable = new HashMap<>();

    //margin pixels
    final int LEFT = 2;
    final int TOP = 10;
    final int RIGHT = 2;
    final int BOTTOM = 10;
    public static final DateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat();
    private Fahrtenzettel fahrtenzettel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fahrtenbuch);

        //just for testing purposes. should load existing data
        fahrtenzettel = getTestFahrtenzettel();

        updateFahrtenzettelView();
    }

    private void activateBottomButtons() {
        Fahrt lastFahrt = fahrtenzettel.getLastFahrt();

        FahrtenbuchBottomSection bottomSection = (FahrtenbuchBottomSection) getFragmentManager().findFragmentById(R.id.bottom_fragment);
        bottomSection.enableButtonsDependingOnLastFahrt(lastFahrt);
    }

    /**
     * Dynamicly creates lines for the entries of a Fahrtenzettel
     */
    private void updateFahrtenzettelView() {
        RelativeLayout fahrtenbuchLayout = (RelativeLayout) this.findViewById(R.id.root_layout);

        int previousLine = R.id.kilometer_label;

        FahrtenbuchTopSection topSection = (FahrtenbuchTopSection) getFragmentManager().findFragmentById(R.id.top_fragment);
        topSection.setFahrtenzettelData(fahrtenzettel);

        // Display elements
        List<Fahrt> fahrten = fahrtenzettel.getFahrten();
        for (int i = 0; i < fahrten.size(); i++){
            int lineIndex = (i + 1) * MAX_ITEMS_PER_LINE;
            Fahrt currentFahrt = fahrten.get(i);

            //create relation from line index to data object
            lineTable.put(lineIndex, currentFahrt);

            renderAddressTextView(fahrtenbuchLayout, previousLine, lineIndex, currentFahrt);
            previousLine = lineIndex;

            renderKilometerTextView(fahrtenbuchLayout, lineIndex, currentFahrt);
            renderAbfahrtTextView(fahrtenbuchLayout, lineIndex, currentFahrt);
            renderAnkunftTextView(fahrtenbuchLayout, lineIndex, currentFahrt);

            addSonderrechteCheckBox(fahrtenbuchLayout, lineIndex, currentFahrt);
            addInfCheckBox(fahrtenbuchLayout, lineIndex, currentFahrt);
        }

        activateBottomButtons();
    }

    private void renderAddressTextView(RelativeLayout fahrtenbuchLayout, int previousLine, int lineIndex, Fahrt currentFahrt) {
        TextView targetAddress = new TextView(this);
        targetAddress.setId(lineIndex);
        targetAddress.setText(currentFahrt.getZiel());

        RelativeLayout.LayoutParams addressDetails = getTextViewLayoutParams(lineIndex, R.id.adresse_label);

        addressDetails.addRule(RelativeLayout.BELOW, previousLine);
        addressDetails.setMargins(LEFT, TOP, RIGHT, BOTTOM);

        fahrtenbuchLayout.addView(targetAddress, addressDetails);
    }

    private void renderKilometerTextView(RelativeLayout fahrtenbuchLayout, int lineIndex, Fahrt currentFahrt) {
        TextView kilometer = new TextView(this);
        kilometer.setId(lineIndex + 1);
        kilometer.setText(Integer.toString(currentFahrt.getKilometerBeginn()));

        fahrtenbuchLayout.addView(kilometer, getTextViewLayoutParams(lineIndex, R.id.kilometer_label));
    }

    private void renderAbfahrtTextView(RelativeLayout fahrtenbuchLayout, int lineIndex, Fahrt currentFahrt) {
        TextView abfahrt = new TextView(this);
        abfahrt.setId(lineIndex + 2);
        Long abfahrtData = currentFahrt.getAbfahrtszeit();
        if (abfahrtData != null) {
            abfahrt.setText(SIMPLE_DATE_FORMAT.format(currentFahrt.getAbfahrtszeit()));
        }

        fahrtenbuchLayout.addView(abfahrt, getTextViewLayoutParams(lineIndex, R.id.abfahrt_label));
    }

    private void renderAnkunftTextView(RelativeLayout fahrtenbuchLayout, int lineIndex, Fahrt currentFahrt) {
        TextView ankunft = new TextView(this);
        ankunft.setId(lineIndex + 3);
        Long ankunftData = currentFahrt.getAnkunftszeit();
        if (ankunftData != null){
            ankunft.setText(SIMPLE_DATE_FORMAT.format(ankunftData));
        }

        fahrtenbuchLayout.addView(ankunft, getTextViewLayoutParams(lineIndex, R.id.ankunft_label));
    }


    @NonNull
    private RelativeLayout.LayoutParams getTextViewLayoutParams(int lineIndex, int captionId) {
        return getStandardLayoutParams(lineIndex, captionId, RelativeLayout.ALIGN_BASELINE);
    }



    ///////////////////////////////////////////
    //// CHECKBOXES
    //////////////////////////////////////////


    private void addInfCheckBox(RelativeLayout fahrtenbuchLayout, int lineIndex, Fahrt currentFahrt) {
        CheckBox inf = createCheckBox(lineIndex, INF_INDEX, currentFahrt.getInf());
        RelativeLayout.LayoutParams infDetails = getCheckboxLayoutParams(lineIndex,  R.id.inf_label);
        fahrtenbuchLayout.addView(inf, infDetails);
    }

    private void addSonderrechteCheckBox(RelativeLayout fahrtenbuchLayout, int lineIndex, Fahrt currentFahrt) {
        CheckBox sonderrechte = createCheckBox(lineIndex, SONDERRECHTE_INDEX, currentFahrt.getSonderrechte());
        RelativeLayout.LayoutParams srDetails = getCheckboxLayoutParams(lineIndex, R.id.sonderrechte_label);
        fahrtenbuchLayout.addView(sonderrechte, srDetails);
    }

    @NonNull
    private CheckBox createCheckBox(int lineIndex,int index, boolean checked) {
        CheckBox checkBox = new CheckBox(this);
        checkBox.setId(lineIndex + index);
        checkBox.setChecked(checked);
        checkBox.setOnClickListener(getCheckboxesListener(checkBox));
        return checkBox;
    }

    @NonNull
    private RelativeLayout.LayoutParams getCheckboxLayoutParams(int lineIndex, int captionId) {
        return getStandardLayoutParams(lineIndex, captionId, RelativeLayout.ALIGN_TOP);
    }

    private OnClickListener getCheckboxesListener(final CheckBox currentCheckBox) {
        return new CheckBox.OnClickListener(){

            @Override
            public void onClick(View v) {
                int id = (currentCheckBox.getId() / MAX_ITEMS_PER_LINE) * MAX_ITEMS_PER_LINE;
                int offset = (currentCheckBox.getId() % MAX_ITEMS_PER_LINE);
                Fahrt f = lineTable.get(id);
                if (f != null) {
                    switch (offset) {
                        case SONDERRECHTE_INDEX: f.setSonderrechte(currentCheckBox.isChecked()); break;
                        case INF_INDEX: f.setInf(currentCheckBox.isChecked()); break;
                        default: throw new IllegalArgumentException("Unexpected offset " + offset);
                    }
                } else {
                    Log.e(TAG, "Could not find Fahrt with id " + id);
                }
            }
        };
    }


    ///////////////////////////////////////////
    //// UTIL METHODS
    //////////////////////////////////////////

    @NonNull
    private RelativeLayout.LayoutParams getStandardLayoutParams(int lineIndex, int captionId, int verticalAlignment) {
        RelativeLayout.LayoutParams checkboxDetails = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        checkboxDetails.addRule(verticalAlignment, lineIndex);
        checkboxDetails.addRule(RelativeLayout.ALIGN_LEFT, captionId);
        return checkboxDetails;
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
}
