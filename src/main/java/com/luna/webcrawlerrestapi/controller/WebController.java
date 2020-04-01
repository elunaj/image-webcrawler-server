package com.luna.webcrawlerrestapi.controller;

import com.luna.webcrawlerrestapi.model.PostRequest;
import com.luna.webcrawlerrestapi.model.PostResponse;
import com.luna.webcrawlerrestapi.model.SampleResponse;
import com.luna.webcrawlerrestapi.model.WebsiteUrl;
import com.luna.webcrawlerrestapi.service.WebsiteUrlService;
import com.luna.webcrawlerrestapi.model.SampleResponse;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.util.ArrayList;

@CrossOrigin(origins = {"*"}, exposedHeaders = {"Content-Disposition"})
@RestController
public class WebController {

    @GetMapping("/sample")
    public SampleResponse Sample(@RequestParam(value = "name",
            defaultValue = "Robot") String name) {
                    SampleResponse response = new SampleResponse();
                    response.setId(1);
                    response.setMessage("Your name is " + name);
                    return response;
    }

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

        return response;
    }

    @GetMapping(value="/download")
    public ResponseEntity<byte[]> Download(@RequestParam(value="imageLink")
                                                       String image) {

        //Service handler
        WebsiteUrlService service = new WebsiteUrlService();

        String link = image;

        byte[] response = service.downloadImage(link);
        MediaType mediaType = service.processMediaType(link);

        HttpHeaders header = new HttpHeaders();
        header.setContentType(mediaType);
        header.setContentLength(response.length);
//        header.set("Access-Control-Allow-Origin", "*");
//        header.set("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
//        header.set("Access-Control-Max-Age", "3600");
//        header.set("Access-Control-Allow-Headers", "authorization, content-type, xsrf-token");
//        header.set("Access-Control-Expose-Headers", "xsrf-token");
        header.set("Content-Disposition", "attachment; filename=" + link );

        return new ResponseEntity<>(response, header, HttpStatus.OK);

    }

    @PostMapping("/test")
    public PostResponse Test(@RequestBody PostRequest inputPayload) {
        PostResponse response = new PostResponse();
        response.setId(inputPayload.getId() * 100);
        response.setMessage("Hello " + inputPayload.getName());
        response.setExtra("Some text");
        return response;
    }
}
