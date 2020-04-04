package com.luna.webcrawlerrestapi.controller;

import com.luna.webcrawlerrestapi.model.WebsiteUrl;
import com.luna.webcrawlerrestapi.service.WebsiteUrlService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.util.ArrayList;

@CrossOrigin(origins = {"*"}, exposedHeaders = {"Content-Disposition"})
@RestController
public class WebController {

    @Cacheable(value="images", key="#url")
    @GetMapping("/search")
    public ArrayList<WebsiteUrl> Search(@RequestParam(value = "url")
                                                    String url) {

        //Service handler
        WebsiteUrlService service = new WebsiteUrlService();

        //Holds all links to url Images
        ArrayList<WebsiteUrl> response = new ArrayList<>();

        //Calls method that finds all links on html page
        ArrayList<String> urlList = service.findLinks(url);

        //Calls method that finds all images on html page for every link in urlList
        for (String link : urlList) {

            ArrayList<WebsiteUrl> imageLinks = service.findImages(link);

            //Append image links to response list
            response.addAll(imageLinks);
        }

        System.out.println("response" + response);

        return response;
    }

    @GetMapping(value="/download")
    public ResponseEntity<byte[]> Download(@RequestParam(value="imageLink")
                                                       String image) throws MalformedURLException{

        //Service handler
        WebsiteUrlService service = new WebsiteUrlService();

        String link = image;

        byte[] response = service.downloadImage(link);
        MediaType mediaType = service.processMediaType(link);

        HttpHeaders header = new HttpHeaders();
        header.setContentType(mediaType);
        header.setContentLength(response.length);
        header.set("Content-Disposition", "attachment; filename=" + link );

        return new ResponseEntity<>(response, header, HttpStatus.OK);

    }

}
