package in.phani.springboot.spring5.repository;

import in.phani.springboot.spring5.pojo.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Tailable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ReactiveProductRepository extends ReactiveCrudRepository<Product, String>{

  Mono<Product> findByName(String productName);

  @Tailable
  @Query("{}")
  Flux<Product> findAllProducts();

  @Tailable
  Flux<Product> findByPriceGreaterThan(double min);
}
