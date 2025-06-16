package org.example.tinaco.models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class TablaSensores {
    private final SimpleIntegerProperty numero;
    private final SimpleStringProperty nombre;

    public TablaSensores(int numero, String nombre) {
        this.numero = new SimpleIntegerProperty(numero);
        this.nombre = new SimpleStringProperty(nombre);
    }

    public int getNumero() {
        return numero.get();
    }
    public void setNumero(int numero) {
        this.numero.set(numero);
    }
    public String getNombre() {
        return nombre.get();
    }
    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

}
