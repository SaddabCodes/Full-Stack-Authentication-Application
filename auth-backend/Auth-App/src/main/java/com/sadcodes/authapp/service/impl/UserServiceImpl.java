package com.sadcodes.authapp.service.impl;

import com.sadcodes.authapp.dto.UserDto;
import com.sadcodes.authapp.entities.User;
import com.sadcodes.authapp.exceptions.ResourceNotFoundException;
import com.sadcodes.authapp.repository.UserRepository;
import com.sadcodes.authapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        if (userDto.getEmail() != null || userDto.getEmail().isBlank()) {
            throw new RuntimeException("Email is required");
        }

        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new RuntimeException("Email already exist");
        }
        User user = modelMapper.map(userDto, User.class);
        User saveUser = userRepository.save(user);
        UserDto dto = modelMapper.map(saveUser, UserDto.class);
        return dto;

    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User with given email: " + email + " is not present"));
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        return null;
    }

    @Override
    public void deleteUser(String userId) {

    }

    @Override
    public Iterable<UserDto> getAllUser() {
        return userRepository.findAll().stream()
                .map(user -> modelMapper.map(user, UserDto.class)).toList();
    }
}
