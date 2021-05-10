package com.nobokko.numberplace4j;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Numberplace4jApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(Numberplace4jApplication.class);
		app.setDefaultProperties(Collections.singletonMap("server.port", "8079"));
		app.run(args);
	}

}
