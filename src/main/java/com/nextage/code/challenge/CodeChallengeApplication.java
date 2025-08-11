package com.nextage.code.challenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class CodeChallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodeChallengeApplication.class, args);
	}

}
