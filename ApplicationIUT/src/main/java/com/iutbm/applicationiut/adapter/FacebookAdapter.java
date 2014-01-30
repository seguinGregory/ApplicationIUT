package com.iutbm.applicationiut.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.iutbm.applicationiut.R;
import com.iutbm.applicationiut.facebook.Facebook;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by P-E on 02/12/13.
 */
public class FacebookAdapter extends ArrayAdapter {
    Context mContext;
    ArrayList<Facebook> BookList;
    LayoutInflater inflater;

    public FacebookAdapter(Context context, int resource, int textViewResourceId, ArrayList objects) {
        super(context, resource, textViewResourceId, objects);
        this.mContext = context;
        this.BookList = objects;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return BookList.size();
    }

    public Facebook getItem(int position) {
        return this.BookList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        TextView tvLink;
        TextView tvDate;
        TextView tvDescription;
        //ImageView ivImage;
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
            holder.tvLink = (TextView) inView.findViewById(R.id.textViewLink);
            holder.tvDate = (TextView) inView.findViewById(R.id.textViewDate);
            holder.tvDescription = (TextView) inView.findViewById(R.id.textViewResume);
            //holder.ivImage = (ImageView) inView.findViewById(R.id.imageViewFacebook);
            inView.setTag(holder);
        }
        // Sinon on récupère la ligne qui est en mémoire
        // Else we get row which is in memory
        else
            holder = (ViewHolder) inView.getTag();
        // On récupère l'objet courant
        // We get the current object
        Facebook facebook = this.BookList.get(pos);

        // On met à jour nos views
        // We update views
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        holder.tvLink.setText(Html.fromHtml(facebook.getLink()));
        holder.tvLink.setClickable(true);
        holder.tvLink.setMovementMethod(LinkMovementMethod.getInstance());
        holder.tvDate.setText(sdf.format(facebook.getDate()));
        //holder.tvDescription.setText(facebook.getDescription());

        holder.tvDescription.setText(Html.fromHtml(facebook.getDescription()));
        holder.tvDescription.setMovementMethod(LinkMovementMethod.getInstance());

        /*StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());

        try {
            Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL(facebook.getImage()).getContent());
            holder.ivImage.setImageBitmap(bitmap);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        /*if (actualite.getContenu().isEmpty()) {
            holder.ivContinue.setVisibility(View.GONE);
        }*/

        return (inView);
    }
}
