package com.iutbm.applicationiut;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;


public class PlanFragment extends Fragment {
    private WebView part1;
    private WebView accesBelfort;
    private WebView accesBelfortCentre;
    private WebView accesMontbe;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plan, container, false);

        this.getActivity().getActionBar().setTitle("Plan");

        part1 = (WebView) view.findViewById(R.id.webViewPlanPart1);
        accesBelfort = (WebView) view.findViewById(R.id.webViewAccesBelfort1);
        accesBelfortCentre = (WebView) view.findViewById(R.id.webViewAccesBelfort2);
        accesMontbe = (WebView) view.findViewById(R.id.webViewAccesMontbe);

        String strPart1 = "<html><body bgcolor=\"F1E7C4\">" +
                "<p align=\"justify\">" +
                getString(R.string.plan_part1) +
                "</p>" +
                "</body></html>";

        String strPart2 = "<html><body bgcolor=\"F1E7C4\">" +
                "<p align=\"justify\">" +
                getString(R.string.acces_belfort) +
                "</p>" +
                "</body></html>";

        String strPart3 = "<html><body bgcolor=\"F1E7C4\">" +
                "<p align=\"justify\">" +
                getString(R.string.acces_belfort_centre) +
                "</p>" +
                "</body></html>";

        String strPart4 = "<html><body bgcolor=\"F1E7C4\">" +
                "<p align=\"justify\">" +
                getString(R.string.acces_montbe) +
                "</p>" +
                "</body></html>";

        part1.loadData(strPart1, "text/html; charset=UTF-8", null);
        accesBelfort.loadData(strPart2, "text/html; charset=UTF-8", null);
        accesBelfortCentre.loadData(strPart3, "text/html; charset=UTF-8", null);
        accesMontbe.loadData(strPart4, "text/html; charset=UTF-8", null);

        return view;
    }}

