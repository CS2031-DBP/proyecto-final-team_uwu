package com.example.demo.Usuario;


import com.example.demo.Hilos.Hilo;
import com.example.demo.Hilos.HiloRepository;
import com.example.demo.Respuesta.Respuesta;
import com.example.demo.Hilos.HiloService;
import com.example.demo.Respuesta.RespuestaService;
import jakarta.persistence.EntityNotFoundException;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    UsuarioService usuarioService;

    @Autowired
    HiloService hiloService;

    @Autowired
    RespuestaService respuestaService;

    @Autowired
    HiloRepository hiloRepository;


    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> getUsuarios() {
        List<Usuario> usuarios = usuarioService.getUsuarios();
        List<UsuarioDTO> usuarioDTOs = new ArrayList<>();
        for(Usuario usuario : usuarios) {
            UsuarioDTO usuarioDTO = new UsuarioDTO();
            usuarioDTO.setId(usuario.getId());
            usuarioDTO.setNickname(usuario.getNickname());
            usuarioDTO.setCorreo(usuario.getCorreo());
            usuarioDTO.setImage_path(usuario.getImage_path());
            usuarioDTO.setFavoriteAnimeIds(usuario.getFavoriteAnimeIds());
            for(Hilo hilos: usuario.getHilosCreados()){
                usuarioDTO.getHilosCreados().add(hilos.getId());
            }
            usuarioDTO.setRespuestasParticipadas(usuario.getRespuestasParticipadas());
            usuarioDTOs.add(usuarioDTO);
        }
        return ResponseEntity.ok(usuarioDTOs);
    }
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> getUserById(@PathVariable Long id) {
        Usuario usuario = usuarioService.getUserById(id);
        if (usuario != null) {
            UsuarioDTO usuarioDTO = new UsuarioDTO();
            usuarioDTO.setId(usuario.getId());
            usuarioDTO.setNickname(usuario.getNickname());
            usuarioDTO.setCorreo(usuario.getCorreo());
            usuarioDTO.setImage_path(usuario.getImage_path());
            usuarioDTO.setFavoriteAnimeIds(usuario.getFavoriteAnimeIds());
            for(Hilo hilo: usuario.getHilosCreados()){
                usuarioDTO.getHilosCreados().add(hilo.getId());
            }
            usuarioDTO.setRespuestasParticipadas(usuario.getRespuestasParticipadas());
            return ResponseEntity.ok(usuarioDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/hilos-creados")
    public ResponseEntity<List<Hilo>> getThreadsCreatedByUser(@PathVariable Long id) {
        Usuario usuario = usuarioService.getUserById(id);
        if (usuario != null) {
            List<Hilo> hilos_creados_usuario = hiloService.getThreadsCreatedByUser(usuario);
            return ResponseEntity.ok(hilos_creados_usuario);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/respuestas-participadas")
    public ResponseEntity<List<Respuesta>> getResponsesParticipatedByUser(@PathVariable Long id) {
        Usuario usuario = usuarioService.getUserById(id);
        if (usuario != null) {
            List<Respuesta> respuestas_usuario = respuestaService.getResponsesParticipatedByUser(usuario);
            return ResponseEntity.ok(respuestas_usuario);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/animes-favoritos")
    public ResponseEntity<List<Long>> getFavoriteAnimesByUser(@PathVariable Long id) {
        Usuario usuario = usuarioService.getUserById(id);
        if (usuario != null) {
            List<Long> favoriteAnimeIds = usuarioService.getFavoriteAnimeIdsByUser(usuario);
            return ResponseEntity.ok(favoriteAnimeIds);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) {
        try {
            usuarioService.deleteUserById(id);
            return ResponseEntity.ok("User and associated data deleted successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting the user");
        }
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UsuarioDTO usuarioDTO) {
        try {
            // Convert the UsuarioDTO to a Usuario entity
            Usuario newUser = new Usuario();
            newUser.setNickname(usuarioDTO.getNickname());
            newUser.setCorreo(usuarioDTO.getCorreo());
            newUser.setImage_path(usuarioDTO.getImage_path());
            usuarioService.createUser(newUser);
            // Return a success response
            return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
        } catch (Exception e) {
            // Handle any errors that may occur during user creation
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while creating the user");
        }
    }



}










