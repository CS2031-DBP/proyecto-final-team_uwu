package com.example.demo.Hilos.domain;

import com.example.demo.Hilos.domain.Hilo;
import com.example.demo.Usuario.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HiloRepository extends JpaRepository<Hilo, Long> {
    List<Hilo> findByUsuario(Usuario usuario);
}
