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

import com.iutbm.applicationiut.Twitter.TwParser;
import com.iutbm.applicationiut.Twitter.Tweet;
import com.iutbm.applicationiut.adapter.TwitterAdapter;

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
 * Created by umut on 15/11/13.
 */
public class TwitterFragment extends Fragment {
    private PullToRefreshListView tweetListView;
    private boolean needHelp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_twitter, container, false);

        this.getActivity().getActionBar().setTitle("Twitter");

        this.tweetListView = (PullToRefreshListView) view.findViewById(R.id.listViewTweet);

        //this.tweetListView.setOnItemClickListener(toTweet);
        this.tweetListView.setOnRefreshListener(refreshTweet);
        this.tweetListView.setShowLastUpdatedText(true);

        File sauvegardeTweets = new File(getActivity().getFilesDir().getPath() + "/twitter.data");

        SharedPreferences settings = getActivity().getSharedPreferences("help", 0);
        needHelp = settings.getBoolean("aide", true);

        if (!sauvegardeTweets.exists()) {
            if (isNetworkAvailable()) {
                FetchTweetWithProgress fetchTweet = new FetchTweetWithProgress();
                fetchTweet.execute();
            } else {
                Toast.makeText(getActivity(), "Pas de connexion internet disponible, l'application ne peut pas récupérer les tweets.", Toast.LENGTH_SHORT).show();
            }
        } else {
            try {
                if (needHelp) {
                    affichageToast();
                }

                FileInputStream fis = new FileInputStream(sauvegardeTweets);
                ObjectInputStream ois = new ObjectInputStream(fis);

                ArrayList<Tweet> lesTweet = new ArrayList<Tweet>();

                lesTweet = (ArrayList<Tweet>) ois.readObject();

                TwitterAdapter adapter = new TwitterAdapter(getActivity(), R.layout.fragment_actualite, R.layout.ligne_actu, lesTweet);
                tweetListView.setAdapter(adapter);

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

        tv.setText("Glisser vers le bas pour actualiser la liste de tweets.");
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

    private PullToRefreshListView.OnRefreshListener refreshTweet = new PullToRefreshListView.OnRefreshListener() {
        @Override
        public void onRefresh() {
            if (isNetworkAvailable()) {
                FetchTweet fetchtweets = new FetchTweet();
                fetchtweets.execute();
            } else {
                Toast.makeText(getActivity(), "Pas de connexion internet disponible, l'application ne peut pas récupérer les tweets.", Toast.LENGTH_SHORT).show();
                tweetListView.onRefreshComplete();
            }
        }
    };

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    private class FetchTweet extends AsyncTask<Void, Integer, ArrayList<Tweet>> {

        @Override
        protected void onPostExecute(ArrayList<Tweet> listeT) {
            TwitterAdapter adaptertw = new TwitterAdapter(getActivity(), R.layout.fragment_twitter, R.layout.ligne_tweet, listeT);
            tweetListView.setAdapter(adaptertw);
            tweetListView.onRefreshComplete();

            try {
                FileOutputStream fos = new FileOutputStream(getActivity().getFilesDir().getPath() + "/twitter.data");
                ObjectOutputStream oos = new ObjectOutputStream(fos);

                oos.writeObject(listeT);

                oos.close();
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected ArrayList<Tweet> doInBackground(Void... voids) {
            ArrayList<Tweet> lesTweets = new ArrayList<Tweet>();
            try {
                TwParser leParser = new TwParser();
                lesTweets = leParser.getLesTweet();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return lesTweets;
        }
    }

    private class FetchTweetWithProgress extends AsyncTask<Void, Integer, ArrayList<Tweet>> {
        private ProgressDialog progress;

        @Override
        protected void onPostExecute(ArrayList<Tweet> listeT) {

            TwitterAdapter adaptertw = new TwitterAdapter(getActivity(), R.layout.fragment_twitter, R.layout.ligne_tweet, listeT);
            tweetListView.setAdapter(adaptertw);

            progress.cancel();
            tweetListView.onRefreshComplete();

            if (needHelp) {
                affichageToast();
            }


            try {
                FileOutputStream fos = new FileOutputStream(getActivity().getFilesDir().getPath() + "/twitter.data");
                ObjectOutputStream oos = new ObjectOutputStream(fos);

                oos.writeObject(listeT);

                oos.close();
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected ArrayList<Tweet> doInBackground(Void... voids) {
            ArrayList<Tweet> lesTweet = new ArrayList<Tweet>();
            try {
                TwParser leParser = new TwParser();
                lesTweet = leParser.getLesTweet();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return lesTweet;
        }

        @Override
        protected void onPreExecute() {
            progress = new ProgressDialog(getActivity(), R.style.ProgressDialogTheme);
            progress.setMessage("Recherche des tweets ...");
            progress.show();
        }
    }
}