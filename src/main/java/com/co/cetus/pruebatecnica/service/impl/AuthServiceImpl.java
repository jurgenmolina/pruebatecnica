package com.co.cetus.pruebatecnica.service.impl;

import com.co.cetus.pruebatecnica.jwt.JwtService;
import com.co.cetus.pruebatecnica.model.entity.Usuario;
import com.co.cetus.pruebatecnica.model.request.LoginRequest;
import com.co.cetus.pruebatecnica.model.request.RegisterRequest;
import com.co.cetus.pruebatecnica.model.response.AuthResponse;
import com.co.cetus.pruebatecnica.repository.RolRepository;
import com.co.cetus.pruebatecnica.repository.UsuarioRepository;
import com.co.cetus.pruebatecnica.service.AuthService;
import com.co.cetus.pruebatecnica.exception.AuthenticationException;
import com.co.cetus.pruebatecnica.exception.RegistrationException;
import com.co.cetus.pruebatecnica.exception.TokenRefreshException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse login(LoginRequest request) {
        try {
            logger.info("Intento de inicio de sesión para el usuario: {}", request.getUsername());
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            Usuario usuario = usuarioRepository.findByUsername(request.getUsername())
                    .orElseThrow(() -> new AuthenticationException("Usuario no encontrado", HttpStatus.NOT_FOUND));
            String token = jwtService.getToken(usuario);
            logger.info("Inicio de sesión exitoso para el usuario: {}", request.getUsername());
            return AuthResponse.builder()
                    .token(token)
                    .build();
        } catch (org.springframework.security.core.AuthenticationException e) {
            logger.error("Error de autenticación para el usuario: {}", request.getUsername(), e);
            throw new AuthenticationException("Credenciales de inicio de sesión inválidas", HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    @Transactional
    public AuthResponse registerUser(RegisterRequest request) {
        try {
            logger.info("Intento de registro para el usuario: {}", request.getUsername());
            if (usuarioRepository.existsByUsername(request.getUsername())) {
                logger.warn("Intento de registro con nombre de usuario existente: {}", request.getUsername());
                throw new RegistrationException("El nombre de usuario ya existe");
            }

            Usuario usuario = Usuario.builder()
                    .username(request.getUsername().toUpperCase())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .nombre(request.getNombre().toUpperCase())
                    .tipoDocumento(request.getTipoDocumento().toUpperCase())
                    .documento(request.getDocumento().toUpperCase())
                    .telefono(request.getTelefono().toUpperCase())
                    .email(request.getEmail().toUpperCase())
                    .roles(Collections.singletonList(rolRepository.findByNombre("USER")
                            .orElseThrow(() -> new RegistrationException("Rol de usuario no encontrado"))))
                    .build();

            usuarioRepository.save(usuario);
            logger.info("Usuario registrado exitosamente: {}", request.getUsername());

            return AuthResponse.builder()
                    .token(jwtService.getToken(usuario))
                    .build();
        } catch (Exception e) {
            logger.error("Error durante el registro del usuario: {}", request.getUsername(), e);
            throw new RegistrationException("Error al registrar usuario: " + e.getMessage());
        }
    }

    @Override
    public AuthResponse refreshToken(String refreshToken) {
        try {
            logger.info("Intento de refrescar token");
            if (jwtService.isTokenExpired(refreshToken)) {
                logger.warn("Intento de refrescar token expirado");
                throw new TokenRefreshException("El token de refresco ha expirado");
            }

            String username = jwtService.getUsernameFromToken(refreshToken);
            Usuario usuario = usuarioRepository.findByUsername(username)
                    .orElseThrow(() -> new TokenRefreshException("Usuario no encontrado para el token proporcionado"));
            String newToken = jwtService.getToken(usuario);

            logger.info("Token refrescado exitosamente para el usuario: {}", username);
            return AuthResponse.builder()
                    .token(newToken)
                    .build();
        } catch (Exception e) {
            logger.error("Error al refrescar el token", e);
            throw new TokenRefreshException("Error al refrescar el token: " + e.getMessage());
        }
    }
}