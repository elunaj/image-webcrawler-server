package com.luna.webcrawlerrestapi.model;

import java.io.Serializable;

public class WebsiteUrl implements Serializable {

    private String id;
    private String imageUrl; // holds urls found in
    private String imageLink; // holds links for images

    public WebsiteUrl(String imageUrl, String imageLink) {
        this.imageUrl = imageUrl;
        this.imageLink = imageLink;
    }

    public String getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    @Override
    public String toString() {
        return "WebsiteUrl{" +
                "id='" + id + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", imageLink='" + imageLink + '\'' +
                '}';
    }
}
