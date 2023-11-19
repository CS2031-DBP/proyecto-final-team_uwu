package com.example.demo.Estados.domain;

import com.example.demo.Estados.EstadoDTO.EstadosDTO;
import com.example.demo.Usuario.domain.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstadosService {

    @Autowired
    EstadoRespository estadoRespository;
    @Autowired
    UsuarioService usuarioService;

    public List<EstadosDTO> getAllEstados() {
        List<Estados> estados = estadoRespository.findAll();
        return estados.stream()
                .map(this::mapToEstadosDTO)
                .collect(Collectors.toList());
    }

    private EstadosDTO mapToEstadosDTO(Estados estado) {
        EstadosDTO estadoDTO = new EstadosDTO();
        // Mapear los atributos de estado a los del DTO (id, nickname, fechaCreacion, image_path, contenido, cantidadReacciones, isReport)
        estadoDTO.setId(estado.getId());
        estadoDTO.setNickname(estado.getUsuario().getNickname());
        estadoDTO.setFechaCreacion(estado.getFechaCreacion());
        estadoDTO.setImagePath(estado.getImagenPath());
        estadoDTO.setContenido(estado.getContenido());
        estadoDTO.setCantidadReacciones(estado.getCantidadReacciones());
        estadoDTO.setIsReport(estado.isReport());
        // Mapear otros atributos según sea necesario

        return estadoDTO;
    }



}
