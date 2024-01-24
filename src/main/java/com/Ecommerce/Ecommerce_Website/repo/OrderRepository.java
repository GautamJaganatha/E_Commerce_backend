package com.Ecommerce.Ecommerce_Website.repo;

import com.Ecommerce.Ecommerce_Website.entity.Order;
import com.Ecommerce.Ecommerce_Website.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findByUserIdAndOrderStatus(Long userId, OrderStatus orderStatus);
}
