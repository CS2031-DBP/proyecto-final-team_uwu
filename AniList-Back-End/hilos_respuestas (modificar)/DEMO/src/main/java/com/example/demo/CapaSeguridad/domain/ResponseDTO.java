package com.example.demo.CapaSeguridad.domain;

public class ResponseDTO {
    private Long id;
    private String token;

    public ResponseDTO() {
    }

    public Long getId() {
        return id;
    }

    public ResponseDTO(Long id, String token) {
        this.id = id;
        this.token = token;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
