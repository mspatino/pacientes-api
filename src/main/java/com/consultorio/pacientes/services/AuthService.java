package com.consultorio.pacientes.services;

import com.consultorio.pacientes.dtos.LoginRequestDTO;
import com.consultorio.pacientes.dtos.TokenResponseDTO;

public interface AuthService {

     public TokenResponseDTO login(LoginRequestDTO request);
     public TokenResponseDTO refreshToken(String requestToken);

}
