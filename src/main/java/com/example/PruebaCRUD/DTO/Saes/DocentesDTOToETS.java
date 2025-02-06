package com.example.PruebaCRUD.DTO.Saes;

import com.example.PruebaCRUD.CryptAES.AES;

public class DocentesDTOToETS {
    private String curp;
    private String nombre;

    public DocentesDTOToETS(String curp, String nombre) throws Exception {
        this.curp = new AES().Encriptar(curp);
        this.nombre = nombre;
    }

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
