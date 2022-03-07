package com.contactmanager.helper;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CustomWebMvcAutoConfig implements WebMvcConfigurer {

	String myExternalFilePath = "file:/home/project/uploads/"; // end your path with a /
	
    @Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// TODO Auto-generated method stub
    	System.out.println(this.myExternalFilePath+"-------------------");
		registry.addResourceHandler("/uploads/**").addResourceLocations(myExternalFilePath);
	}

    
}