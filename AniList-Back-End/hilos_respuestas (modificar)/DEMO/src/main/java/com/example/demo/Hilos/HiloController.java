package com.example.demo.Hilos;

import com.example.demo.Respuesta.Respuesta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/hilos")
public class HiloController {

    @Autowired
    private HiloRepository hiloRepository;

    // Endpoint para obtener todos los hilos
    // Convierte una entidad Hilo en un HiloDTO
    private HiloDTO convertToDTO(Hilo hilo) {
        HiloDTO dto = new HiloDTO();
        dto.setId(hilo.getId());
        dto.setTema(hilo.getTema());
        dto.setContenido(hilo.getContenido());
        dto.setFechaCreacion(hilo.getFechaCreacion());

        // Obtiene solo los IDs de las respuestas asociadas
        List<Long> respuestaIds = hilo.getRespuestas()
                .stream()
                .map(Respuesta::getId)
                .collect(Collectors.toList());
        dto.setRespuestaIds(respuestaIds);

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
