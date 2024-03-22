package com.ecommerce.library.service;

import com.ecommerce.library.dto.CustomerDto;
import com.ecommerce.library.dto.ProductDto;
import com.ecommerce.library.model.CartItem;
import com.ecommerce.library.model.Customer;
import com.ecommerce.library.model.Product;
import com.ecommerce.library.model.ShoppingCart;
import com.ecommerce.library.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class ShoppingCartService {
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private CustomerService customerService;

    @Transactional
    public ShoppingCart addItemToCart(ProductDto productDto, int quantity, String userName) {


        Customer customer = customerService.findByUsername(userName);
        ShoppingCart shoppingCart = customer.getShoppingCart();
        if (shoppingCart == null) {
            shoppingCart = new ShoppingCart();
            shoppingCart.setCustomer(customer);
            shoppingCartRepository.save(shoppingCart);
        }
        Set<CartItem> cartItemslist = shoppingCart.getCartItems();
        CartItem cartItem = finditem(cartItemslist, productDto.getId());
        Product product = transfer(productDto);

        if (cartItemslist == null) {
            cartItemslist = new HashSet<>();
            if (cartItem == null) {
                cartItem = new CartItem();
                cartItem.setProduct(product);
                cartItem.setQuantity(quantity);
                cartItem.setUnitePrice(product.getCostPrice());
                cartItem.setShoppingCart(shoppingCart);
                cartItemslist.add(cartItem);
                cartItemService.saveCartItem(cartItem);
            } else {
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
                cartItemService.saveCartItem(cartItem);
            }

        } else {
            if (cartItem == null) {
                cartItem = new CartItem();
                cartItem.setProduct(product);
                cartItem.setQuantity(quantity);
                cartItem.setUnitePrice(product.getCostPrice());
                cartItem.setShoppingCart(shoppingCart);
                cartItemslist.add(cartItem);
                cartItemService.saveCartItem(cartItem);
            } else {
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
                cartItemService.saveCartItem(cartItem);
            }

        }
        shoppingCart.setCartItems(cartItemslist);
        double totalCost = getTotalCost(cartItemslist);
        int totalItems = getTotalItems(cartItemslist);
        shoppingCart.setTotalPrice(totalCost);
        shoppingCart.setTotalItems(totalItems);


        return shoppingCartRepository.save(shoppingCart);

    }
    @Transactional
    public  ShoppingCart updateItemCart(ProductDto productDto  , int quantity, String username){
        Customer customer = customerService.findByUsername(username);
        ShoppingCart shoppingCart = customer.getShoppingCart();
        Set<CartItem> cartItemslist = shoppingCart.getCartItems();
        CartItem cartItem = finditem(cartItemslist, productDto.getId());
        if (cartItem == null) {
            return null;
        } else {
            cartItem.setQuantity(quantity);
            cartItemService.saveCartItem(cartItem);
            double totalCost = getTotalCost(cartItemslist);
            int totalItems = getTotalItems(cartItemslist);
            shoppingCart.setTotalPrice(totalCost);
            shoppingCart.setTotalItems(totalItems);
            return shoppingCartRepository.save(shoppingCart);
        }

    }
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public  ShoppingCart deleteItemCart(ProductDto productDto, String username){

        Customer customer = customerService.findByUsername(username);
        ShoppingCart shoppingCart = customer.getShoppingCart();
        Set<CartItem> cartItemslist = shoppingCart.getCartItems();
        CartItem cartItem = finditem(cartItemslist, productDto.getId());
        if (cartItem == null) {
            return null;
        } else {
            cartItemslist.remove(cartItem);
            cartItemService.deleteItemCart(cartItem);
            double totalCost = getTotalCost(cartItemslist);
            int totalItems = getTotalItems(cartItemslist);
            shoppingCart.setTotalPrice(totalCost);
            shoppingCart.setTotalItems(totalItems);
            return shoppingCartRepository.save(shoppingCart);
        }


    }


    private CartItem finditem(Set<CartItem> cartItems, Long productId) {
        if (cartItems == null) {
            return null;
        }
        for (CartItem cartItem : cartItems) {
            if (cartItem.getProduct().getId().equals(productId)) {
                return cartItem;
            }
        }
        return null;
    }
public void deleteShpoingCartById(Long id){
    shoppingCartRepository.deleteById(id);
}
    private Product transfer(ProductDto productDto) {
        Product newproduct = new Product();
        newproduct.setId(productDto.getId());
        newproduct.setName(productDto.getName());
        newproduct.setCurrentQuantity(productDto.getCurrentQuantity());
        newproduct.setCostPrice(productDto.getCostPrice());
        newproduct.setSalePrice(productDto.getSalePrice());
        newproduct.setCategory(productDto.getCategory());
        newproduct.setImage(productDto.getImage());
        newproduct.setDescription(productDto.getDescription());
        newproduct.setIs_actived(productDto.getIs_actived());
        newproduct.setIs_deleted(productDto.getIs_deleted());
        return newproduct;
    }

    private int getTotalItems(Set<CartItem> cartItems) {
        if (cartItems == null) {
            return 0;

        } else {
            int total = 0;
            for (CartItem cartItem : cartItems) {
                total += cartItem.getQuantity();
            }
            return total;

        }
    }

    private double getTotalCost(Set<CartItem> cartItems) {
        if (cartItems == null) {
            return 0;
        } else {
            double total = 0;
            for (CartItem cartItem : cartItems) {
                total += cartItem.getQuantity() * cartItem.getUnitePrice();
            }
            return total;
        }
    }
}

