package com.example.PruebaCRUD.DTO;

import java.util.List;

public class ComparacionDTO {
    private boolean coinciden;
    private List<String> errores;

    public ComparacionDTO(boolean coinciden, List<String> errores) {
        this.coinciden = coinciden;
        this.errores = errores;
    }

    public boolean isCoinciden() {
        return coinciden;
    }

    public void setCoinciden(boolean coinciden) {
        this.coinciden = coinciden;
    }

    public List<String> getErrores() {
        return errores;
    }

    public void setErrores(List<String> errores) {
        this.errores = errores;
    }

    @Override
    public String toString() {
        return "ComparacionDTO{" +
                "coinciden=" + coinciden +
                ", errores=" + errores +
                '}';
    }
}
