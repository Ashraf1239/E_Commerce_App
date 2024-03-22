package com.ecommerce.library.service;

import com.ecommerce.library.dto.CustomerDto;
import com.ecommerce.library.model.Customer;
import com.ecommerce.library.repository.CustomerRepository;
import com.ecommerce.library.repository.RuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.Arrays;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private RuleRepository ruleRepository;

    public Customer update(CustomerDto customerDto) {
        Customer customer = customerRepository.findByUsername(customerDto.getUsername());
        customer.setAddress(customerDto.getAddress());
        customer.setCity(customerDto.getCity());
        customer.setCountry(customerDto.getCountry());
        customer.setPhoneNumber(customerDto.getPhoneNumber());
        return customerRepository.save(customer);


    }

    public CustomerDto save(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setFirstname(customerDto.getFirstName());
        customer.setLastname(customerDto.getLastName());
        customer.setUsername(customerDto.getUsername());
        customer.setPassword(customerDto.getPassword());
        customer.setRole(Arrays.asList(ruleRepository.findByName("CUSTOMER")));
        customerRepository.save(customer);
        return map(customer);
    }

    public Customer findByUsername(String username) {
        Customer customer = customerRepository.findByUsername(username);
        return customer;
    }

    public Customer changePass(CustomerDto customerDto) {
        Customer customer = customerRepository.findByUsername(customerDto.getUsername());
        customer.setPassword(customerDto.getPassword());
        return customerRepository.save(customer);
    }

    public CustomerDto map(Customer customer) {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setFirstName(customer.getFirstname());
        customerDto.setLastName(customer.getLastname());
        customerDto.setPassword(customer.getPassword());
        customerDto.setUsername(customer.getUsername());
        customerDto.setAddress(customer.getAddress());
        customerDto.setCountry(customer.getCountry());
        customerDto.setCity(customer.getCity());
        customerDto.setPhoneNumber(customer.getPhoneNumber());
        customerDto.setImage(customer.getImage());
        return customerDto;
    }
}
