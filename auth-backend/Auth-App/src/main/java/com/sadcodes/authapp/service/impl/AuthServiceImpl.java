package com.sadcodes.authapp.service.impl;

import com.sadcodes.authapp.dto.UserDto;
import com.sadcodes.authapp.service.AuthService;
import com.sadcodes.authapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final UserService userService;

    @Override
    public UserDto registerDto(UserDto userDto) {
        UserDto user = userService.createUser(userDto);
        return user;
    }
}
