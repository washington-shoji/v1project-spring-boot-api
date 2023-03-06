package com.v1project.v1project.controllers;

import com.v1project.v1project.entities.Product;
import com.v1project.v1project.services.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("product")
public class ProductController {

    private final ProductServiceImpl productService;

    @Autowired
    public ProductController(ProductServiceImpl productService) {
        this.productService = productService;
    }

    @GetMapping
    List<Product> getAllProductsList() {
        return productService.findAll();
    }

    @GetMapping("/{productId}")
    Product getProductById(@PathVariable Long productId) {
        return productService.findById(productId);
    }

    @PostMapping
    Product createProduct(@RequestBody Product product) {
       return productService.saveProduct(product);
    }

    @PutMapping("/{productId}")
    Product updateProduct(@PathVariable Long productId, @RequestBody Product product) {
        return productService.updateProduct(productId, product);
    }

    @DeleteMapping("/{productId}")
    void deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
    }
}
