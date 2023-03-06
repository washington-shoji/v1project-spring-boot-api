package com.v1project.v1project.services.impl;

import com.v1project.v1project.entities.Product;
import com.v1project.v1project.repositories.ProductRepository;
import com.v1project.v1project.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public
class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> findAll() {
      return productRepository.findAll();
    }

    @Override
    public Product findById(Long productId) {
        return productRepository.findById(productId).orElseThrow();
    }

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Long productId, Product product) {
        Product existingProduct = productRepository.findById(productId).orElseThrow();
        existingProduct.setProductName(product.getProductName());
        existingProduct.setProductDescription(product.getProductDescription());
        existingProduct.setProductPrice(product.getProductPrice());
        existingProduct.setProductImage(product.getProductImage());
        return productRepository.save(existingProduct);
    }

    @Override
    public Product deleteProduct(Long productId) {
        Product existingProduct = productRepository.findById(productId).orElseThrow();
        productRepository.deleteById(productId);
        return existingProduct;
    }
}
