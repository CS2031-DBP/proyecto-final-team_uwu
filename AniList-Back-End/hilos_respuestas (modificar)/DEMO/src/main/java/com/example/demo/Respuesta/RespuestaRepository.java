package com.example.demo.Respuesta;

import com.example.demo.Hilos.Hilo;
import com.example.demo.CapaSeguridad.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface RespuestaRepository extends JpaRepository<Respuesta, Long> {
    List<Respuesta> findByUsuariosParticipantes(Usuario usuario);
    List<Respuesta> findByHilo(Hilo hilo);

}