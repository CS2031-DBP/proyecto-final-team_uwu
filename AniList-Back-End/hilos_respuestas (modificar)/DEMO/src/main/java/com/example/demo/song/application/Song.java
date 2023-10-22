package com.example.demo.song.application;

import com.example.demo.exceptions.DurationException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

@Entity
@Table(name = "song")
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String title;


    private String artist;

    private String album;

    private Date releaseDate;

    private String genre;

    private Integer duration;

    private String coverImage;

    private String spotifyUrl;

    public Song() {}

    public Song(Long id, String title, String artist, String album, Date releaseDate, String genre, Integer duration, String coverImage, String spotifyUrl) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.releaseDate = releaseDate;
        this.genre = genre;
        this.duration = duration;
        this.coverImage = coverImage;
        this.spotifyUrl = spotifyUrl;
    }


    // Getters y setters
    public Long getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getArtist() {
        return this.artist;
    }

    public String getAlbum() {
        return this.album;
    }

    public Date getReleaseDate() {
        return this.releaseDate;
    }

    public String getGenre() {
        return this.genre;
    }

    public Integer getDuration() {
        return this.duration;
    }

    public String getCoverImage() {
        return this.coverImage;
    }

    public String getSpotifyUrl() {
        return this.spotifyUrl;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public void setSpotifyUrl(String spotifyUrl) {
        this.spotifyUrl = spotifyUrl;
    }

    // toString

    @Override
    public String toString() {
        return "Song{" + "id=" + this.id + ", title='" + this.title + '\'' + ", artist='" + this.artist + '\'' + ", album='" + this.album + '\'' + ", releaseDate=" + this.releaseDate + ", genre='" + this.genre + '\'' + ", duration=" + this.duration + ", coverImage='" + this.coverImage + '\'' + ", spotifyUrl='" + this.spotifyUrl + '\'' + '}';
    }

    // equals y hashCode

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Song))
            return false;
        Song song = (Song) o;
        return this.id.equals(song.id);
    }

}