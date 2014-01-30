package com.iutbm.applicationiut.facebook;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class DOMParserFace {
    private Document doc;
    private ArrayList<Facebook> lesActus;
    private String url_rss = "https://www.facebook.com/feeds/page.php?format=rss20&id=122353991130308";

    public DOMParserFace() throws ParserConfigurationException, SAXException, IOException {
        lesActus = new ArrayList<Facebook>();
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
                replace("&quot;", "\"");
    }

    public ArrayList<Facebook> analyze(Node node, int depth) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            if (element.getNodeName().contentEquals("item")) {
                NodeList elements = element.getChildNodes();
                //Titre
                String titre = this.replaceSpecialChars(elements.item(3).getTextContent());

                //Description
                Node description = elements.item(7);
                String resume = this.replaceSpecialChars(description.getTextContent());

                //Date
                String pubDate = elements.item(9).getTextContent();
                Date date = null;
                SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss", Locale.ENGLISH);
                try {
                    date = dateFormat.parse(pubDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Facebook actu = new Facebook(titre, resume, date);
                lesActus.add(actu);
            }
        }

        for (int i = 0; i < node.getChildNodes().getLength(); i++) {
            analyze(node.getChildNodes().item(i), depth + 1);
        }
        return lesActus;
    }

    public ArrayList<Facebook> getLesActus() {
        return lesActus;
    }

}
