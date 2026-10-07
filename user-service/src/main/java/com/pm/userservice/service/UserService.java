package com.pm.userservice.service;

import com.pm.userservice.dto.UserRequestDTO;
import com.pm.userservice.dto.UserResponseDTO;
import com.pm.userservice.entity.User;
import com.pm.userservice.mapper.UserMapper;
import com.pm.userservice.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }

    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        if (userRepository.findByEmail(userRequestDTO.getEmail()).isPresent()) {
            log.error("User with email {} already exists", userRequestDTO.getEmail());
            throw new IllegalArgumentException("User with email " + userRequestDTO.getEmail() + " already exists");
        }
        return UserMapper.toDto(userRepository.save(UserMapper.toEntity(userRequestDTO)));
    }

    public UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("User with id {} not found", id);
                    return new IllegalArgumentException("User with id " + id + " not found");
                });

        if (!user.getEmail().equals(userRequestDTO.getEmail()) && userRepository.findByEmail(userRequestDTO.getEmail()).isPresent()) {
            log.error("User with email {} already exists", userRequestDTO.getEmail());
            throw new IllegalArgumentException("User with email " + userRequestDTO.getEmail() + " already exists");
        }
        user.setName(userRequestDTO.getName());
        user.setSurname(userRequestDTO.getSurname());
        user.setEmail(userRequestDTO.getEmail());
        user.setAddress(userRequestDTO.getAddress());
        user.setAlerting(userRequestDTO.isAlerting());
        user.setEnergyAlertingThreshold(userRequestDTO.getEnergyAlertingThreshold());
        return UserMapper.toDto(userRepository.save(user));
    }

    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("User with id {} not found", id);
                    return new IllegalArgumentException("User with id " + id + " not found");
                });
        return UserMapper.toDto(user);
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("User with id {} not found", id);
                    return new IllegalArgumentException("User with id " + id + " not found");
                });
        userRepository.delete(user);
    }


}
