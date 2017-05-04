package com.suresh.projects.movieworld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages="com.suresh.projects.movieworld")
public class MovieWorldApplication extends SpringBootServletInitializer {

	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MovieWorldApplication.class);
    }	
	
	public static void main(String[] args) {
		SpringApplication.run(MovieWorldApplication.class, args);
	}
}
