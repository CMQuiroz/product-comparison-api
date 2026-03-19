package com.hackerrank.sample.service;

import com.hackerrank.sample.dto.ProductComparisonResponse;
import com.hackerrank.sample.model.Product;
import com.hackerrank.sample.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Handles business logic related to products.
 */
@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private ProductRepository productRepository;

    /**
     * Retrieves products based on a list of ids.
     */
    @Override
    public List<Product> getProductsByIds(List<Long> ids) {

        log.info("Fetching products by ids: {}", ids);

        if (ids == null || ids.isEmpty()) {
            log.warn("Invalid product retrieval request. Id list is empty or null");
            throw new IllegalArgumentException("Product ids must not be empty");
        }

        List<Product> products = productRepository.findByIds(ids);

        log.info("Products retrieved: {}", products.size());

        return products;
    }

    /**
     * Returns all available products.
     */
    @Override
    public List<Product> getAllProducts() {
        log.info("Fetching all available products");

        List<Product> products = productRepository.findAll();

        log.info("Total products found: {}", products.size());

        return products;
    }

    /**
     * Compares two or more products based on price, rating and specifications.
     */
    @Override
    public ProductComparisonResponse compareProducts(List<Long> ids) {

        log.info("Comparing products with ids: {}", ids);

        if (ids == null || ids.size() < 2) {
            log.warn("Invalid comparison request. Not enough product ids: {}", ids);
            throw new IllegalArgumentException("At least two product ids are required");
        }

        List<Product> products = productRepository.findByIds(ids);

        log.info("Products found for comparison: {}", products.size());

        if (products.size() < 2) {
            log.warn("Comparison request failed. Not enough products found for ids: {}", ids);
            throw new IllegalArgumentException("Not enough products found");
        }

        Product p1 = products.get(0);
        Product p2 = products.get(1);

        Map<String, Object> comparison = new HashMap<>();

        // Compare price
        comparison.put("priceDifference", Math.abs(p1.getPrice() - p2.getPrice()));

        // Compare rating
        comparison.put("ratingDifference", Math.abs(p1.getRating() - p2.getRating()));

        // Compare specifications dynamically
        Map<String, List<String>> specDifferences = new HashMap<>();

        for (String key : p1.getSpecs().keySet()) {
            String val1 = p1.getSpecs().get(key);
            String val2 = p2.getSpecs().get(key);

            if (!val1.equals(val2)) {
                specDifferences.put(key, List.of(val1, val2));
            }
        }

        comparison.put("specDifferences", specDifferences);

        ProductComparisonResponse response = new ProductComparisonResponse();
        response.setProducts(products);
        response.setComparison(comparison);

        log.info("Product comparison completed successfully");

        return response;
    }
}