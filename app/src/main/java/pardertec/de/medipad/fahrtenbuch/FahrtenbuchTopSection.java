package pardertec.de.medipad.fahrtenbuch;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pardertec.de.medipad.R;
import pardertec.de.medipad.fahrtenbuch.model.Fahrtenzettel;

/**
 * Created by Thiemo on 24.02.2016.
 */
public class FahrtenbuchTopSection extends Fragment {

    private TextView kennzeichen;
    private TextView fahrer;
    private TextView sani;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fahrtenbuch_top_section, container,false);

        kennzeichen = (TextView) view.findViewById(R.id.kennzeichen);
        fahrer = (TextView) view.findViewById(R.id.fahrer);
        sani = (TextView) view.findViewById(R.id.sani);

        return view;
    }


    public void setFahrtenzettelData(Fahrtenzettel fahrtenzettel) {
        this.kennzeichen.setText(fahrtenzettel.getKennzeichen());
        this.fahrer.setText(fahrtenzettel.getFahrer());
        this.sani.setText(fahrtenzettel.getSani());
    }
}
