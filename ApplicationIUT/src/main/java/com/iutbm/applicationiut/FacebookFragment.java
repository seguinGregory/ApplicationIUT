package com.iutbm.applicationiut;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.iutbm.applicationiut.adapter.FacebookAdapter;
import com.iutbm.applicationiut.facebook.DOMParserFace;
import com.iutbm.applicationiut.facebook.Facebook;

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
public class FacebookFragment extends Fragment {
    private PullToRefreshListView actuListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_facebook, container, false);

        this.getActivity().getActionBar().setTitle("Facebook");

        this.actuListView = (PullToRefreshListView) view.findViewById(R.id.listViewActus);
        //this.actuListView.setOnItemClickListener(toActu);
        this.actuListView.setOnRefreshListener(refreshActu);
        this.actuListView.setShowLastUpdatedText(true);

        File sauvegarde = new File(getActivity().getFilesDir().getPath() + "/facebook_actus.data");
        if (!sauvegarde.exists()) {
            if (isNetworkAvailable()) {
                FetchActusWithProgress fetchActus = new FetchActusWithProgress();
                fetchActus.execute();
            } else {
                Toast.makeText(getActivity(), "Pas de connexion internet disponible, l'application ne peut pas récupérer les actualités.", Toast.LENGTH_SHORT).show();
            }
        } else {
            try {
                FileInputStream fis = new FileInputStream(sauvegarde);
                ObjectInputStream ois = new ObjectInputStream(fis);

                ArrayList<Facebook> lesActus = new ArrayList<Facebook>();

                lesActus = (ArrayList<Facebook>) ois.readObject();

                FacebookAdapter adapter = new FacebookAdapter(getActivity(), R.layout.fragment_facebook, R.layout.ligne_actu, lesActus);
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

    /*private AdapterView.OnItemClickListener toActu = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Actualite actualite = (Actualite) parent.getItemAtPosition(position+1);

            if(!actualite.getContenu().isEmpty()) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
                ft.replace(R.id.container, new ContenuActuFragment(actualite)).addToBackStack("retour9").commit();
            } else {
                Toast.makeText(getActivity(), "Pas de contenu", Toast.LENGTH_SHORT).show();
            }
        }
    };*/

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    private class FetchActus extends AsyncTask<Void, Integer, ArrayList<Facebook>> {

        @Override
        protected void onPostExecute(ArrayList<Facebook> liste) {
            FacebookAdapter adapter = new FacebookAdapter(getActivity(), R.layout.fragment_facebook, R.layout.ligne_actu, liste);
            actuListView.setAdapter(adapter);
            actuListView.onRefreshComplete();

            try {
                FileOutputStream fos = new FileOutputStream(getActivity().getFilesDir().getPath() + "/facebook_actus.data");
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
        protected ArrayList<Facebook> doInBackground(Void... voids) {
            ArrayList<Facebook> lesActus = new ArrayList<Facebook>();
            try {
                DOMParserFace leParser = new DOMParserFace();
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

    private class FetchActusWithProgress extends AsyncTask<Void, Integer, ArrayList<Facebook>> {
        private ProgressDialog progress;

        @Override
        protected void onPostExecute(ArrayList<Facebook> liste) {
            FacebookAdapter adapter = new FacebookAdapter(getActivity(), R.layout.fragment_facebook, R.layout.ligne_actu, liste);
            actuListView.setAdapter(adapter);

            progress.cancel();
            actuListView.onRefreshComplete();

            try {
                FileOutputStream fos = new FileOutputStream(getActivity().getFilesDir().getPath() + "/facebook_actus.data");
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
        protected ArrayList<Facebook> doInBackground(Void... voids) {
            ArrayList<Facebook> lesActus = new ArrayList<Facebook>();
            try {
                DOMParserFace leParser = new DOMParserFace();
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