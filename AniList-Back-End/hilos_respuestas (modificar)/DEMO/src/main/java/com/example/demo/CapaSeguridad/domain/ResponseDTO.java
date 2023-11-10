package com.example.demo.CapaSeguridad.domain;

public class ResponseDTO {
    private Long id;
    private String token;
    private String nickName;

    public ResponseDTO(Long id, String token, String nickName) {
        this.id = id;
        this.token = token;
        this.nickName = nickName;
    }



    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

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
