package com.lab.securing_project_tracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SecuringProjectTrackerApplication {

	public static void main(String[] args) {

		SpringApplication.run(SecuringProjectTrackerApplication.class, args);
	}

}
