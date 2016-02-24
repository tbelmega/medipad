package pardertec.de.medipad.fahrtenbuch;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pardertec.de.medipad.R;

/**
 * Created by Thiemo on 24.02.2016.
 */
public class FahrtenbuchTopSection extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fahrtenbuch_top_section, container,false);
        return view;
    }
}
