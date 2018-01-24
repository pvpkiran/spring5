package in.phani.springboot.spring5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class Spring5Application {

  public static void main(String[] args) {
    SpringApplication.run(Spring5Application.class, args);
  }
}
