package de.weconvert.restspringbootexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class RestSpringbootExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestSpringbootExampleApplication.class, args);
	}

}
