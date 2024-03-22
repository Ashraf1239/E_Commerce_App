package com.ecommerce.library.repository;

import com.ecommerce.library.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailsRepostery extends JpaRepository<OrderDetail, Long> {
}
