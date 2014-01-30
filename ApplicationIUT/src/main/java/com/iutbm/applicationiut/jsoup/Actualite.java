package com.iutbm.applicationiut.jsoup;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Actualite implements Serializable {
    private String titre, urlImage, resume;
    private ArrayList<String> contenu;
    private Date date;

    public Actualite(String titre, String urlImage, String resume,
                     ArrayList<String> contenu, Date date) {
        super();
        this.titre = titre;
        this.urlImage = urlImage;
        this.resume = resume;
        this.contenu = contenu;
        this.date = date;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public ArrayList<String> getContenu() {
        return contenu;
    }

    public void setContenu(ArrayList<String> contenu) {
        this.contenu = contenu;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Actualite [titre=" + titre + "\n urlImage=" + urlImage
                + "\n resume=" + resume + "\n contenu=" + contenu + "\n date="
                + date + "]";
    }


}
