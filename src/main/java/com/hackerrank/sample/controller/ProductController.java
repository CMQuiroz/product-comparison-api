package com.hackerrank.sample.controller;

import com.hackerrank.sample.dto.ProductComparisonRequest;
import com.hackerrank.sample.dto.ProductComparisonResponse;
import com.hackerrank.sample.model.Product;
import com.hackerrank.sample.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Exposes REST endpoints related to products.
 */
@RestController
@RequestMapping("/products")
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    /**
     * Returns all products.
     */
    @GetMapping
    public List<Product> getAllProducts() {
        log.info("Received request: GET /products");
        return productService.getAllProducts();
    }

    /**
     * Returns products filtered by a list of ids.
     * Example: /products?ids=1,2
     */
    @GetMapping(params = "ids")
    public List<Product> getProductsByIds(@RequestParam List<Long> ids) {
        log.info("Received request: GET /products with ids={}", ids);
        return productService.getProductsByIds(ids);
    }

    /**
     * Compares products based on the provided list of ids.
     */
    @PostMapping("/compare")
    public ProductComparisonResponse compareProducts(@RequestBody ProductComparisonRequest request) {
        log.info("Received request: POST /products/compare with ids={}", request.getProductIds());
        return productService.compareProducts(request.getProductIds());
    }
}