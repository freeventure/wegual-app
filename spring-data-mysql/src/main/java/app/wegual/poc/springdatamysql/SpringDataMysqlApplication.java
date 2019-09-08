package app.wegual.poc.springdatamysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.dao.DataIntegrityViolationException;

import app.wegual.poc.common.model.User;

@EntityScan(basePackages = "app.wegual.poc.common.model")
@SpringBootApplication
public class SpringDataMysqlApplication implements CommandLineRunner {
	
//	@Autowired
//	private UserRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(SpringDataMysqlApplication.class, args);
	}
	
	
	public void run(String... args) {
//		try {
//			repository.save(new User("Michael Jackson", "jacksonm@gmail.com"));
//		} catch (DataIntegrityViolationException dive) {
//			System.out.println("User already exists with email");
//		}
//		repository.findByEmail("jacksonm@gmail.com").forEach(x -> System.out.println(x.getName()));

	}
}
