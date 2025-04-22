package com.example.PruebaCRUD.Exceptions;

public class ExcelProcessingException extends RuntimeException {
    public ExcelProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}