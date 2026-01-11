package com.sadcodes.authapp.controller;

import com.sadcodes.authapp.dto.UserDto;
import com.sadcodes.authapp.service.impl.AuthServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthServiceImpl authService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerDto(@RequestBody UserDto userDto){
        return new ResponseEntity<>(authService.registerDto(userDto), HttpStatus.CREATED);
    }
}
