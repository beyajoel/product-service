package com.bent.productservice.mapper;

import com.bent.productservice.dto.ProductRequest;
import com.bent.productservice.dto.ProductResponse;
import com.bent.productservice.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class ProductMapperImplTest {

    private ProductMapperImpl productMapperImplUnderTest;

    @BeforeEach
    void setUp() {
        productMapperImplUnderTest = new ProductMapperImpl();
    }

    @Test
    void testToResponse() {
        // Setup
        final Product product = new Product("id", "name", "description", new BigDecimal("0.00"));
        final ProductResponse expectedResult = new ProductResponse("id", "name", "description", new BigDecimal("0.00"));

        // Run the test
        final ProductResponse result = productMapperImplUnderTest.toResponse(product);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testFromResponse() {
        // Setup
        final ProductResponse response = new ProductResponse("id", "name", "description", new BigDecimal("0.00"));
        final Product expectedResult = new Product("id", "name", "description", new BigDecimal("0.00"));

        // Run the test
        final Product result = productMapperImplUnderTest.fromResponse(response);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testToRequest() {
        // Setup
        final Product product = new Product("id", "name", "description", new BigDecimal("0.00"));
        final ProductRequest expectedResult = new ProductRequest("name", "description", new BigDecimal("0.00"));

        // Run the test
        final ProductRequest result = productMapperImplUnderTest.toRequest(product);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testFromRequest() {
        // Setup
        final ProductRequest request = new ProductRequest("name", "description", new BigDecimal("0.00"));
        final Product expectedResult = new Product(null, "name", "description", new BigDecimal("0.00"));

        // Run the test
        final Product result = productMapperImplUnderTest.fromRequest(request);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testNullValues() {
        assertThat(productMapperImplUnderTest.fromRequest(null)).isNull();
        assertThat(productMapperImplUnderTest.toRequest(null)).isNull();
        assertThat(productMapperImplUnderTest.fromResponse(null)).isNull();
        assertThat(productMapperImplUnderTest.toResponse(null)).isNull();

    }
}
