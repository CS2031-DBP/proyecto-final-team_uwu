/**
 *
 Claro, el archivo SongController.java que has proporcionado es una clase controladora de Spring Boot
 que maneja las solicitudes HTTP relacionadas con la entidad Song. Aquí te explico qué hace cada parte del código
 */
package com.example.demo.song.domain;

import com.example.demo.DTO.SongSaveDTO;
import com.example.demo.DTO.SongSearchmynameDTO;
import com.example.demo.exceptions.DurationException;
import com.example.demo.exceptions.ErrorMessage;
import com.example.demo.exceptions.SongNotFound;
import com.example.demo.exceptions.SongValuesNull;
import com.example.demo.playlist.application.Playlist;
import com.example.demo.playlist.application.PlaylistRepository;
import com.example.demo.services.SongService;
import com.example.demo.song.application.Song;
import com.example.demo.song.application.SongRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.nio.channels.ScatteringByteChannel;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @RestController: Esta anotación indica que la clase SongController es un controlador de Spring que manejará solicitudes HTTP y generará respuestas HTTP.
 * @RequestMapping("/song"): Esta anotación establece que todas las rutas en este controlador tendrán un prefijo "/song".
 * Esto significa que las rutas definidas en los métodos de esta clase estarán bajo la ruta base "/song".
 */
@RestController
@RequestMapping("/song")
public class SongController {

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    protected SongService service;

    @GetMapping
    public ResponseEntity<List<Song>> songs() {
        List<Song> songs = songRepository.findAll();
        return new ResponseEntity<>(songs, HttpStatus.OK);
    }


/*    @GetMapping("/{id}")
    public ResponseEntity<Song> getPlaylistById(@PathVariable Long id) {

        Optional<Song> optionalPlaylist = songRepository.findById(id);

        if (optionalPlaylist.isPresent()) {
            return ResponseEntity.ok(optionalPlaylist.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Con ResponseStatusExcpetion, para el meotod get usare try catch:
    @GetMapping("/{id}")
    public Song getPlaylistById(@PathVariable Long id) {

        try{
            return service.findById(id);    // A esa song la vas a buscar y si la encuentras returnas, pero sino la encuentras
        } catch (SongNotFound songNotFound){    // si no la encuentras arrojas la excepsion que retorna el throw
            // si el throw captura otra excepcion NUNCA ENTRARIA AQUI porque aqui solamente hace a esta excepecion en especifico
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Song not found",songNotFound);
        }
    }
    */
    // Con ExceptionHandler OMITIMOS el try catch, reduce codigo:
    @GetMapping("/{id}")
    public Song getPlaylistById(@PathVariable Long id) {

        return service.findById(id);
    }
    @PostMapping()
    public ResponseEntity<String> createPlaylist(@RequestBody List<Song> songs) {
        // Crear y guardar las canciones en la base de datos
        for (Song song: songs) {
            service.validateSong(song);
        }
        songRepository.saveAll(songs);

        return ResponseEntity.status(HttpStatus.CREATED).body("Playlist created");
    }
    @PostMapping("/crear")
    public ResponseEntity<String> createPlaylistbyDTO(@Valid @RequestBody SongSaveDTO song) {
        // Crear y guardar las canciones en la base de datos
        service.save(song);
        return new ResponseEntity<>("Song created",HttpStatus.CREATED);
    }
    @GetMapping("/search")
    public ResponseEntity<List<Song>> listByName(@RequestBody SongSearchmynameDTO song){
        List<Song> SongFilterByName = songRepository.findByTitle(song.getTitle());
        return new ResponseEntity<>(SongFilterByName,HttpStatus.OK);
    }

    // TODO: Añadir métodos PUT, PATCH, DELETE
    @PutMapping("/{id}")
    public ResponseEntity<String> updateSong(@PathVariable Long id, @RequestBody Song updatedSong) {
        Optional<Song> optionalSong = songRepository.findById(id);

        if (optionalSong.isPresent()) {
            Song song = optionalSong.get();
            song.setTitle(updatedSong.getTitle());
            song.setArtist(updatedSong.getArtist());
            song.setAlbum(updatedSong.getAlbum());
            song.setReleaseDate(updatedSong.getReleaseDate());
            song.setGenre(updatedSong.getGenre());
            song.setDuration(updatedSong.getDuration());
            song.setCoverImage(updatedSong.getCoverImage());
            song.setSpotifyUrl(updatedSong.getSpotifyUrl());

            songRepository.save(song);
            return ResponseEntity.status(HttpStatus.OK).body("Song updated");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Song not found");
        }
    }
    @PatchMapping("/{id}")
    public ResponseEntity<String> partialUpdateSong(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Optional<Song> optionalSong = songRepository.findById(id);

        if (optionalSong.isPresent()) {
            Song song = optionalSong.get();

            updates.forEach((key, value) -> {
                switch (key) {
                    case "title":
                        song.setTitle((String) value);
                        break;
                    case "artist":
                        song.setArtist((String) value);
                        break;
                    case "album":
                        song.setAlbum((String) value);
                        break;
                    case "releaseDate":
                        song.setReleaseDate((Date) value);
                        break;
                    case "genre":
                        song.setGenre((String) value);
                        break;
                    case "duration":
                        song.setDuration((Integer) value);
                        break;
                    case "coverImage":
                        song.setCoverImage((String) value);
                        break;
                    case "spotifyUrl":
                        song.setSpotifyUrl((String) value);
                        break;
                }
            });

            songRepository.save(song);
            return ResponseEntity.status(HttpStatus.OK).body("Song updated partially");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Song not found");
        }
    }
    @DeleteMapping("/{id}")
    public  ResponseEntity<String>deleteSong_id(@PathVariable Long id){
        Optional<Song>optionalSong=songRepository.findById(id);
        if(optionalSong.isPresent()){
            Song musica= optionalSong.get();
            List<Playlist>playlistsSong=playlistRepository.findBySongsContaining(musica);
            for( Playlist playlist:playlistsSong){
                playlist.getSongs().remove(musica);
                playlistRepository.save(playlist);
            }
            songRepository.delete(musica);
            return ResponseEntity.status(200).body("DELETE SONG");
        }
        else{
            return  ResponseEntity.status(4040).body("Error");
        }

    }

    @DeleteMapping("/delete_all")
    public ResponseEntity<String> deleteAllSongs() {
        songRepository.deleteAll();
        return ResponseEntity.status(HttpStatus.OK).body("All songs deleted");
    }
    @ExceptionHandler(SongNotFound.class)
    public ResponseEntity<ErrorMessage> conflict1(){
        ErrorMessage error = new ErrorMessage(404,"Persona No encontrada");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
    @ExceptionHandler(DurationException.class)
    public ResponseEntity<ErrorMessage> conflict2(){
        ErrorMessage error = new ErrorMessage(400,"Duracion no valida");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleValidation(MethodArgumentNotValidException exception){
        ErrorMessage error = new ErrorMessage(400,exception.getBindingResult().getFieldError().getDefaultMessage());
        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    }

}
/**
 * En el método GET del controlador, se utiliza return new ResponseEntity<>(songs, HttpStatus.OK);
 * para construir y devolver una respuesta HTTP al cliente (por ejemplo, un navegador web o una aplicación móvil) que realizó la solicitud GET al endpoint del controlador. Aquí está lo que significa cada parte:

 * new ResponseEntity<>(songs, HttpStatus.OK):

 * ResponseEntity: Es una clase proporcionada por Spring Framework que representa una respuesta HTTP completa,
 * incluyendo el cuerpo de la respuesta, los encabezados y el código de estado.

 * songs: Es la lista de canciones que deseas devolver como parte de la respuesta.

 * HttpStatus.OK: Es un código de estado HTTP que indica que la solicitud se ha procesado con éxito. En este caso, se utiliza el código 200 OK.

 * return new ResponseEntity<>(songs, HttpStatus.OK);:
 * Esta línea de código crea una nueva instancia de ResponseEntity con la lista de canciones y el código de estado 200 OK.
 * Luego, devuelve esta instancia como resultado del método GET. Esto significa que la respuesta generada por esta línea de
 * código se enviará al cliente que realizó la solicitud.
 *


 /////
 ResponseEntity.status(201).body("Created"); para construir y devolver una respuesta HTTP al cliente que realizó la solicitud POST al endpoint del controlador.
 Aquí está lo que significa cada parte:

 ResponseEntity.status(201):

 ResponseEntity: Como mencioné anteriormente, es una clase proporcionada por Spring Framework que representa una respuesta HTTP completa.
 .status(201): Esto establece el código de estado HTTP de la respuesta en 201 Created. El código 201 se utiliza
 para indicar que la solicitud POST ha tenido éxito y ha resultado en la creación de un nuevo recurso en el servidor.

 .body("Created"): Esto establece el cuerpo de la respuesta como un simple texto "Created".
 En una respuesta HTTP, el cuerpo es la parte de la respuesta que contiene los datos o el contenido devuelto por el servidor.

 return ResponseEntity.status(201).body("Created");:
 Esta línea de código crea una instancia de ResponseEntity con el código de estado 201 Created y el cuerpo de respuesta "Created".
 Luego, devuelve esta instancia como resultado del método POST. Esto significa que la respuesta generada por esta línea
 de código se enviará al cliente que realizó la solicitud.
 */