package com.iutbm.applicationiut;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

/**
 * Created by greg on 15/11/13.
 */
public class ChoisirIUTFragment extends Fragment {
    private WebView part1;
    private WebView part2;
    private WebView part3;
    private WebView part4;
    private WebView part5;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choisir_iut, container, false);

        this.getActivity().getActionBar().setTitle("Choisir l'IUT");

        part1 = (WebView) view.findViewById(R.id.webViewChoisirPart1);
        part2 = (WebView) view.findViewById(R.id.webViewChoisirPart2);
        part3 = (WebView) view.findViewById(R.id.webViewChoisirPart3);
        part4 = (WebView) view.findViewById(R.id.webViewChoisirPart4);
        part5 = (WebView) view.findViewById(R.id.webViewChoisirPart5);

        String strPart1 = "<html><body bgcolor=\"F1E7C4\">" +
                "<p align=\"justify\">" +
                getString(R.string.choisir_part1) +
                "</p>" +
                "</body></html>";

        String strPart2 = "<html><body bgcolor=\"F1E7C4\">" +
                "<p align=\"justify\">" +
                getString(R.string.choisir_part2_para1) +
                "</p><p align=\"justify\">" +
                getString(R.string.choisir_part2_para2) +
                "</p><p align=\"justify\">" +
                getString(R.string.choisir_part2_para3) +
                "</p>" +
                "</body></html>";

        String strPart3 = "<html><body bgcolor=\"F1E7C4\">" +
                "<p align=\"justify\">" +
                getString(R.string.choisir_part3) +
                "</p>" +
                "</body></html>";

        String strPart4 = "<html><body bgcolor=\"F1E7C4\">" +
                "<p align=\"justify\">" +
                getString(R.string.choisir_part4) +
                "</p>" +
                "</body></html>";

        String strPart5 = "<html><body bgcolor=\"F1E7C4\">" +
                "<p align=\"justify\">" +
                getString(R.string.choisir_part5) +
                "</p>" +
                "</body></html>";

        part1.loadData(strPart1, "text/html; charset=UTF-8", null);
        part2.loadData(strPart2, "text/html; charset=UTF-8", null);
        part3.loadData(strPart3, "text/html; charset=UTF-8", null);
        part4.loadData(strPart4, "text/html; charset=UTF-8", null);
        part5.loadData(strPart5, "text/html; charset=UTF-8", null);

        return view;
    }
}