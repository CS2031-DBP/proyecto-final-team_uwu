package com.example.demo.Respuesta.application;

import com.example.demo.CapaSeguridad.exception.HiloNotFoundException;
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
@RequestMapping("/respuestas")
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
    public List<RespuestaDTO> getRespuestas() { //me bota todas las respuestas
        List<Respuesta> respuestas = respuestaRepository.findAll();

        // Mapear las respuestas a RespuestaDTO
        List<RespuestaDTO> respuestaDTOs = respuestas.stream().map(respuesta -> {
            RespuestaDTO respuestaDTO = new RespuestaDTO();
            respuestaDTO.setId(respuesta.getId());
            respuestaDTO.setContenido(respuesta.getContenido());
            respuestaDTO.setSubRespuestaIds(respuesta.getSubrespuestas().stream()
                    .map(Respuesta::getId)
                    .collect(Collectors.toList()));
            respuestaDTO.setHiloId(respuesta.getHilo().getId());
            respuestaDTO.setUsuarioid(respuesta.getUsuario().getId());

            UsuarioDTO_thread userDTO = new UsuarioDTO_thread();
            userDTO.setImage_path(respuesta.getUsuario().getImage_path());

            // Obtener al usuario dueño del hilo
            Usuario usuarioDueñoHilo = respuesta.getHilo().getUsuario(); // Suponiendo que existe un método "getUsuario" en la entidad Hilo
            UsuarioDTO_thread ownerDTO = new UsuarioDTO_thread();
            ownerDTO.setImage_path(usuarioDueñoHilo.getImage_path());

            respuestaDTO.setUsuarioM(ownerDTO);

            return respuestaDTO;
        }).collect(Collectors.toList());

        // Construir la URL completa de la imagen del usuario
        respuestaDTOs.forEach(respuestaDTO -> {
            String imagePath = respuestaDTO.getUsuarioM().getImage_path();
            if (imagePath != null) {
                respuestaDTO.getUsuarioM().setImage_path("http://localhost:8080/usuarios/" + respuestaDTO.getUsuarioM().getNickname() + "/profile_picture");
            }
        });

        return respuestaDTOs;
    }



    /*@GetMapping("/{messageId}") //esto me bota solo una respuesta identificado por su id
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
    }*/


    @GetMapping("/{hiloId}")   //esto me bota todas las respuestas de un hilo identificado por su id
    public ResponseEntity<List<RespuestaDTO>> getRespuestasByUserAndHilo(
            @PathVariable Long hiloId) {
        System.out.println("entrando a metodo");
        List<RespuestaDTO> respuestaDto = new ArrayList<>();
        Optional<Hilo> hiloOptional = hiloRepository.findById(hiloId);
        if(hiloOptional.isPresent()){
            System.out.println("entrando a condicional");
            List<Respuesta> respuestas = respuestaRepository.findByHilo(hiloOptional.get());
            respuestaDto = respuestas.stream()
                    .map(respuesta -> new RespuestaDTO(
                            respuesta.getId(),
                            respuesta.getContenido(),
                            respuesta.getSubrespuestas().stream()
                                    .map(Respuesta::getId)
                                    .collect(Collectors.toList())
                            ,respuesta.getHilo().getId(),respuesta.getUsuario().getId(),
                            (respuesta.getUsuario().getImage_path()==null)?null:"http://localhost:8080/usuarios/" + respuesta.getUsuario().getId() + "/profile_picture"))
                    .collect(Collectors.toList());
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
                System.out.println(usuario.getId());
                System.out.println(respuestaDTO.getUsuarioid());
                Respuesta respuesta = new Respuesta();
                respuesta.setContenido(respuestaDTO.getContenido());
                respuesta.setHilo(hilo);
                respuesta.setUsuario(usuario);  // Set the usuario who created the response
                Respuesta nuevaRespuesta = respuestaRepository.save(respuesta);
                return new ResponseEntity<>(nuevaRespuesta, HttpStatus.CREATED);
            }
        }
        throw new HiloNotFoundException();
    }

    // Método PATCH para añadir una subrespuesta a una respuesta existente
    // En progreso
    /*@PatchMapping("/{userId}/{respuestaId}")
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
    }*/


    // Método PATCH(CONTENIDO,TEMA,FECHA) DELETE









}
