package com.example.demo.Hilos;

import com.example.demo.Hilos.Hilo;
import com.example.demo.Usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HiloRepository extends JpaRepository<Hilo, Long> {
    List<Hilo> findByUsuario(Usuario usuario);
}
