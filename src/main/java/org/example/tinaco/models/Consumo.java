package org.example.tinaco.models;

public class Consumo {
    private String fechaHora; // fecha + hora como string
    private int cantidad;

    public Consumo(String fechaHora, int cantidad) {
        this.fechaHora = fechaHora;
        this.cantidad = cantidad;
    }

    public String getFechaHora() {
        return fechaHora;
    }
    public int getCantidad() {
        return cantidad;
    }
}
