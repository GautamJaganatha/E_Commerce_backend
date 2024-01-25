package com.Ecommerce.Ecommerce_Website.repo;

import com.Ecommerce.Ecommerce_Website.entity.FAQ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FAQRepository extends JpaRepository<FAQ, Long> {
}
