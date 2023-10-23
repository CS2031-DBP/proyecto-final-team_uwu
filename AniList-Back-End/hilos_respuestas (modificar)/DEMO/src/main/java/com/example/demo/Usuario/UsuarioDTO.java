package com.example.demo.Usuario;


import com.example.demo.Hilos.Hilo;
import com.example.demo.Respuesta.Respuesta;

import java.util.Set;

public class UsuarioDTO {
    private Long id;
    private String nickname;
    private String correo;

    private  String image_path;

    private Set<Long> favoriteAnimeIds;

    private Set<Hilo> hilosCreados;

    private Set<Respuesta> respuestasParticipadas;

    public UsuarioDTO() {
    }

    public UsuarioDTO(Long id, String nickname, String correo, String image_path, Set<Long> favoriteAnimeIds, Set<Hilo> hilosCreados, Set<Respuesta> respuestasParticipadas) {
        this.id = id;
        this.nickname = nickname;
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