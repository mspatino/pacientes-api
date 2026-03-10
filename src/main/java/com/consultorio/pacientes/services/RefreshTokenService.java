package com.consultorio.pacientes.services;

import com.consultorio.pacientes.entities.RefreshToken;
import com.consultorio.pacientes.entities.User;

public interface RefreshTokenService {

    public RefreshToken createRefreshToken(User user);
    public RefreshToken findByToken(String token);
     public void revokeAllUserTokens(User user);
    
}
