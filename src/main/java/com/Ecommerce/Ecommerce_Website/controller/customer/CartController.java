package com.Ecommerce.Ecommerce_Website.controller.customer;

import com.Ecommerce.Ecommerce_Website.dto.AddProductInCartDto;
import com.Ecommerce.Ecommerce_Website.dto.OrderDto;
import com.Ecommerce.Ecommerce_Website.service.customer.cart.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/admin/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping("/addProductToCart")
    public ResponseEntity<?> addProductToCart(@RequestBody AddProductInCartDto addProductInCartDto){
        return cartService.addProductToCart(addProductInCartDto);
    }

    @GetMapping("/userId{userId}")
    public ResponseEntity<?> getCartByUserId(@PathVariable Long userId){
        OrderDto orderDto = cartService.getCartByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(orderDto);
    }
}
