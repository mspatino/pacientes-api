package com.consultorio.pacientes.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.consultorio.pacientes.entities.RefreshToken;
import com.consultorio.pacientes.entities.User;

import jakarta.transaction.Transactional;

public interface RefreshTokenRepository
        extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    List<RefreshToken> findByUser(User user);

    @Modifying
    @Transactional
    @Query("""
            DELETE FROM RefreshToken r
            WHERE r.expiryDate < :now OR r.revoked = true
            """)
    void deleteExpiredTokens(LocalDateTime now);
}

