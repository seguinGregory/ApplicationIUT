package com.iutbm.applicationiut;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

/**
 * Created by Greg on 08/11/13.
 */
public class AccueilFragment extends Fragment {
    private Button buttonActualites;
    private Button buttonFormations;
    private Button buttonEcoCampus;
    private Button buttonAgenda;
    private Button buttonFacebook;
    private Button buttonTwitter;
    private Button buttonIUT;
    private Button buttonUniversite;
    private Animation vanish;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_accueil, container, false);
        vanish = AnimationUtils.loadAnimation(this.getActivity(), R.anim.vanish);
        buttonActualites = (Button) view.findViewById(R.id.buttonActualites);
        buttonFormations = (Button) view.findViewById(R.id.buttonFormations);
        buttonEcoCampus = (Button) view.findViewById(R.id.buttonEcoCampus);
        buttonAgenda = (Button) view.findViewById(R.id.buttonAgenda);
        buttonFacebook = (Button) view.findViewById(R.id.buttonFacebook);
        buttonTwitter = (Button) view.findViewById(R.id.buttonTwitter);
        buttonIUT = (Button) view.findViewById(R.id.buttonIUT);
        buttonUniversite = (Button) view.findViewById(R.id.buttonUniversite);

        Typeface typeFace = Typeface.createFromAsset(this.getActivity().getAssets(), "Comic_Book.ttf");
        buttonActualites.setTypeface(typeFace);
        buttonFacebook.setTypeface(typeFace);
        buttonAgenda.setTypeface(typeFace);
        buttonEcoCampus.setTypeface(typeFace);
        buttonIUT.setTypeface(typeFace);
        buttonFormations.setTypeface(typeFace);
        buttonUniversite.setTypeface(typeFace);
        buttonTwitter.setTypeface(typeFace);

        buttonFormations.setOnClickListener(toFormations);
        buttonEcoCampus.setOnClickListener(toEcoCampus);
        buttonIUT.setOnClickListener(toIUT);
        buttonUniversite.setOnClickListener(toUniversite);
        buttonAgenda.setOnClickListener(toAgenda);
        buttonActualites.setOnClickListener(toActualites);
        buttonFacebook.setOnClickListener(toFacebook);
        buttonTwitter.setOnClickListener(toTwitter);

        return view;
    }

    private View.OnClickListener toFormations = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            buttonFormations.startAnimation(vanish);
            buttonFormations.postDelayed(new Runnable() {
                @Override
                public void run() {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction ft = fragmentManager.beginTransaction();
                    ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
                    ft.replace(R.id.container, new FormationsFragment()).addToBackStack("retour8").commit();
                }
            }, vanish.getDuration());
        }
    };

    private View.OnClickListener toEcoCampus = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            buttonEcoCampus.startAnimation(vanish);
            buttonEcoCampus.postDelayed(new Runnable() {
                @Override
                public void run() {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction ft = fragmentManager.beginTransaction();
                    ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
                    ft.replace(R.id.container, new EcoCampusFragment()).addToBackStack("retour9").commit();
                }
            }, vanish.getDuration());
        }
    };

    private View.OnClickListener toIUT = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            buttonIUT.startAnimation(vanish);
            buttonIUT.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent toIut = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.iut-bm.univ-fcomte.fr/‎"));
                    startActivity(toIut);
                }
            }, vanish.getDuration());

        }
    };

    private View.OnClickListener toUniversite = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            buttonUniversite.startAnimation(vanish);
            buttonUniversite.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent toUniversite = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.univ-fcomte.fr/"));
                    startActivity(toUniversite);
                }
            }, vanish.getDuration());

        }
    };

    private View.OnClickListener toAgenda = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            buttonAgenda.startAnimation(vanish);
            buttonAgenda.postDelayed(new Runnable() {
                @Override
                public void run() {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction ft = fragmentManager.beginTransaction();
                    ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
                    ft.replace(R.id.container, new AgendaFragment()).addToBackStack("retour10").commit();
                }
            }, vanish.getDuration());
        }
    };

    private View.OnClickListener toActualites = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            buttonActualites.startAnimation(vanish);
            buttonActualites.postDelayed(new Runnable() {
                @Override
                public void run() {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction ft = fragmentManager.beginTransaction();
                    ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
                    ft.replace(R.id.container, new ActualiteFragment()).addToBackStack("retour11").commit();
                }
            }, vanish.getDuration());

        }
    };

    private View.OnClickListener toFacebook = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            buttonFacebook.startAnimation(vanish);
            buttonFacebook.postDelayed(new Runnable() {
                @Override
                public void run() {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction ft = fragmentManager.beginTransaction();
                    ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
                    ft.replace(R.id.container, new FacebookFragment()).addToBackStack("retour12").commit();
                }
            }, vanish.getDuration());

        }
    };

    private View.OnClickListener toTwitter = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            buttonTwitter.startAnimation(vanish);
            buttonTwitter.postDelayed(new Runnable() {
                @Override
                public void run() {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction ft = fragmentManager.beginTransaction();
                    ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
                    ft.replace(R.id.container, new TwitterFragment()).addToBackStack("retour13").commit();
                }
            }, vanish.getDuration());

        }
    };


}