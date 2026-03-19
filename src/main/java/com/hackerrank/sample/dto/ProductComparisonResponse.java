package com.hackerrank.sample.dto;

import com.hackerrank.sample.model.Product;

import java.util.List;
import java.util.Map;

public class ProductComparisonResponse {

    private List<Product> products;
    private Map<String, Object> comparison;

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Map<String, Object> getComparison() {
        return comparison;
    }

    public void setComparison(Map<String, Object> comparison) {
        this.comparison = comparison;
    }
}