package com.example.demo.Hilos;

import com.example.demo.CapaSeguridad.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HiloRepository extends JpaRepository<Hilo, Long> {
    List<Hilo> findByUsuario(Usuario usuario);
}
