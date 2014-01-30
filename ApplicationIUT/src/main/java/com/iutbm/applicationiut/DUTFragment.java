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
public class DUTFragment extends Fragment {
    private Spinner listDUTBelfort;
    private Spinner listDUTMontbelliard;
    private WebView part1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dut, container, false);

        this.getActivity().getActionBar().setTitle("DUT");

        listDUTMontbelliard = (Spinner) view.findViewById(R.id.spinnerMontbelliard);
        listDUTBelfort = (Spinner) view.findViewById(R.id.spinnerBelfort);
        part1 = (WebView) view.findViewById(R.id.webViewDUTPart1);

        String strPart1 = "<html><body bgcolor=\"F1E7C4\">" +
                "<p align=\"justify\">" +
                getString(R.string.dut_part1_para1) +
                "</p>" +
                "<p align=\"justify\">" +
                getString(R.string.dut_part1_para2) +
                "</p>" +
                "<p align=\"justify\">" +
                getString(R.string.dut_part1_para3) +
                "<ul>" +
                "<li> en école d’ingénieurs et de commerce, </li>" +
                "<li> en licence professionnelle, </li>" +
                "<li> en licence classique, puis en Master (professionnel ou recherche) et Doctorat. </li>" +
                "</ul></p>" +
                "</body></html>";

        part1.loadData(strPart1, "text/html; charset=UTF-8", null);

        List<String> listItemBelfort = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.DUTBelfort)));
        List<String> listItemMontbelliard = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.DUTMontbelliard)));

        ArrayAdapter<String> adapterBelfort = new ArrayAdapter<String>(getActivity(), R.layout.custom_spinner, listItemBelfort);
        adapterBelfort.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> adapterMontbelliard = new ArrayAdapter<String>(getActivity(), R.layout.custom_spinner, listItemMontbelliard);
        adapterMontbelliard.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        listDUTBelfort.setAdapter(adapterBelfort);
        listDUTMontbelliard.setAdapter(adapterMontbelliard);

        listDUTBelfort.setOnItemSelectedListener(toBelfort);
        listDUTMontbelliard.setOnItemSelectedListener(toMontbelliard);

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
                        ft.replace(R.id.container, new DUTDetailleFragment("ASS_DUT")).addToBackStack("retour20").commit();
                        listDUTBelfort.setSelection(0);
                        break;
                    case 2:
                        ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
                        ft.replace(R.id.container, new DUTDetailleFragment("GU_DUT")).addToBackStack("retour20").commit();
                        listDUTBelfort.setSelection(0);
                        break;
                    case 3:
                        ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
                        ft.replace(R.id.container, new DUTDetailleFragment("SAP_DUT")).addToBackStack("retour20").commit();
                        listDUTBelfort.setSelection(0);
                        break;
                    case 4:
                        ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
                        ft.replace(R.id.container, new DUTDetailleFragment("G2CD_DUT")).addToBackStack("retour20").commit();
                        listDUTBelfort.setSelection(0);
                        break;
                    case 5:
                        ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
                        ft.replace(R.id.container, new DUTDetailleFragment("GEII_DUT")).addToBackStack("retour20").commit();
                        listDUTBelfort.setSelection(0);
                        break;
                    case 6:
                        ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
                        ft.replace(R.id.container, new DUTDetailleFragment("GTE_DUT")).addToBackStack("retour20").commit();
                        listDUTBelfort.setSelection(0);
                        break;
                    case 7:
                        ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
                        ft.replace(R.id.container, new DUTDetailleFragment("INFO_DUT")).addToBackStack("retour20").commit();
                        listDUTBelfort.setSelection(0);
                        break;
                    case 8:
                        ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
                        ft.replace(R.id.container, new DUTDetailleFragment("TDC_DUT")).addToBackStack("retour20").commit();
                        listDUTBelfort.setSelection(0);
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
                        // GACO
                        adb.setMessage("Vous allez être redirigé vers un site externe à l'application, voulez-vous continuer ?");
                        adb.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent toGaco = new Intent(Intent.ACTION_VIEW, Uri.parse("http://gacoweb.pu-pm.univ-fcomte.fr/"));
                                startActivity(toGaco);
                                listDUTMontbelliard.setSelection(0);
                            }
                        });
                        adb.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                listDUTMontbelliard.setSelection(0);
                            }
                        });
                        adb.create().show();
                        break;
                    case 2:
                        ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
                        ft.replace(R.id.container, new DUTDetailleFragment("MP_DUT")).addToBackStack("retour20").commit();
                        listDUTMontbelliard.setSelection(0);
                        break;
                    case 3:
                        // RT
                        adb.setMessage("Vous allez être redirigé vers un site externe à l'application, voulez-vous continuer ?");
                        adb.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent toRT = new Intent(Intent.ACTION_VIEW, Uri.parse("http://rt.pu-pm.univ-fcomte.fr/index.php/Accueil"));
                                startActivity(toRT);
                                listDUTMontbelliard.setSelection(0);
                            }
                        });
                        adb.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                listDUTMontbelliard.setSelection(0);
                            }
                        });
                        adb.create().show();
                        break;
                    case 4:
                        // MMI
                        adb.setMessage("Vous allez être redirigé vers un site externe à l'application, voulez-vous continuer ?");
                        adb.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent toMMI = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.src-media.com/"));
                                startActivity(toMMI);
                                listDUTMontbelliard.setSelection(0);
                            }
                        });
                        adb.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                listDUTMontbelliard.setSelection(0);
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