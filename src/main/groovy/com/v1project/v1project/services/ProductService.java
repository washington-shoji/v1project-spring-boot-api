package com.v1project.v1project.services;

import com.v1project.v1project.entities.Product;

import java.util.List;

public interface ProductService {
    List<Product> findAll();
    Product findById(Long productId);
    Product saveProduct(Product product);
    Product updateProduct(Long productId, Product product);
    Product deleteProduct(Long productId);
}
