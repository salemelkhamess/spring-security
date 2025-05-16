package org.example.springsecurityapp.repository;

import org.example.springsecurityapp.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository  extends JpaRepository<Product, Long> {
    Product findById(long id);
    List<Product> findByNameContainingIgnoreCase(String name);
    Page<Product> findByNameContains(String name, Pageable pageable);



}
