package com.pm.userservice.mapper;

import com.pm.userservice.dto.UserRequestDTO;
import com.pm.userservice.dto.UserResponseDTO;
import com.pm.userservice.entity.User;

public class UserMapper {
    public static UserResponseDTO toDto(User user) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(user.getId());
        userResponseDTO.setName(user.getName());
        userResponseDTO.setSurname(user.getSurname());
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setAddress(user.getAddress());
        userResponseDTO.setAlerting(user.isAlerting());
        userResponseDTO.setEnergyAlertingThreshold(user.getEnergyAlertingThreshold());
        return userResponseDTO;
    }

    public static User toEntity(UserRequestDTO userResponseDTO) {
        User user = new User();
        user.setName(userResponseDTO.getName());
        user.setSurname(userResponseDTO.getSurname());
        user.setEmail(userResponseDTO.getEmail());
        user.setAddress(userResponseDTO.getAddress());
        user.setAlerting(userResponseDTO.isAlerting());
        user.setEnergyAlertingThreshold(userResponseDTO.getEnergyAlertingThreshold());
        return user;
    }
}
