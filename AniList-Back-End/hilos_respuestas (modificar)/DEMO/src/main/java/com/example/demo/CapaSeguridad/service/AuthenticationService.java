package com.example.demo.CapaSeguridad.service;

import com.example.demo.CapaSeguridad.domain.ResponseDTO;
import com.example.demo.CapaSeguridad.domain.Role;
import com.example.demo.CapaSeguridad.dto.JwtAuthenticationResponse;
import com.example.demo.CapaSeguridad.dto.SignUpRequest;
import com.example.demo.CapaSeguridad.dto.SigninRequest;
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
    private final UsuarioService userService;

    @Autowired
    public AuthenticationService(UsuarioRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager,UsuarioService userService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    public ResponseDTO signup(SignUpRequest request) {
        if (userService.existsUserByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("El correo electrónico ya está registrado.");
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

    public ResponseDTO signin(SigninRequest request) throws IllegalArgumentException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            var user = userRepository.findByEmail(request.getEmail());
            var jwt = jwtService.generateToken(user);

            JwtAuthenticationResponse response = new JwtAuthenticationResponse();
            response.setToken(jwt);
            ResponseDTO info = new ResponseDTO();
            info.setId(user.getId());
            info.setNickName(user.getNickname());
            info.setToken(jwt);
            return info;
        } catch (BadCredentialsException e) {
            throw new IllegalArgumentException("Invalid email or password");
        } catch (Exception e) {
            throw new IllegalArgumentException("Bad request");
        }
    }

}