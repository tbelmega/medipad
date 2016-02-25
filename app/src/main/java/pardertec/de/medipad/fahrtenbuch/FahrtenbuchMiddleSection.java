package pardertec.de.medipad.fahrtenbuch;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pardertec.de.medipad.R;
import pardertec.de.medipad.fahrtenbuch.model.Fahrt;
import pardertec.de.medipad.fahrtenbuch.model.Fahrtenzettel;

/**
 * Created by Thiemo on 25.02.2016.
 */
public class FahrtenbuchMiddleSection extends Fragment {

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

    MiddleSectionListener activityCommander;
    private View view;

    public interface MiddleSectionListener{
        void activateBottomButtons();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fahrtenbuch_middle_section, container, false);

        return view;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.activityCommander = (MiddleSectionListener) activity;
        } catch (ClassCastException e) {
            Log.e(FahrtenbuchActivity.TAG, "Could not cast activity to MiddleSectionListener");
            throw e;
        }
    }

    /**
     * Dynamicly creates lines for the entries of a Fahrtenzettel
     */
    void updateFahrtenzettelView(Fahrtenzettel fahrtenzettel) {
        RelativeLayout activityLayout = (RelativeLayout) view.findViewById(R.id.middle_fragment);
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

            renderAddressTextView(activityLayout, previousLine, lineIndex, currentFahrt);
            previousLine = lineIndex;

            renderKilometerTextView(activityLayout, lineIndex, currentFahrt);
            renderAbfahrtTextView(activityLayout, lineIndex, currentFahrt);
            renderAnkunftTextView(activityLayout, lineIndex, currentFahrt);

            addSonderrechteCheckBox(activityLayout, lineIndex, currentFahrt);
            addInfCheckBox(activityLayout, lineIndex, currentFahrt);
        }

        activityCommander.activateBottomButtons();
    }

    private void renderAddressTextView(RelativeLayout fahrtenbuchLayout, int previousLine, int lineIndex, Fahrt currentFahrt) {
        TextView targetAddress = new TextView(view.getContext());
        targetAddress.setId(lineIndex);
        targetAddress.setText(currentFahrt.getZiel());

        RelativeLayout.LayoutParams addressDetails = getTextViewLayoutParams(lineIndex, R.id.adresse_label);

        addressDetails.addRule(RelativeLayout.BELOW, previousLine);
        addressDetails.setMargins(LEFT, TOP, RIGHT, BOTTOM);

        fahrtenbuchLayout.addView(targetAddress, addressDetails);
    }

    private void renderKilometerTextView(RelativeLayout fahrtenbuchLayout, int lineIndex, Fahrt currentFahrt) {
        TextView kilometer = new TextView(view.getContext());
        kilometer.setId(lineIndex + 1);
        kilometer.setText(Integer.toString(currentFahrt.getKilometerBeginn()));

        fahrtenbuchLayout.addView(kilometer, getTextViewLayoutParams(lineIndex, R.id.kilometer_label));
    }

    private void renderAbfahrtTextView(RelativeLayout fahrtenbuchLayout, int lineIndex, Fahrt currentFahrt) {
        TextView abfahrt = new TextView(view.getContext());
        abfahrt.setId(lineIndex + 2);
        Long abfahrtData = currentFahrt.getAbfahrtszeit();
        if (abfahrtData != null) {
            abfahrt.setText(SIMPLE_DATE_FORMAT.format(currentFahrt.getAbfahrtszeit()));
        }

        fahrtenbuchLayout.addView(abfahrt, getTextViewLayoutParams(lineIndex, R.id.abfahrt_label));
    }

    private void renderAnkunftTextView(RelativeLayout fahrtenbuchLayout, int lineIndex, Fahrt currentFahrt) {
        TextView ankunft = new TextView(view.getContext());
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
        CheckBox checkBox = new CheckBox(view.getContext());
        checkBox.setId(lineIndex + index);
        checkBox.setChecked(checked);
        checkBox.setOnClickListener(getCheckboxesListener(checkBox));
        return checkBox;
    }

    @NonNull
    private RelativeLayout.LayoutParams getCheckboxLayoutParams(int lineIndex, int captionId) {
        return getStandardLayoutParams(lineIndex, captionId, RelativeLayout.ALIGN_TOP);
    }

    private View.OnClickListener getCheckboxesListener(final CheckBox currentCheckBox) {
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
                    Log.e(FahrtenbuchActivity.TAG, "Could not find Fahrt with id " + id);
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
}
