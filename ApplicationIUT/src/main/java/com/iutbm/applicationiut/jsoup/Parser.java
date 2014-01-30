package com.iutbm.applicationiut.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Parser {
    private String url;

    public Parser(String url) {
        this.url = url;
    }

    public ArrayList<String> parseActu() {
        ArrayList<String> lesParagraphes = new ArrayList<String>();
        String actuContenu = "";
        System.out.println("DEBUG : url param jsoup => " + url);
        try {
            Document docCont = Jsoup.connect(url).get();
            Element actuCont = docCont.select("div.divcontenu div.divcontenu").first();
            if (actuCont != null) {
                actuContenu = actuCont.toString();
                actuContenu = Jsoup.parse(actuContenu.replaceAll("(?i)<br[^>]*>", "br2n").replaceAll("(?i)<p[^>]*>", "p2split")).text();
                actuContenu = actuContenu.replaceAll("br2n", "\n");
                String[] lesParas = actuContenu.split("p2split");
                List<String> list = Arrays.asList(lesParas);
                lesParagraphes = new ArrayList<String>(list);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lesParagraphes;
    }

}
