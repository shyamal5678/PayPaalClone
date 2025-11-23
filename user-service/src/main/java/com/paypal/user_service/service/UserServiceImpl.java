package com.paypal.user_service.service;

import com.paypal.user_service.dto.UserResponseDTO;
import com.paypal.user_service.entity.User;
import com.paypal.user_service.repository.UserRepository;
import com.paypal.user_service.security.SecurityConfig;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UserServiceImpl implements UserService {

    private SecurityConfig config;
    private UserRepository userRepository;

    //construtor injection
    public UserServiceImpl(UserRepository userRepository,SecurityConfig securityConfig) {
        this.userRepository = userRepository;
        this.config=securityConfig;
    }



    @Override
    public User createUser(User user) {
        userRepository.findByEmail(user.getEmail()).ifPresent(
                existingUser->{
                    throw new IllegalArgumentException("Email already exists: " + user.getEmail());
                }
                );
        String pwd= config.passwordEncoder().encode(user.getPassword());
        user.setPassword(pwd);
        return userRepository.save(user);
    }

    @Override
    public Optional<UserResponseDTO> getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::mapToDTO);
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {

        return userRepository.findAll().stream().map(this::mapToDTO).toList();
    }

    @Override
    public UserResponseDTO mapToDTO(User user) {
        return new UserResponseDTO(user.getUserId(),user.getName(),user.getEmail());
    }

    @Override
    public User mapToUser(UserResponseDTO userResponseDTO) {
        return null;
    }
}