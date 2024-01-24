package com.Ecommerce.Ecommerce_Website.dto;

import lombok.Data;

@Data
public class CartItemsDto {
    private Long id;
    private Long price;
    private Long quantity;
    private Long productId;
    private Long OrderId;
    private String productName;
    private byte[] returnedImg;
    private Long userId;
}
