package com.example.demo.Respuesta;

import java.util.List;

public class RespuestaDTO {
    private Long id;
    private String contenido;
    private List<Long> subRespuestaIds;

    public RespuestaDTO() {
    }

    public RespuestaDTO(Long id, String contenido, List<Long> subRespuestaIds) {
        this.id = id;
        this.contenido = contenido;
        this.subRespuestaIds = subRespuestaIds;
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
}
