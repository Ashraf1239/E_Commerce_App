package com.ecommerce.library.service;

import com.ecommerce.library.model.Order;
import com.ecommerce.library.model.OrderDetail;
import com.ecommerce.library.repository.OrderDetailsRepostery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailsService {
    @Autowired
    private OrderDetailsRepostery orderDetailsRepostery;
public void saveOrderDetails(OrderDetail order){
    orderDetailsRepostery.save(order);

}

}
