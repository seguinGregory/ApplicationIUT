package com.iutbm.applicationiut;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by greg on 29/11/13.
 */
public class LPDetailleFragment extends Fragment {
    private String idFormation;
    private TextView intituleFormation;
    private WebView caracFormation;
    private WebView presentationFormation;
    private WebView publicFormation;
    private WebView insertionFormation;
    private LinearLayout layout;
    private Animation vanish;

    public LPDetailleFragment(String idFormation) {
        this.idFormation = idFormation;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lp_detaille, container, false);

        this.getActivity().getActionBar().setTitle("Licence pro");

        intituleFormation = (TextView) view.findViewById(R.id.textViewIntituleLP);
        caracFormation = (WebView) view.findViewById(R.id.webViewCaracLP);
        presentationFormation = (WebView) view.findViewById(R.id.webViewPresentationLP);
        publicFormation = (WebView) view.findViewById(R.id.webViewPublicLP);
        insertionFormation = (WebView) view.findViewById(R.id.webViewInsertionLP);
        layout = (LinearLayout) view.findViewById(R.id.linearLayoutLP);
        vanish = AnimationUtils.loadAnimation(this.getActivity(), R.anim.vanish);

        List<String> detailFormation = null;
        if (idFormation.equals("ASS_LP")) {
            detailFormation = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.ASS_LP)));
        } else if (idFormation.equals("CTPEB_LP")) {
            detailFormation = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.CTPEB_LP)));
        } else if (idFormation.equals("ENR_LP")) {
            detailFormation = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.ENR_LP)));

            Button video = new Button(this.getActivity());
            ImageView separator = new ImageView(this.getActivity());
            separator.setImageResource(R.drawable.separator);
            video.setText("Vidéo de présentation");
            video.setTextColor(Color.WHITE);
            video.setBackgroundColor(Color.parseColor("#971354"));
            video.setOnClickListener(toVideo);

            layout.addView(separator);
            layout.addView(video);
        } else if (idFormation.equals("FVPI_LP")) {
            detailFormation = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.FVPI_LP)));
        } else if (idFormation.equals("VEGA_LP")) {
            detailFormation = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.VEGA_LP)));

            ImageView stat1 = new ImageView(this.getActivity());
            stat1.setImageResource(R.drawable.statvega);
            stat1.setPadding(20, 20, 20, 20);
            ImageView stat2 = new ImageView(this.getActivity());
            stat2.setImageResource(R.drawable.statvega2);
            stat2.setPadding(20, 20, 20, 20);

            layout.addView(stat1);
            layout.addView(stat2);
        } else if (idFormation.equals("TIC_LP")) {
            detailFormation = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.TIC_LP)));
        } else if (idFormation.equals("TEPROW_LP")) {
            detailFormation = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.TEPROW_LP)));
        } else if (idFormation.equals("CIM_LP")) {
            detailFormation = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.CIM_LP)));
        } else if (idFormation.equals("CART_LP")) {
            detailFormation = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.CART_LP)));
        } else if (idFormation.equals("DORA_LP")) {
            detailFormation = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.DORA_LP)));
        }

        intituleFormation.setText(detailFormation.get(0));

        caracFormation.loadData("<html><body bgcolor=\"F1E7C4\">" + detailFormation.get(1) + "</body></html>", "text/html; charset=UTF-8", null);
        presentationFormation.loadData("<html><body bgcolor=\"F1E7C4\">" + detailFormation.get(2) + "</body></html>", "text/html; charset=UTF-8", null);
        publicFormation.loadData("<html><body bgcolor=\"F1E7C4\">" + detailFormation.get(3) + "</body></html>", "text/html; charset=UTF-8", null);
        insertionFormation.loadData("<html><body bgcolor=\"F1E7C4\">" + detailFormation.get(4) + "</body></html>", "text/html; charset=UTF-8", null);

        return view;
    }


    private View.OnClickListener toVideo = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            v.startAnimation(vanish);
            v.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?feature=player_embedded&v=sFWr4L1jSHc")));
                    Log.i("Video", "Video Playing....");
                }
            }, vanish.getDuration());
        }
    };
}