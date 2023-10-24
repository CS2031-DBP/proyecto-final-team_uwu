package com.example.demo.Usuario;

import com.example.demo.Hilos.Hilo;
import com.example.demo.Respuesta.Respuesta;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname;

    private String contraseña;

    private String correo;

    private String image_path;


    @ElementCollection
    @CollectionTable(name = "usuario_animeFavorito", joinColumns = @JoinColumn(name = "usuario_id"))
    @Column(name = "anime_id")
    private Set<Long> favoriteAnimeIds = new HashSet<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Hilo> hilosCreados = new HashSet<>();

    @ManyToMany(mappedBy = "usuariosParticipantes", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Respuesta> respuestasParticipadas = new HashSet<>();

    public Usuario() {
    }

    public Usuario(Long id, String nickname, String contraseña, String correo, String image_path, Set<Long> favoriteAnimeIds, Set<Hilo> hilosCreados, Set<Respuesta> respuestasParticipadas) {
        this.id = id;
        this.nickname = nickname;
        this.contraseña = contraseña;
        this.correo = correo;
        this.image_path = image_path;
        this.favoriteAnimeIds = favoriteAnimeIds;
        this.hilosCreados = hilosCreados;
        this.respuestasParticipadas = respuestasParticipadas;
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

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public Set<Long> getFavoriteAnimeIds() {
        return favoriteAnimeIds;
    }

    public void setFavoriteAnimeIds(Set<Long> favoriteAnimeIds) {
        this.favoriteAnimeIds = favoriteAnimeIds;
    }

    public Set<Hilo> getHilosCreados() {
        return hilosCreados;
    }

    public void setHilosCreados(Set<Hilo> hilosCreados) {
        this.hilosCreados = hilosCreados;
    }

    public Set<Respuesta> getRespuestasParticipadas() {
        return respuestasParticipadas;
    }

    public void setRespuestasParticipadas(Set<Respuesta> respuestasParticipadas) {
        this.respuestasParticipadas = respuestasParticipadas;
    }
}
