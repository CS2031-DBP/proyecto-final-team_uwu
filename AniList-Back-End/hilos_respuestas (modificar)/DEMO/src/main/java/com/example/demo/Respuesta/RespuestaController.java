package com.example.demo.Respuesta;

import com.example.demo.Hilos.Hilo;
import com.example.demo.Hilos.HiloRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/respuestas")
public class RespuestaController {

    @Autowired
    private RespuestaRepository respuestaRepository;

    // Endpoint para obtener todas las respuestas
    @Autowired
    private HiloRepository hiloRepository;


    @GetMapping
    public List<RespuestaDTO> getRespuestas() {
        List<Respuesta> respuestas = respuestaRepository.findAll();

        // Mapea las respuestas a RespuestaDTO
        List<RespuestaDTO> respuestaDTOs = respuestas.stream()
                .map(respuesta -> new RespuestaDTO(
                        respuesta.getId(),
                        respuesta.getContenido(),
                        respuesta.getSubrespuestas().stream()
                                .map(Respuesta::getId)
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());

        return respuestaDTOs;
    }

    // Endpoint para crear una nueva respuesta
    @PostMapping("/{hiloId}")
    public ResponseEntity<Respuesta> createRespuesta(@RequestBody Respuesta respuesta, @PathVariable Long hiloId) {
        Optional<Hilo> existe = hiloRepository.findById(hiloId);
        if (existe.isPresent()){
            respuesta.setHilo(existe.get());
            Respuesta nuevaRespuesta = respuestaRepository.save(respuesta);
            return new ResponseEntity<>(new Respuesta(), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(new Respuesta(), HttpStatus.BAD_REQUEST);
    }
    // Método PATCH para añadir una subrespuesta a una respuesta existente
    @PatchMapping("/{respuestaId}")
    public ResponseEntity<RespuestaDTO> addSubrespuesta(
            @PathVariable Long respuestaId,
            @RequestBody Respuesta subrespuesta) {

        Respuesta respuesta = respuestaRepository.findById(respuestaId)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la respuesta con el ID: " + respuestaId));

        // Verifica si la lista de subrespuestas es nula y, si es así, crea una nueva lista
        if (respuesta.getSubrespuestas() == null) {
            respuesta.setSubrespuestas(new ArrayList<>());
        }

        // Agrega la subrespuesta a la lista de subrespuestas
        subrespuesta.setHilo(respuesta.getHilo());  // Asigna el mismo hilo a la subrespuesta
        subrespuesta.setRespuestaPadre(respuesta);  // Establece la respuesta padre

        respuesta.getSubrespuestas().add(subrespuesta);

        // Guarda la respuesta actualizada
        respuesta = respuestaRepository.save(respuesta);

        // Mapea la respuesta a un RespuestaDTO para la respuesta
        RespuestaDTO respuestaDTO = new RespuestaDTO(
                respuesta.getId(),
                respuesta.getContenido(),
                respuesta.getSubrespuestas().stream()
                        .map(Respuesta::getId)
                        .collect(Collectors.toList())
        );

        return new ResponseEntity<>(respuestaDTO, HttpStatus.OK);
    }
}
