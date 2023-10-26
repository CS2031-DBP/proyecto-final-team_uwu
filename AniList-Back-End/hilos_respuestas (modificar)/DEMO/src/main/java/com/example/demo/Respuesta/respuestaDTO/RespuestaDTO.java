package com.example.demo.Respuesta.respuestaDTO;

import com.example.demo.Usuario.usuarioDTO.UsuarioDTO_thread;

import java.util.ArrayList;
import java.util.List;

public class RespuestaDTO {
    private Long id;
    private String contenido;
    private List<Long> subRespuestaIds = new ArrayList<>();

    private Long HiloId;

    private Long usuarioid;

    private UsuarioDTO_thread usuarioM = new UsuarioDTO_thread();

    public RespuestaDTO() {
    }

    public UsuarioDTO_thread getUsuarioM() {
        return usuarioM;
    }

    public void setUsuarioM(UsuarioDTO_thread usuarioM) {
        this.usuarioM = usuarioM;
    }

    public RespuestaDTO(Long id, String contenido, List<Long> subRespuestaIds, Long hiloId, Long usuarioid, UsuarioDTO_thread usuarioM) {
        this.id = id;
        this.contenido = contenido;
        this.subRespuestaIds = subRespuestaIds;
        HiloId = hiloId;
        this.usuarioid = usuarioid;
        this.usuarioM = usuarioM;
    }
    public RespuestaDTO(Long id, String contenido, List<Long> subRespuestaIds, Long hiloId, Long usuarioid) {
        this.id = id;
        this.contenido = contenido;
        this.subRespuestaIds = subRespuestaIds;
        HiloId = hiloId;
        this.usuarioid = usuarioid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public List<Long> getSubRespuestaIds() {
        return subRespuestaIds;
    }

    public void setSubRespuestaIds(List<Long> subRespuestaIds) {
        this.subRespuestaIds = subRespuestaIds;
    }

    public Long getHiloId() {
        return HiloId;
    }

    public void setHiloId(Long hiloId) {
        HiloId = hiloId;
    }

    public Long getUsuarioid() {
        return usuarioid;
    }

    public void setUsuarioid(Long usuarioid) {
        this.usuarioid = usuarioid;
    }
}
