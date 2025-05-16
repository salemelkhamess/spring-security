package org.example.springsecurityapp;

import org.example.springsecurityapp.entities.Product;
import org.example.springsecurityapp.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

//SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@SpringBootApplication
public class SpringSecurityAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityAppApplication.class, args);
    }


    @Bean
    public CommandLineRunner commandLineRunner(ProductRepository productRepository) {
        return args -> {
            productRepository.save(
                    Product.builder()
                            .name("P1")
                            .price(1000)
                            .quantity(12)
                            .build()
            );

            productRepository.save(
                    Product.builder()
                            .name("P2")
                            .price(1200)
                            .quantity(10)
                            .build()
            );

            productRepository.save(
                    Product.builder()
                            .name("P3")
                            .price(1030)
                            .quantity(20)
                            .build()
            );

            productRepository.findAll().forEach(System.out::println);
        };

    }
}
