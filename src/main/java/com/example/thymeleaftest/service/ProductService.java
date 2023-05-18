package com.example.thymeleaftest.service;

import com.example.thymeleaftest.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    List<Product> findAllProduct();
    Long saveOrUpdateProduct(Product product);
    void deleteProductById(Long id);
    Product findProductById(Long id);
    Page<Product> findPaginated(Pageable pageable);
}
