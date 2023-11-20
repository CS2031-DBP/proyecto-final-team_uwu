package com.example.demo.Estados.domain;


import com.example.demo.Usuario.domain.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
@Table(name = "estados")
public class Estados {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(max = 50)
    private String nickname;


    @Column(columnDefinition = "TEXT")
    private String contenido; // texto del estado

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;


    private String user_profilepicture;

    @Column(columnDefinition = "TEXT")
    private String imagenPath;

    private int cantidadReacciones;

    private boolean isReport;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario; // Relación con Usuario

    public Estados() {
    }

    public Estados(Long id, String nickname, String contenido, LocalDateTime fechaCreacion, String user_profilepicture, int cantidadReacciones, boolean isReport, Usuario usuario,
                   String imagenPath) {
        this.id = id;
        this.nickname = nickname;
        this.contenido = contenido;
        this.fechaCreacion = fechaCreacion;
        this.user_profilepicture = user_profilepicture;
        this.cantidadReacciones = cantidadReacciones;
        this.isReport = isReport;
        this.usuario = usuario;
        this.imagenPath = imagenPath;
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

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }



    public int getCantidadReacciones() {
        return cantidadReacciones;
    }

    public void setCantidadReacciones(int cantidadReacciones) {
        this.cantidadReacciones = cantidadReacciones;
    }

    public boolean isReport() {
        return isReport;
    }

    public void setReport(boolean report) {
        isReport = report;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getUser_profilepicture() {
        return user_profilepicture;
    }

    public void setUser_profilepicture(String user_profilepicture) {
        this.user_profilepicture = user_profilepicture;
    }

    public String getImagenPath() {
        return imagenPath;
    }

    public void setImagenPath(String imagenPath) {
        this.imagenPath = imagenPath;
    }
}
