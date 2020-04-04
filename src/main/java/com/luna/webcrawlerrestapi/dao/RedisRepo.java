package com.luna.webcrawlerrestapi.dao;

import com.luna.webcrawlerrestapi.model.WebsiteUrl;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RedisRepo {

    private HashOperations hashOperations;

    private RedisTemplate redisTemplate;

    public RedisRepo(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = this.redisTemplate.opsForHash();
    }

    public void save(ArrayList<WebsiteUrl> websiteUrl, String url) {
        hashOperations.put("WEBSITEURL", url, websiteUrl);
    }

    public List finaAll() {
        return hashOperations.values("WEBSITEURL");
    }

    public WebsiteUrl findById(String id) {
        return (WebsiteUrl) hashOperations.get("WEBSITEURL", id);
    }


    public void delete(String id) {
        hashOperations.delete("WEBSITEURL", id);
    }

}
