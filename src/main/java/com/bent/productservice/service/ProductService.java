package com.bent.productservice.service;

import com.bent.productservice.dto.ProductRequest;
import com.bent.productservice.dto.ProductResponse;
import com.bent.productservice.mapper.ProductMapper;
import com.bent.productservice.model.Product;
import com.bent.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductMapper mapper;
    private final ProductRepository productRepository;

    public void createProduct(ProductRequest productRequest) {
        Product product = mapper.fromRequest(productRequest);
        productRepository.save(product);
        log.info("Product {} is saved successfully!", product.getId());
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(mapper::toResponse).toList();
    }
}
