package com.sadcodes.authapp.controller;

import com.sadcodes.authapp.dto.LoginRequest;
import com.sadcodes.authapp.dto.TokenResponse;
import com.sadcodes.authapp.dto.UserDto;
import com.sadcodes.authapp.entities.RefreshToken;
import com.sadcodes.authapp.entities.User;
import com.sadcodes.authapp.repository.RefreshTokenRepository;
import com.sadcodes.authapp.repository.UserRepository;
import com.sadcodes.authapp.security.JwtService;
import com.sadcodes.authapp.service.CookieService;
import com.sadcodes.authapp.service.impl.AuthServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthServiceImpl authService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final ModelMapper modelMapper;
    private final RefreshTokenRepository refreshTokenRepository;
    private final CookieService cookieService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        // authenticate
        Authentication authenticate = authenticate(loginRequest);
        User user = userRepository.findByEmail(loginRequest.email())
                .orElseThrow(() -> new BadCredentialsException("Invalid username or password"));

        if (!user.isEnable()) {
            throw new DisabledException("User is disabled");
        }

        String jti = UUID.randomUUID().toString();
        var refreshTokenObj = RefreshToken.builder()
                .jti(jti)
                .user(user)
                .createdAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(jwtService.getRefreshTtlSeconds()))
                .revoked(false)
                .build();

        // refresh token save -- information
        refreshTokenRepository.save(refreshTokenObj);

        // generate token
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user,refreshTokenObj.getJti());

        // use cookie service to attach refresh token in cookie
        cookieService.attachRefreshCookie(response,refreshToken, (int) jwtService.getAccessTtlSeconds());
        cookieService.addNoStoreHeaders(response);

        TokenResponse tokenResponse = TokenResponse.of(accessToken, refreshToken,
                jwtService.getAccessTtlSeconds(), modelMapper.map(user, UserDto.class));
        return ResponseEntity.ok(tokenResponse);

    }

    private Authentication authenticate(LoginRequest loginRequest) {
        try {
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.email(),
                    loginRequest.password()));
        } catch (Exception e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerDto(@RequestBody UserDto userDto) {
        return new ResponseEntity<>(authService.registerDto(userDto), HttpStatus.CREATED);
    }
}
