package com.example.demo.Respuesta;

import com.example.demo.Usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RespuestaRepository extends JpaRepository<Respuesta, Long> {
    List<Respuesta> findByUsuarioParticipante(Usuario usuario);
}
