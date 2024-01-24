package com.Ecommerce.Ecommerce_Website.dto;

import com.Ecommerce.Ecommerce_Website.enums.OrderStatus;
import lombok.Data;
import org.apache.catalina.LifecycleState;


import java.util.Date;
import java.util.UUID;
import java.util.List;

@Data
public class OrderDto {
    private Long id;
    private String orderDescription;
    private Date date;
    private Long amount;
    private String address;
    private String payment;
    private OrderStatus orderStatus;
    private Long totalAmount;
    private Long discount;
    private UUID trackingId;
    private String userName;
    private List<CartItemsDto> cartItems;
}
