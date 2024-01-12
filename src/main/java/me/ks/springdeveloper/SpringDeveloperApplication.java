package me.ks.springdeveloper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SpringDeveloperApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringDeveloperApplication.class, args);
	}

}
