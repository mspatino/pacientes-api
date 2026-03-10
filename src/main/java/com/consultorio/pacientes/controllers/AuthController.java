package com.consultorio.pacientes.controllers;

import org.springframework.web.bind.annotation.*;

import com.consultorio.pacientes.dtos.*;
import com.consultorio.pacientes.services.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public TokenResponseDTO login(@RequestBody LoginRequestDTO request) {
        return authService.login(request);
    }

    @PostMapping("/refresh")
    public TokenResponseDTO refreshToken(
            @RequestBody RefreshTokenRequestDTO request) {

        return authService.refreshToken(request.getRefreshToken());
    }
}

