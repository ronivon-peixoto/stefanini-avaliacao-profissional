package com.stefanini.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class StefaniniAppServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(StefaniniAppServerApplication.class, args);
	}
}
