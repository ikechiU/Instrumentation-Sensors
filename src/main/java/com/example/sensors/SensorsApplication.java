package com.example.sensors;

import com.cloudinary.Cloudinary;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class SensorsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SensorsApplication.class, args);
    }

    //Endeavor to put your own credentials here
    @Bean
    public Cloudinary cloudinaryConfig() {
        Cloudinary cloudinary = null;
        Map config = new HashMap();
        config.put("cloud_name", "[YOUR OWN CLOUD NAME]");
        config.put("api_key", "[YOUR OWN API KEY]");
        config.put("api_secret", "[YOUR OWN API SECRET]");
        cloudinary = new Cloudinary(config);
        return cloudinary;
    }

}
