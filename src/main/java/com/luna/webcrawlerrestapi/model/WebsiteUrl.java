package com.luna.webcrawlerrestapi.model;

/*
 * Class built in order to distinguish logos from other web images
 *
 */

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class WebsiteUrl {

    //static id generator shared among all instances of Coordinates
    private static final AtomicInteger idGenerator = new AtomicInteger(1);

    private final Integer id;
    private String imageTag; // holds either a "logo" or "image" value
    private String link; // holds urls for image

    public WebsiteUrl(String imageTag, String link) {
        this.id = idGenerator.getAndIncrement();
        this.imageTag = imageTag;
        this.link = link;
    }

    public Integer getId() {
        return id;
    }

    public String getImageTag() {
        return imageTag;
    }

    public void setImageTag(String imageTag) {
        this.imageTag = imageTag;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WebsiteUrl websiteUrl = (WebsiteUrl) o;
        return link == websiteUrl.link &&
                Objects.equals(id, websiteUrl.id) &&
                Objects.equals(imageTag, websiteUrl.imageTag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, imageTag, link);
    }

    @Override
    public String toString() {
        return "WebsiteUrl{" +
                "id=" + id +
                ", imageTag='" + imageTag + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}
