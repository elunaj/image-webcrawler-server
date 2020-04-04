package com.luna.webcrawlerrestapi.model;

/*
 * Class built in order to distinguish logos from other web images
 *
 */

import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

import java.util.Date;

public class WebsiteUrl implements Serializable {

    private String id;
    private String imageUrl; // holds either a "logo" or "image" value
    private String imageLink; // holds urls for image
    private Date deleteAt;

    public WebsiteUrl(String imageUrl, String imageLink) {
        this.imageUrl = imageUrl;
        this.imageLink = imageLink;
        this.deleteAt = new Date();
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

    public Date getDeleteAt() {
        return deleteAt;
    }

    public void setDeleteAt(Date deleteAt) {
        this.deleteAt = deleteAt;
    }

    @Override
    public String toString() {
        return "WebsiteUrl{" +
                "id='" + id + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", imageLink='" + imageLink + '\'' +
                ", deleteAt=" + deleteAt +
                '}';
    }
}
