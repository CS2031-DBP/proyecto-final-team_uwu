package com.example.demo.playlist.domain;

import com.example.demo.playlist.application.Playlist;
import com.example.demo.playlist.application.PlaylistRepository;
import com.example.demo.song.application.Song;
import com.example.demo.song.application.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/playlist")
public class PlaylistController {

    @Autowired
    private PlaylistRepository playlistRepository;
    @Autowired
    private SongRepository songRepository;

    @GetMapping
    public ResponseEntity<List<Playlist>> getPlaylists() {
        List<Playlist> playlists = playlistRepository.findAll();
        return new ResponseEntity<>(playlists, HttpStatus.OK);  //Que vas a devolver y su estado
    }

    @GetMapping("/{id}")
    public ResponseEntity<Playlist> getPlaylistById(@PathVariable Long id) {
        Optional<Playlist> optionalPlaylist = playlistRepository.findById(id);

        if (optionalPlaylist.isPresent()) {
            return ResponseEntity.ok(optionalPlaylist.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<String> createPlaylist(@RequestBody Playlist playlistRequest) {
        if (playlistRequest.getSongs() == null){
            return new ResponseEntity<>("VALOR NULO", HttpStatus.NOT_FOUND);
        }
        // Crear una nueva instancia de Playlist
        // Buscar y asociar las canciones a la lista de reproducción por sus IDs
        Set<Song> songs = new HashSet<>();
        for (Song song : playlistRequest.getSongs()) {
            // Buscar la canción por su ID en la base de datos
            Optional<Song> existingSong = songRepository.findById(song.getId());

            if (existingSong.isPresent()) {
                // Si la canción existe, agrégala al conjunto de canciones existentes
                songs.add(existingSong.get());
            } else {
                return new ResponseEntity<>("NO EXISTE uwu", HttpStatus.NOT_FOUND);
            }
        }
        // Establecer las canciones en la lista de reproducción
        playlistRequest.setSongs(songs);
        // Guardar la lista de reproducción en la base de datos
        playlistRepository.save(playlistRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("Playlist created");
    }


    @PutMapping("/{id}")
    public ResponseEntity<String> updatePlaylist(@PathVariable Long id, @RequestBody Playlist updatedPlaylist) {
        Optional<Playlist> optionalPlaylist = playlistRepository.findById(id);
        if (updatedPlaylist.getTitle() == null ){
            return new ResponseEntity<>("VALOR NULO", HttpStatus.NOT_FOUND);
        }
        else if (updatedPlaylist.getSongs() == null){
            return new ResponseEntity<>("VALOR NULO", HttpStatus.NOT_FOUND);
        }
        else if (updatedPlaylist.getCoverImage() == null){
            return new ResponseEntity<>("VALOR NULO", HttpStatus.NOT_FOUND);
        }
        if (optionalPlaylist.isPresent()) {
            Playlist playlist = optionalPlaylist.get();
            playlist.setTitle(updatedPlaylist.getTitle());
            // Buscar y asociar las canciones a la lista de reproducción por sus IDs
            Set<Song> songs = new HashSet<>();
            for (Song song : updatedPlaylist.getSongs()) {
                // Buscar la canción por su ID en la base de datos
                Optional<Song> existingSong = songRepository.findById(song.getId());

                if (existingSong.isPresent()) {
                    // Si la canción existe, agrégala al conjunto de canciones existentes
                    songs.add(existingSong.get());
                } else {
                    return new ResponseEntity<>("NO EXISTE uwu", HttpStatus.NOT_FOUND);
                }
            }

            playlist.setSongs( songs);
            playlist.setCoverImage(updatedPlaylist.getCoverImage());

            playlistRepository.save(playlist);  //
            return ResponseEntity.status(HttpStatus.OK).body("Playlist updated");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Playlist not found");
        }
    }
    @PatchMapping("/{id}")
    public ResponseEntity<String> partialUpdatePlaylist(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Optional<Playlist> optionalPlaylist = playlistRepository.findById(id);
        if (optionalPlaylist.isPresent()) {
            Playlist playlist = optionalPlaylist.get();
            Set<String> keys = updates.keySet(); // Obtiene el conjunto de llaves
            for (String key : keys) {
                Object value = updates.get(key); // Obtén el valor asociado a la llave
                if (key == "title"){
                    playlist.setTitle((String) value);
                }
                else if (key == "coverImage"){
                    playlist.setCoverImage((String) value);
                }
                else if (key == "songs"){
                    Set<Song> songs = new HashSet<>();
                    List<Map<String, Object>> songUpdates = (List<Map<String, Object>>) updates.get("songs");

                    for (Map<String, Object> songUpdate : songUpdates) {
                        if (songUpdate.containsKey("id")) {
                            Integer songIdInt = (Integer) songUpdate.get("id");
                            Long songId = songIdInt.longValue(); // Convierte el Integer a Long
                            Optional<Song> existingSong = songRepository.findById(songId);

                            if (existingSong.isPresent()) {
                                songs.add(existingSong.get());
                            } else {
                                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Canción no encontrada con ID: " + songId);
                            }
                        }
                    }
                    // Actualiza las canciones de la lista de reproducción
                    playlist.setSongs(songs);
                    }
            }

            playlistRepository.save(playlist);
            return ResponseEntity.status(HttpStatus.OK).body("Playlist updated partially");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Playlist not found");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePlaylist(@PathVariable Long id) {
        Optional<Playlist> optionalPlaylist = playlistRepository.findById(id);
        if (optionalPlaylist.isPresent()) {
            playlistRepository.delete(optionalPlaylist.get());
            return ResponseEntity.status(HttpStatus.OK).body("Playlist deleted");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Playlist not found");
        }
    }

    @DeleteMapping("/delete_all")
    public ResponseEntity<String> deleteAllSongs() {
        playlistRepository.deleteAll();
        return ResponseEntity.status(HttpStatus.OK).body("All playlist deleted");
    }
}
