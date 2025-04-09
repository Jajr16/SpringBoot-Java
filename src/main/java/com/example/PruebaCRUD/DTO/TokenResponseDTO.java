package com.example.PruebaCRUD.DTO;

public class TokenResponseDTO {
    private String message;
    private int status;

//    ============= CONSTRUCTOR =============
    public TokenResponseDTO(String message, int status) {
        this.message = message;
        this.status = status;
    }

//    ============== GETTERS AND SETTERS ==============

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "TokenResponse{" +
                "message='" + message + '\'' +
                ", status=" + status +
                '}';
    }
}
