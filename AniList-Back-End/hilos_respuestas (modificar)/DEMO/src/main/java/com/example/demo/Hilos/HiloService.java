package com.example.demo.Hilos;


import com.example.demo.CapaSeguridad.domain.Usuario;
import com.example.demo.Usuario.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HiloService {

    @Autowired
    HiloRepository hiloRepository;

    @Autowired
    UsuarioService usuarioService;

    public List<Hilo> getThreadsCreatedByUser(Usuario usuario) {
        return hiloRepository.findByUsuario(usuario);
    }



}
