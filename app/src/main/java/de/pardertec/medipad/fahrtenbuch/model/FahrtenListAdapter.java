package de.pardertec.medipad.fahrtenbuch.model;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import de.pardertec.medipad.MedipadApplication;
import de.pardertec.medipad.R;

/**
 * Created by Thiemo on 05.03.2016.
 */
public class FahrtenListAdapter extends RecyclerView.Adapter<FahrtenListAdapter.FahrtViewHolder> {

    public interface FahrtCardListener {
        void cardClicked(Fahrt f);
    }

    final private FahrtCardListener cardListener;
    final private List<Fahrt> fahrtenList;
    private View fahrtCardView;

    public FahrtenListAdapter(FahrtCardListener cardListener, List<Fahrt> fahrtenList) {
        this.cardListener = cardListener;
        this.fahrtenList = fahrtenList;
    }

    @Override
    public FahrtViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        fahrtCardView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.fahrten_card_view, parent, false);

        return new FahrtViewHolder(fahrtCardView);
    }



    @Override
    public void onBindViewHolder(FahrtViewHolder holder, int position) {
        Fahrt f = fahrtenList.get(position);
        holder.bindFahrt(f);
    }


    @Override
    public int getItemCount() {
        return fahrtenList.size();
    }


    public class FahrtViewHolder extends RecyclerView.ViewHolder{
        private Fahrt fahrt;

        private TextView kilometerLabel;
        private TextView adresseLabel;
        private TextView ankunftLabel;
        private TextView abfahrtLabel;

        public FahrtViewHolder(View v) {
            super(v);
            kilometerLabel =  (TextView) v.findViewById(R.id.kilometer_label);
            adresseLabel = (TextView)  v.findViewById(R.id.adresse_label);
            ankunftLabel = (TextView)  v.findViewById(R.id.ankunft_label);
            abfahrtLabel = (TextView) v.findViewById(R.id.abfahrt_label);

            v.setOnLongClickListener(createCardLongClickListener());
        }

        private View.OnLongClickListener createCardLongClickListener() {
            return new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    cardListener.cardClicked(fahrt);
                    return true;
                }
            };
        }

        public void bindFahrt(Fahrt f) {
            this.fahrt = f;
            kilometerLabel.setText(stringify(f.getKilometerBeginn()));
            adresseLabel.setText(stringify(f.getZiel()));
            abfahrtLabel.setText(datify(f.getAbfahrtszeit()));
            ankunftLabel.setText(datify(f.getAnkunftszeit()));
        }
    }



    ///////////////////////////////////////////
    //// UTIL METHODS
    //////////////////////////////////////////

    private String datify(Long time) {
        if (time == null) return "";
        return MedipadApplication.GERMAN_STD_DATE_FORMAT.format(time);
    }

    private String stringify(String string) {
        if (string == null) return "";
        return string;
    }

    private String stringify(Integer integer) {
        if (integer == null) return "";
        return Integer.toString(integer);
    }
}
