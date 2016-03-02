package de.pardertec.medipad.fahrtenbuch;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import de.pardertec.medipad.fahrtenbuch.model.Fahrtenzettel;
import de.pardertec.medipad.R;

/**
 * Created by Thiemo on 24.02.2016.
 */
public class FahrtenbuchTopSection extends Fragment {

    private TextView kennzeichen;
    private TextView fahrer;
    private TextView sani;

    private Button neuesBlatt;
    private Button bearbeiten;
    private ImageView logo;

    private TopSectionListener activityCommander;

    public interface TopSectionListener{
        void clearSheet();
        void modifySheetData();
        void playHymn();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fahrtenbuch_top_section, container,false);

        kennzeichen = (TextView) view.findViewById(R.id.kennzeichen);
        fahrer = (TextView) view.findViewById(R.id.fahrer);
        sani = (TextView) view.findViewById(R.id.sani);

        neuesBlatt = (Button) view.findViewById(R.id.neues_blatt_button);
        neuesBlatt.setOnClickListener(getNeuesBlattListener());
        bearbeiten = (Button) view.findViewById(R.id.bearbeiten_button);
        bearbeiten.setOnClickListener(getBearbeitenListener());

        logo = (ImageView) view.findViewById(R.id.logo);
        logo.setOnClickListener(getLogoListener());

        return view;
    }

    private View.OnClickListener getLogoListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityCommander.playHymn();
            }
        };
    }

    private View.OnClickListener getBearbeitenListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityCommander.modifySheetData();
            }
        };
    }

    private View.OnClickListener getNeuesBlattListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityCommander.clearSheet();
            }
        };
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.activityCommander = (TopSectionListener) activity;
        } catch (ClassCastException e) {
            Log.e(FahrtenbuchActivity.TAG, "Could not cast activity to TopSectionListener");
            throw e;
        }
    }


    public void setFahrtenzettelData(Fahrtenzettel fahrtenzettel) {
        this.kennzeichen.setText(fahrtenzettel.getKennzeichen());
        this.fahrer.setText(fahrtenzettel.getFahrer());
        this.sani.setText(fahrtenzettel.getSani());
    }
}
