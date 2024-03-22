package com.ecommerce.customers.controller;

import com.ecommerce.library.dto.ProductDto;
import com.ecommerce.library.model.Category;
import com.ecommerce.library.model.Customer;
import com.ecommerce.library.model.Product;
import com.ecommerce.library.model.ShoppingCart;
import com.ecommerce.library.service.CategoryService;
import com.ecommerce.library.service.CustomerService;
import com.ecommerce.library.service.ProductService;
import com.ecommerce.library.utils.ImageUtil;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeControllrt {
 @Autowired
 private CustomerService customerService;





    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String home(Model model, Principal principal, HttpSession session) {
        model.addAttribute("title", "Home");
        model.addAttribute("page", "Home");
        if (principal != null) {
            Customer customer = customerService.findByUsername(principal.getName());
            session.setAttribute("username", customer.getFirstname() + " " + customer.getLastname());
            ShoppingCart shoppingCart = customer.getShoppingCart();
            if (shoppingCart != null) {
                session.setAttribute("totalItems", shoppingCart.getTotalItems());
            }
        }
        return "home";
    }

    @GetMapping("/contact")
    public String contact(Model model) {
        model.addAttribute("title", "Contact");
        model.addAttribute("page", "Contact");
        return "contact-us";
    }

}
