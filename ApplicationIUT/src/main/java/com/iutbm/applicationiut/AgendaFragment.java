package com.iutbm.applicationiut;

import android.app.Fragment;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Toast;

/**
 * Created by Greg on 10/11/13.
 */
public class AgendaFragment extends Fragment {
    private WebView calendar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_agenda, container, false);

        this.calendar = (WebView) view.findViewById(R.id.webViewCalendar);

        this.getActivity().getActionBar().setTitle("Agenda");

        if (isNetworkAvailable()) {
            DisplayMetrics metrics = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

            String strCal = "<html><body>" + "<iframe src=\"https://www.google.com/calendar/embed?src=communication%40iut-bm.univ-fcomte.fr&ctz=Europe/Paris\" style=\"border: 0\" width=\"" + (metrics.widthPixels / 2.1) + "\" height=\"" + (metrics.heightPixels / 2.1) + "\" frameborder=\"0\" scrolling=\"no\"></iframe></body></html>";
            this.calendar.getSettings().setJavaScriptEnabled(true);
            this.calendar.loadData(strCal, "text/html; charset=utf-8", null);
        } else {
            Toast.makeText(getActivity(), "Pas de connexion internet disponible, impossible de charger le calendrier.", Toast.LENGTH_SHORT);
        }

        return view;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}