package com.ecommerce.library.service;

import com.ecommerce.library.model.CartItem;
import com.ecommerce.library.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CartItemService {
    @Autowired
    private CartItemRepository  cartItemRepository;
    public CartItem saveCartItem(CartItem cartItem) {
        return cartItemRepository.save(cartItem);

    }
    public void deleteItemCart(CartItem cartItem) {
        cartItemRepository.delete(cartItem);
    }
    public void deleteAllItemCart(Set<CartItem> cartItem) {
        cartItemRepository.deleteAll(cartItem);
    }

}
