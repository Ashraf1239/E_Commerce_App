package com.ecommerce.library.service;

import com.ecommerce.library.model.*;
import com.ecommerce.library.repository.CustomerRepository;
import com.ecommerce.library.repository.OrderRepostery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepostery orderRepostery;

    @Autowired
    private  OrderDetailsService orderDetailsService;
    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private CustomerRepository customerRepository;
    public Order saveOrder(ShoppingCart shoppingCart) {
        Order order = new Order();
        order.setAccept(false);
        order.setPaymentMethod("Cash");
        order.setOrderStatus("Pending");
        order.setTax(2);
        order.setOrderDate(new Date());
        order.setCustomer(shoppingCart.getCustomer());
        order.setTotalPrice(shoppingCart.getTotalPrice());
        order.setQuantity(shoppingCart.getTotalItems());
        List<OrderDetail> orderDetailList = new ArrayList<>();
        for(CartItem item : shoppingCart.getCartItems()) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setQuantity(item.getQuantity());
            orderDetail.setTotalPrice(item.getTotalPrice());
            orderDetail.setOrder(order);
            orderDetail.setProduct(item.getProduct());
            orderDetail.setUnitePrice(item.getUnitePrice());

            orderDetailList.add(orderDetail);

        }
        order.setOrderDetail(orderDetailList);
        shoppingCartService.deleteShpoingCartById(shoppingCart.getId());



        return orderRepostery.save(order);

    }

    public List<Order> findAll(String username) {
        Customer customer = customerRepository.findByUsername(username);
        List<Order> orders = customer.getOrder();
        return orders;
    }


    public List<Order> findALlOrders() {
        return orderRepostery.findAll();
    }



    public Order acceptOrder(Long id) {
        Order order = orderRepostery.getById(id);
        order.setAccept(true);
        order.setDeliveryDate(new Date());
        return orderRepostery.save(order);
    }


    public void cancelOrder(Long id) {
        orderRepostery.deleteById(id);
    }


}
