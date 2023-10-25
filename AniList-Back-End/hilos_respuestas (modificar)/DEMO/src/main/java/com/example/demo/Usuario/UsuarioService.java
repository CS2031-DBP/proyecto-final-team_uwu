package com.example.demo.Usuario;

import com.example.demo.Hilos.HiloRepository;
import com.example.demo.Hilos.HiloService;
import com.example.demo.Respuesta.RespuestaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;


    public List<Usuario> getUsuarios(){
        return usuarioRepository.findAll();
    }
    public Usuario getUserById(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }
    public Optional<Usuario> getUserByIdOptional(Long id){
        return usuarioRepository.findById(id);
    }


    public List<Long> getFavoriteAnimeIdsByUser(@NotNull Usuario usuario) {
        return new ArrayList<>(usuario.getFavoriteAnimeIds());
    }

    public void deleteUserById(Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario == null) {
            throw new EntityNotFoundException("User not found");
        }

        usuarioRepository.delete(usuario);
    }

    public void createUser(Usuario user) {
        usuarioRepository.save(user);
    }










}
