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
            for (int i = 1; i <= 10000; i++) {
                productRepository.save(
                        Product.builder()
                                .name("Product " + i)
                                .price(1000 + i * 10)
                                .quantity(10 + i)
                                .build()
                );
            }

            productRepository.findAll().forEach(System.out::println);
        };
    }

}
