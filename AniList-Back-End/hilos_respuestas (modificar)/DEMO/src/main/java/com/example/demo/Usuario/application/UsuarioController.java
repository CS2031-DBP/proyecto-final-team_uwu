package com.example.demo.Usuario.application;


import com.example.demo.Usuario.domain.Usuario;
import com.example.demo.Hilos.domain.Hilo;
import com.example.demo.Hilos.domain.HiloRepository;
import com.example.demo.Respuesta.domain.Respuesta;
import com.example.demo.Hilos.domain.HiloService;
import com.example.demo.Respuesta.domain.RespuestaService;
import com.example.demo.Usuario.usuarioDTO.UsuarioDTO;
import com.example.demo.Usuario.domain.UsuarioService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "http://localhost:3000") // Reemplaza con la URL de tu frontend
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
            usuarioDTO.setCorreo(usuario.getUsername());
            usuarioDTO.setImage_path(usuario.getImage_path());
            usuarioDTO.setFavoriteAnimeIds(usuario.getFavoriteAnimeIds());
            for(Hilo hilos: usuario.getHilosCreados()){
                usuarioDTO.getHilosCreados().add(hilos.getId());
            }
            for(Respuesta respuesta : usuario.getRespuestasParticipadas()){
                usuarioDTO.getRespuestasParticipadas().add(respuesta.getId());
            }
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
            usuarioDTO.setCorreo(usuario.getUsername());
            usuarioDTO.setImage_path(usuario.getImage_path());
            usuarioDTO.setFavoriteAnimeIds(usuario.getFavoriteAnimeIds());
            for(Hilo hilo: usuario.getHilosCreados()){
                usuarioDTO.getHilosCreados().add(hilo.getId());
            }
            for(Respuesta respuesta : usuario.getRespuestasParticipadas()){
                usuarioDTO.getRespuestasParticipadas().add(respuesta.getId());
            }
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
            Usuario newUser = new Usuario();
            newUser.setNickname(usuarioDTO.getNickname());
            newUser.setEmail(usuarioDTO.getCorreo());
            newUser.setImage_path(usuarioDTO.getImage_path());
            usuarioService.createUser(newUser);
            return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while creating the user");
        }
    }

}










