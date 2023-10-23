package com.example.demo.Usuario;

import com.example.demo.Hilos.Hilo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
}
