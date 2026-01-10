package com.sadcodes.authapp.service.impl;

import com.sadcodes.authapp.dto.UserDto;
import com.sadcodes.authapp.entities.User;
import com.sadcodes.authapp.exceptions.ResourceNotFoundException;
import com.sadcodes.authapp.helpers.UserHelper;
import com.sadcodes.authapp.repository.UserRepository;
import com.sadcodes.authapp.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public UserDto createUser(UserDto userDto) {
        if (userDto.getEmail() == null || userDto.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email is required");
        }

        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new IllegalArgumentException("User with given email is already exist");
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
        UUID uId = UserHelper.parseUUID(userId);
        User existingUser = userRepository.findById(uId)
                .orElseThrow(() -> new ResourceNotFoundException("User with give id is not present"));

        // we are not going to change email id for this project
        if (userDto.getName() != null)
            existingUser.setName(userDto.getName());
        if (userDto.getImage() != null)
            existingUser.setImage(userDto.getImage());
        if (userDto.getPassword() != null)
            existingUser.setPassword(userDto.getPassword());
        if (userDto.getProvider() != null)
            existingUser.setProvider(userDto.getProvider());
        existingUser.setEnable(userDto.isEnable());
        User updateUser = userRepository.save(existingUser);
        return modelMapper.map(updateUser, UserDto.class);
    }


    @Override
    public UserDto getUserById(String userId) {
        User user = userRepository.findById(UserHelper.parseUUID(userId))
                .orElseThrow(() -> new ResourceNotFoundException("User with give id is not present"));
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public Iterable<UserDto> getAllUser() {
        return userRepository.findAll().stream()
                .map(user -> modelMapper.map(user, UserDto.class)).toList();
    }

    @Override
    public void deleteUser(String userId) {
        UUID uuid = UserHelper.parseUUID(userId);
        User user = userRepository.findById(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("User with give id is not present"));
        userRepository.delete(user);
    }

}
