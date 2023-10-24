package com.example.demo.Usuario;

import com.example.demo.CapaSeguridad.domain.Usuario;
import jakarta.persistence.EntityNotFoundException;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }



    public boolean existsUserByEmail(String email) {
        return usuarioRepository.findByEmail(email) != null;
    }

    public List<Usuario> getUsuarios(){
        return usuarioRepository.findAll();
    }
    public Usuario getUserById(Long id) {
        return usuarioRepository.findById(id).orElse(null);
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
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return usuarioRepository.findByEmail(username);
            }
        };
    }
    public void createUser(Usuario user) {
        usuarioRepository.save(user);
    }










}
