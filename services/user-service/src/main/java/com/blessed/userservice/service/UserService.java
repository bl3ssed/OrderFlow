package com.blessed.userservice.service;

import com.blessed.userservice.exception.UserNotFoundException;
import com.blessed.userservice.mapper.UserMapper;
import com.blessed.userservice.model.UserEntity;
import com.blessed.userservice.model.dto.UserCreateRequest;
import com.blessed.userservice.model.dto.UserResponse;
import com.blessed.userservice.model.dto.UserUpdateRequest;
import com.blessed.userservice.repository.UserRepository;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.blessed.userservice.mapper.UserMapper.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse createUser(UserCreateRequest userCreateDto) {
        UserEntity userEntity = UserEntity.builder()
                .email(userCreateDto.getEmail())
                .password(passwordEncoder.encode(userCreateDto.getPassword()))
                .name(userCreateDto.getName())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        return toUserResponseDto(userRepository.save(userEntity)) ;
    }

    public UserResponse findUserById(Long id) {
        return toUserResponseDto(userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found")));
    }

    public Page<UserResponse> findAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(UserMapper::toUserResponseDto);
    }

    public UserResponse updateUser(Long id, UserUpdateRequest userUpdateDto) {
        UserEntity entity = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
        if ((userUpdateDto.getEmail() != null) && !userUpdateDto.getEmail().isEmpty()) {
            entity.setEmail(userUpdateDto.getEmail());
        }
        if (userUpdateDto.getName()!= null && !userUpdateDto.getName().isEmpty()) {
            entity.setName(userUpdateDto.getName());
        }
        entity.setUpdatedAt(LocalDateTime.now());
        return toUserResponseDto(userRepository.save(entity));
    }


    public Optional<UserResponse> findByEmail(@NotBlank @Email String email) {
        if(userRepository.findByEmail(email)!=null) {
            return Optional.ofNullable(toUserResponseDto(userRepository.findByEmail(email)));
        }
        return Optional.empty();
    }
}
