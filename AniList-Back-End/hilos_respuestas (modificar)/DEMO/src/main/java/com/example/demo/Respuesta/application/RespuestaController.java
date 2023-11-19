package com.example.demo.Respuesta.application;

import com.example.demo.Hilos.domain.Hilo;
import com.example.demo.Hilos.domain.HiloRepository;
import com.example.demo.Respuesta.respuestaDTO.RespuestaDTO;
import com.example.demo.Respuesta.domain.Respuesta;
import com.example.demo.Respuesta.domain.RespuestaRepository;
import com.example.demo.Usuario.usuarioDTO.UsuarioDTO_thread;
import com.example.demo.Usuario.domain.Usuario;
import com.example.demo.Usuario.domain.UsuarioRepository;
import com.example.demo.Usuario.domain.UsuarioService;
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
@RequestMapping("/api/auth/respuestas")
@CrossOrigin(origins = "http://localhost:3000")
public class RespuestaController {

    @Autowired
    private RespuestaRepository respuestaRepository;

    // Endpoint para obtener todas las respuestas
    @Autowired
    private HiloRepository hiloRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public List<RespuestaDTO> getRespuestas() {
        List<Respuesta> respuestas = respuestaRepository.findAll();
        UsuarioDTO_thread user;
        // Mapea las respuestas a RespuestaDTO
        List<RespuestaDTO> respuestaDTOs = respuestas.stream()
                .map(respuesta -> new RespuestaDTO(
                        respuesta.getId(),
                        respuesta.getContenido(),
                        respuesta.getSubrespuestas().stream()
                                .map(Respuesta::getId)
                                .collect(Collectors.toList())
                ,respuesta.getHilo().getId(),respuesta.getUsuario().getId()))
                .collect(Collectors.toList());
        return respuestaDTOs;
    }
    @GetMapping("/{messageId}")
    public RespuestaDTO getRespuesta(@PathVariable Long messageId) {
        Optional<Respuesta> respuesta = respuestaRepository.findById(messageId);
        if (respuesta.isPresent()){
            RespuestaDTO newRespuesta = new RespuestaDTO();
            newRespuesta.setId(respuesta.get().getId());
            newRespuesta.setContenido(respuesta.get().getContenido());
            for(Respuesta respuesta2: respuesta.get().getSubrespuestas()){
                newRespuesta.getSubRespuestaIds().add(respuesta2.getId());
            }
            newRespuesta.setUsuarioid(respuesta.get().getUsuario().getId());
            newRespuesta.setHiloId(respuesta.get().getHilo().getId());
            UsuarioDTO_thread user = new UsuarioDTO_thread();
            user.setNickname(respuesta.get().getUsuario().getNickname());
            user.setImage_path(respuesta.get().getUsuario().getImage_path());
            newRespuesta.setUsuarioM(user);
            return newRespuesta;
        }
        return null;
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
                                    ,respuesta.getHilo().getId(),respuesta.getUsuario().getId()))
                            .collect(Collectors.toList());
                }
            }
            System.out.println(temp);
        }



        return new ResponseEntity<>(respuestaDto,HttpStatus.OK);
    }

    // Endpoint para crear una nueva respuesta
    /*@PostMapping("/{hiloId}")   //genero una respuesta al hilo con id --> hiloId
    public ResponseEntity<Respuesta> createRespuesta(@RequestBody Respuesta respuesta, @PathVariable Long hiloId) {
        Optional<Hilo> existe = hiloRepository.findById(hiloId);
        if (existe.isPresent()){
            respuesta.setHilo(existe.get());
            Respuesta nuevaRespuesta = respuestaRepository.save(respuesta);
            return new ResponseEntity<>(nuevaRespuesta, HttpStatus.CREATED);
        }else{
        return ResponseEntity.badRequest().build();}
    }*/

    @PostMapping("/{idEmisor}/{hiloId}")
    public ResponseEntity<Respuesta> createRespuesta(@RequestBody RespuestaDTO respuestaDTO, @PathVariable Long hiloId,@PathVariable Long idEmisor) {
        Optional<Usuario> existUsuario = usuarioRepository.findById(idEmisor);
        if (existUsuario.isPresent()){
            Optional<Hilo> existe = hiloRepository.findById(hiloId);
            if (existe.isPresent()) {
                System.out.println("entrando a condicional");
                Hilo hilo = existe.get();
                // You can fetch the Usuario based on userNickname or userId
                Usuario usuario = usuarioService.getUserById(idEmisor);
                System.out.println(respuestaDTO.getUsuarioid());
                Respuesta respuesta = new Respuesta();
                respuesta.setContenido(respuestaDTO.getContenido());
                respuesta.setHilo(hilo);
                respuesta.setUsuario(usuario);  // Set the usuario who created the response
                Respuesta nuevaRespuesta = respuestaRepository.save(respuesta);
                return new ResponseEntity<>(nuevaRespuesta, HttpStatus.CREATED);
            }
        }
        return ResponseEntity.badRequest().build();
    }

    // Método PATCH para añadir una subrespuesta a una respuesta existente
    @PatchMapping("/{userId}/{respuestaId}")
    public ResponseEntity<RespuestaDTO> addSubrespuesta(
            @PathVariable Long respuestaId,
            @PathVariable Long userId,
            @RequestBody Respuesta subrespuesta){
            Respuesta respuesta = respuestaRepository.findById(respuestaId)
                    .orElseThrow(() -> new EntityNotFoundException("No se encontró la respuesta con el ID: " + respuestaId));
            Usuario usuario = usuarioRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la respuesta con el ID: " + respuestaId));
            // Agrega la subrespuesta a la lista de subrespuestas



        subrespuesta.setHilo(respuesta.getHilo());  // Asigna el mismo hilo a la subrespuesta
            subrespuesta.setRespuestaPadre(respuesta);  // Establece la respuesta padre
        subrespuesta.setUsuario(usuario);
            respuesta.getSubrespuestas().add(subrespuesta);

            // Guarda la respuesta actualizada
            respuesta = respuestaRepository.save(respuesta);

            // Mapea la respuesta a un RespuestaDTO para la respuesta
            RespuestaDTO respuestaDTO = new RespuestaDTO(
                    respuesta.getId(),
                    respuesta.getContenido(),
                    respuesta.getSubrespuestas().stream()
                            .map(Respuesta::getId)
                            .collect(Collectors.toList()),respuesta.getHilo().getId(),
                    respuesta.getUsuario().getId()
            );

            return new ResponseEntity<>(respuestaDTO, HttpStatus.OK);
    }
}
