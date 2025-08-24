package com.blessed.userservice.mapper;

import com.blessed.userservice.model.UserEntity;
import com.blessed.userservice.model.dto.UserResponse;

public class UserMapper {

    public static UserResponse toUserResponseDto(UserEntity user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}
