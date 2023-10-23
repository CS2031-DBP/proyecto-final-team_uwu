package com.example.demo.Hilos;

import com.example.demo.Respuesta.Respuesta;
import com.example.demo.Usuario.Usuario;
import com.example.demo.Usuario.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/hilos")
public class HiloController {

    @Autowired
    private HiloRepository hiloRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Endpoint para obtener todos los hilos
    // Convierte una entidad Hilo en un HiloDTO
    private HiloDTO convertToDTO(Hilo hilo) {
        HiloDTO dto = new HiloDTO();
        dto.setId(hilo.getId());
        dto.setTema(hilo.getTema());
        dto.setContenido(hilo.getContenido());
        dto.setFechaCreacion(hilo.getFechaCreacion());

        if (hilo.getUsuario() != null) {
            dto.setUserId(hilo.getUsuario().getId());
            dto.setUserNickname(hilo.getUsuario().getNickname());
        }

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
    public ResponseEntity<HiloDTO> createHilo(@RequestBody Hilo hilo, @RequestParam Long userId) {
        Usuario usuario = usuarioRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        hilo.setUsuario(usuario);
        hilo.setFechaCreacion(new Date());
        Hilo nuevoHilo = hiloRepository.save(hilo);

        // Create HiloDTO with user information
        HiloDTO hiloDTO = new HiloDTO();
        hiloDTO.setId(nuevoHilo.getId());
        hiloDTO.setTema(nuevoHilo.getTema());
        hiloDTO.setContenido(nuevoHilo.getContenido());
        hiloDTO.setFechaCreacion(nuevoHilo.getFechaCreacion());

        // Set user information
        hiloDTO.setUserId(usuario.getId());
        hiloDTO.setUserNickname(usuario.getNickname());

        return new ResponseEntity<>(hiloDTO, HttpStatus.CREATED);
    }
}
