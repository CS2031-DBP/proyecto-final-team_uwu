package com.example.demo.Estados.application;

import com.example.demo.Estados.domain.EstadoRespository;
import com.example.demo.Estados.domain.EstadosService;
import com.example.demo.Usuario.domain.UsuarioRepository;
import com.example.demo.Usuario.domain.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/estados")
public class EstadoController {

    @Autowired
    EstadoRespository estadoRespository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    EstadosService estadosService;













}
