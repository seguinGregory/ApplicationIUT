package com.iutbm.applicationiut.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.iutbm.applicationiut.R;
import com.iutbm.applicationiut.jsoup.Actualite;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by P-E on 02/12/13.
 */
public class ActualiteAdapter extends ArrayAdapter {
    Context mContext;
    ArrayList<Actualite> ActualiteList;
    LayoutInflater inflater;

    public ActualiteAdapter(Context context, int resource, int textViewResourceId, ArrayList objects) {
        super(context, resource, textViewResourceId, objects);
        this.mContext = context;
        this.ActualiteList = objects;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return ActualiteList.size();
    }

    public Actualite getItem(int position) {
        return this.ActualiteList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        TextView tvTitre;
        TextView tvDate;
        TextView tvResume;
        ImageView ivContinue;
    }

    @Override
    public View getView(int pos, View inView, ViewGroup parent) {
        ViewHolder holder;
        // Si la ligne n'éxiste pas -> On créée la ligne
        // If row doesn't exist -> we make it
        if (inView == null) {
            inView = this.inflater.inflate(R.layout.ligne_actu, parent, false);
            holder = new ViewHolder();
            // On affecte les views
            // We get views
            holder.tvTitre = (TextView) inView.findViewById(R.id.textViewTitre);
            holder.tvDate = (TextView) inView.findViewById(R.id.textViewDate);
            holder.tvResume = (TextView) inView.findViewById(R.id.textViewResume);
            holder.ivContinue = (ImageView) inView.findViewById(R.id.imageViewContinue);
            inView.setTag(holder);
        }
        // Sinon on récupère la ligne qui est en mémoire
        // Else we get row which is in memory
        else
            holder = (ViewHolder) inView.getTag();
        // On récupère l'objet courant
        // We get the current object
        Actualite actualite = this.ActualiteList.get(pos);

        // On met à jour nos views
        // We update views
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        holder.tvTitre.setText(actualite.getTitre());
        holder.tvDate.setText(sdf.format(actualite.getDate()));
        holder.tvResume.setText(actualite.getResume());

        if (actualite.getContenu().isEmpty()) {
            holder.ivContinue.setVisibility(View.GONE);
        }

        return (inView);
    }
}
