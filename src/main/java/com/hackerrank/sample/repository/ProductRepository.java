package com.hackerrank.sample.repository;

import com.hackerrank.sample.model.Product;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Handles access to product data.
 * In this case, products are stored in a simple list to simulate a data source.
 */
@Repository
public class ProductRepository {

    // This list acts as our data source for the application
    private final List<Product> products = new ArrayList<>();

    /**
     * Loads a predefined set of products when the application starts.
     * This allows us to work without connecting to an external database.
     */
    public ProductRepository() {
        products.add(new Product(
                1L,
                "iPhone 13",
                "Apple smartphone",
                "https://example.com/iphone.jpg",
                1200.0,
                4.5,
                Map.of("ram", "4GB", "storage", "128GB", "screen", "6.1")
        ));

        products.add(new Product(
                2L,
                "Samsung S22",
                "Samsung flagship",
                "https://example.com/samsung.jpg",
                1000.0,
                4.3,
                Map.of("ram", "8GB", "storage", "256GB", "screen", "6.1")
        ));

        products.add(new Product(
                3L,
                "Xiaomi Mi 11",
                "Xiaomi smartphone",
                "https://example.com/xiaomi.jpg",
                800.0,
                4.2,
                Map.of("ram", "8GB", "storage", "128GB", "screen", "6.8")
        ));
    }

    /**
     * Returns all available products.
     */
    public List<Product> findAll() {
        return products;
    }

    /**
     * Searches for a product using its id.
     */
    public Optional<Product> findById(Long id) {
        return products.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst();
    }

    /**
     * Returns the products that match the given list of ids.
     */
    public List<Product> findByIds(List<Long> ids) {
        return products.stream()
                .filter(product -> ids.contains(product.getId()))
                .collect(Collectors.toList());
    }
}