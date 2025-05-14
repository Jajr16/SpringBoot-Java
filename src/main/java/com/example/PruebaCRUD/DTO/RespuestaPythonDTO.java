package com.example.PruebaCRUD.DTO;

public class RespuestaPythonDTO {
    private float presicion;
    private String detalles;

    // Constructor, getters y setters
    public RespuestaPythonDTO() {}

    public RespuestaPythonDTO(float presicion, String detalles) {
        this.presicion = presicion;
        this.detalles = detalles;
    }

    public float getPresicion() {
        return presicion;
    }

    public void setPresicion(float presicion) {
        this.presicion = presicion;
    }

    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }
}
