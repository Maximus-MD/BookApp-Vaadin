package com.cedacri.vaadin_task.backend.service;

import com.cedacri.vaadin_task.backend.dto.CartItemDto;

import java.util.List;

public interface CartItemService {

    List<CartItemDto> getCartItems(String username);

    void addToCart(String username, Long bookId);

    void deleteFromCartById(Long cartItemId);

}
