package com.hackerrank.sample.service;

import com.hackerrank.sample.dto.ProductComparisonResponse;
import com.hackerrank.sample.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> getProductsByIds(List<Long> ids);

    List<Product> getAllProducts();

    ProductComparisonResponse compareProducts(List<Long> ids);
}