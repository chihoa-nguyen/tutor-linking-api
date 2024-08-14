package com.nchowf.tutorlinking.config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {
    @Bean
    public Cloudinary cloudinary() {
        final Map<String, String> config = new HashMap<>();
        config.put("cloud_name", "dmmlklkge");
        config.put("api_key", "972929676626537");
        config.put("api_secret", "5Eiq3T64umJSkDbF7OItCf5M_9U");
        return new Cloudinary(config);
    }
}
