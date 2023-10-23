package com.example.demo.Labels;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Hilos.HiloDTO;
import com.example.demo.Labels.Label;
import com.example.demo.Labels.LabelRepository;
import com.example.demo.Respuesta.Respuesta;
@RestController
@RequestMapping("/labels")
public class LabelController {
    @Autowired
    private LabelRepository lavelRepository;

    // Endpoint para obtener todos los hilos
    // Convierte una entidad Hilo en un HiloDTO

    // Endpoint para obtener todos los hilos como DTOs
    @GetMapping
    public List<Label> getHilos() {
        List<Label> hilos = lavelRepository.findAll();
        return hilos;
    }
    // Endpoint para crear un nuevo hilo
    @PostMapping
    public ResponseEntity<Label> createHilo(@RequestBody Label label) {
        Label nuevoHilo=lavelRepository.save(label);
        return new ResponseEntity<>(nuevoHilo, HttpStatus.CREATED);
    }
}
