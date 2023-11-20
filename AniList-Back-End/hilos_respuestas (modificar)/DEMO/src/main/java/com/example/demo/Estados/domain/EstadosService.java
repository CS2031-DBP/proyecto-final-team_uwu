package com.example.demo.Estados.domain;

import com.example.demo.CapaSeguridad.exception.EstadoNotFoundException;
import com.example.demo.CapaSeguridad.exception.UserNotFoundException;
import com.example.demo.Estados.EstadoDTO.EstadosDTO;
import com.example.demo.Usuario.domain.Usuario;
import com.example.demo.Usuario.domain.UsuarioRepository;
import com.example.demo.Usuario.domain.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EstadosService {

    @Autowired
    EstadoRespository estadoRespository;
    @Autowired
    UsuarioService usuarioService;
    @Autowired
    UsuarioRepository usuarioRepository;

    public List<EstadosDTO> getAllEstados() {
        List<Estados> estados = estadoRespository.findAll();
        return estados.stream()
                .map(this::mapToEstadosDTO)
                .collect(Collectors.toList());
    }

    private EstadosDTO mapToEstadosDTO(Estados estado) {
        EstadosDTO estadoDTO = new EstadosDTO();
        estadoDTO.setId(estado.getId());
        estadoDTO.setUserId(estado.getUsuario().getId());
        estadoDTO.setNickname(estado.getUsuario().getNickname());
        estadoDTO.setFechaCreacion(estado.getFechaCreacion());
        if(estado.getUsuario().getImage_path() != null){
            estadoDTO.setUser_profile_picture("http://localhost:8080/usuarios/" + estado.getUsuario().getId() + "/profile_picture");
        }
        estadoDTO.setContenido(estado.getContenido());
        estadoDTO.setCantidadReacciones(estado.getCantidadReacciones());
        estadoDTO.setIsReport(estado.isReport());
        estadoDTO.setImagePath(estado.getImagenPath());
        return estadoDTO;
    }


    public EstadosDTO getEstadoById(Long estadoId) {
        Estados estado = estadoRespository.findById(estadoId).orElseThrow(() -> new EstadoNotFoundException());
        return mapToEstadosDTO(estado);
    }


    public EstadosDTO crearEstado(EstadosDTO estadoDTO, Usuario usuario) {
        Estados estado = new Estados();
        estado.setUsuario(usuario);
        estado.setNickname(usuario.getNickname());
        estado.setFechaCreacion(LocalDateTime.now());
        if (usuario.getImage_path() != null) {
            estado.setUser_profilepicture("http://localhost:8080/usuarios/" + usuario.getId() + "/profile_picture");
        }
        estado.setContenido(estadoDTO.getContenido());
        estado.setCantidadReacciones(estadoDTO.getCantidadReacciones());
        estado.setReport(estadoDTO.getIsReport());
        estado.setImagenPath(estadoDTO.getImagePath());

        estadoRespository.save(estado);

        estadoDTO.setId(estado.getId());
        estadoDTO.setUserId(usuario.getId());
        estadoDTO.setNickname(usuario.getNickname());
        estadoDTO.setFechaCreacion(estado.getFechaCreacion());
        estadoDTO.setUser_profile_picture(estado.getUser_profilepicture());
        estadoDTO.setContenido(estado.getContenido());
        estadoDTO.setCantidadReacciones(estado.getCantidadReacciones());
        estadoDTO.setIsReport(estado.isReport());
        estadoDTO.setImagePath(estado.getImagenPath());
        return estadoDTO;
    }

    public List<EstadosDTO> getEstadosByUserId(Long userId) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(userId);
        if (usuarioOptional.isPresent()) {
            List<Estados> estados = estadoRespository.findAllByUsuarioId(userId);
            return estados.stream()
                    .map(this::mapToEstadosDTO)
                    .collect(Collectors.toList());
        } else {
            throw new UserNotFoundException();
        }
    }

    public void deleteEstadoById(Long estadoId) {
        estadoRespository.deleteById(estadoId);
    }
}
