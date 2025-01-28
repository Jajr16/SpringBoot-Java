package com.example.PruebaCRUD.DTO;

public class TimeToETSDTO {
    private String text;

    public TimeToETSDTO() {}

    public TimeToETSDTO(String text) {
        this.text = text;
    }

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
