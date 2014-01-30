package com.iutbm.applicationiut.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.iutbm.applicationiut.R;
import com.iutbm.applicationiut.Twitter.Tweet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class TwitterAdapter extends ArrayAdapter {
    Context mContext;
    ArrayList<Tweet> TweetList;
    LayoutInflater inflater;

    public TwitterAdapter(Context context, int resource, int textViewResourceId, ArrayList objects) {
        super(context, resource, textViewResourceId, objects);
        this.mContext = context;
        this.TweetList = objects;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return TweetList.size();
    }

    public Tweet getItem(int position) {
        return this.TweetList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        TextView tvLien;
        TextView tvContenu;
        TextView tvDate;
        //ImageView ivContinue;
    }

    @Override
    public View getView(int pos, View inView, ViewGroup parent) {
        ViewHolder holder;
        // Si la ligne n'éxiste pas -> On créée la ligne
        // If row doesn't exist -> we make it
        if (inView == null) {
            inView = this.inflater.inflate(R.layout.ligne_tweet, parent, false);
            holder = new ViewHolder();
            // On affecte les views
            // We get views
            holder.tvLien = (TextView) inView.findViewById(R.id.textViewLien);
            holder.tvDate = (TextView) inView.findViewById(R.id.textViewDate);
            holder.tvContenu = (TextView) inView.findViewById(R.id.textViewContenu);
            //holder.ivContinue = (ImageView) inView.findViewById(R.id.imageViewContinue);
            inView.setTag(holder);
        }
        // Sinon on récupère la ligne qui est en mémoire
        // Else we get row which is in memory
        else
            holder = (ViewHolder) inView.getTag();
        // On récupère l'objet courant
        // We get the current object
        Tweet tweet = this.TweetList.get(pos);

        // On met à jour nos views
        // We update views
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        holder.tvLien.setText(tweet.getTitre());
        holder.tvDate.setText(tweet.getSincePost());
        holder.tvContenu.setText(tweet.getContenu());

       /* if (tweet.getContenu().isEmpty()) {
            holder.ivContinue.setVisibility(View.GONE);
        }*/

        return (inView);
    }
}
