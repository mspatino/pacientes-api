package com.consultorio.pacientes.services.impl;

import java.time.LocalDateTime;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.consultorio.pacientes.dtos.LoginRequestDTO;
import com.consultorio.pacientes.dtos.TokenResponseDTO;
import com.consultorio.pacientes.entities.RefreshToken;
import com.consultorio.pacientes.entities.User;
import com.consultorio.pacientes.repositories.UserRepository;
import com.consultorio.pacientes.security.JwtService;
import com.consultorio.pacientes.services.RefreshTokenService;


@Service
public class AuthServiceImpl {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    public AuthServiceImpl(
            AuthenticationManager authenticationManager,
            UserRepository userRepository,
            JwtService jwtService,
            RefreshTokenService refreshTokenService) {

        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
    }

    public TokenResponseDTO login(LoginRequestDTO request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow();

        String accessToken = jwtService.generateToken(user.getUsername());

        RefreshToken refreshToken =
                refreshTokenService.createRefreshToken(user);

        return new TokenResponseDTO(
                accessToken,
                refreshToken.getToken()
        );
    }

    public TokenResponseDTO refreshToken(String requestToken) {

            RefreshToken refreshToken = refreshTokenService.findByToken(requestToken);

        //     if (refreshToken.isRevoked()) {
        //             throw new RuntimeException("Refresh token revocado");
        //     }

            if (refreshToken.isRevoked()) {
                    // posible ataque
                    refreshTokenService.revokeAllUserTokens(
                                    refreshToken.getUser());

                    throw new RuntimeException("Token reuse detectado");
            }

            if (refreshToken.getExpiryDate().isBefore(LocalDateTime.now())) {
                    throw new RuntimeException("Refresh token expirado");
            }

            String newAccessToken = jwtService.generateToken(
                            refreshToken.getUser().getUsername());

            return new TokenResponseDTO(newAccessToken, requestToken);
    }

    

}
