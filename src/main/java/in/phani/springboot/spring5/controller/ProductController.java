package in.phani.springboot.spring5.controller;

import in.phani.springboot.spring5.pojo.Product;
import in.phani.springboot.spring5.repository.ReactiveProductRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

  private final ReactiveProductRepository productRepository;

  @Autowired
  public ProductController(final ReactiveProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @GetMapping("/initialize")
  public Flux<Product> initialize() {
    Product product1 = Product.builder().description("somedescription").name("product1").price(10.0).build();
    Product product2 = Product.builder().description("somedescription").name("product2").price(20.0).build();
    Product product3 = Product.builder().description("somedescription").name("product3").price(30.0).build();

    return Flux.just(product1, product2, product3).flatMap(productRepository::save);
  }

  @GetMapping("/findbyname/{name}")
  public Mono<Product> findByName(@PathVariable final String name) {
    return productRepository.findByName(name);
  }

  /*@GetMapping("/findall")
  public Flux<ServerSentEvent<Product>> findAll() {
    return productRepository.findAllProducts().map(data -> ServerSentEvent.<Product>builder().event("product").data(data).build());
  }*/

  @GetMapping(value = "/findall", produces= MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<Product> findAll() {
    return productRepository.findAllProducts();
  }

  @GetMapping(value = "/findbyprice", produces= MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<Product> findByPrice() {
    return productRepository.findByPriceGreaterThan(10.0);
  }

  @PostMapping("/save")
  public Mono<Product> saveProduct(@RequestBody final Product product) {
    return productRepository.save(product);
  }

}
