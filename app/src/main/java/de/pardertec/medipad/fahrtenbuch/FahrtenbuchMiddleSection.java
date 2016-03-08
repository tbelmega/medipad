package de.pardertec.medipad.fahrtenbuch;

import android.app.Activity;
import android.app.Fragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import de.pardertec.medipad.MedipadApplication;
import de.pardertec.medipad.R;
import de.pardertec.medipad.fahrtenbuch.model.FahrtenListAdapter;
import de.pardertec.medipad.fahrtenbuch.model.Fahrtenzettel;

import static de.pardertec.medipad.MedipadApplication.TAG;

/**
 * Created by Thiemo on 25.02.2016.
 */
public class FahrtenbuchMiddleSection extends Fragment {

    private RecyclerView fahrtenList;
    private FahrtenListAdapter.FahrtCardListener activityCommander;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.activityCommander = (FahrtenListAdapter.FahrtCardListener) activity;
        } catch (ClassCastException e) {
            Log.e(TAG, "Could not cast activity to TopSectionListener");
            throw e;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fahrtenbuch_middle_section, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fahrtenList = (RecyclerView) getActivity().findViewById(R.id.fahrtenList);
        fahrtenList.setHasFixedSize(false);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        fahrtenList.setLayoutManager(llm);

        FahrtenListAdapter fahrtenListAdapter = new FahrtenListAdapter(activityCommander, MedipadApplication.getCurrentFahrtenzettel().getFahrten());
        fahrtenList.setAdapter(fahrtenListAdapter);
    }


    public void updateFahrtenListView(Fahrtenzettel fahrtenzettel) {
        FahrtenListAdapter fahrtenListAdapter = new FahrtenListAdapter(activityCommander, MedipadApplication.getCurrentFahrtenzettel().getFahrten());
        fahrtenList.setAdapter(fahrtenListAdapter);
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
