package com.example.demo.CapaSeguridad.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(EmailAlreadyExitsException.class)
    protected ResponseEntity<ErrorMessage> conflicto_email(){
        ErrorMessage error =  new ErrorMessage(409,"El correo electrónico proporcionado ya está registrado.");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }
    @ExceptionHandler(UserAlreadyExistsException.class)
    protected ResponseEntity<ErrorMessage> conflicto_nickname(){
        ErrorMessage error = new ErrorMessage(409,"El nickname ya esta en uso. Por favor, elija un nickname diferente.");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }
    @ExceptionHandler(EmailPasswordException.class)
    protected ResponseEntity<ErrorMessage> conflicto_signin(){
        ErrorMessage error = new ErrorMessage(400,"Incorrect Email or Password");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}