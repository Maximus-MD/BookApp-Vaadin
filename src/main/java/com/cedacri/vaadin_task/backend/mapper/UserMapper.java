package com.cedacri.vaadin_task.backend.mapper;

import com.cedacri.vaadin_task.backend.dto.RegisterDto;
import com.cedacri.vaadin_task.backend.dto.UserDto;
import com.cedacri.vaadin_task.backend.entity.UserEntity;

public class UserMapper {
    public static UserDto mapToUserDTO(UserEntity user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }

    public static UserEntity mapToUserEntity(RegisterDto registerDto) {
        return UserEntity.builder()
                .username(registerDto.getUsername())
                .email(registerDto.getEmail())
                .password(registerDto.getPassword())
                .build();
    }
}
