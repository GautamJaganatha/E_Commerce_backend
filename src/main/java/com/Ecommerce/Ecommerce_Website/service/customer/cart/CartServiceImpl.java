package com.Ecommerce.Ecommerce_Website.service.customer.cart;

import com.Ecommerce.Ecommerce_Website.dto.AddProductInCartDto;
import com.Ecommerce.Ecommerce_Website.dto.CartItemsDto;
import com.Ecommerce.Ecommerce_Website.dto.OrderDto;
import com.Ecommerce.Ecommerce_Website.entity.CartItems;
import com.Ecommerce.Ecommerce_Website.entity.Order;
import com.Ecommerce.Ecommerce_Website.entity.Product;
import com.Ecommerce.Ecommerce_Website.enums.OrderStatus;
import com.Ecommerce.Ecommerce_Website.repo.CartItemRepository;
import com.Ecommerce.Ecommerce_Website.repo.OrderRepository;
import com.Ecommerce.Ecommerce_Website.repo.ProductRepository;
import com.Ecommerce.Ecommerce_Website.repo.UserRepo;
import com.Ecommerce.Ecommerce_Website.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CartServiceImpl implements CartService{
    private OrderRepository orderRepository;

    private UserRepo userRepo;

    private CartItemRepository cartItemRepository;

    private ProductRepository productRepository;

    public ResponseEntity<?> addProductToCart(AddProductInCartDto addProductInCartDto){
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(addProductInCartDto.getUserId(), OrderStatus.Pending);
        Optional<CartItems> optionalCartItems = cartItemRepository.findByProductIdAndOrderIdAndUserId(
                addProductInCartDto.getProductId(),activeOrder.getId(),addProductInCartDto.getUserId());

        if (optionalCartItems.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }else {
            Optional<Product> optionalProduct = productRepository.findById(addProductInCartDto.getProductId());
            Optional<User> optionalUser = userRepo.findById(addProductInCartDto.getUserId());

            if (optionalProduct.isPresent() && optionalUser.isPresent()){
                CartItems cart = new CartItems();
                cart.setProduct(optionalProduct.get());
                cart.setPrice(optionalProduct.get().getPrice());
                cart.setQuantity(1L);
                cart.setUser(optionalUser.get());
                cart.setOrder(activeOrder);

                CartItems updatedCart = cartItemRepository.save(cart);
                activeOrder.setTotalAmount(activeOrder.getTotalAmount()+ cart.getPrice());
                activeOrder.setAmount(activeOrder.getAmount()+ cart.getPrice());
                activeOrder.getCartItems().add(cart);


                orderRepository.save(activeOrder);


                return ResponseEntity.status(HttpStatus.CREATED).body(cart);

            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user or product not found");
            }
        }

    }

    public OrderDto getCartByUserId(Long userId){
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(userId, OrderStatus.Pending);
        List<CartItemsDto> cartItemsDtoList = activeOrder.getCartItems().stream().map(CartItems::getCartDto).collect(Collectors.toList());
        OrderDto orderDto = new OrderDto();
        orderDto.setAmount(activeOrder.getAmount());
        orderDto.setId(activeOrder.getId());
        orderDto.setOrderStatus(activeOrder.getOrderStatus());
        orderDto.setDiscount(activeOrder.getDiscount());
        orderDto.setTotalAmount(activeOrder.getTotalAmount());
        orderDto.setCartItems(cartItemsDtoList);

        return orderDto;
    }

}
