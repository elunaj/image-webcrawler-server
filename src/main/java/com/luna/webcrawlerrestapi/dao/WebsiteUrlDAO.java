package com.luna.webcrawlerrestapi.dao;

import com.luna.webcrawlerrestapi.model.WebsiteUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;

@Controller
public class WebsiteUrlDAO {

    @Autowired
    private WebsiteUrlRepo websiteUrlRepo;

    public ArrayList<WebsiteUrl> getWebsiteUrls() {
        return (ArrayList<WebsiteUrl>) websiteUrlRepo.findAll();
    }

    public WebsiteUrl createWebsiteUrl(WebsiteUrl websiteUrl) {
        return websiteUrlRepo.insert(websiteUrl);
    }
}
