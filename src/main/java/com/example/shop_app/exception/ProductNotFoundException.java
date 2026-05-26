package com.example.shop_app.exception;

public class ProductNotFoundException extends CustomException {

    public ProductNotFoundException() {
        super(ErrorCode.Product_NOT_FOUND);
    }
}
