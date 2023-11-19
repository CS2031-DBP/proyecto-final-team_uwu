package com.example.demo.Estados.EstadoDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class EstadosDTO {
    private Long id;
    private String nickname;
    private LocalDateTime fechaCreacion;
    private String imagePath;
    private String contenido;
    private int cantidadReacciones;
    private boolean isReport;

    public EstadosDTO() {
    }

    public EstadosDTO(Long id, String nickname, LocalDateTime fechaCreacion, String imagePath, String contenido, int cantidadReacciones, boolean isReport) {
        this.id = id;
        this.nickname = nickname;
        this.fechaCreacion = fechaCreacion;
        this.imagePath = imagePath;
        this.contenido = contenido;
        this.cantidadReacciones = cantidadReacciones;
        this.isReport = isReport;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public int getCantidadReacciones() {
        return cantidadReacciones;
    }

    public void setCantidadReacciones(int cantidadReacciones) {
        this.cantidadReacciones = cantidadReacciones;
    }

    public boolean getIsReport() {
        return isReport;
    }

    public void setIsReport(boolean isReport) {
        this.isReport = isReport;
    }
}
