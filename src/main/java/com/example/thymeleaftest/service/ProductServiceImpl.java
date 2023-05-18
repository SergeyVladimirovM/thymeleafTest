package com.example.thymeleaftest.service;

import com.example.thymeleaftest.entity.Product;
import com.example.thymeleaftest.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> findAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public Long saveOrUpdateProduct(Product product) {
        return productRepository.save(product).getId();
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Product findProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public Page<Product> findPaginated(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Product> productList;
        List<Product> products = findAllProduct();

        if (products.size() < startItem) {
            productList = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, products.size());
            productList = products.subList(startItem, toIndex);
        }
        return new PageImpl<>(productList, PageRequest.of(currentPage, pageSize), products.size());
    }
}
