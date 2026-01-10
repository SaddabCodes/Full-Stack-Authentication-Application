package com.sadcodes.authapp.service;

import com.sadcodes.authapp.dto.UserDto;

public interface UserService {

    UserDto createUser(UserDto userDto);
    UserDto getUserByEmail(String email);
    UserDto updateUser(UserDto userDto, String userId);
    void deleteUser(String userId);
    Iterable<UserDto>getAllUser();
    UserDto getUserById(String userId);
}
