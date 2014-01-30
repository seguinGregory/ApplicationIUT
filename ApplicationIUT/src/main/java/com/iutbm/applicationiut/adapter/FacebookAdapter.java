package com.iutbm.applicationiut.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.iutbm.applicationiut.R;
import com.iutbm.applicationiut.facebook.Facebook;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by P-E on 02/12/13.
 */
public class FacebookAdapter extends ArrayAdapter {
    Context mContext;
    ArrayList<Facebook> ActualiteList;
    LayoutInflater inflater;

    public FacebookAdapter(Context context, int resource, int textViewResourceId, ArrayList objects) {
        super(context, resource, textViewResourceId, objects);
        this.mContext = context;
        this.ActualiteList = objects;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return ActualiteList.size();
    }

    public Facebook getItem(int position) {
        return this.ActualiteList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        TextView tvTitre;
        TextView tvDate;
        TextView tvDescription;
    }

    @Override
    public View getView(int pos, View inView, ViewGroup parent) {
        ViewHolder holder;
        // Si la ligne n'éxiste pas -> On créée la ligne
        // If row doesn't exist -> we make it
        if (inView == null) {
            inView = this.inflater.inflate(R.layout.ligne_facebook, parent, false);
            holder = new ViewHolder();
            // On affecte les views
            // We get views
            holder.tvTitre = (TextView) inView.findViewById(R.id.textViewTitre);
            holder.tvDate = (TextView) inView.findViewById(R.id.textViewDate);
            holder.tvDescription = (TextView) inView.findViewById(R.id.textViewResume);
            inView.setTag(holder);
        }
        // Sinon on récupère la ligne qui est en mémoire
        // Else we get row which is in memory
        else
            holder = (ViewHolder) inView.getTag();
        // On récupère l'objet courant
        // We get the current object
        Facebook facebook = this.ActualiteList.get(pos);

        // On met à jour nos views
        // We update views
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        holder.tvTitre.setText(facebook.getTitre());
        holder.tvDate.setText(sdf.format(facebook.getDate()));
        holder.tvDescription.setText(facebook.getDescription());

        /*if (actualite.getContenu().isEmpty()) {
            holder.ivContinue.setVisibility(View.GONE);
        }*/

        return (inView);
    }
}
