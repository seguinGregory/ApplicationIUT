package com.iutbm.applicationiut;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

/**
 * Created by Greg on 10/11/13.
 */
public class EcoCampusFragment extends Fragment {
    private WebView part1;
    private WebView part2;
    private WebView part3;
    private WebView part4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ecocampus, container, false);

        this.getActivity().getActionBar().setTitle("Eco-Campus");

        part1 = (WebView) view.findViewById(R.id.webViewEcoPart1);
        part2 = (WebView) view.findViewById(R.id.webViewEcoPart2);
        part3 = (WebView) view.findViewById(R.id.webViewEcoPart3);
        part4 = (WebView) view.findViewById(R.id.webViewEcoPart4);

        String strPart1 = "<html><body bgcolor=\"F1E7C4\">" +
                "<p align=\"justify\">" +
                getString(R.string.eco_part1_para1) +
                "</p><p align=\"justify\">" +
                getString(R.string.eco_part1_para2) +
                "</p><p align=\"justify\">" +
                getString(R.string.eco_part1_para3) +
                "</p><p align=\"justify\">" +
                getString(R.string.eco_part1_para4) +
                "</p><p align=\"justify\">" +
                getString(R.string.eco_part1_para5) +
                "</p><p align=\"justify\">" +
                getString(R.string.eco_part1_para6) +
                "</p>" +
                "</body></html>";

        String strPart2 = "<html><body bgcolor=\"F1E7C4\">" +
                "<p align=\"justify\">" +
                getString(R.string.eco_part2_para1) +
                "</p><p align=\"justify\">" +
                getString(R.string.eco_part2_para2) +
                "</p>" +
                "</body></html>";

        String strPart3 = "<html><body bgcolor=\"F1E7C4\">" +
                "<p align=\"justify\">" +
                getString(R.string.eco_part3_para1) +
                "</p><p align=\"justify\">" +
                getString(R.string.eco_part3_para2) +
                "</p><p align=\"justify\">" +
                getString(R.string.eco_part3_para3) +
                "</p>" +
                "</body></html>";

        String strPart4 = "<html><body bgcolor=\"F1E7C4\">" +
                "Olivier Prévôt<br>" +
                "Directeur de l’IUT Belfort-Montbéliard<br>" +
                "Tél. 03 84 58 77 01<br>" +
                "olivier.prevot@univ-fcomte.fr" +
                "</body></html>";

        part1.loadData(strPart1, "text/html; charset=UTF-8", null);
        part2.loadData(strPart2, "text/html; charset=UTF-8", null);
        part3.loadData(strPart3, "text/html; charset=UTF-8", null);
        part4.loadData(strPart4, "text/html; charset=UTF-8", null);

        return view;
    }
}