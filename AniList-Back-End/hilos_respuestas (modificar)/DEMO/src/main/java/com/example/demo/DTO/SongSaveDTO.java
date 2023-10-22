package com.example.demo.DTO;

import jakarta.validation.constraints.*;

import java.util.Date;

public class SongSaveDTO {
    @NotNull(message = "El titulo es nulo")
    @Size(min = 3,max = 100,message = "El titulo debe ser como minimo 3 caracteres")
    String title;
    @NotNull(message = "El artista es nulo")
    String artist;

    @NotNull(message = "El album es nulo")
    String album;

    @NotNull(message = "La fecha es nula")
    Date releaseDate;

    @NotNull(message = "El genero es nulo")
    String genre;

    @NotNull(message = "La duracion de cancion es nula")
    @Positive(message = "Debe ser positivo")
    @Max(value = 10, message = "La duración debe ser igual o menor que 10")
    Integer duration;

    @NotNull(message = "El image no puede ser nulo")
    String coverImage;


    @NotNull(message = "El URL no puede ser nulo")
    String spotifyUrl;
    public SongSaveDTO() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getSpotifyUrl() {
        return spotifyUrl;
    }

    public void setSpotifyUrl(String spotifyUrl) {
        this.spotifyUrl = spotifyUrl;
    }

    public SongSaveDTO(String title, String artist, String album, Date releaseDate, String genre, Integer duration, String coverImage, String spotifyUrl) {
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.releaseDate = releaseDate;
        this.genre = genre;
        this.duration = duration;
        this.coverImage = coverImage;
        this.spotifyUrl = spotifyUrl;
    }
}
