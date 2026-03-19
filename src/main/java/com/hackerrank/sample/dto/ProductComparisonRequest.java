package com.hackerrank.sample.dto;

import java.util.List;

public class ProductComparisonRequest {

    private List<Long> productIds;

    public List<Long> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<Long> productIds) {
        this.productIds = productIds;
    }
}