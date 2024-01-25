package com.Ecommerce.Ecommerce_Website.service.customer.cart;

import com.Ecommerce.Ecommerce_Website.dto.AddProductInCartDto;
import com.Ecommerce.Ecommerce_Website.dto.CartItemsDto;
import com.Ecommerce.Ecommerce_Website.dto.OrderDto;
import com.Ecommerce.Ecommerce_Website.dto.PlaceOrderDto;
import com.Ecommerce.Ecommerce_Website.entity.*;
import com.Ecommerce.Ecommerce_Website.enums.OrderStatus;
import com.Ecommerce.Ecommerce_Website.exceptions.ValidationException;
import com.Ecommerce.Ecommerce_Website.repo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CartServiceImpl implements CartService{
    private OrderRepository orderRepository;

    private UserRepo userRepo;

    private CartItemRepository cartItemRepository;

    private ProductRepository productRepository;

    private CouponRepository couponRepository;

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
        if (activeOrder.getCoupon()!=null){
            orderDto.setCouponName(activeOrder.getCoupon().getName());
        }

        return orderDto;
    }

    public OrderDto applyCoupon(Long userId, String code){
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(userId, OrderStatus.Pending);
        Coupon coupon = couponRepository.findByCode(code).orElseThrow(()->new ValidationException("Coupon not found "));
        if (couponIsExpired(coupon)){
            throw new ValidationException("Coupon is expired");
        }
        double discountAmount = ((coupon.getDiscount()/100.0) * activeOrder.getTotalAmount());
        double netAmount = activeOrder.getTotalAmount() - discountAmount;
        activeOrder.setAmount((long) netAmount);
        activeOrder.setDiscount((long)discountAmount);
        activeOrder.setCoupon(coupon);


        orderRepository.save(activeOrder);
        return activeOrder.getOrderDto();
    }

    private boolean couponIsExpired(Coupon coupon){
        Date currentDate = new Date();
        Date expirationDate = coupon.getExpirationDate();

        return expirationDate!=null && currentDate.after(expirationDate);

    }

    public OrderDto increaseProductQuantity(AddProductInCartDto addProductInCartDto){
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(addProductInCartDto.getUserId(), OrderStatus.Pending);
        Optional<Product> optionalProduct = productRepository.findById(addProductInCartDto.getProductId());
        Optional<CartItems> cartItems = cartItemRepository.findByProductIdAndOrderIdAndUserId(addProductInCartDto.getProductId(), activeOrder.getId(), addProductInCartDto.getUserId());
        if (optionalProduct.isPresent() && cartItems.isPresent()){
            CartItems cartItems1 = cartItems.get();
            Product product = optionalProduct.get();


            activeOrder.setAmount(activeOrder.getAmount() + product.getPrice());
            activeOrder.setTotalAmount(activeOrder.getTotalAmount() + product.getPrice());
            cartItems1.setQuantity(cartItems1.getQuantity()+1);

            if(activeOrder.getCoupon()!=null){
                double discountAmount = ((activeOrder.getCoupon().getDiscount()/100.0) * activeOrder.getTotalAmount());
                double netAmount = activeOrder.getTotalAmount() - discountAmount;
                activeOrder.setAmount((long) netAmount);
                activeOrder.setDiscount((long)discountAmount);

            }
            cartItemRepository.save(cartItems1);
            orderRepository.save(activeOrder);
            return activeOrder.getOrderDto();
        }
        return null;
    }


    public OrderDto decreaseProductQuantity(AddProductInCartDto addProductInCartDto){
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(addProductInCartDto.getUserId(), OrderStatus.Pending);
        Optional<Product> optionalProduct = productRepository.findById(addProductInCartDto.getProductId());
        Optional<CartItems> cartItems = cartItemRepository.findByProductIdAndOrderIdAndUserId(addProductInCartDto.getProductId(), activeOrder.getId(), addProductInCartDto.getUserId());
        if (optionalProduct.isPresent() && cartItems.isPresent()){
            CartItems cartItems1 = cartItems.get();
            Product product = optionalProduct.get();


            activeOrder.setAmount(activeOrder.getAmount() - product.getPrice());
            activeOrder.setTotalAmount(activeOrder.getTotalAmount() - product.getPrice());
            cartItems1.setQuantity(cartItems1.getQuantity()-1);

            if(activeOrder.getCoupon() != null){
                double discountAmount = ((activeOrder.getCoupon().getDiscount()/100.0) * activeOrder.getTotalAmount());
                double netAmount = activeOrder.getTotalAmount() - discountAmount;
                activeOrder.setAmount((long) netAmount);
                activeOrder.setDiscount((long)discountAmount);

            }
            cartItemRepository.save(cartItems1);
            orderRepository.save(activeOrder);
            return activeOrder.getOrderDto();
        }
        return null;
    }

    public OrderDto placeOrder(PlaceOrderDto placeOrderDto){
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(placeOrderDto.getUserId(), OrderStatus.Pending);
        Optional<User> optionalUser = userRepo.findById(placeOrderDto.getUserId());
        if (optionalUser.isPresent()){
            activeOrder.setOrderDescription(placeOrderDto.getOrderDescription());
            activeOrder.setAddress(placeOrderDto.getAddress());
            activeOrder.setDate(new Date());
            activeOrder.setOrderStatus(OrderStatus.Placed);
            activeOrder.setTrackingId(UUID.randomUUID());

            orderRepository.save(activeOrder);


            Order order = new Order();
            order.setAmount(0L);
            order.setTotalAmount(0L);
            order.setDiscount(0L);
            order.setUser(optionalUser.get());
            order.setOrderStatus(OrderStatus.Pending);
            orderRepository.save(order);

            return activeOrder.getOrderDto();
        }
        return null;
    }

    public List<OrderDto> getMyPlacedOrders(Long userId){
        return orderRepository.findByUserIdAndOrderStatusIn(userId,List.of(OrderStatus.Placed,OrderStatus.Shipped,OrderStatus.Delivered)
        ).stream().map(Order::getOrderDto).collect(Collectors.toList());
    }

}
