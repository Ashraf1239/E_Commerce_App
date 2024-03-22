package com.ecommerce.library.repository;

import com.ecommerce.library.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepostery extends JpaRepository<Order, Long> {
}
