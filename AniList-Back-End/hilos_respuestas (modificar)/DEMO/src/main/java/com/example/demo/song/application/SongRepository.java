package com.example.demo.song.application;

import com.example.demo.playlist.application.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SongRepository extends JpaRepository<Song, Long> {
    List<Song> findByTitle(String title);
}