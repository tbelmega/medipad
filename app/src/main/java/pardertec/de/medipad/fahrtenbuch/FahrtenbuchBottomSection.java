package pardertec.de.medipad.fahrtenbuch;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import pardertec.de.medipad.R;
import pardertec.de.medipad.fahrtenbuch.model.Fahrt;

/**
 * Created by Thiemo on 24.02.2016.
 */
public class FahrtenbuchBottomSection extends Fragment {

    private Button neueFahrt;
    private Button abfahrt;
    private Button ankunft;

    private BottomSectionListener activityCommander;

    public interface BottomSectionListener{
        void addFahrt();
        void departNow();
        void arriveNow();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fahrtenbuch_bottom_section, container,false);

        neueFahrt = (Button) view.findViewById(R.id.neue_fahrt_button);
        neueFahrt.setOnClickListener(getNeueFahrtButtonListener());

        abfahrt = (Button) view.findViewById(R.id.abfahrt_button);
        abfahrt.setOnClickListener(getAbfahrtButtonListener());

        ankunft = (Button) view.findViewById(R.id.ankunft_button);
        ankunft.setOnClickListener(getAnkunftButtonListener());

        return view;
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.activityCommander = (BottomSectionListener) activity;
        } catch (ClassCastException e) {
            Log.e(FahrtenbuchActivity.TAG, "Could not cast activity to BottomSectionListener");
            throw e;
        }
    }

    public void enableButtonsDependingOnLastFahrt(Fahrt lastFahrt) {
        if (lastFahrt == null || lastFahrt.arrived()){
            enableNewButtonOnly();
        } else if (lastFahrt.departed()) {
            enableArriveButtonOnly();
        } else {
            enableDepartButtonOnly();
        }
    }

    void enableNewButtonOnly() {
        neueFahrt.setEnabled(true);
        abfahrt.setEnabled(false);
        ankunft.setEnabled(false);
    }

    void enableArriveButtonOnly() {
        neueFahrt.setEnabled(false);
        abfahrt.setEnabled(false);
        ankunft.setEnabled(true);
    }

    void enableDepartButtonOnly() {
        neueFahrt.setEnabled(false);
        abfahrt.setEnabled(true);
        ankunft.setEnabled(false);
    }


    private View.OnClickListener getNeueFahrtButtonListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityCommander.addFahrt();
            }
        };
    }

    private View.OnClickListener getAnkunftButtonListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityCommander.arriveNow();
            }
        };
    }

    private View.OnClickListener getAbfahrtButtonListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityCommander.departNow();
            }
        };
    }


}
