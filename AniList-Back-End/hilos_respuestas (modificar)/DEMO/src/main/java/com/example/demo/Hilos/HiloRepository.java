package com.example.demo.Hilos;

import com.example.demo.Hilos.Hilo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HiloRepository extends JpaRepository<Hilo, Long> {
    // Aquí puedes agregar consultas personalizadas si es necesario
}
