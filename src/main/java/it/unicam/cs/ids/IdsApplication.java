package it.unicam.cs.ids;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class IdsApplication {

	public static void main(String[] args) {
		SpringApplication.run(IdsApplication.class, args);
	}

}
