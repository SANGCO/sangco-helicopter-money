package dev.sangco.hm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class HelicopterMoneyApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelicopterMoneyApplication.class, args);
	}

}
