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
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/auth/usuarios")
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
            usuarioDTO.setBackground_picture(usuario.getBackground_picture());
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
            usuarioDTO.setBackground_picture(usuario.getBackground_picture());
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
            newUser.setBackground_picture(usuarioDTO.getBackground_picture());
            usuarioService.createUser(newUser);
            return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while creating the user");
        }
    }

    /*@PostMapping("/{userId}/upload-profile-picture")
    public ResponseEntity<String> uploadProfilePicture(
            @PathVariable("userId") Long userId,
            @RequestParam("file") MultipartFile file) {
        try {
            usuarioService.uploadProfilePicture(userId, file);
            return ResponseEntity.ok("Profile picture uploaded successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading profile picture");
        }
    }*/



    /*@PostMapping("/{usuario_id}/upload_image")
    public ResponseEntity<Usuario> uploadImage(@PathVariable("usuario_id") Long usuarioId, @RequestParam("image") MultipartFile file) {
        try {
            // Obtener el usuario existente por ID
            Usuario usuario = usuarioService.getUserById(usuarioId);

            // Verificar si el usuario existe
            if (usuario == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            // Guardar la imagen en el sistema de archivos
            String originalFilename = file.getOriginalFilename();
            System.out.println(originalFilename);
            Path fileNameAndPath = Paths.get("src/main/recursos/static", originalFilename);
            Files.write(fileNameAndPath, file.getBytes());
            // Actualizar la ruta de la imagen en el usuario
            usuario.setImage_path(originalFilename);

            // Guardar el usuario actualizado
            Usuario usuarioGuardado = usuarioService.createUser(usuario);

            return ResponseEntity.ok(usuarioGuardado);
        } catch (IOException e) {
            // Manejar la excepción de manera adecuada (puedes lanzar una excepción personalizada o devolver un ResponseEntity con un código de error)
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }*/

    @PostMapping("/{usuarioId}/upload-picture")
    public ResponseEntity<Usuario> uploadPicture(
            @PathVariable("usuarioId") Long usuarioId,
            @RequestParam("file") MultipartFile file,
            @RequestParam("pictureType") String pictureType
    ) {
        try {
            Usuario usuarioActualizado = usuarioService.uploadPicture(usuarioId, file, pictureType);
            return ResponseEntity.ok(usuarioActualizado);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @GetMapping(value = "/{usuarioId}/background-picture", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getBackgroundPicture(@PathVariable Long usuarioId) {
        return usuarioService.getImage(usuarioId, "background");
    }
    @GetMapping("/{usuario_id}/profile_picture")
    public ResponseEntity<byte[]> getProfilePicture(@PathVariable("usuario_id") Long usuarioId) {
        try {
            // Obtener el usuario por ID
            Usuario usuario = usuarioService.getUserById(usuarioId);

            if (usuario == null || usuario.getImage_path() == null) {
                // Manejar el caso en que el usuario o la imagen no existan
                return ResponseEntity.notFound().build();
            }

            // Leer la imagen como un array de bytes
            Path imagePath = Paths.get(usuario.getImage_path());
            byte[] imageBytes = Files.readAllBytes(imagePath);

            // Construir la respuesta con el contenido de la imagen y los encabezados adecuados
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG); // Ajusta el tipo de contenido según el formato de tus imágenes

            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            // Manejar la excepción de manera adecuada
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/{usuario_id}/banner_picture")
    public ResponseEntity<byte[]> getBannerPicture(@PathVariable("usuario_id") Long usuarioId) {
        try {
            // Obtener el usuario por ID
            Usuario usuario = usuarioService.getUserById(usuarioId);

            if (usuario == null || usuario.getImage_path() == null) {
                // Manejar el caso en que el usuario o la imagen no existan
                return ResponseEntity.notFound().build();
            }

            // Leer la imagen como un array de bytes
            Path imagePath = Paths.get(usuario.getBackground_picture());
            byte[] imageBytes = Files.readAllBytes(imagePath);

            // Construir la respuesta con el contenido de la imagen y los encabezados adecuados
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG); // Ajusta el tipo de contenido según el formato de tus imágenes

            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            // Manejar la excepción de manera adecuada
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

















