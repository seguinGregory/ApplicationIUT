package com.iutbm.applicationiut;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by greg on 28/11/13.
 */
public class DUTDetailleFragment extends Fragment {
    private String idFormation;
    private TextView intituleFormation;
    private WebView caracFormation;
    private WebView presentationFormation;
    private WebView publicFormation;
    private WebView insertionFormation;
    private WebView poursuiteFormation;

    public DUTDetailleFragment(String idFormation) {
        this.idFormation = idFormation;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dut_detaille, container, false);

        this.getActivity().getActionBar().setTitle("DUT");

        intituleFormation = (TextView) view.findViewById(R.id.textViewIntituleDUT);
        caracFormation = (WebView) view.findViewById(R.id.webViewCaracDUT);
        presentationFormation = (WebView) view.findViewById(R.id.webViewPresentationDUT);
        publicFormation = (WebView) view.findViewById(R.id.webViewPublicDUT);
        insertionFormation = (WebView) view.findViewById(R.id.webViewInsertionDUT);
        poursuiteFormation = (WebView) view.findViewById(R.id.webViewPoursuiteDUT);

        List<String> detailFormation = null;
        if (idFormation.equals("ASS_DUT")) {
            detailFormation = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.ASS_DUT)));
        } else if (idFormation.equals("GU_DUT")) {
            detailFormation = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.GU_DUT)));
        } else if (idFormation.equals("SAP_DUT")) {
            detailFormation = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.SAP_DUT)));
        } else if (idFormation.equals("G2CD_DUT")) {
            detailFormation = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.G2CD_DUT)));
        } else if (idFormation.equals("GEII_DUT")) {
            detailFormation = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.GEII_DUT)));
        } else if (idFormation.equals("GTE_DUT")) {
            detailFormation = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.GTE_DUT)));
        } else if (idFormation.equals("INFO_DUT")) {
            detailFormation = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.INFO_DUT)));
        } else if (idFormation.equals("TDC_DUT")) {
            detailFormation = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.TDC_DUT)));
        } else if (idFormation.equals("MP_DUT")) {
            detailFormation = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.MP_DUT)));
        }

        intituleFormation.setText(detailFormation.get(0));

        caracFormation.loadData("<html><body bgcolor=\"F1E7C4\">" + detailFormation.get(1) + "</body></html>", "text/html; charset=UTF-8", null);
        presentationFormation.loadData("<html><body bgcolor=\"F1E7C4\">" + detailFormation.get(2) + "</body></html>", "text/html; charset=UTF-8", null);
        publicFormation.loadData("<html><body bgcolor=\"F1E7C4\">" + detailFormation.get(3) + "</body></html>", "text/html; charset=UTF-8", null);
        insertionFormation.loadData("<html><body bgcolor=\"F1E7C4\">" + detailFormation.get(4) + "</body></html>", "text/html; charset=UTF-8", null);
        poursuiteFormation.loadData("<html><body bgcolor=\"F1E7C4\">" + detailFormation.get(5) + "</body></html>", "text/html; charset=UTF-8", null);

        return view;
    }
}