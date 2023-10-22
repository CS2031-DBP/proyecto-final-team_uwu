package com.example.demo.services;

import com.example.demo.DTO.SongSaveDTO;
import com.example.demo.exceptions.SongNotFound;
import com.example.demo.exceptions.SongValuesNull;
import com.example.demo.song.application.Song;
import com.example.demo.song.application.SongRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongService {

    @Autowired
    SongRepository songRepository;

    // instancia un mapper:
    ModelMapper mapper = new ModelMapper();
    public Song findById(Long id){
        Song song = songRepository.findById(id).orElse(null);
        if (song == null){
            throw new SongValuesNull();       // SI LANZO OTRA COSA NO LO CAPTURARE EN MI CONTROLLER
        }
        return song;
    }
    public void validateSong(Song song) {
        if (song == null || song.getTitle() == null || song.getArtist() == null ||
                song.getAlbum() == null || song.getReleaseDate() == null ||
                song.getGenre() == null || song.getCoverImage() == null ||
                song.getSpotifyUrl() == null || song.getDuration() == null || song.getDuration() <= 0) {
            throw new SongValuesNull();
        }
    }
    public void save(SongSaveDTO songs){
        Song newSong = mapper.map(songs,Song.class);
        songRepository.save(newSong);
    }
}
