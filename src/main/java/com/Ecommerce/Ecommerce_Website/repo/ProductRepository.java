package com.Ecommerce.Ecommerce_Website.repo;

import com.Ecommerce.Ecommerce_Website.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
