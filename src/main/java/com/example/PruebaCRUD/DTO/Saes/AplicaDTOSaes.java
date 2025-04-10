package com.example.PruebaCRUD.DTO.Saes;

/**
 * Clase DTO la cuál sirve para pasar o recibir datos entre un cliente y un servidor, en este caso, ayudará a pasar
 * datos de la tabla Aplica
 */
public class AplicaDTOSaes {
    private Integer idETS;
    private String docenteCURP;
    private boolean titular;

     // ==================== CONSTRUCTORES =====================
    public AplicaDTOSaes(Integer idETS, String docenteCURP, boolean titular) {
        this.idETS = idETS;
        this.docenteCURP = docenteCURP;
        this.titular = titular;
    }

    // ==================== SETTERS AND GETTERS ====================
    public Integer getIdETS() {
        return idETS;
    }

    public void setIdETS(Integer idETS) {
        this.idETS = idETS;
    }

    public String getDocenteCURP() {
        return docenteCURP;
    }

    public void setDocenteCURP(String docenteCURP) {
        this.docenteCURP = docenteCURP;
    }

    public boolean isTitular() {
        return titular;
    }

    public void setTitular(boolean titular) {
        this.titular = titular;
    }

    @Override
    public String toString() {
        return "AplicaDTOSaes{" +
                "idETS=" + idETS +
                ", docenteCURP='" + docenteCURP + '\'' +
                ", titular=" + titular +
                '}';
    }
}
