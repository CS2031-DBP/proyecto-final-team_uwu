package com.example.demo.Hilos.application;

import com.example.demo.Hilos.hilosDTO.HiloDTO;
import com.example.demo.Hilos.domain.Hilo;
import com.example.demo.Hilos.domain.HiloRepository;
import com.example.demo.Respuesta.domain.Respuesta;
import com.example.demo.Usuario.domain.Usuario;
import com.example.demo.Usuario.domain.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth/hilos")
@CrossOrigin(origins = "http://localhost:3000")
public class HiloController {

    @Autowired
    private HiloRepository hiloRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

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
    @GetMapping("/{hiloId}")
    public ResponseEntity<HiloDTO> getHilobyId(@PathVariable Long hiloId){
        Optional<Hilo> Hexiste = hiloRepository.findById(hiloId);
        if (Hexiste.isPresent()){
            HiloDTO newHilo = new HiloDTO();
            newHilo.setId(Hexiste.get().getId());
            newHilo.setTema(Hexiste.get().getTema());
            newHilo.setContenido(Hexiste.get().getContenido());
            newHilo.setUserId(Hexiste.get().getUsuario().getId());
            newHilo.setUserNickname(Hexiste.get().getUsuario().getNickname());
            newHilo.setFechaCreacion(Hexiste.get().getFechaCreacion());
            for(Respuesta respuesta: Hexiste.get().getRespuestas()){
                newHilo.getRespuestaIds().add(respuesta.getId());
            }
            return ResponseEntity.ok(newHilo);
        }

        return new ResponseEntity<>(new HiloDTO(),HttpStatus.BAD_REQUEST);
    }


    // Endpoint para crear un nuevo hilo
    @PostMapping("/{idUser}")
    public ResponseEntity<HiloDTO> createHilo(@RequestBody Hilo hilo, @PathVariable Long idUser) {
        Usuario usuario = usuarioRepository.findById(idUser)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        hilo.setUsuario(usuario);
        hilo.setFechaCreacion(new Date());
        Hilo nuevoHilo = hiloRepository.save(hilo);

        HiloDTO hiloDTO = new HiloDTO();
        hiloDTO.setId(nuevoHilo.getId());
        hiloDTO.setTema(nuevoHilo.getTema());
        hiloDTO.setContenido(nuevoHilo.getContenido());
        hiloDTO.setFechaCreacion(nuevoHilo.getFechaCreacion());

        hiloDTO.setUserId(usuario.getId());
        hiloDTO.setUserNickname(usuario.getNickname());

        return new ResponseEntity<>(hiloDTO, HttpStatus.CREATED);
    }
}