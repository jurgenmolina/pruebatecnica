package com.co.cetus.pruebatecnica.service;

import com.co.cetus.pruebatecnica.model.request.LoginRequest;
import com.co.cetus.pruebatecnica.model.request.RegisterRequest;
import com.co.cetus.pruebatecnica.model.response.AuthResponse;

public interface AuthService {

    AuthResponse login(LoginRequest request);

    AuthResponse registerUser(RegisterRequest request);

    AuthResponse refreshToken(String refreshToken);
}
