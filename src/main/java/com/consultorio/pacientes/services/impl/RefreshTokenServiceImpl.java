package com.consultorio.pacientes.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.consultorio.pacientes.entities.RefreshToken;
import com.consultorio.pacientes.entities.User;
import com.consultorio.pacientes.repositories.RefreshTokenRepository;
import com.consultorio.pacientes.services.RefreshTokenService;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService{

    private final RefreshTokenRepository repository;

    public RefreshTokenServiceImpl(RefreshTokenRepository repository) {
        this.repository = repository;
    }

    public RefreshToken createRefreshToken(User user) {

        RefreshToken token = new RefreshToken();

        token.setToken(UUID.randomUUID().toString());
        token.setUser(user);
        token.setExpiryDate(LocalDateTime.now().plusDays(7));
        token.setRevoked(false);

        return repository.save(token);
    }

    public RefreshToken findByToken(String token) {

        return repository.findByToken(token)
                .orElseThrow(() ->
                        new RuntimeException("Refresh token inválido"));
    }

        public void revokeAllUserTokens(User user) {

            List<RefreshToken> tokens = repository.findByUser(user);

            for (RefreshToken token : tokens) {

                token.setRevoked(true);

            }

            repository.saveAll(tokens);
        }

}
