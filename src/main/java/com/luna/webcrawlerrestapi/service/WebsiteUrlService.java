package com.luna.webcrawlerrestapi.service;

import com.luna.webcrawlerrestapi.dao.WebsiteUrlDAO;
import com.luna.webcrawlerrestapi.model.WebsiteUrl;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;


import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

@Service
public class WebsiteUrlService {

    /**
     *
     */
    @Autowired
    private WebsiteUrlDAO websiteUrlDAO;

    // hashmap to hold image links for urls already searched
    private HashMap<String, ArrayList<WebsiteUrl>> links;

    // instantiate hashmap when class loads
    public WebsiteUrlService() {
        this.links = new HashMap<String, ArrayList<WebsiteUrl>>();
    }

    //Method finds links in HTML page and calls findImages() for each
    public final ArrayList<String> findLinks(String url) {

        ArrayList<String> listOfLinks = new ArrayList<>();
        String websiteUrl = url;

        try {
            Document doc = Jsoup.connect(websiteUrl)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.132 Safari/537.36")
                    .referrer("http://www.google.com")
                    .get();

            int count = 0;
            Elements links = doc.select("a[href]");

            for (Element link : links) {
                if (count <= 5) {
                    //add url to listOfLinks
                    listOfLinks.add(link.attr("abs:href"));
                    count++;
                }
                //break if 5 links found
                if (count == 5) {
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return listOfLinks;
    }

    //Implementation of Jsoup web crawl
    public final ArrayList<WebsiteUrl> findImages(String url) {

        ArrayList<WebsiteUrl> urlImages = new ArrayList<>();
        String websiteUrl = url;

        if (!this.links.containsKey(websiteUrl)) {
            try {
                //Connect to the website and get the html
                Document doc = Jsoup.connect(websiteUrl)
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.132 Safari/537.36")
                        .referrer("http://www.google.com")
                        .get();

                //Get all elements with img tag
                Elements img = doc.getElementsByTag("img");

                for (Element el : img) {
                    //for each element get the src url with jpg or png file extension
                    if (el.attr("alt").toLowerCase().contains("logo") &&
                            el.attr("alt").toLowerCase().endsWith("jpg") ||
                            el.attr("src").toLowerCase().contains("logo") &&
                            el.attr("alt").toLowerCase().endsWith("png")) {

                        String src = el.absUrl("src");

                        //tag images deemed logos
                        WebsiteUrl newUrl = new WebsiteUrl("logo", src);

                        //add img url to return arraylist
                        urlImages.add(newUrl);

                    } else if (!el.attr("src").endsWith("gif") &&
                                    el.attr("src").toLowerCase().endsWith("jpg") ||
                                    !el.attr("src").endsWith("gif") &&
                                    el.attr("src").toLowerCase().endsWith("png"))  {

                        String src = el.absUrl("src");

                        //tag non-logo images
                        WebsiteUrl newUrl = new WebsiteUrl("image", src);

                        //add img url to return arraylist
                        urlImages.add(newUrl);
                    }
                }

                //add website url as hashmap key, and entire url array of tags/links
                this.links.put(websiteUrl, urlImages);
                System.out.println("Added to map" + this.links);

            } catch (IOException e) {
                e.printStackTrace();
            }

            // enter this else block if website already crawled
        } else {
            //return value(list of urls) for websiteUrl key
            urlImages = this.links.get(websiteUrl);
            System.out.println("In map already" + this.links);
        }

        return urlImages;
    }

    //Handles image download logic
    public byte[] downloadImage(String strImageUrl) {

        String strImageName =
                strImageUrl.substring( strImageUrl.lastIndexOf("/"));

        ByteArrayOutputStream os =
                new ByteArrayOutputStream();

        System.out.println("Saving: " + strImageName + ", from: " + strImageUrl);

        try {

            //open the stream from URL
            URL urlImage = new URL(strImageUrl);
            InputStream in = urlImage.openStream();

            byte[] buffer = new byte[4096];
            int n = 0;

            //write bytes to the output stream
            while ( (n = in.read(buffer)) != -1) {
                os.write(buffer, 0, n);
            }

            //close the streams
            os.close();
            in.close();

            System.out.println("Image saved");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return os.toByteArray();
    }

    public MediaType processMediaType(String path){
        MediaType type = MediaType.ALL;

        if (path.endsWith("jpg")){
            type = MediaType.IMAGE_JPEG;
        } else if (path.endsWith("png")){
            type = MediaType.IMAGE_PNG;
        }
        return type;
    }

    public ArrayList<WebsiteUrl> getWebsiteUrls() {
        return websiteUrlDAO.getWebsiteUrls();
    }

    public WebsiteUrl createWebsiteUrl(WebsiteUrl websiteUrl) {
        return websiteUrlDAO.createWebsiteUrl(websiteUrl);
    }
}
