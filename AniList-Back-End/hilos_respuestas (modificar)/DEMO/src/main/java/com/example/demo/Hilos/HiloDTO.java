package com.example.demo.Hilos;

import java.util.Date;
import java.util.List;
import java.util.Set;


public class HiloDTO {
    private Long id;
    private String tema;
    private String contenido;
    private Date fechaCreacion;
    private List<Long> respuestaIds;
    private Long cantidadReaccciones;
    private Long cantidadReports;
    private List<Long> labelsIds;
    // Constructor vacío
    public HiloDTO() {
    }

    // Constructor con parámetros
    public HiloDTO(Long id, String tema, String contenido, Date fechaCreacion, Long cantidadReaccciones,Long cantidadReports, List<Long> respuestaIds,List<Long> labelsIds) {
        this.id = id;
        this.tema = tema;
        this.contenido = contenido;
        this.fechaCreacion = fechaCreacion;
        this.respuestaIds = respuestaIds;
        this.cantidadReaccciones=cantidadReaccciones;
        this.cantidadReports=cantidadReports;
        this.labelsIds=labelsIds;
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
    public Long getCantidadReaccciones() {
        return this.cantidadReaccciones;
    }

    public void getCantidadReaccciones(Long cantidadReaccciones) {
        this.cantidadReaccciones = cantidadReaccciones;
    }

     public Long getCantidadReports() {
        return this.cantidadReports;
    }

    public void setCantidadReports(Long cantidadReports) {
        this.cantidadReports= cantidadReports;
    }
    public List<Long> getLabelsIds() {
        return this.labelsIds;
    }

    public void setLabelsIds(List<Long> labelsIds) {
        this.labelsIds = labelsIds;
    }
}
