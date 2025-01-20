package com.example.PruebaCRUD.DTO;

public class PersonaDTO {
    private String nombre;
    private String sexo;
    private String escuela;

    public PersonaDTO(String nombre, String sexo, String escuela) {
        this.nombre = nombre;
        this.sexo = sexo;
        this.escuela = escuela;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getEscuela() {
        return escuela;
    }

    public void setEscuela(String escuela) {
        this.escuela = escuela;
    }
}
