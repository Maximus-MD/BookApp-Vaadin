package com.cedacri.vaadin_task.backend.repository;

import com.cedacri.vaadin_task.backend.entity.Book;
import com.cedacri.vaadin_task.backend.entity.CartItem;
import com.cedacri.vaadin_task.backend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findByUser(UserEntity user);

    Optional<CartItem> findByUserAndBook(UserEntity user, Book book);
}
