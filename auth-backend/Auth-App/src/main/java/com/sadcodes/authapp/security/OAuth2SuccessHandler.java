package com.sadcodes.authapp.security;

import com.sadcodes.authapp.entities.Provider;
import com.sadcodes.authapp.entities.User;
import com.sadcodes.authapp.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@AllArgsConstructor
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        logger.info("Successful authentication");

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        String registrationId = "Unknown";

        if (authentication instanceof OAuth2AuthenticationToken token) {
            registrationId = token.getAuthorizedClientRegistrationId();
        }

        logger.info("registrationId: " + registrationId);
        logger.info("user: " + oAuth2User.getAttributes());

        User user = null;

        switch (registrationId) {

            case "google" -> {
                String googleId = oAuth2User.getAttribute("sub");
                String email = oAuth2User.getAttribute("email");
                String name = oAuth2User.getAttribute("name");
                String picture = oAuth2User.getAttribute("picture");

                user = User.builder()
                        .email(email)
                        .name(name)
                        .image(picture)
                        .provider(Provider.GOOGLE)
                        .providerId(googleId)
                        .build();

                userRepository.findByEmail(email)
                        .ifPresentOrElse(
                                existingUser -> {
                                    logger.info("User already exists: " + existingUser);
                                },
                                () -> {
                                    logger.info("Saving new user");
                                    userRepository.save(user);
                                }
                        );
            }

            default -> logger.info("Unknown provider");
        }

        response.getWriter().write("Login successful");
    }

}
