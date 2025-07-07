package com.cedacri.vaadin_task.backend.service.impl;

import com.cedacri.vaadin_task.backend.dto.RegisterDto;
import com.cedacri.vaadin_task.backend.dto.UserDto;
import com.cedacri.vaadin_task.backend.entity.Role;
import com.cedacri.vaadin_task.backend.entity.UserEntity;
import com.cedacri.vaadin_task.backend.entity.enums.UserRole;
import com.cedacri.vaadin_task.backend.exception.RoleNotFoundException;
import com.cedacri.vaadin_task.backend.exception.UserAlreadyExistsException;
import com.cedacri.vaadin_task.backend.mapper.UserMapper;
import com.cedacri.vaadin_task.backend.repository.RoleRepository;
import com.cedacri.vaadin_task.backend.repository.UserRepository;
import com.cedacri.vaadin_task.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.cedacri.vaadin_task.backend.mapper.UserMapper.mapToUserEntity;
import static org.reflections.Reflections.log;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void saveUser(RegisterDto registerDto) {
        if (checkIfEmailExists(registerDto.getEmail()) || checkIfUsernameExists(registerDto.getUsername())) {
            throw new UserAlreadyExistsException("User already exists.");
        }

        UserEntity user = mapToUserEntity(registerDto);

        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setRole(getDefaultRole());

        log.info("Saving user with email: {}", registerDto.getEmail());

        UserEntity savedUser = userRepository.save(user);

        log.info("User saved with ID: {}", savedUser.getId());
    }

    @Override
    @Transactional
    public UserEntity getByUsername(String username) {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format("User %s not found", username)));
    }

    @Override
    @Transactional
    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::mapToUserDTO)
                .toList();
    }

    @Override
    @Transactional
    public void removeUser(Long userId) {
        userRepository.deleteById(userId);
    }

    private boolean checkIfEmailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    private boolean checkIfUsernameExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    private Role getDefaultRole() {
        return roleRepository.findByRoleName(UserRole.USER)
                .orElseThrow(() -> new RoleNotFoundException("Role not found."));
    }
}
