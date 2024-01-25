package com.Ecommerce.Ecommerce_Website.service.admin.adminOrder;

import com.Ecommerce.Ecommerce_Website.dto.OrderDto;

import java.util.List;

public interface AdminOrderService {
    List<OrderDto> getAllPlacedOrder();

    OrderDto changeOderStatus(Long orderId, String status);
}
