package com.hackerrank.sample.service;

import com.hackerrank.sample.dto.ProductComparisonResponse;
import com.hackerrank.sample.model.Product;
import com.hackerrank.sample.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

/**
 * Unit tests for ProductServiceImpl.
 */
class ProductServiceImplTest {

    private ProductRepository productRepository;
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        productRepository = Mockito.mock(ProductRepository.class);
        productService = new ProductServiceImpl();

        // Inject mocked repository into the service
        ReflectionTestUtils.setField(productService, "productRepository", productRepository);
    }

    @Test
    void shouldCompareProductsSuccessfully() {
        Product product1 = new Product(
                1L,
                "iPhone 13",
                "Apple smartphone",
                "https://example.com/iphone.jpg",
                1200.0,
                4.5,
                Map.of("ram", "4GB", "storage", "128GB", "screen", "6.1")
        );

        Product product2 = new Product(
                2L,
                "Samsung S22",
                "Samsung flagship",
                "https://example.com/samsung.jpg",
                1000.0,
                4.3,
                Map.of("ram", "8GB", "storage", "256GB", "screen", "6.1")
        );

        when(productRepository.findByIds(List.of(1L, 2L)))
                .thenReturn(List.of(product1, product2));

        ProductComparisonResponse response = productService.compareProducts(List.of(1L, 2L));

        assertNotNull(response);
        assertEquals(2, response.getProducts().size());
        assertNotNull(response.getComparison());
        assertEquals(200.0, (Double) response.getComparison().get("priceDifference"), 0.0001);
        assertEquals(0.2, (Double) response.getComparison().get("ratingDifference"), 0.0001);
    }

    @Test
    void shouldThrowExceptionWhenLessThanTwoIdsAreProvided() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> productService.compareProducts(List.of(1L))
        );

        assertEquals("At least two product ids are required", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenNotEnoughProductsAreFound() {
        when(productRepository.findByIds(List.of(1L, 2L)))
                .thenReturn(List.of());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> productService.compareProducts(List.of(1L, 2L))
        );

        assertEquals("Not enough products found", exception.getMessage());
    }
}