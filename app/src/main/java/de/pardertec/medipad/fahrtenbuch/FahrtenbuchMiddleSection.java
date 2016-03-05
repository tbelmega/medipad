package de.pardertec.medipad.fahrtenbuch;

import android.app.Fragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import de.pardertec.medipad.R;

/**
 * Created by Thiemo on 25.02.2016.
 */
public class FahrtenbuchMiddleSection extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fahrtenbuch_middle_section, container, false);
        return view;
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
