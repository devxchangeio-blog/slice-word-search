package com.slice.word.search.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 
 * @author karthy
 *
 */
@SpringBootApplication(scanBasePackages = "com.slice.word.search")
public class SliceWordSearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(SliceWordSearchApplication.class, args);
	}
}
