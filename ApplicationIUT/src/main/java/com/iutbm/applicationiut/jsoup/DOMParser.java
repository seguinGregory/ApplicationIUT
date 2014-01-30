package com.iutbm.applicationiut.jsoup;


import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class DOMParser {
    private Document doc;
    private ArrayList<Actualite> lesActus;
    private String url_rss = "http://www.iut-bm.univ-fcomte.fr/rss.php?site=72&langue=1&id=7160";

    public DOMParser() throws ParserConfigurationException, SAXException, IOException {
        lesActus = new ArrayList<Actualite>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setCoalescing(true);
        factory.setValidating(false);
        DocumentBuilder builder = factory.newDocumentBuilder();
        String out = new Scanner(new URL(url_rss).openStream(), "UTF-8").useDelimiter("\\A").next();
        doc = builder.parse(new ByteArrayInputStream(out.getBytes()));
        lesActus = analyze(doc, 0);
    }

    public String replaceSpecialChars(String str) {
        return str.replace("&#xe9;", "é").replace("&#xe0;", "à").replace("&#x2019;", "'").
                replace("&#064;", "@").replace("&#039;", "'").replace("&#xe2;", "â").
                replace("&quot;", "\"").replace("&rsquo;", "\'").replace("&ecirc;", "ê");
    }

    public ArrayList<Actualite> analyze(Node node, int depth) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            if (element.getNodeName().contentEquals("item")) {
                NodeList elements = element.getChildNodes();
                //Titre
                String title = replaceSpecialChars(elements.item(1).getTextContent());
                //R�sum�
                Node description = elements.item(3);
                String resume = replaceSpecialChars(description.getTextContent().replace("<p>", "").replace("</p>", "").replace("&acirc;", "â")); 
                if (resume.contains("</a>")) {
                    String lien = resume.substring(resume.indexOf("href=")+6, resume.indexOf("\" "));
                    resume = resume.substring(0,resume.indexOf("<a href"));
                    resume += lien;
                }
                //Date
                String pubDate = elements.item(5).getTextContent();
                Date date = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss", Locale.ENGLISH);
                try {
                    date = dateFormat.parse(pubDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                //Lien contenu
                String link = elements.item(7).getTextContent();
                //Appelle de la classe Parser qui fetch le contenu gr�ce � Jsoup
                Parser dat_parser = new Parser(link);

                Actualite actu = new Actualite(title, "", resume, dat_parser.parseActu(), date);
                lesActus.add(actu);
            }
        }

        for (int i = 0; i < node.getChildNodes().getLength(); i++) {
            analyze(node.getChildNodes().item(i), depth + 1);
        }
        return lesActus;
    }

    public ArrayList<Actualite> getLesActus() {
        return lesActus;
    }

    public void setLesActus(ArrayList<Actualite> lesActus) {
        this.lesActus = lesActus;
    }
}
