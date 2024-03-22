package com.ecommerce.customers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages ={ "com.ecommerce.library.*","com.ecommerce.customers.*"})
@EnableJpaRepositories(value = "com.ecommerce.library.repository")
@EntityScan(value = "com.ecommerce.library.model")
public class CustomersApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomersApplication.class, args);
    }

}
