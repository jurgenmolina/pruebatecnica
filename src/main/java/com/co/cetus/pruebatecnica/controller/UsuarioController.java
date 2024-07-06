package com.co.cetus.pruebatecnica.controller;

import com.co.cetus.pruebatecnica.jwt.JwtService;
import com.co.cetus.pruebatecnica.model.response.UsuarioResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:4200"})
public class UsuarioController {

    private static final Logger logger = Logger.getLogger(UsuarioController.class.getName());
    private final JwtService jwtService;
    private final ModelMapper modelMapper;

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/token")
    public ResponseEntity<UsuarioResponse> getUsuarionameToken(@RequestHeader("Authorization") String token) {
        try {
            UsuarioResponse usuarioResponse = jwtService.getUserFromToken(token);
            return ResponseEntity.ok(usuarioResponse);
        } catch (Exception e) {
            logger.severe("Ha ocurrido un error:");
            logger.severe(e.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}