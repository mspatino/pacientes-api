package com.consultorio.pacientes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class PacientesApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PacientesApiApplication.class, args);
	}

}
