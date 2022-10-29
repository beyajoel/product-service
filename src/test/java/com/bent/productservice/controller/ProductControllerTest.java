package com.bent.productservice.controller;

import com.bent.productservice.dto.ProductRequest;
import com.bent.productservice.dto.ProductResponse;
import com.bent.productservice.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService mockProductService;

    @Test
    void testCreateProduct() throws Exception {
        // Setup
        ProductRequest productRequest = ProductRequest
                .builder()
                .name("name")
                .description("description")
                .price(new BigDecimal("0.00"))
                .build();

        doNothing().when(mockProductService).createProduct(productRequest);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("http://localhost:8080/api/products")
                        .content(new ObjectMapper().writeValueAsString(productRequest)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEmpty();
        verify(mockProductService).createProduct(new ProductRequest("name", "description", new BigDecimal("0.00")));
    }

    @Test
    void testGetAllProducts() throws Exception {
        // Setup
        // Configure ProductService.getAllProducts(...).
        final List<ProductResponse> productResponses = List.of(
                new ProductResponse("id", "name", "description", new BigDecimal("0.00")));
        when(mockProductService.getAllProducts()).thenReturn(productResponses);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("http://localhost:8080/api/products")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("[{\"id\":\"id\",\"name\":\"name\",\"description\":\"description\",\"price\":0.00}]");
    }

    @Test
    void testGetAllProducts_ProductServiceReturnsNoItems() throws Exception {
        // Setup
        when(mockProductService.getAllProducts()).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("http://localhost:8080/api/products")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("[]");
    }
}
