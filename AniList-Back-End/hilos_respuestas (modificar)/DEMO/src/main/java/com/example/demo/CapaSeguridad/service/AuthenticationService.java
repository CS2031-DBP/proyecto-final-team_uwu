package com.example.demo.CapaSeguridad.service;

import com.example.demo.CapaSeguridad.domain.ResponseDTO;
import com.example.demo.CapaSeguridad.domain.Role;
import com.example.demo.CapaSeguridad.dto.JwtAuthenticationResponse;
import com.example.demo.CapaSeguridad.dto.SignUpRequest;
import com.example.demo.CapaSeguridad.dto.SigninRequest;
import com.example.demo.CapaSeguridad.exception.EmailAlreadyExitsException;
import com.example.demo.CapaSeguridad.exception.EmailPasswordException;
import com.example.demo.CapaSeguridad.exception.ErrorMessage;
import com.example.demo.CapaSeguridad.exception.UserAlreadyExistsException;
import com.example.demo.Usuario.domain.Usuario;
import com.example.demo.Usuario.domain.UsuarioRepository;
import com.example.demo.Usuario.domain.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Service
public class AuthenticationService {
    private final UsuarioRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;
    private final UsuarioService usuarioService;

    @Autowired
    public AuthenticationService(UsuarioRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager, UsuarioService userService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.usuarioService = userService;
    }

    public ResponseDTO signup(SignUpRequest request) {
        if (usuarioService.existsUserByEmail(request.getEmail())) {
            throw new EmailAlreadyExitsException();
        }
        if (usuarioService.existUserByNickname(request.getNickname())) {
            throw new UserAlreadyExistsException();
        }

        var user = new Usuario();
        user.setNickname(request.getNickname());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);

        userRepository.save(user);
        var jwt = jwtService.generateToken(user);

        JwtAuthenticationResponse response = new JwtAuthenticationResponse();
        response.setToken(jwt);
        ResponseDTO info = new ResponseDTO();
        info.setId(user.getId());
        info.setToken(jwt);
        return info;
    }

    public ResponseDTO signin(SigninRequest request) {
        var user = usuarioService.getUserByEmail(request.getEmail());
        var userDTO = new ResponseDTO();
        if(user.getImage_path() != null){
            userDTO.setImage_path("http://localhost:8080/usuarios/" + user.getId() + "/profile_picture");
        }
        if(user.getBackground_picture() != null){
            userDTO.setBackground_picture("http://localhost:8080/usuarios/" + user.getId() + "/banner_picture");
        }
        var jwt = jwtService.generateToken(user);
        JwtAuthenticationResponse response = new JwtAuthenticationResponse();
        response.setToken(jwt);
        ResponseDTO info = new ResponseDTO();
        info.setId(user.getId());
        info.setToken(jwt);
        info.setImage_path(userDTO.getImage_path());
        info.setNickName(user.getNickname());
        info.setBackground_picture(userDTO.getBackground_picture());
        return info;
    }

}
