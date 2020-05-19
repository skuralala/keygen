package com.cxm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class KeygenApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(KeygenApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(KeygenApplication.class, args);
	}

}

//public class KeygenApplication{
//
//	public static void main(String[] args) {
//		SpringApplication.run(KeygenApplication.class, args);
//	}
//
//}
