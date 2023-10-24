package com.example.demo.CapaSeguridad.controllers;

import com.example.demo.CapaSeguridad.domain.Usuario;
import com.example.demo.Usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users") // Define la ruta base para las operaciones de usuario
public class UserController {
    private final UsuarioRepository userRepository;

    @Autowired
    public UserController(UsuarioRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping // Esta anotación manejará las solicitudes GET a esta ruta
    public List<Usuario> getAllUsers() {
        // Recupera la lista de usuarios registrados desde el repositorio
        return userRepository.findAll();
    }
}
