package com.cedacri.vaadin_task.backend.mapper;

import com.cedacri.vaadin_task.backend.dto.CartItemDto;
import com.cedacri.vaadin_task.backend.entity.CartItem;

public class CartItemMapper {
    public static CartItemDto mapToCartItemDTO(CartItem item) {
        return CartItemDto.builder()
                .id(item.getId())
                .bookName(item.getBook().getName())
                .quantity(item.getQuantity())
                .price(item.getBook().getPrice() * item.getQuantity())
                .build();
    }
}
