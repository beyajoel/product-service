package com.bent.productservice.service;

import com.bent.productservice.dto.ProductRequest;
import com.bent.productservice.dto.ProductResponse;
import com.bent.productservice.mapper.ProductMapper;
import com.bent.productservice.model.Product;
import com.bent.productservice.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class ProductServiceTest {

    @Mock
    private ProductMapper mockMapper;
    @Mock
    private ProductRepository mockProductRepository;

    @InjectMocks
    private ProductService productServiceUnderTest;

    @BeforeEach
    void setUp() {
        productServiceUnderTest = new ProductService(mockMapper, mockProductRepository);
    }

    @Test
    void testCreateProduct() {
        // Setup
        final ProductRequest productRequest = new ProductRequest("name", "description", new BigDecimal("0.00"));

        // Configure ProductMapper.fromRequest(...).
        final Product product = new Product("id", "name", "description", new BigDecimal("0.00"));
        when(mockMapper.fromRequest(new ProductRequest("name", "description", new BigDecimal("0.00"))))
                .thenReturn(product);

        // Configure ProductRepository.save(...).
        final Product product1 = new Product("id", "name", "description", new BigDecimal("0.00"));
        when(mockProductRepository.save(new Product("id", "name", "description", new BigDecimal("0.00"))))
                .thenReturn(product1);

        // Run the test
        productServiceUnderTest.createProduct(productRequest);

        // Verify the results
        verify(mockProductRepository).save(new Product("id", "name", "description", new BigDecimal("0.00")));
    }

    @Test
    void testGetAllProducts() {
        // Setup
        final List<ProductResponse> expectedResult = List.of(
                new ProductResponse("id", "name", "description", new BigDecimal("0.00")));

        // Configure ProductRepository.findAll(...).
        final List<Product> products = List.of(new Product("id", "name", "description", new BigDecimal("0.00")));
        when(mockProductRepository.findAll()).thenReturn(products);

        // Configure ProductMapper.toResponse(...).
        final ProductResponse productResponse = new ProductResponse("id", "name", "description",
                new BigDecimal("0.00"));
        when(mockMapper.toResponse(new Product("id", "name", "description", new BigDecimal("0.00"))))
                .thenReturn(productResponse);

        // Run the test
        final List<ProductResponse> result = productServiceUnderTest.getAllProducts();

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetAllProducts_ProductRepositoryReturnsNoItems() {
        // Setup
        when(mockProductRepository.findAll()).thenReturn(Collections.emptyList());
//
//        // Configure ProductMapper.toResponse(...).
//        final ProductResponse productResponse = new ProductResponse("id", "name", "description",
//                new BigDecimal("0.00"));
//        when(mockMapper.toResponse(new Product("id", "name", "description", new BigDecimal("0.00"))))
//                .thenReturn(productResponse);

        // Run the test
        final List<ProductResponse> result = productServiceUnderTest.getAllProducts();

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }
}
