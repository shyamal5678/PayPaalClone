package com.paypal.user_service.service;

import com.paypal.user_service.dto.UserResponseDTO;
import com.paypal.user_service.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User createUser(User user);
    Optional<UserResponseDTO> getUserById(Long id);
    List<UserResponseDTO> getAllUsers();
    UserResponseDTO mapToDTO(User user);
    User mapToUser(UserResponseDTO userResponseDTO);
}
