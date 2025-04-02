package com.example.PruebaCRUD.DTO;

import java.util.List;

public class CredencialResponseDTO {
    private String imagen;
    private List<CredencialDTO> credenciales;

    public CredencialResponseDTO(String imagen, List<CredencialDTO> credenciales) {
        this.imagen = imagen;
        this.credenciales = credenciales;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public List<CredencialDTO> getCredenciales() {
        return credenciales;
    }

    public void setCredenciales(List<CredencialDTO> credenciales) {
        this.credenciales = credenciales;
    }
}


