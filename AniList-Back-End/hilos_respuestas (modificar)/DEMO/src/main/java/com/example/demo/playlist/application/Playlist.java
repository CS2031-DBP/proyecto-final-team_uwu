package com.example.demo.playlist.application;

import com.example.demo.song.application.Song;
import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "playlist")
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String title;

    @ManyToMany
    @JoinTable(
            name = "playlist_song",
            joinColumns = @JoinColumn(name = "playlist_id"),        // llaves foraneas
            inverseJoinColumns = @JoinColumn(name = "song_id")      // llaves foraneas
    )
    private Set<Song> songs;

    private String coverImage;

    // Constructor, getters, setters, etc.
    public Playlist() {
        // Constructor vacío requerido por JPA
    }

    public Playlist(String title, Set<Song> songs, String coverImage) {
        this.title = title;
        this.songs = songs;
        this.coverImage = coverImage;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Song> getSongs() {
        return songs;
    }

    public void setSongs(Set<Song> songs) {
        this.songs = songs;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }
}
