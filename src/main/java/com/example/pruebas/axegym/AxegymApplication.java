package com.example.pruebas.axegym;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class AxegymApplication {

	public static void main(String[] args) {
		SpringApplication.run(AxegymApplication.class, args);
	}

	@Autowired
	private PasswordEncoder passwordEncoder;


	@Bean
	public CommandLineRunner createPasswords(){
		return args -> {
			System.out.println(passwordEncoder.encode("mahomes15"));
		};
	}
}
