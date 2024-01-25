package com.Ecommerce.Ecommerce_Website.service.customer.cart;

import com.Ecommerce.Ecommerce_Website.dto.AddProductInCartDto;
import com.Ecommerce.Ecommerce_Website.dto.OrderDto;
import com.Ecommerce.Ecommerce_Website.dto.PlaceOrderDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CartService {
     ResponseEntity<?> addProductToCart(AddProductInCartDto addProductInCartDto);

     OrderDto getCartByUserId(Long userId);

     OrderDto applyCoupon(Long userId, String code);

     OrderDto increaseProductQuantity(AddProductInCartDto addProductInCartDto);

     OrderDto decreaseProductQuantity(AddProductInCartDto addProductInCartDto);

     OrderDto placeOrder(PlaceOrderDto placeOrderDto);

     List<OrderDto> getMyPlacedOrders(Long userId);
}
