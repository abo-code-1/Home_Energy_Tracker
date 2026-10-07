package com.pm.userservice.service;

import com.pm.userservice.dto.UserRequestDTO;
import com.pm.userservice.dto.UserResponseDTO;
import com.pm.userservice.entity.User;
import com.pm.userservice.mapper.UserMapper;
import com.pm.userservice.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
@Slf4j
@Service
public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        if (userRepository.findByEmail(userRequestDTO.getEmail()).isPresent()) {
            log.error("User with email {} already exists", userRequestDTO.getEmail());
            throw new IllegalArgumentException("User with email " + userRequestDTO.getEmail() + " already exists");
        }
        return UserMapper.toDto(userRepository.save(UserMapper.toEntity(userRequestDTO)));

    }


}
