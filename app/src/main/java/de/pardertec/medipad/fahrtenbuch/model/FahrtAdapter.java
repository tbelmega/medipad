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
public class FahrtAdapter extends RecyclerView.Adapter<FahrtAdapter.FahrtViewHolder> {

    List<Fahrt> fahrtenList;

    public FahrtAdapter(List<Fahrt> fahrtenList) {
        this.fahrtenList = fahrtenList;
    }

    @Override
    public FahrtViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.fahrten_card_view, parent, false);

        return new FahrtViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FahrtViewHolder holder, int position) {
        Fahrt f = fahrtenList.get(position);
        holder.vName.setText(stringify(f.getKilometerBeginn()));
        holder.vSurname.setText(stringify(f.getZiel()));
        holder.vEmail.setText(datify(f.getAbfahrtszeit()));
        holder.vTitle.setText(datify(f.getAbfahrtszeit()));
    }

    private String datify(Long time) {
        return MedipadApplication.GERMAN_STD_DATE_FORMAT.format(time);
    }


    private String stringify(String string) {
        if (string != null) return string;
        else return "";
    }

    private String stringify(Integer integer) {
        if (integer != null) return Integer.toString(integer);
        else return "";
    }


    @Override
    public int getItemCount() {
        return fahrtenList.size();
    }

    public class FahrtViewHolder extends RecyclerView.ViewHolder{
        protected TextView vName;
        protected TextView vSurname;
        protected TextView vEmail;
        protected TextView vTitle;

        public FahrtViewHolder(View v) {
            super(v);
            vName =  (TextView) v.findViewById(R.id.kilometer_label);
            vSurname = (TextView)  v.findViewById(R.id.adresse_label);
            vEmail = (TextView)  v.findViewById(R.id.ankunft_label);
            vTitle = (TextView) v.findViewById(R.id.abfahrt_label);
        }
    }
}
