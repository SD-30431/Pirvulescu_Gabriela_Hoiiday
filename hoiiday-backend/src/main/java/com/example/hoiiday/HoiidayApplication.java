package com.example.hoiiday;

import com.example.hoiiday.DTO.UserDTO;
import com.example.hoiiday.model.User;
import com.example.hoiiday.model.enums.UserRole;
import com.example.hoiiday.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.hoiiday.repository")
@EntityScan(basePackages = "com.example.hoiiday.model")
@EnableAsync
public class HoiidayApplication {

	public static void main(String[] args) {

		SpringApplication.run(HoiidayApplication.class, args);
	}
}
