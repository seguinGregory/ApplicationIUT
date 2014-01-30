package com.iutbm.applicationiut;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by greg on 15/11/13.
 */
public class LicenceFragment extends Fragment {
    private Spinner listLPBelfort;
    private Spinner listLPMontbelliard;
    private WebView part1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_licence, container, false);

        this.getActivity().getActionBar().setTitle("Licence pro");

        listLPMontbelliard = (Spinner) view.findViewById(R.id.spinnerMontbelliard);
        listLPBelfort = (Spinner) view.findViewById(R.id.spinnerBelfort);
        part1 = (WebView) view.findViewById(R.id.webViewLPPart1);

        String strPart1 = "<html><body bgcolor=\"F1E7C4\">" +
                "<p align=\"justify\">" +
                getString(R.string.lp_part1_para1) +
                "</p>" +
                "<p align=\"justify\">" +
                getString(R.string.lp_part1_para2) +
                "</p>" +
                "</body></html>";

        part1.loadData(strPart1, "text/html; charset=UTF-8", null);

        List<String> listItemBelfort = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.LPBelfort)));
        List<String> listItemMontbelliard = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.LPMontbelliard)));

        ArrayAdapter<String> adapterBelfort = new ArrayAdapter<String>(getActivity(), R.layout.custom_spinner, listItemBelfort);
        adapterBelfort.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> adapterMontbelliard = new ArrayAdapter<String>(getActivity(), R.layout.custom_spinner, listItemMontbelliard);
        adapterMontbelliard.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        listLPBelfort.setAdapter(adapterBelfort);
        listLPMontbelliard.setAdapter(adapterMontbelliard);

        listLPBelfort.setOnItemSelectedListener(toBelfort);
        listLPMontbelliard.setOnItemSelectedListener(toMontbelliard);

        return view;
    }

    private AdapterView.OnItemSelectedListener toBelfort = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            if (position != 0) {
                switch (position) {
                    case 1:
                        ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
                        ft.replace(R.id.container, new LPDetailleFragment("ASS_LP")).addToBackStack("retour20").commit();
                        listLPBelfort.setSelection(0);
                        break;
                    case 2:
                        ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
                        ft.replace(R.id.container, new LPDetailleFragment("CTPEB_LP")).addToBackStack("retour20").commit();
                        listLPBelfort.setSelection(0);
                        break;
                    case 3:
                        ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
                        ft.replace(R.id.container, new LPDetailleFragment("ENR_LP")).addToBackStack("retour20").commit();
                        listLPBelfort.setSelection(0);
                        break;
                    case 4:
                        ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
                        ft.replace(R.id.container, new LPDetailleFragment("FVPI_LP")).addToBackStack("retour20").commit();
                        listLPBelfort.setSelection(0);
                        break;
                    case 5:
                        ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
                        ft.replace(R.id.container, new LPDetailleFragment("VEGA_LP")).addToBackStack("retour20").commit();
                        listLPBelfort.setSelection(0);
                        break;
                    case 6:
                        ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
                        ft.replace(R.id.container, new LPDetailleFragment("TIC_LP")).addToBackStack("retour20").commit();
                        listLPBelfort.setSelection(0);
                        break;
                    case 7:
                        ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
                        ft.replace(R.id.container, new LPDetailleFragment("TEPROW_LP")).addToBackStack("retour20").commit();
                        listLPBelfort.setSelection(0);
                        break;
                }
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private AdapterView.OnItemSelectedListener toMontbelliard = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
            if (position != 0) {
                switch (position) {
                    case 1:
                        ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
                        ft.replace(R.id.container, new LPDetailleFragment("ASS_LP")).addToBackStack("retour20").commit();
                        listLPMontbelliard.setSelection(0);
                        break;
                    case 2:
                        ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
                        ft.replace(R.id.container, new LPDetailleFragment("CIM_LP")).addToBackStack("retour20").commit();
                        listLPMontbelliard.setSelection(0);
                        break;
                    case 3:
                        ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
                        ft.replace(R.id.container, new LPDetailleFragment("CART_LP")).addToBackStack("retour20").commit();
                        listLPMontbelliard.setSelection(0);
                        break;
                    case 4:
                        ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
                        ft.replace(R.id.container, new LPDetailleFragment("DORA_LP")).addToBackStack("retour20").commit();
                        listLPMontbelliard.setSelection(0);
                        break;
                    case 5:
                        // MOSEL
                        adb.setMessage("Vous allez être redirigé vers un site externe à l'application, voulez-vous continuer ?");
                        adb.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent toMOSEL = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.lpmosel.net/"));
                                startActivity(toMOSEL);
                                listLPMontbelliard.setSelection(0);
                            }
                        });
                        adb.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                listLPMontbelliard.setSelection(0);
                            }
                        });
                        adb.create().show();
                        break;
                    case 6:
                        // WD
                        adb.setMessage("Vous allez être redirigé vers un site externe à l'application, voulez-vous continuer ?");
                        adb.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent toWD = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.src-media.com/"));
                                startActivity(toWD);
                                listLPBelfort.setSelection(0);
                            }
                        });
                        adb.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                listLPMontbelliard.setSelection(0);
                            }
                        });
                        adb.create().show();
                        break;
                }
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
}