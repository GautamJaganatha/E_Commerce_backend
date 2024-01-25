package com.Ecommerce.Ecommerce_Website.service.admin.adminOrder;

import com.Ecommerce.Ecommerce_Website.dto.OrderDto;
import com.Ecommerce.Ecommerce_Website.entity.Order;
import com.Ecommerce.Ecommerce_Website.enums.OrderStatus;
import com.Ecommerce.Ecommerce_Website.repo.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminOrderServiceImpl implements AdminOrderService{
    private final OrderRepository orderRepository;

    public List<OrderDto> getAllPlacedOrder(){
        List<Order> orderList = orderRepository.findAllByOrderStatusIn(List.of(OrderStatus.Placed,OrderStatus.Shipped,OrderStatus.Delivered));
        return orderList.stream().map(Order::getOrderDto).collect(Collectors.toList());
    }

    public OrderDto changeOderStatus(Long orderId, String status){
        Optional<Order> optionalOrder= orderRepository.findById(orderId);
        if (optionalOrder.isPresent()){
            Order order = optionalOrder.get();
            if (Objects.equals(status,"Shipped")){
                order.setOrderStatus(OrderStatus.Shipped);
                
            } else if (Objects.equals(status,"Delivered")) {
                order.setOrderStatus(OrderStatus.Shipped);
            }
            return orderRepository.save(order).getOrderDto();
        }
        return null;
    }
}
