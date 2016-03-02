package de.pardertec.medipad.fahrtenbuch.input;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.pardertec.medipad.R;

/**
 * Created by Thiemo on 28.02.2016.
 */
public class ZieleingabeManuellSection extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.zieleingabe_manuell_section, container,false);
        return view;
    }
}
