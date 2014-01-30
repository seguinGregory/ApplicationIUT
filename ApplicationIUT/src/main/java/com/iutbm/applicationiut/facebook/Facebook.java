package com.iutbm.applicationiut.facebook;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by P-E on 17/12/13.
 */
public class Facebook implements Serializable {
    private String description, link, image;
    private Date date;

    public Facebook(String description, Date date, String link, String image) {
        super();
        this.description = description;
        this.date = date;
        this.link = link;
        this.image = image;
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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Facebook{" +
                "description='" + description + '\'' +
                ", link='" + link + '\'' +
                ", image='" + image + '\'' +
                ", date=" + date +
                '}';
    }
}
