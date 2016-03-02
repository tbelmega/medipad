package pardertec.de.medipad.fahrtenbuch;

import android.app.Activity;
import android.app.Fragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.TypedValue;
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

    public interface MiddleSectionListener{
        void activateBottomButtons();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fahrtenbuch_middle_section, container, false);
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


    void updateFahrtenzettelView(Fahrtenzettel fahrtenzettel) {
        //TODO: Move this to activity
        FahrtenbuchTopSection topSection = (FahrtenbuchTopSection) getFragmentManager().findFragmentById(R.id.top_fragment);
        topSection.setFahrtenzettelData(fahrtenzettel);


        RelativeLayout fahrtenZettelLayout = (RelativeLayout) this.getActivity().findViewById(R.id.middle_fragment);
        fahrtenZettelLayout.removeAllViewsInLayout();

        displayCaptions(fahrtenZettelLayout);
        displayElements(fahrtenzettel, fahrtenZettelLayout, R.id.kilometer_label);

        //TODO: Move this to activity
        activityCommander.activateBottomButtons();
    }

    private void displayCaptions(RelativeLayout fahrtenZettelLayout) {
        renderCaptionLabel(fahrtenZettelLayout, R.id.kilometer_label, R.string.kilometer_label, 100, null);
        renderCaptionLabel(fahrtenZettelLayout, R.id.adresse_label, R.string.adresse_label, 300, R.id.kilometer_label);
        renderCaptionLabel(fahrtenZettelLayout, R.id.abfahrt_label, R.string.abfahrt_label, 150, R.id.adresse_label);
        renderCaptionLabel(fahrtenZettelLayout, R.id.ankunft_label, R.string.ankunft_label, 150, R.id.abfahrt_label);
        renderCaptionLabel(fahrtenZettelLayout, R.id.sonderrechte_label, R.string.sonderrechte_label, 100, R.id.ankunft_label);
        renderCaptionLabel(fahrtenZettelLayout, R.id.inf_label, R.string.inf_label, 100, R.id.sonderrechte_label);
    }

    private void displayElements(Fahrtenzettel fahrtenzettel, RelativeLayout fahrtenZettelLayout, int previousLine) {
        // Display elements
        List<Fahrt> fahrten = fahrtenzettel.getFahrten();
        for (int i = 0; i < fahrten.size(); i++){
            int lineIndex = (i + 1) * MAX_ITEMS_PER_LINE;
            Fahrt currentFahrt = fahrten.get(i);

            //create relation from line index to data object
            lineTable.put(lineIndex, currentFahrt);

            renderAddressTextView(fahrtenZettelLayout, previousLine, lineIndex, currentFahrt);
            previousLine = lineIndex;

            renderKilometerTextView(fahrtenZettelLayout, lineIndex, currentFahrt);
            renderAbfahrtTextView(fahrtenZettelLayout, lineIndex, currentFahrt);
            renderAnkunftTextView(fahrtenZettelLayout, lineIndex, currentFahrt);

            addSonderrechteCheckBox(fahrtenZettelLayout, lineIndex, currentFahrt);
            addInfCheckBox(fahrtenZettelLayout, lineIndex, currentFahrt);
        }
    }


    private void renderCaptionLabel(RelativeLayout fahrtenZettelLayout, int id, int label, int width, Integer toTheRightOfId) {
        TextView targetAddress = new TextView(this.getActivity());
        targetAddress.setId(id);
        targetAddress.setText(label);

        targetAddress.setWidth(convertToDip(width));

        RelativeLayout.LayoutParams addressDetails = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        addressDetails.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        if (toTheRightOfId == null) {
            addressDetails.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        } else {
            addressDetails.addRule(RelativeLayout.RIGHT_OF, toTheRightOfId);
        }
        addressDetails.setMargins(LEFT, TOP, RIGHT, BOTTOM);

        fahrtenZettelLayout.addView(targetAddress, addressDetails);
    }



    private void renderAddressTextView(RelativeLayout fahrtenZettelLayout, int previousLine, int lineIndex, Fahrt currentFahrt) {
        TextView targetAddress = new TextView(this.getActivity());
        targetAddress.setId(lineIndex);
        targetAddress.setText(currentFahrt.getZiel());

        RelativeLayout.LayoutParams addressDetails = getTextViewLayoutParams(lineIndex, R.id.adresse_label);

        addressDetails.addRule(RelativeLayout.BELOW, previousLine);
        addressDetails.setMargins(LEFT, TOP, RIGHT, BOTTOM);

        fahrtenZettelLayout.addView(targetAddress, addressDetails);
    }

    private void renderKilometerTextView(RelativeLayout fahrtenZettelLayout, int lineIndex, Fahrt currentFahrt) {
        TextView kilometer = new TextView(this.getActivity());
        kilometer.setId(lineIndex + 1);
        kilometer.setText(Integer.toString(currentFahrt.getKilometerBeginn()));

        fahrtenZettelLayout.addView(kilometer, getTextViewLayoutParams(lineIndex, R.id.kilometer_label));
    }

    private void renderAbfahrtTextView(RelativeLayout fahrtenZettelLayout, int lineIndex, Fahrt currentFahrt) {
        TextView abfahrt = new TextView(this.getActivity());
        abfahrt.setId(lineIndex + 2);
        Long abfahrtData = currentFahrt.getAbfahrtszeit();
        if (abfahrtData != null) {
            abfahrt.setText(SIMPLE_DATE_FORMAT.format(currentFahrt.getAbfahrtszeit()));
        }

        fahrtenZettelLayout.addView(abfahrt, getTextViewLayoutParams(lineIndex, R.id.abfahrt_label));
    }

    private void renderAnkunftTextView(RelativeLayout fahrtenZettelLayout, int lineIndex, Fahrt currentFahrt) {
        TextView ankunft = new TextView(this.getActivity());
        ankunft.setId(lineIndex + 3);
        Long ankunftData = currentFahrt.getAnkunftszeit();
        if (ankunftData != null){
            ankunft.setText(SIMPLE_DATE_FORMAT.format(ankunftData));
        }

        fahrtenZettelLayout.addView(ankunft, getTextViewLayoutParams(lineIndex, R.id.ankunft_label));
    }


    @NonNull
    private RelativeLayout.LayoutParams getTextViewLayoutParams(int lineIndex, int captionId) {
        return getStandardLayoutParams(lineIndex, captionId, RelativeLayout.ALIGN_BASELINE);
    }



    ///////////////////////////////////////////
    //// CHECKBOXES
    //////////////////////////////////////////


    private void addInfCheckBox(RelativeLayout fahrtenZettelLayout, int lineIndex, Fahrt currentFahrt) {
        CheckBox inf = createCheckBox(lineIndex, INF_INDEX, currentFahrt.getInf());
        RelativeLayout.LayoutParams infDetails = getCheckboxLayoutParams(lineIndex,  R.id.inf_label);
        fahrtenZettelLayout.addView(inf, infDetails);
    }

    private void addSonderrechteCheckBox(RelativeLayout fahrtenZettelLayout, int lineIndex, Fahrt currentFahrt) {
        CheckBox sonderrechte = createCheckBox(lineIndex, SONDERRECHTE_INDEX, currentFahrt.getSonderrechte());
        RelativeLayout.LayoutParams srDetails = getCheckboxLayoutParams(lineIndex, R.id.sonderrechte_label);
        fahrtenZettelLayout.addView(sonderrechte, srDetails);
    }

    @NonNull
    private CheckBox createCheckBox(int lineIndex,int index, boolean checked) {
        CheckBox checkBox = new CheckBox(this.getActivity());
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


    private int convertToDip(int width) {
        Resources r = getResources();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, r.getDisplayMetrics());
    }
}
