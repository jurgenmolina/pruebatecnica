package com.co.cetus.pruebatecnica.config;

import com.co.cetus.pruebatecnica.model.entity.Rol;
import com.co.cetus.pruebatecnica.repository.RolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RolDataInitializer implements CommandLineRunner {

    private final RolRepository rolRepository;

    @Override
    public void run(String... args) throws Exception {

        List<Rol> existingRoles = rolRepository.findAll();
        boolean userRoleExists = existingRoles.stream().anyMatch(rol -> rol.getNombre().equals("USER"));

        if (!userRoleExists) {
            Rol userRole = new Rol();
            userRole.setNombre("USER");
            rolRepository.save(userRole);
        }

    }
}
