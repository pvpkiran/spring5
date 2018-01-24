package in.phani.springboot.spring5.controller;

import in.phani.springboot.spring5.pojo.Product;
import in.phani.springboot.spring5.repository.ReactiveProductRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class ProductRoutes {

  @Bean
  public RouterFunction routes(final ReactiveProductRepository productRepository) {
    return
        route(GET("/initialize"),
            serverRequest -> {
              Product product1 = Product.builder().description("somedescription").name("product1").price(10.0).build();
              Product product2 = Product.builder().description("somedescription").name("product2").price(20.0).build();
              Product product3 = Product.builder().description("somedescription").name("product3").price(30.0).build();
              return ServerResponse.ok()
                  .body(BodyInserters.fromPublisher(Flux.just(product1, product2, product3).flatMap(productRepository::save),
                      Product.class));
            }
        )
            .and(
                route(GET("/findbyname/{name}"),
                    serverRequest -> {
                      String name = serverRequest.pathVariable("name");
                      return ServerResponse.ok()
                          .body(BodyInserters.fromPublisher(productRepository.findByName(name), Product.class));
                    }
                )
            )
            .and(
                route(GET("/findall"),
                    serverRequest -> ServerResponse.ok()
                        .body(BodyInserters.fromPublisher(productRepository.findAllProducts().doOnNext(System.out::println), Product.class))
                )
            )
            .and(
                route(POST("/save"),
                    serverRequest -> {
                      Mono<Product> product = serverRequest.body(BodyExtractors.toMono(Product.class));
                      return ServerResponse.ok()
                          .body(BodyInserters.fromPublisher(product.flatMap(productRepository::save), Product.class));
                    }
                )
            )
        ;
  }
}
