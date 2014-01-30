package com.iutbm.applicationiut;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.iutbm.applicationiut.facebook.DOMParserFace;
import com.iutbm.applicationiut.facebook.Facebook;
import com.iutbm.applicationiut.adapter.FacebookAdapter;

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
    private PullToRefreshListView bookListView;
    private boolean needHelp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_facebook, container, false);
        this.bookListView = (PullToRefreshListView) view.findViewById(R.id.listViewBooks);
        //this.actuListView.setOnItemClickListener(toActu);
        this.bookListView.setOnRefreshListener(refreshBook);
        this.bookListView.setShowLastUpdatedText(true);


        File sauvegarde = new File(getActivity().getFilesDir().getPath() + "/facebook.data");

        SharedPreferences settings = getActivity().getSharedPreferences("help", 0);
        needHelp = settings.getBoolean("aide", true);

        if (!sauvegarde.exists()) {
            if (isNetworkAvailable()) {
                FetchBooksWithProgress fetchBooks = new FetchBooksWithProgress();
                fetchBooks.execute();
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

                ArrayList<Facebook> lesBooks = new ArrayList<Facebook>();

                lesBooks = (ArrayList<Facebook>) ois.readObject();

                FacebookAdapter adapter = new FacebookAdapter(getActivity(), R.layout.fragment_facebook, R.layout.ligne_facebook, lesBooks);
                bookListView.setAdapter(adapter);

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

        tv.setText("Glisser vers le bas pour actualiser la liste facebook.");
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

    private PullToRefreshListView.OnRefreshListener refreshBook = new PullToRefreshListView.OnRefreshListener() {
        @Override
        public void onRefresh() {
            if (isNetworkAvailable()) {
                FetchBooks fetchBooks = new FetchBooks();
                fetchBooks.execute();
            } else {
                Toast.makeText(getActivity(), "Pas de connexion internet disponible, l'application ne peut pas récupérer les books.", Toast.LENGTH_SHORT).show();
                bookListView.onRefreshComplete();
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


    private class FetchBooks extends AsyncTask<Void, Integer, ArrayList<Facebook>> {

        @Override
        protected void onPostExecute(ArrayList<Facebook> listeBooks) {
            FacebookAdapter adapter = new FacebookAdapter(getActivity(), R.layout.fragment_facebook, R.layout.ligne_facebook, listeBooks);
            bookListView.setAdapter(adapter);
            bookListView.onRefreshComplete();

            try {

                FileOutputStream fos = new FileOutputStream(getActivity().getFilesDir().getPath() + "/facebook.data");
                ObjectOutputStream oos = new ObjectOutputStream(fos);

                oos.writeObject(listeBooks);

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
            ArrayList<Facebook> lesBooks = new ArrayList<Facebook>();
            try {
                System.out.println("je passe implementation DOMPARSER");
                DOMParserFace leParser = new DOMParserFace();
                lesBooks = leParser.getLesBooks();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //System.out.println(lesBooks.toString());
            return lesBooks;
        }
    }

    private class FetchBooksWithProgress extends AsyncTask<Void, Integer, ArrayList<Facebook>> {
        private ProgressDialog progress;

        @Override
        protected void onPostExecute(ArrayList<Facebook> listeBooks) {
            FacebookAdapter adapter = new FacebookAdapter(getActivity(), R.layout.fragment_facebook, R.layout.ligne_facebook, listeBooks);
            bookListView.setAdapter(adapter);

            progress.cancel();
            bookListView.onRefreshComplete();

            try {
                FileOutputStream fos = new FileOutputStream(getActivity().getFilesDir().getPath() + "/facebook.data");
                ObjectOutputStream oos = new ObjectOutputStream(fos);

                oos.writeObject(listeBooks);

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
            ArrayList<Facebook> lesBooks = new ArrayList<Facebook>();
            try {
                DOMParserFace leParser = new DOMParserFace();
                lesBooks = leParser.getLesBooks();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return lesBooks;
        }

        @Override
        protected void onPreExecute() {
            progress = new ProgressDialog(getActivity(), R.style.ProgressDialogTheme);
            progress.setMessage("Recherche des books ...");
            progress.show();
        }
    }
}