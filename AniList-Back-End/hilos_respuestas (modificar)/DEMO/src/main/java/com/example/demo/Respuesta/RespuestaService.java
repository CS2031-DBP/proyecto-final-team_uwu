package com.example.demo.Respuesta;

import com.example.demo.CapaSeguridad.domain.Usuario;
import com.example.demo.Usuario.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RespuestaService {
    @Autowired
    RespuestaRepository respuestaRepository;
    @Autowired
    UsuarioService usuarioService;

    public List<Respuesta> getResponsesParticipatedByUser(Usuario usuario) {
        return respuestaRepository.findByUsuariosParticipantes(usuario);
    }

}
