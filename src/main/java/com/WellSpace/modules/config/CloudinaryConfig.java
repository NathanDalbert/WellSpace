package com.WellSpace.modules.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dw1rl6msf",
                "api_key", "227698983672615",
                "api_secret", "OMuLNiiBbRRvqsNw1aWXimjTYus"));
    }
}
