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

/**
 * Created by Thiemo on 24.02.2016.
 */
public class FahrtenbuchBottomSection extends Fragment {

    private Button neueFahrt;
    private Button abfahrt;
    private Button ankunft;

    private BottomSectionListener activityCommander;

    public interface BottomSectionListener{
        public void addFahrt();
        public void departNow();
        public void arriveNow();
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fahrtenbuch_bottom_section, container,false);

        neueFahrt = (Button) view.findViewById(R.id.neue_fahrt_button);
        neueFahrt.setOnClickListener(getNeueFahrtButtonListener());
        abfahrt = (Button) view.findViewById(R.id.abfahrt_button);
        ankunft = (Button) view.findViewById(R.id.ankunft_button);

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

    //TODO
    private View.OnClickListener getNeueFahrtButtonListener() {
        return null;
    }
}
