package com.example.demo.Estados.domain;

import com.example.demo.CapaSeguridad.exception.EstadoNotFoundException;
import com.example.demo.CapaSeguridad.exception.UserNotFoundException;
import com.example.demo.Estados.EstadoDTO.EstadosDTO;
import com.example.demo.Usuario.domain.Usuario;
import com.example.demo.Usuario.domain.UsuarioRepository;
import com.example.demo.Usuario.domain.UsuarioService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Arrays;
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

    public EstadosDTO mapToEstadosDTO(Estados estado) {
        EstadosDTO estadoDTO = new EstadosDTO();
        estadoDTO.setId(estado.getId());
        estadoDTO.setUserId(estado.getUsuario().getId());
        estadoDTO.setNickname(estado.getUsuario().getNickname());
        estadoDTO.setFechaCreacion(LocalDateTime.now());
        if(estado.getUsuario().getImage_path() != null){
            estadoDTO.setUser_profile_picture("http://localhost:8080/usuarios/" + estado.getUsuario().getId() + "/profile_picture");
        }
        estadoDTO.setContenidos_url(estado.getContenidos());
        estadoDTO.setCantidadReacciones(estado.getCantidadReacciones());
        estadoDTO.setIsReport(estado.isReport());
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
        estado.setContenidos(estadoDTO.getContenidos_url());
        estado.setCantidadReacciones(estadoDTO.getCantidadReacciones());
        estado.setReport(estadoDTO.getIsReport());


        estadoRespository.save(estado);

        estadoDTO.setId(estado.getId());
        estadoDTO.setUserId(usuario.getId());
        estadoDTO.setNickname(usuario.getNickname());
        estadoDTO.setFechaCreacion(estado.getFechaCreacion());
        estadoDTO.setUser_profile_picture(estado.getUser_profilepicture());
        estadoDTO.setContenidos_url(estado.getContenidos());
        estadoDTO.setCantidadReacciones(estado.getCantidadReacciones());
        estadoDTO.setIsReport(estado.isReport());
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
