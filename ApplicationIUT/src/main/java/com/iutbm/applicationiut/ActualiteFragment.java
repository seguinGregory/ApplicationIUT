package com.iutbm.applicationiut;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.iutbm.applicationiut.adapter.ActualiteAdapter;
import com.iutbm.applicationiut.jsoup.Actualite;
import com.iutbm.applicationiut.jsoup.DOMParser;

import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by greg on 15/11/13.
 */
public class ActualiteFragment extends Fragment {
    private PullToRefreshListView actuListView;
    private boolean needHelp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_actualite, container, false);

        this.getActivity().getActionBar().setTitle("Actualités");

        this.actuListView = (PullToRefreshListView) view.findViewById(R.id.listViewActus);
        this.actuListView.setOnItemClickListener(toActu);
        this.actuListView.setOnRefreshListener(refreshActu);
        this.actuListView.setShowLastUpdatedText(true);

        File sauvegarde = new File(getActivity().getFilesDir().getPath() + "/actualites.data");

        SharedPreferences settings = getActivity().getSharedPreferences("help", 0);
        needHelp = settings.getBoolean("aide", true);

        if (!sauvegarde.exists()) {
            if (isNetworkAvailable()) {
                FetchActusWithProgress fetchActus = new FetchActusWithProgress();
                fetchActus.execute();
            } else {
                Toast.makeText(getActivity(), "Pas de connexion internet disponible, l'application ne peut pas récupérer les actualités.", Toast.LENGTH_SHORT).show();
            }
        } else {
            try {
                if (needHelp) {
                    affichageToast();
                }

                FileInputStream fis = new FileInputStream(sauvegarde);
                ObjectInputStream ois = new ObjectInputStream(fis);

                ArrayList<Actualite> lesActus = new ArrayList<Actualite>();

                lesActus = (ArrayList<Actualite>) ois.readObject();

                ActualiteAdapter adapter = new ActualiteAdapter(getActivity(), R.layout.fragment_actualite, R.layout.ligne_actu, lesActus);
                actuListView.setAdapter(adapter);

                ois.close();
                fis.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (StreamCorruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return view;
    }

    private void affichageToast() {
        LinearLayout layout = new LinearLayout(getActivity());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setBackgroundColor(getResources().getColor(android.R.color.background_dark));
        layout.setGravity(Gravity.CENTER_VERTICAL);
        layout.setPadding(20, 20, 20, 20);

        TextView tv = new TextView(getActivity());
        tv.setTextSize(16);

        tv.setGravity(Gravity.CENTER_VERTICAL);

        tv.setText("Glisser vers le bas pour actualiser la liste d'actualités.");
        tv.setTextColor(getResources().getColor(android.R.color.white));

        ImageView img = new ImageView(getActivity());
        img.setImageResource(R.drawable.toast_refresh);

        ImageView separator = new ImageView(getActivity());
        separator.setImageResource(R.drawable.separator);

        layout.addView(tv);
        layout.addView(separator);
        layout.addView(img);

        Toast toast = new Toast(getActivity());
        toast.setView(layout);
        toast.setGravity(Gravity.BOTTOM, 0, 50);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

    private PullToRefreshListView.OnRefreshListener refreshActu = new PullToRefreshListView.OnRefreshListener() {
        @Override
        public void onRefresh() {
            if (isNetworkAvailable()) {
                FetchActus fetchActus = new FetchActus();
                fetchActus.execute();
            } else {
                Toast.makeText(getActivity(), "Pas de connexion internet disponible, l'application ne peut pas récupérer les actualités.", Toast.LENGTH_SHORT).show();
                actuListView.onRefreshComplete();
            }
        }
    };

    private AdapterView.OnItemClickListener toActu = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Actualite actualite = (Actualite) parent.getItemAtPosition(position + 1);

            if (!actualite.getContenu().isEmpty()) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
                ft.replace(R.id.container, new ContenuActuFragment(actualite)).addToBackStack("retour9").commit();
            } else {
                if(actualite.getResume().contains("http")) {
                    String link = actualite.getResume().substring(actualite.getResume().indexOf("http"));
                    Intent toIut = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                    startActivity(toIut);
                }
                Toast.makeText(getActivity(), "Pas de contenu", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    private class FetchActus extends AsyncTask<Void, Integer, ArrayList<Actualite>> {

        @Override
        protected void onPostExecute(ArrayList<Actualite> liste) {
            ActualiteAdapter adapter = new ActualiteAdapter(getActivity(), R.layout.fragment_actualite, R.layout.ligne_actu, liste);
            actuListView.setAdapter(adapter);
            actuListView.onRefreshComplete();

            try {
                FileOutputStream fos = new FileOutputStream(getActivity().getFilesDir().getPath() + "/actualites.data");
                ObjectOutputStream oos = new ObjectOutputStream(fos);

                oos.writeObject(liste);

                oos.close();
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected ArrayList<Actualite> doInBackground(Void... voids) {
            ArrayList<Actualite> lesActus = new ArrayList<Actualite>();
            try {
                DOMParser leParser = new DOMParser();
                lesActus = leParser.getLesActus();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return lesActus;
        }
    }

    private class FetchActusWithProgress extends AsyncTask<Void, Integer, ArrayList<Actualite>> {
        private ProgressDialog progress;

        @Override
        protected void onPostExecute(ArrayList<Actualite> liste) {
            ActualiteAdapter adapter = new ActualiteAdapter(getActivity(), R.layout.fragment_actualite, R.layout.ligne_actu, liste);
            actuListView.setAdapter(adapter);

            progress.cancel();
            actuListView.onRefreshComplete();


            if (needHelp) {
                affichageToast();
            }

            try {
                FileOutputStream fos = new FileOutputStream(getActivity().getFilesDir().getPath() + "/actualites.data");
                ObjectOutputStream oos = new ObjectOutputStream(fos);

                oos.writeObject(liste);

                oos.close();
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected ArrayList<Actualite> doInBackground(Void... voids) {
            ArrayList<Actualite> lesActus = new ArrayList<Actualite>();
            try {
                DOMParser leParser = new DOMParser();
                lesActus = leParser.getLesActus();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return lesActus;
        }

        @Override
        protected void onPreExecute() {
            progress = new ProgressDialog(getActivity(), R.style.ProgressDialogTheme);
            progress.setMessage("Recherche des actualités ...");
            progress.show();
        }
    }
}