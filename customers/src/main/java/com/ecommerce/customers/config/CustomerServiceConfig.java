package com.ecommerce.customers.config;

import com.ecommerce.library.model.Customer;
import com.ecommerce.library.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
@Service
public class CustomerServiceConfig implements UserDetailsService {
@Autowired
        private CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByUsername(username);
        if(customer == null){
            throw new UsernameNotFoundException("User not found");
        }
        return new User(customer.getUsername(), customer.getPassword(), customer.getRole().stream().map(
                role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList())
        );
    }
}
