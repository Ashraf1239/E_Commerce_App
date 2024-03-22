package com.ecommerce.customers.controller;

import com.ecommerce.library.dto.ProductDto;
import com.ecommerce.library.model.Customer;
import com.ecommerce.library.model.ShoppingCart;
import com.ecommerce.library.service.CustomerService;
import com.ecommerce.library.service.ProductService;
import com.ecommerce.library.service.ShoppingCartService;
import com.ecommerce.library.utils.ImageUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ShoppingCartController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private ProductService productService;

    @GetMapping("/cart")
    public String getCart(Model model, Principal principal, HttpSession session) {
        if (principal == null) {
            return "redirect:/login";
        }
        String username = principal.getName();
        Customer customer = customerService.findByUsername(username);
        ShoppingCart shoppingCart = customer.getShoppingCart();
        if (shoppingCart == null) {
            model.addAttribute("check", "No items in cart");
        }


        if (shoppingCart != null) {
            model.addAttribute("grandTotal", shoppingCart.getTotalPrice());

        }
        model.addAttribute("imgUtil", new ImageUtil());
        model.addAttribute("shoppingCart", shoppingCart);
        model.addAttribute("title", "Cart");
//        session.setAttribute("totalItems", shoppingCart.getTotalItems());

        return "cart";
    }
    @PostMapping("/add-to-cart")
    public String addItemToCart(@RequestParam("id") Long id,
                                @RequestParam(value = "quantity", required = false, defaultValue = "1") int quantity,
                                HttpServletRequest request,
                                Model model,
                                Principal principal,
                                HttpSession session) {
        ProductDto productDto = productService.getProductById(id);

        if (principal == null) {
            return "redirect:/login";
        }
        String username = principal.getName();
        ShoppingCart shoppingCart = shoppingCartService.addItemToCart(productDto, quantity, username);
        session.setAttribute("totalItems", shoppingCart.getTotalItems());
        model.addAttribute("shoppingCart", shoppingCart);
        return "redirect:" + request.getHeader("Referer");

    }
    @RequestMapping(value = "/update-cart", method = RequestMethod.POST, params = "action=update")
public String UpdateCart(@RequestParam("id") Long id,@RequestParam("quantity") int quantity,
                         Model model,
                         Principal principal,
                         HttpSession session) {
    if (principal == null) {
        return "redirect:/login";
    }
    ProductDto productDto = productService.getProductById(id);
    String username = principal.getName();
    ShoppingCart shoppingCart = shoppingCartService.updateItemCart(productDto, quantity, username);
    model.addAttribute("shoppingCart", shoppingCart);
    session.setAttribute("totalItems", shoppingCart.getTotalItems());
    return "redirect:/cart";
                         }
    @RequestMapping(value = "/update-cart", method = RequestMethod.POST, params = "action=delete")
    public String deleteItem(@RequestParam("id") Long id,
                             Model model,
                             Principal principal,
                             HttpSession session
    ) {
        if (principal == null) {
            return "redirect:/login";
        } else {
            ProductDto productDto = productService.getProductById(id);
            String username = principal.getName();
            ShoppingCart shoppingCart = shoppingCartService.deleteItemCart(productDto, username);
            model.addAttribute("shoppingCart", shoppingCart);
            session.setAttribute("totalItems", shoppingCart.getTotalItems());
            return "redirect:/cart";
        }
    }
}
