package com.example.demo.Hilos;

import java.util.Date;
import java.util.List;

public class HiloDTO {
    private Long id;
    private String tema;
    private String contenido;
    private Date fechaCreacion;
    private List<Long> respuestaIds;

    // Constructor vacío
    public HiloDTO() {
    }

    // Constructor con parámetros
    public HiloDTO(Long id, String tema, String contenido, Date fechaCreacion, List<Long> respuestaIds) {
        this.id = id;
        this.tema = tema;
        this.contenido = contenido;
        this.fechaCreacion = fechaCreacion;
        this.respuestaIds = respuestaIds;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public List<Long> getRespuestaIds() {
        return respuestaIds;
    }

    public void setRespuestaIds(List<Long> respuestaIds) {
        this.respuestaIds = respuestaIds;
    }
}
