package com.example.demo.Respuesta;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RespuestaRepository extends JpaRepository<Respuesta, Long> {
    // Aquí puedes agregar consultas personalizadas si es necesario
}
