package com.example.PruebaCRUD.DTO;

/**
 * Clase DTO la cuál sirve para pasar o recibir datos entre un cliente y un servidor, en este caso, ayudará a pasar
 * datos de los ETS
 */
public class ListadoUsuariosDTO {
    String usuario;
    String nombre;
    String tipo;

    // ==================== CONSTRUCTORES =====================
    public ListadoUsuariosDTO(String usuario, String nombre, String tipo) {
        this.usuario = usuario;
        this.nombre = nombre;
        this.tipo = tipo;
    }
    // ==================== SETTERS AND GETTERS ====================

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String gettipo() {
        return tipo;
    }

    public void settipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "ListadoUsuariosDTO{" +
                "usuario='" + usuario + '\'' +
                ", nombre='" + nombre + '\'' +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}
