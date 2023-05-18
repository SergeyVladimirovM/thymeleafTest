package com.example.thymeleaftest.repository;

import com.example.thymeleaftest.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAll();
}
