package com.iutbm.applicationiut.facebook;

import java.util.Date;

/**
 * Created by P-E on 17/12/13.
 */
public class Facebook {
    private String titre, description;
    private Date date;

    public Facebook(String titre, String description, Date date) {
        super();
        this.titre = titre;
        this.description = description;
        this.date = date;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Facebook [titre=" + titre + "\n description=" + description
                + ", date=" + date + "]";
    }


}
