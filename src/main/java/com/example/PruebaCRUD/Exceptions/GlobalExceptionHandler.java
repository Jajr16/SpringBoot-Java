package com.example.PruebaCRUD.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntime(RuntimeException ex) {
        Map<String, Object> datos = new HashMap<>();
        datos.put("Error", true);
        datos.put("message", ex.getMessage());
        return new ResponseEntity<>(datos, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneric(Exception ex) {
        Map<String, Object> datos = new HashMap<>();
        datos.put("Error", true);
        datos.put("message", "Error interno: " + ex.getMessage());
        return new ResponseEntity<>(datos, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
