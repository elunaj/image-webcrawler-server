package com.luna.webcrawlerrestapi.dao;

import com.luna.webcrawlerrestapi.model.WebsiteUrl;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WebsiteUrlRepo extends MongoRepository<WebsiteUrl, Integer> {

}
