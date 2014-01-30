package com.iutbm.applicationiut.Twitter;

/**
 * Created by PixelMut on 06/12/13.
 */

import java.io.Serializable;

public class Tweet implements Serializable {
    private String titre, contenu;
    private String sincePost;

    public Tweet(String titre, String contenu, String sincePost) {
        super();
        this.titre = titre;
        this.contenu = contenu;
        this.sincePost = sincePost;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getSincePost() {
        return sincePost;
    }

    public void setSincePost(String sincePost) {
        this.sincePost = sincePost;
    }

    @Override
    public String toString() {
        return "Tweet [titre=" + titre + ", contenu=" + contenu + ", sincePost="
                + sincePost + "]";
    }
}

