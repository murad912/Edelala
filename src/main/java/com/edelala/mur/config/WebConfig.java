package com.edelala.mur.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.beans.factory.annotation.Value;

//@Configuration
//public class WebConfig implements WebMvcConfigurer {
//
//    @Value("${upload.dir}")
//    private String uploadDir; // This will now be an absolute path from application.properties
//
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        // Maps requests to /uploads/** to the absolute file system directory specified by uploadDir
//        registry.addResourceHandler("/uploads/**")
//                .addResourceLocations("file:" + uploadDir + "/"); // IMPORTANT: Add trailing slash
//        System.out.println("WebConfig: Registering resource handler for /uploads/** to: file:" + uploadDir + "/");
//    }
//} jun 24 add for image not display issue

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${upload.dir}")
    private String uploadDir; // This will now be "uploads"

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // This is the correct way to map a relative path like "uploads"
        // to a URL path when the application is run from the directory containing "uploads".
        String absoluteUploadPath = "file:" + System.getProperty("user.dir") + "/" + uploadDir + "/";
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(absoluteUploadPath);
        System.out.println("WebConfig: Registering resource handler for /uploads/** to: " + absoluteUploadPath);
    }
}
