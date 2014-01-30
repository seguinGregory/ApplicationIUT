package com.iutbm.applicationiut;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

/**
 * Created by Greg on 08/11/13.
 */
public class FormationsFragment extends Fragment {
    private Button choisirIUT;
    private Button dut;
    private Button licence;
    private Button plan;
    private Animation vanish;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_formations, container, false);

        this.getActivity().getActionBar().setTitle("Formations");

        vanish = AnimationUtils.loadAnimation(this.getActivity(), R.anim.vanish);

        choisirIUT = (Button) view.findViewById(R.id.buttonInscription);
        dut = (Button) view.findViewById(R.id.buttonDUT);
        licence = (Button) view.findViewById(R.id.buttonLP);
        plan = (Button) view.findViewById(R.id.buttonPLAN);




        choisirIUT.setOnClickListener(toChoisirIUT);
        dut.setOnClickListener(toDut);
        licence.setOnClickListener(toLicence);
        plan.setOnClickListener(toPlan);

        Typeface typeFace = Typeface.createFromAsset(this.getActivity().getAssets(), "Comic_Book.ttf");
        choisirIUT.setTypeface(typeFace);
        dut.setTypeface(typeFace);
        licence.setTypeface(typeFace);
        plan.setTypeface(typeFace);

        return view;
    }


    private View.OnClickListener toChoisirIUT = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            choisirIUT.startAnimation(vanish);
            choisirIUT.postDelayed(new Runnable() {
                @Override
                public void run() {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction ft = fragmentManager.beginTransaction();
                    ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
                    ft.replace(R.id.container, new ChoisirIUTFragment()).addToBackStack("retour14").commit();
                }
            }, vanish.getDuration());

        }
    };

    private View.OnClickListener toDut = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dut.startAnimation(vanish);
            dut.postDelayed(new Runnable() {
                @Override
                public void run() {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction ft = fragmentManager.beginTransaction();
                    ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
                    ft.replace(R.id.container, new DUTFragment()).addToBackStack("retour15").commit();
                }
            }, vanish.getDuration());

        }
    };

    private View.OnClickListener toLicence = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            licence.startAnimation(vanish);
            licence.postDelayed(new Runnable() {
                @Override
                public void run() {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction ft = fragmentManager.beginTransaction();
                    ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
                    ft.replace(R.id.container, new LicenceFragment()).addToBackStack("retour16").commit();
                }
            }, vanish.getDuration());

        }
    };

    private View.OnClickListener toPlan = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            plan.startAnimation(vanish);
            plan.postDelayed(new Runnable() {
                @Override
                public void run() {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction ft = fragmentManager.beginTransaction();
                    ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
                    ft.replace(R.id.container, new PlanFragment()).addToBackStack("retour17").commit();
                }
            }, vanish.getDuration());

        }
    };

}