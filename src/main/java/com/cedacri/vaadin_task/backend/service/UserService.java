package com.cedacri.vaadin_task.backend.service;

import com.cedacri.vaadin_task.backend.dto.RegisterDto;
import com.cedacri.vaadin_task.backend.dto.UserDto;
import com.cedacri.vaadin_task.backend.entity.UserEntity;

import java.util.List;

public interface UserService {

    void saveUser(RegisterDto registerDto);

    UserEntity getByUsername(String username);

    List<UserDto> getAllUsers();

    void removeUser(Long userId);
}
