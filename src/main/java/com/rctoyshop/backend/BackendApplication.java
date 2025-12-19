package com.rctoyshop.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@org.springframework.context.annotation.Bean
	public org.springframework.boot.CommandLineRunner commandLineRunner(
			com.rctoyshop.backend.repository.UserRepository userRepository) {
		return args -> {
			System.out.println("================= DB USER DUMP START =================");
			userRepository.findAll().forEach(u -> {
				System.out.println("ID: " + u.getId() +
						", Email: [" + u.getEmail() + "]" +
						", Password: [" + u.getPassword() + "]" +
						", Role: " + u.getRole() +
						", Status: " + u.getStatus());
			});
			System.out.println("================= DB USER DUMP END ===================");
		};
	}
}
