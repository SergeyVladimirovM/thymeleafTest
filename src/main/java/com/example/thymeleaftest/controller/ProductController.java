package com.example.thymeleaftest.controller;

import com.example.thymeleaftest.entity.Product;
import com.example.thymeleaftest.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/app/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String getAllProduct(
            Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size
    ) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        Page<Product> productPages = productService.findPaginated(PageRequest.of(currentPage - 1, pageSize));

        model.addAttribute("productPages", productPages);

        int totalPages = productPages.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .toList();
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "products";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteProductById(@PathVariable("id") Long id) {
        productService.deleteProductById(id);
        return "redirect:/app/products/";
    }

    @GetMapping("/add")
    public String addProduct(Model model) {
        model.addAttribute("product", new Product());
        return "addProduct";
    }

    @PostMapping("/add")
    public String addProduct(@ModelAttribute Product product, Model model) {
        productService.saveOrUpdateProduct(product);
        return "redirect:/app/products/";
    }
}
