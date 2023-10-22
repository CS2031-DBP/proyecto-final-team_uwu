package com.example.demo.DTO;
// QUIERO SOLO OBTENER EL NOMBRE DEL ATRIBUTO SONG
public class SongSearchmynameDTO {
    String title;
    public SongSearchmynameDTO() {
    }
    public SongSearchmynameDTO(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
