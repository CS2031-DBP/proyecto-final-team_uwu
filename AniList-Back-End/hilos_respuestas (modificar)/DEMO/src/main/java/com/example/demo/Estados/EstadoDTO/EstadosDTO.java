package com.example.demo.Estados.EstadoDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class EstadosDTO {
    private Long id;
    private Long UserId;
    private String nickname;
    private LocalDateTime fechaCreacion;
    private String user_profile_picture;
    private String imagePath;
    private String contenido; //texto del estado
    private int cantidadReacciones;
    private boolean isReport;

    public EstadosDTO() {
    }

    public EstadosDTO(Long id, String nickname, LocalDateTime fechaCreacion, String user_profile_picture, String contenido, int cantidadReacciones, boolean isReport,
                      Long UserId,String imagePath) {
        this.id = id;
        this.nickname = nickname;
        this.fechaCreacion = fechaCreacion;
        this.user_profile_picture = user_profile_picture;
        this.contenido = contenido;
        this.cantidadReacciones = cantidadReacciones;
        this.isReport = isReport;
        this.UserId = UserId;
        this.imagePath = imagePath;
    }

    public Long getUserId() {
        return UserId;
    }

    public void setUserId(Long userId) {
        UserId = userId;
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


    public String getUser_profile_picture() {
        return user_profile_picture;
    }

    public void setUser_profile_picture(String user_profile_picture) {
        this.user_profile_picture = user_profile_picture;
    }

    public boolean isReport() {
        return isReport;
    }

    public void setReport(boolean report) {
        isReport = report;
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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
