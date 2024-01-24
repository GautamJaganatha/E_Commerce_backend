package com.Ecommerce.Ecommerce_Website.service.customer.cart;

import com.Ecommerce.Ecommerce_Website.dto.AddProductInCartDto;
import com.Ecommerce.Ecommerce_Website.dto.OrderDto;
import org.springframework.http.ResponseEntity;

public interface CartService {
     ResponseEntity<?> addProductToCart(AddProductInCartDto addProductInCartDto);

     OrderDto getCartByUserId(Long userId);
}
