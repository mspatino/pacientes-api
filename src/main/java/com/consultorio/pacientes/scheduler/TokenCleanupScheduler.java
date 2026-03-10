package com.consultorio.pacientes.scheduler;

import java.time.LocalDateTime;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.consultorio.pacientes.repositories.RefreshTokenRepository;

@Component
public class TokenCleanupScheduler {

    private final RefreshTokenRepository refreshTokenRepository;

    public TokenCleanupScheduler(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Scheduled(cron = "0 0 3 * * *")
    public void cleanExpiredTokens() {

        refreshTokenRepository.deleteExpiredTokens(LocalDateTime.now());

    }

}
