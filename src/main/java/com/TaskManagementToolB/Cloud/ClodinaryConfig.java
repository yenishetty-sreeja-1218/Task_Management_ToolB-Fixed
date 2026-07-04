package com.TaskManagementToolB.Cloud;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;

@Configuration
public class ClodinaryConfig {
	
	@Value("${clodinary.cloud-name}")
	private String cloudName;
	
     @Value("${clodinary.api-key}")
     private String key;
     
     @Value("${cloudinary.api-secert}")
     private String secret;
     
     
     @Bean
     public Cloudinary cloudinary() {
    	 
    	 Map<String,Object> config= new HashMap<>();
    	 config.put("cloud-name",cloudName );
    	 config.put("api-key", key);
    	 config.put("api-secret",secret );
    	 
    	 return new Cloudinary(config);
     }
     
     
}

