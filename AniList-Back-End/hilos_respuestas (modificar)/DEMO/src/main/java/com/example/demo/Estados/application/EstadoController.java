package com.example.demo.Estados.application;

import com.example.demo.CapaSeguridad.exception.EstadoNotFoundException;
import com.example.demo.Estados.EstadoDTO.EstadosDTO;
import com.example.demo.Estados.domain.EstadoRespository;
import com.example.demo.Estados.domain.EstadosService;
import com.example.demo.Usuario.domain.Usuario;
import com.example.demo.Usuario.domain.UsuarioRepository;
import com.example.demo.Usuario.domain.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/estados")
public class EstadoController {

    @Autowired
    EstadoRespository estadoRespository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    EstadosService estadosService;

    @GetMapping // Obtener todos los estados
    public ResponseEntity<List<EstadosDTO>> getAllEstados() {
        List<EstadosDTO> estados = estadosService.getAllEstados();
        return new ResponseEntity<>(estados, HttpStatus.OK);
    }

    @GetMapping("/busqueda/{estado_id}") // Obtener un estado por id
    public ResponseEntity<EstadosDTO> getEstadoById(@PathVariable("estado_id") Long estado_id) {
        EstadosDTO estado = estadosService.getEstadoById(estado_id);
        return new ResponseEntity<>(estado, HttpStatus.OK);
    }

    @GetMapping("/{user_id}") // Obtener todos los estados de un usuario
    public ResponseEntity<List<EstadosDTO>> getEstadosByUserId(@PathVariable("user_id") Long user_id) {
        List<EstadosDTO> estados = estadosService.getEstadosByUserId(user_id);
        return new ResponseEntity<>(estados, HttpStatus.OK);
    }

    @PostMapping("/{user_id}")  // Crear un estado
    public ResponseEntity<EstadosDTO> Post_Estado(@PathVariable Long user_id, @RequestBody EstadosDTO estadoDTO) {
        Optional<Usuario> usuario = usuarioRepository.findById(user_id);
        if(usuario.isPresent()){
            Usuario usuario1 = usuario.get();
            EstadosDTO estado = estadosService.crearEstado(estadoDTO,usuario1);
            return new ResponseEntity<>(estado, HttpStatus.CREATED);
        }else {
            throw new EstadoNotFoundException();
        }
    }

    @DeleteMapping("/{estadoId}") // Eliminar un estado
    public ResponseEntity<String> deleteEstado(@PathVariable("estadoId") Long estadoId) {
        estadosService.deleteEstadoById(estadoId);
        return ResponseEntity.ok("Estado eliminado correctamente");
    }













}
