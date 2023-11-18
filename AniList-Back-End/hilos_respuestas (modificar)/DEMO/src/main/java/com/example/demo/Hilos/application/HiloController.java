package com.example.demo.Hilos.application;

import com.example.demo.Hilos.hilosDTO.HiloDTO;
import com.example.demo.Hilos.domain.Hilo;
import com.example.demo.Hilos.domain.HiloRepository;
import com.example.demo.Labels.domain.Label;
import com.example.demo.Labels.domain.LabelRepository;
import com.example.demo.Respuesta.domain.Respuesta;
import com.example.demo.Usuario.domain.Usuario;
import com.example.demo.Usuario.domain.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/hilos")
@CrossOrigin(origins = "http://localhost:3000")
public class HiloController {

    @Autowired
    private HiloRepository hiloRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LabelRepository labelRepository;

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
            if(hilo.getUsuario().getImage_path() != null){
                dto.setImage_path("http://localhost:8080/usuarios/" + hilo.getUsuario().getId() + "/profile_picture");
            }
        }
        // Obtener los valores de las etiquetas asociadas
        List<String> etiquetas = hilo.getLabels()
                .stream()
                .map(label -> label.getValor())
                .collect(Collectors.toList());
        dto.setLabelValores(etiquetas);

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


    /*@GetMapping("/{hiloId}")
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
    }*/


    @GetMapping("/{hiloId}")
    public ResponseEntity<?> getHilobyId(@PathVariable Long hiloId) {
        Optional<Hilo> optionalHilo = hiloRepository.findById(hiloId);

        if (optionalHilo.isPresent()) {
            Hilo hilo = optionalHilo.get();
            HiloDTO newHilo = new HiloDTO();
            newHilo.setId(hilo.getId());
            newHilo.setTema(hilo.getTema());
            newHilo.setContenido(hilo.getContenido());
            if (hilo.getUsuario() != null) {
                newHilo.setUserId(hilo.getUsuario().getId());
                newHilo.setUserNickname(hilo.getUsuario().getNickname());
                newHilo.setImage_path(hilo.getUsuario().getImage_path());

            }
            newHilo.setFechaCreacion(hilo.getFechaCreacion());
            for (Respuesta respuesta : hilo.getRespuestas()) {
                newHilo.getRespuestaIds().add(respuesta.getId());
            }
            return ResponseEntity.ok(newHilo);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Hilo con ID " + hiloId + " no encontrado");
        }
    }

    @PostMapping("/{idUser}")
    public ResponseEntity<HiloDTO> createHilo(@RequestBody HiloDTO hiloDTO, @PathVariable Long idUser) {
        Usuario usuario = usuarioRepository.findById(idUser)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Hilo hilo = new Hilo();
        hilo.setUsuario(usuario);
        hilo.setTema(hiloDTO.getTema());
        hilo.setContenido(hiloDTO.getContenido());
        hilo.setFechaCreacion(new Date());

        List<String> etiquetasAsociadas = hiloDTO.getLabelValores();

        List<Label> etiquetas = new ArrayList<>();

        for (String valor : etiquetasAsociadas) {
            Label label = labelRepository.findByValor(valor);

            if (label == null) {
                label = new Label(valor);
                label = labelRepository.save(label);
            }

            etiquetas.add(label);
        }

        hilo.setLabels(etiquetas);
        Hilo nuevoHilo = hiloRepository.save(hilo);

        hiloDTO.setId(nuevoHilo.getId());
        hiloDTO.setUserId(usuario.getId());
        hiloDTO.setUserNickname(usuario.getNickname());
        if(usuario.getImage_path() != null){
            hiloDTO.setImage_path("http://localhost:8080/usuarios/" + usuario.getId() + "/profile_picture");
        }
        hiloDTO.setFechaCreacion(nuevoHilo.getFechaCreacion());

        return new ResponseEntity<>(hiloDTO, HttpStatus.CREATED);
    }
    @GetMapping("/por_etiqueta")
    public ResponseEntity<List<HiloDTO>> obtenerHilosPorEtiqueta(@RequestParam("nombreEtiqueta") String nombreEtiqueta) {
        List<Hilo> hilos = hiloRepository.findByNombreEtiqueta(nombreEtiqueta);

        if (hilos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<HiloDTO> hiloDTOs = hilos.stream()
                .map(this::convertToDTO) // Usando el método convertToDTO que transforma Hilo a HiloDTO
                .collect(Collectors.toList());

        return ResponseEntity.ok(hiloDTOs);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarHilo(@PathVariable("id") Long id) {
        Optional<Hilo> optionalHilo = hiloRepository.findById(id);

        if (optionalHilo.isPresent()) {
            Hilo hilo = optionalHilo.get();
            hiloRepository.delete(hilo);
            return ResponseEntity.ok("Hilo eliminado exitosamente");
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    //PATCH PARA HILOS(ACTUALIZAR TEMA,CONTENIDO,FECHA,LABELS)

}
