package com.iutbm.applicationiut.Twitter;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.Period;
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

public class TwParser {
    private Document doc;
    private ArrayList<Tweet> lesTweet;
    private String url_rss = "https://script.google.com/macros/s/AKfycbzjIDrfBF4lX-WghhxMb1BAOqrjKWC1BmF7dvKC7l_XDhdIookB/exec?action=search&q=montbel_iut";

    public TwParser() throws ParserConfigurationException, SAXException, IOException {
        lesTweet = new ArrayList<Tweet>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setCoalescing(true);
        factory.setValidating(false);
        DocumentBuilder builder = factory.newDocumentBuilder();
        String out = new Scanner(new URL(url_rss).openStream(), "UTF-8").useDelimiter("\\A").next();
        doc = builder.parse(new ByteArrayInputStream(out.getBytes()));
        lesTweet = analyze(doc, 0);
    }

    public ArrayList<Tweet> analyze(Node node, int depth) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            if (element.getNodeName().contentEquals("item")) {
                NodeList elements = element.getChildNodes();
                //description
                String description = elements.item(4).getTextContent();
                //String resume = description.getTextContent().replace("<p>", "").replace("</p>", "");
                //Date
                String pubDate = elements.item(1).getTextContent();
                Date date = null;
                SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss", Locale.ENGLISH);
                try {
                    date = dateFormat.parse(pubDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                String titre = elements.item(0).getTextContent();
                titre = "@" + titre.substring(0, titre.indexOf(":"));

                Date now = new Date();

                String sincePost = getSincePost(date, now);
                ;

                Tweet twe = new Tweet(titre, description, sincePost);
                lesTweet.add(twe);
            }
        }

        for (int i = 0; i < node.getChildNodes().getLength(); i++) {
            analyze(node.getChildNodes().item(i), depth + 1);
        }
        return lesTweet;
    }

    private String getSincePost(Date date, Date now) {
        String sincePost;
        Interval interval = new Interval(new DateTime(date), new DateTime(now));
        Period period = interval.toPeriod();

        if (period.getYears() == 0) {
            if (period.getMonths() == 0) {
                if (period.getWeeks() == 0) {
                    if (period.getDays() == 0) {
                        if (period.getHours() == 0) {
                            sincePost = period.getMinutes() + "min";
                        } else {
                            sincePost = period.getHours() + "h";
                        }
                    } else {
                        sincePost = period.getDays() + "j";
                    }
                } else {
                    sincePost = period.getWeeks() + "sem";
                }
            } else {
                sincePost = period.getMonths() + "mois";
            }
        } else {
            sincePost = period.getYears() + "an";
        }
        return sincePost;
    }

    public ArrayList<Tweet> getLesTweet() {
        return lesTweet;
    }

    public void setLesTweet(ArrayList<Tweet> lesTweet) {
        this.lesTweet = lesTweet;
    }
}
