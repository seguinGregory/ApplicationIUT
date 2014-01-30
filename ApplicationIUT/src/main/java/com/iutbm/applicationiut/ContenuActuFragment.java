package com.iutbm.applicationiut;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.iutbm.applicationiut.jsoup.Actualite;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by greg on 03/12/13.
 */
public class ContenuActuFragment extends Fragment {
    private Actualite actualite;
    private TextView titre;
    private WebView contenu;
    private TextView date;

    public ContenuActuFragment(Actualite actualite) {
        this.actualite = actualite;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contenu_actualite, container, false);

        this.getActivity().getActionBar().setTitle("Actualités");

        titre = (TextView) view.findViewById(R.id.textViewTitreContenu);
        contenu = (WebView) view.findViewById(R.id.webViewContenuActu);
        date = (TextView) view.findViewById(R.id.textViewDateContenu);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        ArrayList<String> paragraphes = actualite.getContenu();
        String html = "<html><body bgcolor=\"F1E7C4\">";
        for (String s : paragraphes)
            html += "<p align=\"justify\">" + s + "</p>";
        html += "</body></html>";
        titre.setText(this.actualite.getTitre());
        contenu.loadData(html, "text/html; charset=UTF-8", null);
        date.setText(sdf.format(this.actualite.getDate()));

        return view;
    }
}