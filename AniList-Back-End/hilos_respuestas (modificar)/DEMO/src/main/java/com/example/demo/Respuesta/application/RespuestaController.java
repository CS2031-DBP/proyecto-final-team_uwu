package com.example.demo.Respuesta.application;

import com.example.demo.AppConfig;
import com.example.demo.CapaSeguridad.exception.ActualizacionException;
import com.example.demo.CapaSeguridad.exception.HiloNotFoundException;
import com.example.demo.CapaSeguridad.exception.RespuestaNotFoundException;
import com.example.demo.Hilos.domain.Hilo;
import com.example.demo.Hilos.domain.HiloRepository;
import com.example.demo.Respuesta.respuestaDTO.RespuestaDTO;
import com.example.demo.Respuesta.domain.Respuesta;
import com.example.demo.Respuesta.domain.RespuestaRepository;
import com.example.demo.Usuario.domain.Usuario;
import com.example.demo.Usuario.domain.UsuarioRepository;
import com.example.demo.Usuario.domain.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
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

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AppConfig appConfig;
    @GetMapping
    public List<RespuestaDTO> getRespuestas() { //me bota todas las respuestas
        List<Respuesta> respuestas = respuestaRepository.findAll();

        // Mapear las respuestas a RespuestaDTO
        List<RespuestaDTO> respuestaDTOs = respuestas.stream().map(respuesta -> {
            RespuestaDTO respuestaDTO = new RespuestaDTO();
            System.out.println("entrando a mapeo");
            respuestaDTO.setId(respuesta.getId());
            respuestaDTO.setContenido(respuesta.getContenido());
            respuestaDTO.setFechaCreacion(respuesta.getFechaCreacion());
            System.out.println(respuesta);
            respuestaDTO.setSubRespuestaIds(respuesta.getSubrespuestas().stream()
                    .map(Respuesta::getId)
                    .collect(Collectors.toList()));
            respuestaDTO.setHiloId(respuesta.getHilo().getId());
            respuestaDTO.setNickname(respuesta.getUsuario().getNickname());
            if(respuesta.getRespuestaPadre()!=null){
                respuestaDTO.setRespuestaPadreId(respuesta.getRespuestaPadre().getId());
            }
            if(respuesta.getUsuario().getImage_path()!=null){
                respuestaDTO.setImagen( appConfig.getBackendBaseUrl() + "/usuarios/" + respuesta.getUsuario().getId() + "/profile_picture");
            }
            return respuestaDTO;
        }).collect(Collectors.toList());

        return respuestaDTOs;
    }

    @GetMapping("/{messageId}") //esto me bota solo una respuesta identificado por su id
    public RespuestaDTO getRespuesta(@PathVariable Long messageId) {
        Optional<Respuesta> respuesta = respuestaRepository.findById(messageId);
        if (respuesta.isPresent()){
            RespuestaDTO newRespuesta = new RespuestaDTO();
            newRespuesta.setId(respuesta.get().getId());
            newRespuesta.setContenido(respuesta.get().getContenido());
            for(Respuesta respuesta2: respuesta.get().getSubrespuestas()){
                newRespuesta.getSubRespuestaIds().add(respuesta2.getId());
            }
            if (respuesta.get().getRespuestaPadre() != null) {
                newRespuesta.setRespuestaPadreId(respuesta.get().getRespuestaPadre().getId());
            }
            newRespuesta.setHiloId(respuesta.get().getHilo().getId());
            newRespuesta.setNickname(respuesta.get().getUsuario().getNickname());
            if (respuesta.get().getUsuario().getImage_path() != null) {
                newRespuesta.setImagen( appConfig.getBackendBaseUrl() + "/usuarios/" + respuesta.get().getUsuario().getId() + "/profile_picture");
            }
            newRespuesta.setReport(respuesta.get().isReport());
            newRespuesta.setCantidadReacciones(respuesta.get().getCantidadReacciones());
            newRespuesta.setFechaCreacion(respuesta.get().getFechaCreacion());
            return newRespuesta;
        }
        return null;
    }

    @GetMapping("/hilo/{hiloId}")   //esto me bota todas las respuestas de un hilo identificado por su id
    public ResponseEntity<List<RespuestaDTO>> getRespuestasByUserAndHilo(
            @PathVariable Long hiloId) {
        System.out.println("entrando a metodo");
        Hilo hilo = hiloRepository.findById(hiloId).orElseThrow(null);
        if (hilo != null){
            List<Respuesta> respuestas = respuestaRepository.findByHilo(hilo);
            List<RespuestaDTO> respuestaDto = respuestas.stream()
                    .map(respuesta -> new RespuestaDTO(
                            respuesta.getContenido(),
                            respuesta.getSubrespuestas().stream()
                                    .map(Respuesta::getId)
                                    .collect(Collectors.toList()),
                            respuesta.getHilo().getId(),
                            respuesta.getUsuario().getNickname(),
                            (respuesta.getUsuario().getImage_path()==null)?null: appConfig.getBackendBaseUrl() + "/usuarios/" + respuesta.getUsuario().getId() + "/profile_picture"
                            ,(respuesta.getRespuestaPadre()==null)?null:respuesta.getRespuestaPadre().getId()
                            ,respuesta.getIsReport(),
                            respuesta.getCantidadReacciones(),
                            respuesta.getId(),
                            respuesta.getFechaCreacion()
                    ))
                    .collect(Collectors.toList());
            return new ResponseEntity<>(respuestaDto,HttpStatus.OK);
        }

    return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
    }
    @GetMapping("/rootMessage/{hiloId}")   //esto me bota todas las respuestas de un hilo identificado por su id
    public ResponseEntity<List<RespuestaDTO>> getRespuestasSinRespuestaPadre(
            @PathVariable Long hiloId)
    {
        List<Respuesta> respuestasSinRespuestaPadre = respuestaRepository.findByRespuestaPadreIsNull();
        List<Respuesta> RespuestasHilo = new ArrayList<>();
        for(Respuesta respuesta: respuestasSinRespuestaPadre){
            if(respuesta.getHilo().getId()==hiloId){
                RespuestasHilo.add(respuesta);
            }
        }
        List<RespuestaDTO> respuestaDTOs = RespuestasHilo.stream()
                .map(respuesta -> {
                    RespuestaDTO respuestaDTO = new RespuestaDTO();
                    respuestaDTO.setId(respuesta.getId());
                    respuestaDTO.setContenido(respuesta.getContenido());
                    respuestaDTO.setFechaCreacion(respuesta.getFechaCreacion());
                    respuestaDTO.setSubRespuestaIds(respuesta.getSubrespuestas().stream()
                            .map(Respuesta::getId)
                            .collect(Collectors.toList()));
                    respuestaDTO.setHiloId(respuesta.getHilo().getId());
                    respuestaDTO.setNickname(respuesta.getUsuario().getNickname());
                    if (respuesta.getUsuario().getImage_path() != null) {
                        respuestaDTO.setImagen(appConfig.getBackendBaseUrl() + "/usuarios/" + respuesta.getUsuario().getId() + "/profile_picture");
                    }
                    return respuestaDTO;
                })
                .collect(Collectors.toList());

        return new ResponseEntity<>(respuestaDTOs, HttpStatus.OK);
    }
    @GetMapping("/respuestas-padre/{respuestaPadreId}")
    public ResponseEntity<List<RespuestaDTO>> getRespuestasHijasDeRespuestaPadre(@PathVariable Long respuestaPadreId) {
        List<Respuesta> respuestasHijas = respuestaRepository.findByRespuestaPadreId(respuestaPadreId);
        List<RespuestaDTO> respuestasHijasDTO = respuestasHijas.stream()
                .map(respuesta -> {
                    RespuestaDTO respuestaDTO = new RespuestaDTO();
                    // Establecer los valores relevantes para el DTO
                    respuestaDTO.setId(respuesta.getId());
                    respuestaDTO.setContenido(respuesta.getContenido());
                    respuestaDTO.setFechaCreacion(respuesta.getFechaCreacion());
                    respuestaDTO.setSubRespuestaIds(respuesta.getSubrespuestas().stream()
                            .map(Respuesta::getId)
                            .collect(Collectors.toList()));
                    respuestaDTO.setHiloId(respuesta.getHilo().getId());
                    respuestaDTO.setNickname(respuesta.getUsuario().getNickname());
                    if (respuesta.getUsuario().getImage_path() != null) {
                        respuestaDTO.setImagen(appConfig.getBackendBaseUrl() + "/usuarios/" + respuesta.getUsuario().getId() + "/profile_picture");
                    }
                    return respuestaDTO;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(respuestasHijasDTO);
    }

    @PostMapping("/{idEmisor}/{hiloId}")   //genero una respuesta al hilo con id --> hiloId
    public ResponseEntity<RespuestaDTO> createRespuesta(@RequestBody RespuestaDTO respuestaDTO, @PathVariable Long hiloId,@PathVariable Long idEmisor) {
        Optional<Usuario> existUsuario = usuarioRepository.findById(idEmisor);
        if (existUsuario.isPresent()){
            Optional<Hilo> existe = hiloRepository.findById(hiloId);
            if (existe.isPresent()) {
                System.out.println("entrando a condicional");
                Hilo hilo = existe.get();
                Respuesta respuesta = new Respuesta();
                respuesta.setFechaCreacion(new Date());
                respuesta.setContenido(respuestaDTO.getContenido());
                respuesta.setHilo(hilo);
                respuesta.setUsuario(existUsuario.get());
                respuesta.setSubrespuestas(new ArrayList<>());
                if(existUsuario.get().getImage_path()!=null){
                    respuesta.setImagen(appConfig.getBackendBaseUrl() + "/usuarios/" + existUsuario.get().getId() + "/profile_picture");
                }
                if(respuestaDTO.getRespuestaPadreId() != null){
                    Respuesta respuestaPadre = respuestaRepository.findById(respuestaDTO.getRespuestaPadreId()).orElseThrow(null);
                    respuestaPadre.getSubrespuestas().add(respuesta);
                    respuesta.setRespuestaPadre(respuestaPadre);
                }
                RespuestaDTO newRespuesta = new RespuestaDTO();
                newRespuesta.setContenido(respuesta.getContenido());
                newRespuesta.setHiloId(respuesta.getHilo().getId());
                newRespuesta.setFechaCreacion(respuesta.getFechaCreacion());
                if(respuesta.getRespuestaPadre()!=null){

                    newRespuesta.setRespuestaPadreId(respuesta.getRespuestaPadre().getId());
                }

                newRespuesta.setNickname(respuesta.getUsuario().getNickname());
                newRespuesta.setImagen(respuesta.getImagen());
                newRespuesta.setSubRespuestaIds(respuesta.getSubrespuestas().stream()
                        .map(Respuesta::getId)
                        .collect(Collectors.toList()));
                respuestaRepository.save(respuesta);
                return new ResponseEntity<>(newRespuesta, HttpStatus.CREATED);
            }
        }
        throw new HiloNotFoundException();
    }
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateRespuestaContenido(@PathVariable Long id, @RequestBody Map<String, String> body) {
        Optional<Respuesta> optionalRespuesta = respuestaRepository.findById(id);
        if (optionalRespuesta.isPresent()) {
            Respuesta respuesta = optionalRespuesta.get();

            // Verificar si el body contiene atributos adicionales al contenido
            if (body.keySet().size() != 1 || !body.containsKey("contenido")) {
                throw new ActualizacionException();
            }

            // Actualizar solo el contenido
            String nuevoContenido = body.get("contenido");
            respuesta.setContenido(nuevoContenido);
            respuesta.setFechaCreacion(new Date());


            respuestaRepository.save(respuesta);
            return ResponseEntity.ok("Contenido de la respuesta actualizado correctamente");
        } else {
            throw new RespuestaNotFoundException();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRespuesta(@PathVariable Long id) {
        Optional<Respuesta> respuestaOptional = respuestaRepository.findById(id);
        if (respuestaOptional.isPresent()) {
            Respuesta respuesta = respuestaOptional.get();
            respuestaRepository.delete(respuesta);
            return ResponseEntity.ok("Respuesta eliminada correctamente");
        } else {
            throw new RespuestaNotFoundException();
        }
    }
}


