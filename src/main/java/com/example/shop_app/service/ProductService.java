package com.example.shop_app.service;

import com.example.shop_app.domain.Member;
import com.example.shop_app.domain.Product;
import com.example.shop_app.dto.ProductCreateRequest;
import com.example.shop_app.dto.ProductResponse;
import com.example.shop_app.dto.ProductUpdateRequest;
import com.example.shop_app.exception.CustomException;
import com.example.shop_app.exception.ErrorCode;
import com.example.shop_app.exception.ProductNotFoundException;
import com.example.shop_app.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final MemberService memberService;

    public ProductResponse createProduct(ProductCreateRequest request) {
        validateProduct(request.getName(), request.getDescription(), request.getPrice());

        Member member = memberService.findById(request.getSellerId());

        Product product = Product.create(member, request.getName(), request.getDescription(),request.getPrice());
        Product savedProduct = productRepository.save(product);

        return ProductResponse.from(savedProduct);
    }

    public ProductResponse getProduct(Long productId) {
        Product product = productRepository.findById(productId)
            .orElseThrow(ProductNotFoundException::new);

        return ProductResponse.from(product);
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findByNameContainingOrderByCreatedAtDesc()
            .stream()
            .map(ProductResponse::from)
            .toList();
    }

    public ProductResponse updateProduct(Long productId, ProductUpdateRequest request) {
        validateProduct(request.getName(), request.getDescription(), request.getPrice());

        Product product = productRepository.findById(productId)
            .orElseThrow(ProductNotFoundException::new);

        product.updateProduct(request.getName(), request.getDescription(), request.getPrice());
        Product savedProduct = productRepository.save(product);

        return ProductResponse.from(savedProduct);
    }

    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
            .orElseThrow(ProductNotFoundException::new);

        productRepository.delete(product);
    }

    private void validateProduct(String name, String description, int price) {
        if (name == null || name.isBlank()) {
            throw new CustomException(ErrorCode.INVALID_PRODUCT_NAME);
        }
        if (description == null || description.isBlank()) {
            throw new CustomException(ErrorCode.INVALID_PRODUCT_DESCRIPTION);
        }
        if (price <= 0) {
            throw new CustomException(ErrorCode.INVALID_PRODUCT_PRICE);
        }
    }
}