package com.example.demo.Estados.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface EstadoRespository extends JpaRepository<Estados,Long> {
    List<Estados> findAllByUsuarioId(Long userId);
}
