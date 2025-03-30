package com.example.hoiiday;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.hoiiday.repository")
@EntityScan(basePackages = "com.example.hoiiday.model")

public class HoiidayApplication {

	public static void main(String[] args) {
		SpringApplication.run(HoiidayApplication.class, args);
	}
}
