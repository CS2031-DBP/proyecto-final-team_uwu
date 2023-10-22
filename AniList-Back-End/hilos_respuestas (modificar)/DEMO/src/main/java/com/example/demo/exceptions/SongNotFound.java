package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Song not found")
public class SongNotFound extends RuntimeException{
}

// En httpStatus podemos fijarnos todos los estados disponibles
// Con el ResponseStatusException NO NECESITAMOS PONER ResponseStatus