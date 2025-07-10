package com.cedacri.vaadin_task.backend.service.impl;

import com.cedacri.vaadin_task.backend.dto.CartItemDto;
import com.cedacri.vaadin_task.backend.entity.Book;
import com.cedacri.vaadin_task.backend.entity.CartItem;
import com.cedacri.vaadin_task.backend.entity.UserEntity;
import com.cedacri.vaadin_task.backend.entity.enums.BookAvailability;
import com.cedacri.vaadin_task.backend.exception.BookNotFoundException;
import com.cedacri.vaadin_task.backend.mapper.CartItemMapper;
import com.cedacri.vaadin_task.backend.repository.BookRepository;
import com.cedacri.vaadin_task.backend.repository.CartItemRepository;
import com.cedacri.vaadin_task.backend.repository.UserRepository;
import com.cedacri.vaadin_task.backend.service.CartItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;

    private final BookRepository bookRepository;

    private final UserRepository userRepository;

    @Override
    public void addToCart(String username, Long bookId) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found.", username)));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(String.format("Book %d not found.", bookId)));

        if (book.getAvailability() != BookAvailability.AVAILABLE) {
            throw new IllegalStateException("Book is not available");
        }

        CartItem cartItem = cartItemRepository.findByUserAndBook(user, book)
                .orElse(CartItem.builder()
                        .user(user)
                        .book(book)
                        .quantity(0)
                        .build());

        cartItem.setQuantity(cartItem.getQuantity() + 1);

        cartItemRepository.save(cartItem);
    }

    @Override
    public List<CartItemDto> getCartItems(String username) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found.", username)));

        List<CartItem> items = cartItemRepository.findByUser(user);

        return items.stream()
                .map(CartItemMapper::mapToCartItemDTO)
                .toList();
    }

    @Override
    public void deleteFromCartById(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
        log.info("CartItem was deleted : {}", cartItemId);
    }
}
