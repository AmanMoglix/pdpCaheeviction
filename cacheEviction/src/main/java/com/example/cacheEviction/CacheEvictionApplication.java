package com.example.cacheEviction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class CacheEvictionApplication {

	public static void main(String[] args) {
		SpringApplication.run(CacheEvictionApplication.class, args);
	}

	@Bean
	public RestTemplate redisTemplate(){
		return  new RestTemplate();
	}

}
