package com.example.PruebaCRUD.DTO;

/**
 * Clase DTO la cuál sirve para pasar o recibir datos entre un cliente y un servidor, en este caso, ayudará a pasar
 * los días que faltan para el periodo de ETS
 */
public class TimeToETSDTO {
    private String text;

    // ==================== CONSTRUCTORES =====================
    public TimeToETSDTO() {}

    public TimeToETSDTO(String text) {
        this.text = text;
    }

    // ==================== SETTERS AND GETTERS ====================
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "{" +
                "text='" + text + '\'' +
                '}';
    }
}
