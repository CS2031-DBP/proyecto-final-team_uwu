package com.example.demo.playlist.application;

import com.example.demo.song.application.Song;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface PlaylistRepository extends ListCrudRepository<Playlist, Long> {
    List<Playlist> findBySongsContaining(Song song);

}
