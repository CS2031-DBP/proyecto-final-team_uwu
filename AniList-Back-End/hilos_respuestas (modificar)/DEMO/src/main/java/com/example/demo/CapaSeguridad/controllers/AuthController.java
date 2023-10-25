package com.example.demo.CapaSeguridad.controllers;

import com.example.demo.CapaSeguridad.domain.ResponseDTO;
import com.example.demo.CapaSeguridad.dto.JwtAuthenticationResponse;
import com.example.demo.CapaSeguridad.dto.SignUpRequest;
import com.example.demo.CapaSeguridad.dto.SigninRequest;
import com.example.demo.CapaSeguridad.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {
    private final AuthenticationService authenticationService;

    @Autowired
    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }


    @PostMapping("/signup")
    public ResponseEntity<ResponseDTO> signup(@RequestBody @Valid SignUpRequest request) {
        return ResponseEntity.ok(authenticationService.signup(request));
    }
    @PostMapping("/signin")
    public ResponseEntity<ResponseDTO>  signin(@RequestBody SigninRequest request) {
        return ResponseEntity.ok(authenticationService.signin(request));
    }
}
