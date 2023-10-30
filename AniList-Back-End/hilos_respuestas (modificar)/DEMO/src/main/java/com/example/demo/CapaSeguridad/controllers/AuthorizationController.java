package com.example.demo.CapaSeguridad.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class AuthorizationController {
    @GetMapping
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello World from Security!");
    }
}