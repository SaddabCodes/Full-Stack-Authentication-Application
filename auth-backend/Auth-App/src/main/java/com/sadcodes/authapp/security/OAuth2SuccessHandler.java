package com.sadcodes.authapp.security;

import com.sadcodes.authapp.entities.Provider;
import com.sadcodes.authapp.entities.RefreshToken;
import com.sadcodes.authapp.entities.User;
import com.sadcodes.authapp.repository.RefreshTokenRepository;
import com.sadcodes.authapp.repository.UserRepository;
import com.sadcodes.authapp.service.CookieService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final CookieService cookieService;
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${app.auth.frontend.success-redirect}")
    private String frontEndSuccessUrl;

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

                String providerId = oAuth2User.getAttribute("sub");
                String name = oAuth2User.getAttribute("name");
                String picture = oAuth2User.getAttribute("picture");
                String email = oAuth2User.getAttribute("email");

                user = userRepository
                        .findByProviderAndProviderId(Provider.GOOGLE, providerId)
                        .orElseGet(() -> {

                            // CHECK EMAIL FIRST
                            return userRepository.findByEmail(email)
                                    .map(existingUser -> {
                                        existingUser.setProvider(Provider.GOOGLE);
                                        existingUser.setProviderId(providerId);
                                        return userRepository.save(existingUser);
                                    })
                                    .orElseGet(() ->
                                            userRepository.save(
                                                    User.builder()
                                                            .email(email)
                                                            .name(name)
                                                            .image(picture)
                                                            .provider(Provider.GOOGLE)
                                                            .providerId(providerId)
                                                            .build()
                                            )
                                    );
                        });
            }


            case "github" -> {

                String providerId = oAuth2User.getAttribute("id").toString();
                String name = oAuth2User.getAttribute("name");
                String image = oAuth2User.getAttribute("avatar_url");
                String email = oAuth2User.getAttribute("email");

                user = userRepository
                        .findByProviderAndProviderId(Provider.GITHUB, providerId)
                        .orElseGet(() -> {

                            return userRepository.findByEmail(email)
                                    .map(existingUser -> {
                                        existingUser.setProvider(Provider.GITHUB);
                                        existingUser.setProviderId(providerId);
                                        return userRepository.save(existingUser);
                                    })
                                    .orElseGet(() ->
                                            userRepository.save(
                                                    User.builder()
                                                            .email(email)
                                                            .name(name)
                                                            .image(image)
                                                            .provider(Provider.GITHUB)
                                                            .providerId(providerId)
                                                            .build()
                                            )
                                    );
                        });
            }
            default -> throw new IllegalStateException("Unsupported provider: " + registrationId);


        }


        String jti = UUID.randomUUID().toString();
        RefreshToken refreshTokenOb = RefreshToken.builder().jti(jti).user(user).revoked(false).createdAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(jwtService.getRefreshTtlSeconds())).build();

        refreshTokenRepository.save(refreshTokenOb);

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user, refreshTokenOb.getJti());

        cookieService.attachRefreshCookie(response, refreshToken, (int) jwtService.getAccessTtlSeconds());
//        response.getWriter().write("Login successful");
        response.sendRedirect(frontEndSuccessUrl);
    }

}
