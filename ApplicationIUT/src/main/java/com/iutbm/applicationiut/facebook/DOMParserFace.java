package com.iutbm.applicationiut.facebook;


import com.iutbm.applicationiut.Twitter.Tweet;
import com.iutbm.applicationiut.jsoup.Actualite;
import com.iutbm.applicationiut.jsoup.Parser;

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
    private ArrayList<Facebook> lesBooks;
    private String url_rss = "https://www.facebook.com/feeds/page.php?format=rss20&id=122353991130308";

    public DOMParserFace() throws ParserConfigurationException, SAXException, IOException {
        lesBooks = new ArrayList<Facebook>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setCoalescing(true);
        factory.setValidating(false);
        DocumentBuilder builder = factory.newDocumentBuilder();
        String out = new Scanner(new URL(url_rss).openStream(), "UTF-8").useDelimiter("\\A").next();
        doc = builder.parse(new ByteArrayInputStream(out.getBytes()));
        lesBooks = analyze(doc, 0);
    }

    public String replaceSpecialChars(String str) {
        return str.replace("&#xe9;", "é").replace("&#xe0;", "à").replace("&#x2019;", "'").
                replace("&#064;", "@").replace("&#039;", "'").replace("&#xe2;", "â").
                replace("&quot;", "\"").replace("<br />", "\n");
    }

    public ArrayList<Facebook> analyze(Node node, int depth) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            if (element.getNodeName().contentEquals("item")) {
                NodeList elements = element.getChildNodes();

                //Titre
                String titre = elements.item(3).getTextContent();
                //Description
                Node description = elements.item(7);
                String resume = this.replaceSpecialChars(description.getTextContent());
                if (resume.startsWith("<a")) {
                    resume = "";
                }

                //Link
                String link = new String();
                String da_link = new String();
                if (resume.contains("href=") && resume.contains("\" ")) {
                    link = "<a href=\"";
                    da_link = resume.substring(resume.indexOf("href=") + 6);
                    da_link = da_link.substring(0, da_link.indexOf("\" "));
                    link += da_link + "\">Cliquez ici</a>";
                }
                System.out.println("Ceci est le lien --->" + link);

                //Image
                String image = new String();
                if (resume.contains("src=") && resume.contains("\" ")) {
                    image = resume.substring(resume.indexOf("src=") + 5);
                    image = image.substring(0, image.indexOf("alt") - 2);
                }

                if (resume.contains("<br/>"))
                    resume = resume.substring(0, resume.indexOf("<br/>"));


                //Date
                String pubDate = elements.item(9).getTextContent();
                Date date = null;
                SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
                try {
                    date = dateFormat.parse(pubDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (resume != "") {
                    Facebook actu = new Facebook(resume, date, link, image);
                    lesBooks.add(actu);
                }
            }
        }

        for (int i = 0; i < node.getChildNodes().getLength(); i++) {
            analyze(node.getChildNodes().item(i), depth + 1);
        }
        return lesBooks;
    }

    public ArrayList<Facebook> getLesBooks() {
        return lesBooks;

    }

    public void setLesBooks(ArrayList<Facebook> lesBooks) {
        this.lesBooks = lesBooks;
    }

}
