package com.example.demo.Respuesta;

import com.example.demo.Hilos.Hilo;
import com.example.demo.Hilos.HiloRepository;
import com.example.demo.Usuario.Usuario;
import com.example.demo.Usuario.UsuarioRepository;
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

    @Autowired
    private UsuarioRepository usuarioRepository;

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
                ,respuesta.getHilo().getId()))
                .collect(Collectors.toList());

        return respuestaDTOs;
    }

    @GetMapping("/{userId}/{hiloId}")
    public ResponseEntity<List<RespuestaDTO>> getRespuestasByUserAndHilo(
            @PathVariable Long userId,
            @PathVariable Long hiloId) {
        List<RespuestaDTO> respuestaDto = new ArrayList<>();
        List<Long> temp = new ArrayList<>();
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(userId);
        if(usuarioOptional.isPresent()){
            List<Hilo> prueba = hiloRepository.findByUsuario(usuarioOptional.get());
            for(Hilo hilo : prueba){
                if(hilo.getId()==hiloId){
                    List<Respuesta> respuestas = respuestaRepository.findByHilo(hilo);
                    respuestaDto = respuestas.stream()
                            .map(respuesta -> new RespuestaDTO(
                                    respuesta.getId(),
                                    respuesta.getContenido(),
                                    respuesta.getSubrespuestas().stream()
                                            .map(Respuesta::getId)
                                            .collect(Collectors.toList())
                                    ,respuesta.getHilo().getId()))
                            .collect(Collectors.toList());
                }
            }
            System.out.println(temp);
        }



        return new ResponseEntity<>(respuestaDto,HttpStatus.OK);
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
            @RequestBody Respuesta subrespuesta){
            Respuesta respuesta = respuestaRepository.findById(respuestaId)
                    .orElseThrow(() -> new EntityNotFoundException("No se encontró la respuesta con el ID: " + respuestaId));
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
                            .collect(Collectors.toList()),respuesta.getHilo().getId()
            );

            return new ResponseEntity<>(respuestaDTO, HttpStatus.OK);
    }
}
