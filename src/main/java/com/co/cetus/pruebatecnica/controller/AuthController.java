package com.co.cetus.pruebatecnica.controller;

import com.co.cetus.pruebatecnica.exception.AuthenticationException;
import com.co.cetus.pruebatecnica.exception.RegistrationException;
import com.co.cetus.pruebatecnica.exception.TokenRefreshException;
import com.co.cetus.pruebatecnica.model.request.LoginRequest;
import com.co.cetus.pruebatecnica.model.request.RefreshTokenRequest;
import com.co.cetus.pruebatecnica.model.request.RegisterRequest;
import com.co.cetus.pruebatecnica.model.response.AuthResponse;
import com.co.cetus.pruebatecnica.model.response.ErrorResponse;
import com.co.cetus.pruebatecnica.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:4200"})
public class AuthController {

    private final AuthService authService;
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        try {
            AuthResponse response = authService.login(request);
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            logger.error("Error de autenticación", e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("Error de autenticación", e.getMessage()));
        } catch (Exception e) {
            logger.error("Error inesperado durante el login", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error interno del servidor", "Ocurrió un error inesperado"));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        try {
            AuthResponse response = authService.registerUser(request);
            return ResponseEntity.ok(response);
        } catch (RegistrationException e) {
            logger.error("Error de registro", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Error de registro", e.getMessage()));
        } catch (AuthenticationException e) {
            logger.error("Error de autenticación durante el registro", e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("Error de autenticación", e.getMessage()));
        } catch (Exception e) {
            logger.error("Error inesperado durante el registro", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error interno del servidor", "Ocurrió un error inesperado"));
        }
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        try {
            AuthResponse authResponse = authService.refreshToken(request.getRefreshToken());
            return ResponseEntity.ok(authResponse);
        } catch (TokenRefreshException e) {
            logger.error("Error al refrescar token", e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("Error al refrescar token", e.getMessage()));
        } catch (Exception e) {
            logger.error("Error inesperado al refrescar token", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error interno del servidor", "Ocurrió un error inesperado"));
        }
    }
}