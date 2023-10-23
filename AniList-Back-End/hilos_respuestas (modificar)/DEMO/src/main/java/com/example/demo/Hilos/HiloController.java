package com.example.demo.Hilos;

import com.example.demo.Labels.Label;
import com.example.demo.Respuesta.Respuesta;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/hilos")
public class HiloController {

    @Autowired
    private HiloRepository hiloRepository;

    private ModelMapper modelMapper = new ModelMapper();

    
    // Endpoint para obtener todos los hilos
    // Convierte una entidad Hilo en un HiloDTO
    private HiloDTO convertToDTO(Hilo hilo) {
        HiloDTO dto = modelMapper.map(hilo, HiloDTO.class);
        // dto.setId(hilo.getId());
        // dto.setTema(hilo.getTema());
        // dto.setContenido(hilo.getContenido());
        // dto.setFechaCreacion(hilo.getFechaCreacion());


        // // Obtiene solo los IDs de las respuestas asociadas
        List<Long> respuestaIds = hilo.getRespuestas()
                .stream()
                .map(Respuesta::getId)
                .collect(Collectors.toList());
        dto.setRespuestaIds(respuestaIds);


        List<Long> labelsIds = hilo.getLabels()
                .stream()
                .map(Label::getId)
                .collect(Collectors.toList());
        dto.setLabelsIds(labelsIds);

        return dto;
    }

    // Endpoint para obtener todos los hilos como DTOs
    @GetMapping
    public List<HiloDTO> getHilos() {
        List<Hilo> hilos = hiloRepository.findAll();
        return hilos.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    // Endpoint para crear un nuevo hilo
    @PostMapping
    public ResponseEntity<Hilo> createHilo(@RequestBody Hilo hilo) {
        Hilo nuevoHilo = hiloRepository.save(hilo);
        return new ResponseEntity<>(nuevoHilo, HttpStatus.CREATED);
    }
}
