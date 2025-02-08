package com.example.PruebaCRUD.DTO.Saes;

import com.example.PruebaCRUD.CryptAES.AES;

/**
 * Clase DTO la cuál sirve para pasar o recibir datos entre un cliente y un servidor, en este caso, ayudará a pasar
 * datos de la tabla PersonalAcademico (en este caso un docente)
 */
public class DocentesDTOToETS {
    private String curp;
    private String nombre;

    // ==================== CONSTRUCTORES =====================
    public DocentesDTOToETS(String curp, String nombre) throws Exception {
        this.curp = new AES().Encriptar(curp);
        this.nombre = nombre;
    }

    // ==================== SETTERS AND GETTERS ====================
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) throws Exception {
        this.curp = this.curp = new AES().Encriptar(curp);
    }

    @Override
    public String toString() {
        return "{" +
                "nombre='" + nombre + '\'' +
                ", curp='" + curp + '\'' +
                '}';
    }
}
