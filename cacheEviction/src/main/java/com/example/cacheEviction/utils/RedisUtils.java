package com.example.cacheEviction.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class RedisUtils {
    private static  final  Logger logger= LoggerFactory.getLogger(RedisUtils.class);

    @Autowired
    RestTemplate restTemplate;

    public String getApi(String apiUrl,String msn){

       try {
           String url = apiUrl + msn;
           HttpHeaders headers = new HttpHeaders();
           headers.setContentType(MediaType.APPLICATION_JSON);
           HttpEntity httpEntity = new HttpEntity<>(headers);
           ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
           int httpStatus = responseEntity.getStatusCodeValue();
           logger.info("Cache evict  request processed with service HTTP code {}", httpStatus);
           return responseEntity.getBody();
       }catch (Exception e){
           e.printStackTrace();
       }
        return null;
    }
}
