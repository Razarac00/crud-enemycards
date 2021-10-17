package com.razarac.enemycrud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class EnemycrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnemycrudApplication.class, args);
	}

}
